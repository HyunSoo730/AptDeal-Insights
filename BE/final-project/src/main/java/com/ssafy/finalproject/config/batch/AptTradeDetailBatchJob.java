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
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AptTradeDetailBatchJob {

    private final WebClient webClient;
    private final PlatformTransactionManager transactionManager;
    private static final String SEOUL_BASE_URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
    private static final String ENCODED_API_KEY = "vk%2FAjAkbf0K4e9bDC7RWG%2B2uj9hSsRVSVOe4WtENZY1dLBUec1AyEgn9AnEPksMUKQ%2FvDw%2BlLuRgusRy5OOLfA%3D%3D";
//    private static final String SEOUL_API_KEY = URLDecoder.decode(ENCODED_API_KEY, StandardCharsets.UTF_8);
    //    private static final String API_KEY = URLDecoder.decode(ENCODED_API_KEY, StandardCharsets.UTF_8);


    @Bean
    public Job aptTradeDetailJob(JobRepository jobRepository) {
        log.info("********** 아파트 매매 상세자료 조회 배치 작업 실행 ***********");
        return new JobBuilder("aptTradeDetailJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(aptTradeDetailXmlParseingStep(jobRepository))
                .build();
    }

    @Bean
    public Step aptTradeDetailXmlParseingStep(JobRepository jobRepository){
        return new StepBuilder("aptTradeDetailXmlParsingStep", jobRepository)
                .<AptSaleDTO, AptSaleDTO>chunk(10, transactionManager)
                .reader(aptTradeDetailReader())
                .processor(aptTradeDetailProcessor()) // ! 현재 읽어오기만 한거라 processor 필요없음
                .writer(aptTradeDetailWriter())
                .build();
    }


    // ! XML로 파싱해서 읽어올 부분, WebClient는 OPEN API 호출할 때마다 URL 수정해야함 (mutate)
    @Bean
    public ItemReader<AptSaleDTO> aptTradeDetailReader() {
        return () -> {
            try {
                final String uri = String.format("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey=%s&pageNo=1&numOfRows=1&LAWD_CD=11110&DEAL_YMD=201512", ENCODED_API_KEY);

                AptSaleDTO response = webClient.get()
                        .uri(new URI(uri))
                        .accept(MediaType.APPLICATION_XML)
                        .retrieve()
                        .bodyToMono(AptSaleDTO.class)
                        .blockOptional()
                        .orElse(null);

                log.info("response = {}", response);
                return response;
            } catch (URISyntaxException e) {
                log.error("URI 구문 오류", e);
                return null;
            }
        };
    }


    // ! XML 파싱 후 aptCode 가공을 위해 -> ItemProcessor
        @Bean
    public ItemProcessor<AptSaleDTO, AptSaleDTO> aptTradeDetailProcessor() {
        return aptSaleDTO -> {
//            List<AptSaleDTO.Item> itemList = aptSaleDTO.getBody().getItems().getItemList();
//            itemList.forEach(item -> {
//                String aptCode = item.getLegalDongSigunguCode() + item.getLegalDongEubmyeondongCode()
//                        + item.getRoadNameBonbun() + item.getRoadNameBubun();
////                item.setAptCode(aptCode);
//            });
            return aptSaleDTO;
        };
    }


    // ! XML 파싱 후 제대로 확인하기 위해 ItemReader : 현재 미정
        @Bean
    public ItemWriter<AptSaleDTO> aptTradeDetailWriter() {
        return items -> {
            for (AptSaleDTO item : items) {
                log.info("AptSaleDTO = {}", item.toString());
            }
        };
    }

}