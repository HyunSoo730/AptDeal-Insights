package com.ssafy.finalproject.config.batch;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.ssafy.finalproject.aptsale.dto.request.AptRentDTO;
import com.ssafy.finalproject.aptsale.entity.AptRentSale;
import com.ssafy.finalproject.aptsale.repository.AptRentSaleRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
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

import java.io.StringReader;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AptRentDetailBatchJob {

    private final AptRentSaleRepository aptRentSaleRepository;

    private final WebClient webClient;
    private final PlatformTransactionManager transactionManager;
    private static final String APT_RENT_URL = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent";
    private static final String APT_RENT_API_KEY = "5Df9lwhanvnvBf%2FXh7YEOJNloxM8au1%2B94Owm%2BW%2BckaSFnIiGWN%2FLGvKdr3c7E8dX0UWbGOcHEA%2FE7K35%2ByfFw%3D%3D";

    // TODO : 배치
// *   ItemReader: AptRentDTO 객체를 WebClient로 읽어옴.
// *   ItemProcessor: AptRentDTO 객체를 받아서 List<AptRentSale>로 변환
// *   ItemWriter: List<AptRentSale> 리스트를 한 번에 저장.

    @Bean
    public Job aptRentDetailJob(JobRepository jobRepository) {
        log.info("***** 아파트 전월세 조회 배치 작업 실행 *****");
        return new JobBuilder("aptRentDetailJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(aptRentDetailStep(jobRepository))
                .build();
    }

    @Bean
    public Step aptRentDetailStep(JobRepository jobRepository) {
        return new StepBuilder("aptRentDetailStep", jobRepository)
                .<AptRentDTO, List<AptRentSale>>chunk(10, transactionManager)
                .reader(aptRentDetailReader())
                .processor(aptRentDetailProcessor())
                .writer(aptRentDetailWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<AptRentDTO> aptRentDetailReader() {
        return new ItemReader<AptRentDTO>() {
            private int index = 0;
            // ! 서울시 일부 지역에 대해서 진행.
            private String[] lawdCodes = {"11680", "11740", "11305", "11500", "11620", "11215", "11530"};

            @Override
            public AptRentDTO read() throws Exception {
                if (index >= lawdCodes.length) {
                    return null; // 모든 데이터를 읽었음을 의미
                }

                String lawdCd = lawdCodes[index];
                LocalDate now = LocalDate.now();
                String dealYmd = String.format("%d%02d", now.getYear(), now.getMonthValue());
                // ! http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?serviceKey=5Df9lwhanvnvBf%2FXh7YEOJNloxM8au1%2B94Owm%2BW%2BckaSFnIiGWN%2FLGvKdr3c7E8dX0UWbGOcHEA%2FE7K35%2ByfFw%3D%3D&LAWD_CD=11110&DEAL_YMD=202405
                String url = String.format("%s?serviceKey=%s&LAWD_CD=%s&DEAL_YMD=%s", APT_RENT_URL, APT_RENT_API_KEY, lawdCd, dealYmd);
                AptRentDTO response = webClient.get()
                        .uri(new URI(url))
                        .accept(MediaType.APPLICATION_XML)
                        .retrieve()
                        .bodyToMono(AptRentDTO.class)
                        .block();
                System.out.println("response = " + response.toString());

                if (response == null || response.getBody().getItems().getItemList().isEmpty()) {
                    index++;
                }

                return response;
            }
        };
    }


    @Bean
    public ItemProcessor<AptRentDTO, List<AptRentSale>> aptRentDetailProcessor() {
        return items -> {
            List<AptRentSale> sales = new ArrayList<>();
            if (items != null && items.getBody() != null && items.getBody().getItems() != null) {
                for (AptRentDTO.Item aptItem : items.getBody().getItems().getItemList()) {
                    sales.add(aptItem.toEntity());
                }
            }
            return sales;
        };
    }

    @Bean
    public ItemWriter<List<AptRentSale>> aptRentDetailWriter() {
        return items -> {
            for (List<AptRentSale> item : items) {
                aptRentSaleRepository.saveAll(item);
            }
        };
    }

}
