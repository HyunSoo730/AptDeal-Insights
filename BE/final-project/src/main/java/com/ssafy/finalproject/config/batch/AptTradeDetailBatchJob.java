package com.ssafy.finalproject.config.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ssafy.finalproject.aptsale.dto.request.AptSaleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AptTradeDetailBatchJob {

    private final WebClient webClient;
    private final PlatformTransactionManager transactionManager;
    private static final String SEOUL_BASE_URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
    private static final String ENCODED_API_KEY = "vk%2FAjAkbf0K4e9bDC7RWG%2B2uj9hSsRVSVOe4WtENZY1dLBUec1AyEgn9AnEPksMUKQ%2FvDw%2BlLuRgusRy5OOLfA%3D%3D";
    private static final String SEOUL_API_KEY = URLDecoder.decode(ENCODED_API_KEY, StandardCharsets.UTF_8);

    @Bean
    public Job aptTradeDetailJob(JobRepository jobRepository) {
        log.info("********** 아파트 매매 상세자료 조회 배치 작업 실행 ***********");
        return new JobBuilder("aptTradeDetailJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(aptTradeDetailStep(jobRepository))
                .next(aptCoordinateStep(jobRepository))
                .build();
    }

    @Bean
    public Step aptTradeDetailStep(JobRepository jobRepository) {
        return new StepBuilder("aptTradeDetailStep", jobRepository)
                .<AptSaleDTO, AptSaleDTO>chunk(10, transactionManager)
                .reader(aptTradeDetailReader())
                .writer(aptTradeDetailWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step aptCoordinateStep(JobRepository jobRepository) {
        return new StepBuilder("aptCoordinateStep", jobRepository)
                .<AptSaleDTO, AptSaleDTO>chunk(10, transactionManager)
                .reader(aptCoordinateReader())
                .processor(aptCoordinateProcessor())
                .writer(aptCoordinateWriter())
                .allowStartIfComplete(true)
                .build();
    }

    private Stream<String> generateYMDParameters() {
        return IntStream.rangeClosed(1, 17)
                .mapToObj(month -> {
                    int year = (month <= 12) ? 2023 : 2024;
                    int adjustedMonth = month % 12;
                    if (adjustedMonth == 0) {
                        adjustedMonth = 12;
                    }
                    return String.format("%d%02d", year, adjustedMonth);
                });
    }

    @Bean
    public ItemReader<AptSaleDTO> aptTradeDetailReader() {
        Iterator<String> ymdIterator = generateYMDParameters().iterator();
        return new ItemReader<AptSaleDTO>() {
            @Override
            public AptSaleDTO read() throws JsonProcessingException, URISyntaxException {
                if (ymdIterator.hasNext()) {
                    String ymd = ymdIterator.next();
                    final String uri = String.format("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey=%s&pageNo=1&numOfRows=40&LAWD_CD=11110&DEAL_YMD=%s", ENCODED_API_KEY, ymd);
                    String response = webClient.get()
                            .uri(new URI(uri))
                            .accept(MediaType.APPLICATION_XML)
                            .retrieve()
                            .bodyToMono(String.class)
                            .blockOptional().orElse("");

                    // XML 파싱 라이브러리를 사용하여 response를 AptSaleDTO로 변환
                    XmlMapper xmlMapper = new XmlMapper();
                    AptSaleDTO aptSaleDTO = xmlMapper.readValue(response, AptSaleDTO.class);
                    return aptSaleDTO;
                } else {
                    return null;
                }
            }
        };
    }

    @Bean
    public ItemWriter<AptSaleDTO> aptTradeDetailWriter() {
        return new ItemWriter<AptSaleDTO>() {
            @Override
            public void write(Chunk<? extends AptSaleDTO> items) throws Exception {
                StepExecution stepExecution = StepSynchronizationManager.getContext().getStepExecution();
                if (stepExecution != null) {
                    stepExecution.getJobExecution().getExecutionContext().put("aptSaleDTOList", new ArrayList<>(items.getItems()));
                }
            }
        };
    }

    @Bean
    public ItemReader<AptSaleDTO> aptCoordinateReader() {
        return new ItemReader<AptSaleDTO>() {
            private Iterator<AptSaleDTO> iterator;

            @Override
            public AptSaleDTO read() {
                if (iterator == null) {
                    List<AptSaleDTO> aptSaleDTOList = (List<AptSaleDTO>) StepSynchronizationManager.getContext()
                            .getStepExecution().getJobExecution().getExecutionContext().get("aptSaleDTOList");
                    iterator = aptSaleDTOList.iterator();
                }

                if (iterator.hasNext()) {
                    return iterator.next();
                } else {
                    return null;
                }
            }
        };
    }

    public String removeLeadingZeros(String code) {
        return code.replaceFirst("^0+", "");
    }

    @Bean
    public ItemProcessor<AptSaleDTO, AptSaleDTO> aptCoordinateProcessor() {
        return item -> {
            for (AptSaleDTO.Item myItem : item.getBody().getItemList()) {
                String roadName = myItem.getRoadName();
                String mainCode = removeLeadingZeros(myItem.getRoadNameBuildingMainCode());
                String subCode = removeLeadingZeros(myItem.getRoadNameBuildingSubCode());

                String address;
                if (!subCode.equals("0")) {
                    address = roadName + " " + mainCode + "-" + subCode;
                } else {
                    address = roadName + " " + mainCode;
                }

                String url = "https://api.vworld.kr/req/address?service=address&request=getcoord&version=2.0&crs=epsg:4326&address=" + address + "&refine=true&simple=false&format=xml&type=road&key=92B84E44-5710-3F5A-9AE1-6F3CF087E8EC";

                // WebClient를 사용하여 데이터 받아오기
                String response = webClient.get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_XML)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                // XML 파싱
                XmlMapper xmlMapper = new XmlMapper();
                JsonNode rootNode = xmlMapper.readTree(response);

                // 필요한 값 추출
                String text = rootNode.path("refined").path("text").asText();
                String x = rootNode.path("result").path("point").path("x").asText();
                String y = rootNode.path("result").path("point").path("y").asText();
                String detail = rootNode.path("refined").path("structure").path("detail").asText();

                /*// 추출한 값을 myItem에 설정
                myItem.setText(text);
                myItem.setX(x);
                myItem.setY(y);
                myItem.setDetail(detail);*/
                System.out.println(text+" "+x+" "+" "+y+" "+detail);
            }
            return item;
        };
    }

    @Bean
    public ItemWriter<AptSaleDTO> aptCoordinateWriter() {
        return items -> {
            for (AptSaleDTO item : items) {
                for (AptSaleDTO.Item myItem : item.getBody().getItemList()) {
                    System.out.println(myItem);
                }
            }
        };
    }
}