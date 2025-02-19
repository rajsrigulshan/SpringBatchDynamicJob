package com.example.BatchProcess.repository;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.BatchProcess.model.User;

@Repository
public class UserRepository {
    @Autowired 
    private DataSource dataSource;

    public  boolean isUserExists(int userId){
            JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
            String query="SELECT EXISTS (SELECT 1 FROM users WHERE userId = ?)";
            boolean result=jdbcTemplate.queryForObject(query,Boolean.class,userId);
            return result;
    }
    public List<Integer> validateChunkAgainstDatabaseRepo(List<User> chunk){ 

        List<Integer> chunkIds=chunk.stream().map(User :: getUserId).collect(Collectors.toList());

        String sql="SELECT distinct incoming_data.data_key " 
                    +"FROM (VALUES " 
                    + generateValuesClause(chunkIds) 
                    + ") AS incoming_data(data_key) " 
                    +"LEFT JOIN users usr ON incoming_data.data_key = usr.userid " 
                    +"WHERE usr.userid IS NULL"; 

        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        List<Integer> uniqueKeyList=(List<Integer>) jdbcTemplate.queryForList(sql,Integer.class);
        // Set<Integer> uniqueKeys=new HashSet<>(uniqueKeyList);
        for (Integer key : uniqueKeyList) {
          System.out.println("unique key: "+key);
        }
        return uniqueKeyList;

        
    }



      private String generateValuesClause(List<Integer> keys){
        return keys.stream()
                   .map(key -> "(" + key + ")")
                   .collect(Collectors.joining(", "));
    }
}
