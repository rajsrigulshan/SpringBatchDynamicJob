package com.example.BatchProcess.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.BatchProcess.model.User;
import com.example.BatchProcess.repository.UserRepository;

@Component
public class UserHelper {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> validateChunkAgainstDatabase(List<User>chunk){
        if(chunk.isEmpty())
            return null;

        List<Integer> uniqueUserIds= userRepository.validateChunkAgainstDatabaseRepo(chunk);
        
        return chunk.stream()
                .filter(user -> uniqueUserIds.contains(user.getUserId()))
                .collect(Collectors.toList());
    }

    public boolean userDataValidation(User user){
        if(user.equals(null) ||user.getUserAdd().isEmpty()||user.getUserContact().isEmpty()||user.getUserName().isEmpty()){
            return false;
        }else
        return true;
    }
}
