package com.example.BatchProcess.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.BatchProcess.helper.UserHelper;
import com.example.BatchProcess.model.User;

public class UserCustomReader  implements ItemReader<User>{

    private Logger logger=LoggerFactory.getLogger(UserCustomReader.class);

    private List<User> users;
    public UserCustomReader(List<User> users){
        logger.info("INFO: total number of users: {}",users.size());
        this.users=users;
    }


    @Autowired
    private UserHelper userHelper;
    @Value("${SpringBatchChunkSize}")
    private int chunkSize;
    int index=0;
    Queue<User> validUsersData;

    @BeforeStep
    public void createEmptyQueue(StepExecution stepExecution){
        validUsersData=new LinkedList<>();
    }

    @Override
    public User read(){
        if(validUsersData.isEmpty()){
            List<User> currentChunk=new ArrayList<>();
            int itemReadInCurrentChunk=0;
            while(index<users.size() && itemReadInCurrentChunk<chunkSize){
                currentChunk.add(users.get(index++));
                itemReadInCurrentChunk++;
            }
            if(currentChunk.isEmpty())
                return null;
        


        //validation against database  needs to be complete....
        validUsersData.addAll(userHelper.validateChunkAgainstDatabase(currentChunk));
            
        }
        return validUsersData.poll();
    }    
    
}




    // private List<User> users;
    // private int index = 0;
    // private Queue<User> usrs=new LinkedList<>();
    

    // public UserCustomReader(List<User> users) {
    //     this.users = users;
    //     logger.info("INFO: total number of users: {}",users.size());
    // }

    // @Override
    // public User read() throws Exception{
    //     if (index < users.size()) {
    //         return users.get(index++);
    //     }
    //     return null;
    // }


/*
  @Override
    public DataItem read() throws Exception {
        if (validItemsQueue.isEmpty()) {
            List<DataItem> currentChunk = new ArrayList<>();
            int itemsReadInChunk = 0;
            while (index < data.length && itemsReadInChunk < chunkSize) {
                String key = data[index++];
                String value = data[index++]; // Assuming key-value pairs
                currentChunk.add(new DataItem(key, value));
                itemsReadInChunk++;
            }

            if (currentChunk.isEmpty()) {
                return null; // End of data
            }

            List<DataItem> validChunk = validateChunkAgainstDatabase(currentChunk);
            validItemsQueue.addAll(validChunk);
        }
        return validItemsQueue.poll();
    }
 */
