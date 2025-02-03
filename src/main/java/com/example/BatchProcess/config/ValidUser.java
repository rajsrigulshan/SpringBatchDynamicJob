package com.example.BatchProcess.config;

import java.io.InvalidObjectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.BatchProcess.model.User;



public class ValidUser implements ItemProcessor<User,User>{

    private Logger loggger=LoggerFactory.getLogger(ValidUser.class);

    @Override
    public User process(User item) throws Exception {
        

            if(item.getUserContact().isEmpty() || item.getUserAdd().isEmpty() || item.getUserName().isEmpty()){
                loggger.info("ERROR: required field missing for id: {}",item.getUserId());
                 throw new InvalidObjectException("Invalid object: required field missing");
            }  
            else{   
                return item;
            }
        
    }

}
