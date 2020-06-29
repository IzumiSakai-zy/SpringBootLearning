package com.example.demo.mapper;

import com.example.demo.entities.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountMapper {
    List<Account> findAll();
    Account findById(Integer id);
    void insert(Account account);
    void delete(Integer id);
    void update(Account account);
}
