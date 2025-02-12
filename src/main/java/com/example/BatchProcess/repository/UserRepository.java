package com.example.BatchProcess.repository;


import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
