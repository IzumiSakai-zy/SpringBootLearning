package com.demo.repository;


import com.demo.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@Mapper
public interface AccountDao {
    List<Account> findAll();

    Account findById(Integer id);

    void add(Account account);

    void deleteById(Integer id);

    void update(Account account);
}
