package com.example.demo.mapper;

import com.example.demo.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    List<User> findAll();
    User findById(Integer id);
    void insert(User user);
    void delete(Integer id);
    void update(User user);
}
