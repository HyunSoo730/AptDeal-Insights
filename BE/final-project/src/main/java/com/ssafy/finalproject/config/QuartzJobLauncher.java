package com.ssafy.finalproject.config;

import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@RequiredArgsConstructor
public class QuartzJobLauncher extends QuartzJobBean {

    private final JobLauncher jobLauncher;

    private final Job aptRentDetailJob;

    private final Job aptSchoolInfoBatchJob;

    private final Job aptTradeDetailJob;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addLong("time", System.currentTimeMillis());
            jobLauncher.run(aptRentDetailJob, jobParametersBuilder.toJobParameters());
            jobLauncher.run(aptSchoolInfoBatchJob, jobParametersBuilder.toJobParameters());
            jobLauncher.run(aptTradeDetailJob, jobParametersBuilder.toJobParameters());
        } catch (Exception e) {
            throw new JobExecutionException("Failed to execute job", e);
        }
    }
}