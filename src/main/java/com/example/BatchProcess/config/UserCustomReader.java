package com.example.BatchProcess.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;


import com.example.BatchProcess.helper.UserHelper;
import com.example.BatchProcess.model.User;



public class UserCustomReader  implements ItemReader<User>{

    private Logger logger=LoggerFactory.getLogger(UserCustomReader.class);

    private List<User> users;
    private UserHelper userHelper;
    private int chunkSize=5;
    public UserCustomReader(List<User> users,UserHelper userHelper,int chunkSize){
        logger.info("INFO: total number of users: {}",users.size());
        this.users=users;
        this.userHelper=userHelper;
        this.chunkSize=chunkSize;
    }
    
    int index=0;
    Queue<User> validUsersData;

    @BeforeStep
    public void createEmptyQueue(StepExecution stepExecution){
        validUsersData=new LinkedList<>();
    }

    @Override
    public User read(){
        while(index<users.size() && validUsersData.isEmpty()){

            if(validUsersData.isEmpty()){
                List<User> currentChunk=new ArrayList<>();
                int itemReadInCurrentChunk=0;
                while(index<users.size() && itemReadInCurrentChunk<chunkSize){
                    currentChunk.add(users.get(index++));
                    itemReadInCurrentChunk++;
                }
              
            //validation against database
            validUsersData.addAll(userHelper.validateChunkAgainstDatabase(currentChunk));    
            }
                
        }
        
        return validUsersData.poll();
    }    
    
}