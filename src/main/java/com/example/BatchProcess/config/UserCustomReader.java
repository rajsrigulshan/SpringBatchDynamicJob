package com.example.BatchProcess.config;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.example.BatchProcess.model.User;

public class UserCustomReader  implements ItemReader<User>{

    private Logger logger=LoggerFactory.getLogger(UserCustomReader.class);

    // private Iterator<User> userIterator;

    // public UserCustomReader(List<User> users){
    //     System.out.println("size of users in userCustomReader class: "+users.size());
    //     this.userIterator=users.iterator();
    // }

    // @Override
    // public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    //     if (userIterator.hasNext()) {
    //         return userIterator.next();
    //     }
    //     return null;



    //     // throw new UnsupportedOperationException("Unimplemented method 'read'");
    // }


    private List<User> users;
    private int index = 0;

    public UserCustomReader(List<User> users) {
        this.users = users;
        logger.info("INFO: total number of users: {}",users.size());
    }

    @Override
    public User read() {
        if (index < users.size()) {
            return users.get(index++);
        }
        return null;
    }
    
}
