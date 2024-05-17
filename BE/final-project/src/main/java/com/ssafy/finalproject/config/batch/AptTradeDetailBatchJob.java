package com.ssafy.finalproject.config.batch;

import com.ssafy.finalproject.aptsale.dto.request.AptSaleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AptTradeDetailBatchJob {

    private final WebClient webClient;
    private final PlatformTransactionManager transactionManager;
    private static final String ENCODED_API_KEY = "vk%2FAjAkbf0K4e9bDC7RWG%2B2uj9hSsRVSVOe4WtENZY1dLBUec1AyEgn9AnEPksMUKQ%2FvDw%2BlLuRgusRy5OOLfA%3D%3D";
    private static final String APT_DEAL_URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";

    @Bean
    public Job aptTradeDetailJob(JobRepository jobRepository) {
        log.info("********** 아파트 매매 상세자료 조회 배치 작업 실행 ***********");
        return new JobBuilder("aptTradeDetailJob", jobRepository)
                .start(aptTradeDetailXmlParsingStep(jobRepository))
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step aptTradeDetailXmlParsingStep(JobRepository jobRepository) {
        return new StepBuilder("aptTradeDetailXmlParsingStep", jobRepository)
                .<AptSaleDTO, AptSaleDTO>chunk(10, transactionManager)
                .reader(aptTradeDetailReader())
                .processor(aptTradeDetailProcessor())
                .writer(aptTradeDetailWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<AptSaleDTO> aptTradeDetailReader() {
        return new ItemReader<AptSaleDTO>() {
            private final String[] lawdCds = {"11110", "26110", "28110"}; // 지역코드 배열 (서울특별시, 대구광역시, 인천광역시 등)
            private int lawdCdIndex = 0;
            private String currentLawdCd = lawdCds[lawdCdIndex];
            private int pageNo = 1;
            private final int numOfRows = 20; // 한 페이지 결과 수
            private String dealYmd = String.valueOf(LocalDate.now().getMonth()) + String.valueOf(LocalDate.now().getDayOfMonth()); // 초기 계약월
            // ! pageNo, numOfRows, LAWD_CD, DEAL_YMD 동적 설정
            @Override
            public AptSaleDTO read() throws Exception {
                while (true) {
                    final String uri = String.format("%s?serviceKey=%s&pageNo=%d&numOfRows=%d&LAWD_CD=%s&DEAL_YMD=%s",
                            APT_DEAL_URL,ENCODED_API_KEY, pageNo, numOfRows, currentLawdCd, dealYmd);

                    AptSaleDTO response = webClient.get()
                            .uri(new URI(uri))
                            .accept(MediaType.APPLICATION_XML)
                            .retrieve()
                            .bodyToMono(AptSaleDTO.class)
                            .blockOptional()
                            .orElse(null);

                    if (response == null || response.getBody() == null || response.getBody().getItems() == null) {
                        // 다음 지역코드로 이동
                        lawdCdIndex++;
                        if (lawdCdIndex >= lawdCds.length) {
                            // 모든 지역코드 처리 완료
                            return null;
                        }
                        currentLawdCd = lawdCds[lawdCdIndex]; // ! 현재 지역 코드.
                        pageNo = 1;
                        dealYmd = String.valueOf(LocalDate.now().getMonth()) + String.valueOf(LocalDate.now().getDayOfMonth()); // 초기 계약월로 초기화
                        continue;
                    }

                    List<AptSaleDTO.Item> itemList = response.getBody().getItems().getItemList();
                    if (itemList.isEmpty()) {
                        // 이전 계약월로 이동
                        dealYmd = getPreviousDealYmd(dealYmd);
                        if (dealYmd == null) {
                            // 다음 지역코드로 이동
                            lawdCdIndex++;
                            if (lawdCdIndex >= lawdCds.length) {
                                // 모든 지역코드 처리 완료
                                return null;
                            }
                            currentLawdCd = lawdCds[lawdCdIndex];
                            pageNo = 1;
                            dealYmd = String.valueOf(LocalDate.now().getMonth()) + String.valueOf(LocalDate.now().getDayOfMonth()); // 초기 계약월로 초기화
                            continue;
                        }
                        pageNo = 1;
                    } else {
                        pageNo++;
                        return response;
                    }
                }
            }

            private String getPreviousDealYmd(String dealYmd) {
                // 이전 계약월 반환 로직 구현
                int year = Integer.parseInt(dealYmd.substring(0, 4));
                int month = Integer.parseInt(dealYmd.substring(4));
                month--;
                if (month < 1) {
                    year--;
                    if (year < 2019) {
                        return null; // 2019년 이전은 제외
                    }
                    month = 12;
                }
                return String.format("%04d%02d", year, month);
            }
        };
    }

    @Bean
    public ItemProcessor<AptSaleDTO, AptSaleDTO> aptTradeDetailProcessor() {
        return aptSaleDTO -> {
            List<AptSaleDTO.Item> itemList = aptSaleDTO.getBody().getItems().getItemList();
            itemList.forEach(item -> {
                String aptCode = item.getLegalDongSigunguCode() + item.getLegalDongEubmyeondongCode()
                        + item.getRoadNameBonbun() + item.getRoadNameBubun();
                item.setAptCode(aptCode);
            });
            return aptSaleDTO;
        };
    }

    @Bean
    public ItemWriter<AptSaleDTO> aptTradeDetailWriter() {
        return items -> {
            for (AptSaleDTO item : items) {
                log.info("AptSaleDTO = {}", item.toString());
            }
        };
    }

}