package com.ssafy.finalproject.config.batch;


import com.ssafy.finalproject.aptshool.dto.AptSchoolInfoDTO;
import com.ssafy.finalproject.aptshool.entity.AptSchoolInfo;
import com.ssafy.finalproject.aptshool.repository.AptSchoolInfoRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AptSchoolBatchJob {

    private final AptSchoolInfoRepository aptSchoolInfoRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job aptSchoolInfoBatchJob(JobRepository jobRepository) {
        return new JobBuilder("aptSchoolInfoBatchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(aptSchoolInfoStep(jobRepository))
                .build();
    }

    @Bean
    public Step aptSchoolInfoStep(JobRepository jobRepository) {
        return new StepBuilder("aptSchoolInfoStep", jobRepository)
                .<AptSchoolInfoDTO, AptSchoolInfo>chunk(100, transactionManager)
                .reader(aptSchoolInfoReader())
                .processor(aptSchoolInfoProcessor())
                .writer(aptSchoolInfoWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<AptSchoolInfoDTO> aptSchoolInfoReader() {
        return new FlatFileItemReaderBuilder<AptSchoolInfoDTO>()
                .name("aptSchoolInfoReader")
                .resource(new FileSystemResource("C:\\data\\school_info.csv"))
                .delimited()
                .names("id", "aptName", "roadNameAddress", "legalDong", "longitude", "latitude",
                        "middleEduNm", "middleHakgudoNm", "highEduNm", "highHakgudoNm", "lowEduNm", "lowHakgudoNm")
                .linesToSkip(1)
                .encoding("UTF-8") // ! 코딩 설정 추가
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(AptSchoolInfoDTO.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<AptSchoolInfoDTO, AptSchoolInfo> aptSchoolInfoProcessor() {
        return aptSchoolInfoDTO -> {
            String aptCode = generateAptCode(aptSchoolInfoDTO.getRoadNameAddress(), aptSchoolInfoDTO.getLegalDong());
//            log.info("apt = {}", aptSchoolInfoDTO);
            return AptSchoolInfo.builder()
                    .aptName(aptSchoolInfoDTO.getAptName())
                    .roadNameAddress(aptSchoolInfoDTO.getRoadNameAddress())
                    .legalDong(aptSchoolInfoDTO.getLegalDong())
                    .longitude(aptSchoolInfoDTO.getLongitude())
                    .latitude(aptSchoolInfoDTO.getLatitude())
                    .middleEduNm(aptSchoolInfoDTO.getMiddleEduNm())
                    .middleHakgudoNm(aptSchoolInfoDTO.getMiddleHakgudoNm())
                    .highEduNm(aptSchoolInfoDTO.getHighEduNm())
                    .highHakgudoNm(aptSchoolInfoDTO.getHighHakgudoNm())
                    .lowEduNm(aptSchoolInfoDTO.getLowEduNm())
                    .lowHakgudoNm(aptSchoolInfoDTO.getLowHakgudoNm())
                    .aptCode(aptCode)
                    .build();
        };
    }

    @Bean
    public JpaItemWriter<AptSchoolInfo> aptSchoolInfoWriter() {
        return new JpaItemWriterBuilder<AptSchoolInfo>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    private String generateAptCode(String roadNameAddress, String legalDong) {
        String[] addressParts = roadNameAddress.split(" ");
        String lastPart = addressParts[addressParts.length - 1];
        String[] codeParts = lastPart.split("-");

        String aptCode = legalDong;
        for (String part : codeParts) {
            try {
                aptCode += String.format("%05d", Integer.parseInt(part));
            } catch (NumberFormatException e) {
                // 파싱 오류 발생 시 기본값 또는 에러 처리 로직 추가
                aptCode += "00000";
            }
        }
        if (codeParts.length == 1) {
            aptCode += "00000";
        }

        return aptCode;
    }

}
