package com.example.BatchProcess.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class JobCompletionNotiImpl implements JobExecutionListener {

    private Logger logger =LoggerFactory.getLogger(JobCompletionNotiImpl.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
       logger.info("Job Started");
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        if(jobExecution.getStatus()==BatchStatus.COMPLETED){
            logger.info("Job Completed");
        }
    }
        
}
