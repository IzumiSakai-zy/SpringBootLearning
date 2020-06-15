# SpringBootLearning
学习springboot
*********************************
### 微服务简介

* 微服务：一个应用由很多个小型服务组成，每个小型服务运行在各自的进程之下，进程之间通过http协力通信。其中每个小型服务都是可以独立升级和独立替换的单元。
* 缺点：部署、运维困难

***********

### 环境搭建

* 版本 

  * jdk -1.8

  * maven -3.3+

  * Idea - 2017.9

  * springboot - 1.5.9
  
* 修改maven使用的java编译版本

  * ```xml
    <profile>
        <id>jdk-1.8</id>
        <activation>
            <activeByDefault>true</activeByDefault>
            <jdk>1.8</jdk>
        </activation>
        <properties>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
            <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
        </properties>
    </profile>
    ```

****************
### 入门HelloWorld

* 创建一个无骨架的maven项目

* 导入springboot相关的依赖

  * ```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>
    <!-- web支持，SpringMVC， Servlet支持等 -->
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
    ```
  
* 编写一个HelloMain类，用于启动springboot应用

  * ```java
    @SpringBootApplication//标注一个主程序类，说明这是一个springboot应用
    public class HelloWorld {
        public static void main(String[] args) {
            SpringApplication.run(HelloWorld.class,args);
        }
    }
    ```
  
* 编写HelloController类

  * ```java
    @Controller
    public class HelloController {
        @ResponseBody
        @RequestMapping("/hello")
        public String hello(){
            return "HelloWorld";
        }
    }
    ```
  
* 在浏览器的地址栏输入**localhost:8080\\hello**。其中hello是@RequestMapping("/hello")里的值

***************
### 简化部署
* 下面这个插件可以把应用打包成一个jar包。使用方法是在maven的Lifecycle里点击package按钮，就会打成jar包，可以直接使用

    ```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    ```

*******************

### 原理探究

* `spring-boot-starter-parent`的父项目依赖定义了spring可能用到的几乎所有的依赖版本。由爷项目来真正管理应用里面的所有依赖版本，以后导入依赖几乎都可以不写版本。

  ```xml
  <properties>
     <!-- Dependency versions -->
     <activemq.version>5.14.5</activemq.version>
     <antlr2.version>2.7.7</antlr2.version>
     <appengine-sdk.version>1.9.59</appengine-sdk.version>
     <artemis.version>1.5.5</artemis.version>
     <aspectj.version>1.8.13</aspectj.version>
      <!--省略很多，没有复制完-->
  </properties>
  ```
  
* `spring-boot-starter`: springboot的场景启动器

* `spring-boot-starter-web`: 负责导入与web应用有关的依赖。最后后缀不一样功能类似

* 主类入口的注释`@SpringBootApplication` 

  * 里面嵌套包含了很多注解，涉及spring的底层注解
  * 注：其中有一个注解作用是扫描入口主类所在包及其子包下的所有注解。因此入口主类只能写在根包下。
  * 里面还会给spring核心容器导入很多自动配置类 