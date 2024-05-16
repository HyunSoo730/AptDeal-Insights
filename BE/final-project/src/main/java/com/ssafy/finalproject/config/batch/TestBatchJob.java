//package com.ssafy.finalproject.config.batch;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class TestBatchJob extends DefaultBatchConfiguration {
//
//    private final WebClient webClient;
//    private static String API_KEY = "644c53536f68797532326271456955";
//
//    @Bean
//    public Job testJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        log.info("******** 현재 testJob 실행 *************");
//        return new JobBuilder("testJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(testStep(jobRepository, transactionManager))
//                .build();
//    }
//
//    @Bean
//    public Step testStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("testStep", jobRepository)
//                .tasklet(testTasklet(), transactionManager)
//                .build();
//    }
//
//    public Tasklet testTasklet() {
//        return (((contribution, chunkContext) -> {
//            System.out.println("배치 테스트");
//            return RepeatStatus.FINISHED;
//        }));
//    }
//
//}
