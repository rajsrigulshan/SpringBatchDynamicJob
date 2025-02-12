package com.example.BatchProcess.service;

import java.util.List;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BatchProcess.config.BatchConfig;
import com.example.BatchProcess.model.User;

@RestController
@RequestMapping("/user")
public class UserService {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private BatchConfig batchConfig;

    @PostMapping("/saveUsers")
    public ResponseEntity<String> saveUserDetails(@RequestBody List<User> users)
            throws JobRestartException, JobParametersInvalidException, JobInstanceAlreadyCompleteException {
        // List<User> uniqueUsers=removeDupicates(users);

        try {
            Step steps= batchConfig.steps(users);
            Job job=new JobBuilder("Dynamic Job",batchConfig.jobRepository)
                        .listener(batchConfig.listener)
                        .start(steps)
                        .build();
            System.out.println(job.toString());

            JobParameters jobParameters= batchConfig.createJobParameters();
            System.out.println(jobParameters.toString());
            jobLauncher.run(job, jobParameters);
            return ResponseEntity.status(200).body("Users saved successfully...");
        } catch (Exception e) {
        }
        return ResponseEntity.status(400).body("Something went wrong");

    }

}
