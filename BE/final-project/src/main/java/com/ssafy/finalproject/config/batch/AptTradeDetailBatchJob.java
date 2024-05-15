package com.ssafy.finalproject.config.batch;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ssafy.finalproject.aptsale.dto.request.AptSaleDTO;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepository;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AptTradeDetailBatchJob {

    private final WebClient webClient;
    private final PlatformTransactionManager transactionManager;
//    private final AptSaleRepository aptSaleRepository;
    private static final String SEOUL_BASE_URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
    private static final String ENCODED_API_KEY = "jMJCTIZSOT9a34dKu0QVnzV4psUhzFnra60JbkjydQelnAYlftbQs1tTrEMN3AmKxjhChrcKi9eaBUGwirsXGw%3D%3D";
    private static final String SEOUL_API_KEY = URLDecoder.decode(ENCODED_API_KEY, StandardCharsets.UTF_8);

    @Bean
    public Job aptTradeDetailJob(JobRepository jobRepository) {
        log.info("********** 아파트 매매 상세자료 조회 배치 작업 실행 ***********");
        return new JobBuilder("aptTradeDetailJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(aptTradeDetailStep(jobRepository))
                .build();
    }

    @Bean
    public Step aptTradeDetailStep(JobRepository jobRepository) {
        return new StepBuilder("aptTradeDetailStep", jobRepository)
                .<AptSaleDTO, AptSaleDTO>chunk(10, transactionManager)
                .reader(aptTradeDetailReader())
//                .processor(aptTradeDetailProcessor())
                .writer(aptTradeDetailWriter())
                .build();
    }

    @Bean
    public ItemReader<AptSaleDTO> aptTradeDetailReader() {
        return () -> {
            String response = webClient.mutate()
                    .baseUrl(SEOUL_BASE_URL)
                    .build()
                    .get().uri(uriBuilder ->
                            uriBuilder.queryParam("serviceKey", SEOUL_API_KEY)
                                    .queryParam("pageNo", "1")
                                    .queryParam("numOfRows", "10")
                                    .queryParam("LAWD_CD", "11110")
                                    .queryParam("DEAL_YMD", "201510")
                                    .build())
                    .accept(MediaType.APPLICATION_XML)
                    .retrieve()
                    .bodyToMono(String.class)
                    .blockOptional().orElse("");

            System.out.println("response = " + response);
            // XML 파싱 라이브러리를 사용하여 response를 AptSaleDTO로 변환
            XmlMapper xmlMapper=new XmlMapper();
            AptSaleDTO aptSaleDTO = xmlMapper.readValue(response, AptSaleDTO.class);
            System.out.println("aptSaleDTO = " + aptSaleDTO);
            return aptSaleDTO;
        };
    }

    @Bean
    public ItemProcessor<AptSaleDTO, AptSaleDTO> aptTradeDetailProcessor() {
        return item -> {
            // aptCode 가공 및 필드 매핑
//            item.setAptCode(generateAptCode(item));
            // 필요한 경우 추가 데이터 가공 및 유효성 검사 수행
            return item;
        };
    }
//
//    private String generateAptCode(AptSaleDTO item) {
//        // aptCode 생성 로직 구현
//        // ...
//        return aptCode;
//    }

    @Bean
    public ItemWriter<AptSaleDTO> aptTradeDetailWriter() {
        return items -> {
            for (AptSaleDTO item : items) {
                System.out.println(item);
            }
        };
    }
}