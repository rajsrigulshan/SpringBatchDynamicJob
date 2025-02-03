package com.example.BatchProcess.config;

import java.io.InvalidObjectException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.example.BatchProcess.model.User;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    public  JobRepository jobRepository;
    @Autowired
    public JobCompletionNotiImpl listener;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private DataSource dataSource;

    
    public  Job  jobBean(){
        System.out.println("job bean creation");
        return new  JobBuilder("job new", jobRepository)
                    .listener(listener)
                    .start(steps(null))
                    .build();
    }

    
    public Step steps(List<User>users){
        return new StepBuilder("jobStep", jobRepository)
        .<User,User>chunk(5000,transactionManager)
        .reader(userReader(users))
        .processor(itemProcessor())
        .writer(itemWriter())
        .faultTolerant()
        .skip(InvalidObjectException.class)
        .skip(DuplicateKeyException.class)
        .skipLimit(100000)
        .build();
    }

        

    //reader
    // @Bean
    public ItemReader<User> userReader(List<User> users){        
        return new UserCustomReader(users);
    }

    // @Bean
    public ItemProcessor<User,User> itemProcessor(){
        return new ValidUser();
    }
    

    @Bean
    public ItemWriter<User> itemWriter(){
        return new JdbcBatchItemWriterBuilder<User>()
        .sql("insert into users(userId,userName,userAdd,userContact) values(:userId,:userName,:userAdd,:userContact)")
        .dataSource(dataSource)
        .beanMapped()
        .build();
    }


    public JobParameters createJobParameters() {
        return new JobParametersBuilder()
                .addString("timestamp", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
    }




}
