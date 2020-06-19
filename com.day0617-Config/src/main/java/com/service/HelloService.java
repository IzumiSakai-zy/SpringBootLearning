package com.service;

public class HelloService {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HelloService{" +
                "id=" + id +
                '}';
    }
}
