//package com.ssafy.finalproject.config.batch;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class SeoulApartmentDealBatchJob extends DefaultBatchConfiguration {
//
//    private final WebClient webClient;
//    private static String API_KEY = "b8WBTDBkKwwfM91RmzwZhEzaEfNsErOB%2FM5DIs2V5CNPFuGLEaAFBERwBbDVWkhb44t%2BvkOrAbdD7QCwEw7Ovg%3D%3D";
//
//    @Bean
//    public Job seoulApartmentDealJob(JobRepository jobRepository, Step seoulApartmentDealStep) {
//        log.info("********** 서울 아파트 실거래가 데이터 배치 작업 실행 ***********");
//        return new JobBuilder("seoulApartmentDealJob", jobRepository)
//                .start(seoulApartmentDealStep)
//                .build();
//    }
//
//    @Bean
//    public Step seoulApartmentDealStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("seoulApartmentDealStep", jobRepository)
//                .<Map<String, Object>, Map<String, Object>>chunk(10, transactionManager)
//                .reader(seoulApartmentDealReader())
//                .processor(seoulApartmentDealProcessor())
//                .writer(seoulApartmentDealWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Map<String, Object>> seoulApartmentDealReader() {
//        return () -> {
//            Map<String, Object> response = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//
//                            .build(API_KEY, 1, 5))
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
//                    .block();
//            log.info("받아온 데이터 : {}", response);
//            return response;
//        };
//    }
//
//    @Bean
//    public ItemProcessor<Map<String, Object>, Map<String, Object>> seoulApartmentDealProcessor() {
//        return item -> {
//            // 필요한 데이터 가공 또는 필터링 로직 구현
//            log.info("item : {}", item);
//            return item;
//        };
//    }
//
//    @Bean
//    public ItemWriter<Map<String, Object>> seoulApartmentDealWriter() {
//        log.info("******Writer 호출*********");
//        return items -> {
//            for (Map<String, Object> item : items) {
////                System.out.println(item);
//                log.info("ITEM : {}", item);
//            }
//        };
//    }
//}
