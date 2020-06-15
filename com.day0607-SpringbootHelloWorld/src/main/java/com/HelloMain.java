package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//标注一个主程序类，说明这是一个springboot应用
public class HelloMain {
    public static void main(String[] args) {
        SpringApplication.run(HelloMain.class,args);
    }
}
