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
    @Controller//IOC注入spring容器
    public class HelloController {
        @ResponseBody//说明这是返回体
        @RequestMapping("/hello")
        public String hello(){
            return "HelloWorld";
        }
    }
    ```
  
* 在浏览器的地址栏输入**localhost:8080/hello**。其中hello是@RequestMapping("/hello")里的值

* bug —— Failed to start connector [Connector[HTTP/1.1-8080]]。原因是Tomcat的端口号被占用

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

* `@SpringBootApplication` 注解

  * 位置：入口主类
  
  * 里面嵌套包含了很多注解，涉及spring的底层注解
  * 注：其中有一个注解作用是扫描入口主类所在包及其子包下的所有注解。因此入口主类只能写在根包下。
  * 里面还会给spring核心容器导入很多自动配置类 
  * 嵌套注解解释
    * `@EnableAutoConfigrution` : springboot帮忙自动配置。这个注解下还嵌套有@Import注解，即确认扫描范围是主配置类所在包及其子包
  
* `@ResponseBody`注解可以不放在方法上而直接放在类上。代表这个类的所有方法都能返回数据，即使是对象也能返回Jason数据

* `@RestController`是`@ResponseBody和@Controller`的合体

*********************

### resources文件夹目录结构

* **static** —— 保存的是静态资源（js、css、image）
* **templates** —— 保存所有的模板页面（springboot默认jar包使用嵌入式的Tomcat，默认不支持JSP）；可以使用模板引擎（freemaker、thymeleaf）
* **application.properties文件** —— springboot的应用配置文件
  * 如在文件中配置: server.port=8081

**************

### 配置文件

* 默认配置文件。名字是固定的，作用是修改springboot配置的默认值，一般放在resources目录或者类路径的config文件夹下
  * application.yml
  * application.properties
  
* yml

  * 简介：以数据为中心，比XML更优秀，更适合作为配置文件

  * 例子：

    ```yaml
    server:
      port: 8081
    ```
    
  * 语法
  
    * 基本语法：
      * "k: v"代表一个键值对，中间的空格不能省略
      * 整体以空格来缩进来控制层级关系，只要左对齐就是同一层级
      * 区分大小写
      
    * 值的表示
    
      * 基础数据类型直接写，且字符串默认不用加引号（如果使用单引号内'\n'为字符串，双引号内"\n"为换行）
    
      * 对象和map（键值对形）
    
        ```yaml
        friend: 
            name: 小明 
            age: 30
        	
        friend: {name: IzumiSakai,age: 30}
        ```
        
      * 数组
      
        ```yaml
        pets:
           - cat
           - dog
         
        pets: [cat,dog]
        ```
  
* yml下配置javabean对象

  * 导入依赖

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
    ```
    
  * 编写javabean对象
  
    * 类上加入`@ConfigurationProperties(prefix = "person")和@Component`注解，其中person值为yml文件中的配置的名字
    * @ConfigurationProperties注解的作用是与yml配置文件相关联形成映射绑定
    * @Component注解作用是加入spring容器，只有加入了容器上面的注解才能生效
  
  * 配置yml文件
  
    ```yaml
    person:
      name: IzumiSakai
      age: 40
      subjects: {数学: true,语文: false}
      pets: [dog,cat]
    ```
    * 注意：键的值可以写"lastName"，也可以写"last-name"
  * 编写测试类进行访问测试，其中Person对象采用spring自动注入的方式进行，且Autowired的对象的位置只能是类的成员，不能是局部变量
  
* properties文件配置Javabean

  ```properties
  person.name=IzumiSakai
  person.age=40
  person.subjeets.数学=true
  person.subjects.语文=false
  person.pets=dog,cat
  ```

  * 其他步骤类似
  
* `@Value`注解配置Javabean

  * @Value是spring的注解，相当于spring的xml配置里写的那个value。是一种注入，@Autowired注入的是对象，@Value注入的是对象的一个成员（可配合${}来获取配置文件中的值）
  * @Value使用方法
    * 直接写基本类型值
    * ${}读取环境变量，配置文件中获取值（需要提前写好properties文件）
    * #{}使用spel表达式。如#{11%5}=1
  
* `@PropertySource`

  * 作用：加载指定目录下 的配置文件，使这个类的注入信息从这个配置文件获取（@ConfigrationProperties默认加载类路径下的全局配置文件）
  * 使用位置：想要注入的类上
  * 使用： @PropertySource(value={classpath:person.properties})
  
* `@ImportSource`

  * 作用：springboot里面没有spring的配置文件，我们自己编写的bea.xml也不能被识别。想要被识别就要使用这个注解导入配置文件，让配置文件内容生效
  
  * 使用位置：主配置类，即包含主方法那个类
  
  * 使用：@ImportSource(location={classpath:bean.xml})
* ```java
@Component
  @PropertySource({"classpah:person.properties"})
@ConfigurationProperties(prefix="person")
  public class Person(){
    @Value("${person.last-name}")
      private String lastName;
      
      @Value("#{11*2}")
      private Integer age;
      
      @Value("true")
      
      private Boolean isBoss;
  }
  ```
  
* springboot推荐使用的配置类

  * 作用：取代XML配置，而全部使用类的方法。在spring课程中有完全使用注解进行IOC有讲过

  * 使用及原理解释

    ```java
    //标明这是一个配置类
    @Configuration
    public class MyConfig {
  
        //把这个方法的返回值添加进spring容器
        @Bean
        public HelloService helloService(){
            HelloService service=new HelloService();
            service.setId(50);
            return service;
        }
    }
    ```
********************
### 配置的其他事项
* properties配置文件的占位符和默认值
  * 示例
    ```properties
    # 随机数
    person.last‐name=张三${random.uuid} 
    person.age=${random.int} 
    person.birth=2017/12/15 
    person.boss=false 
    person.maps.k1=v1 
    person.maps.k2=14 
    person.lists=a,b,c 
    # 默认值
    person.dog.name=${person.hello:hello}_dog 
    person.dog.age=15
    ```
  
* properties配置文件的profile多环境使用

  * 作用：不同的环境下使用不同的配置文件
  
* yml多环境配置文件的文档块使用

  * 作用：不同环境下使用相同配置文件的不同内容
  
* 配置文件加载位置

  * 四个位置（请两个是在项目的顶层根路径下，后两个是在resources文件夹下）
    * file: ./config/
    * file: ./
    * classpath:/config/
    * classpath:/
  * 四个文件夹优先级从上到下依次降低
  * 互补配置，在某个yml配置文件中加入`server.context-path: /path2`就只能通过localhost:8080/path2/hello来访问。实现互补
  * 互补配置可以实现运维时不用重新修改配置重新打包部署，直接命令行指定配置文件位置实现互补
  * 所有配置命令行优先级是最高的
  
* 自动配置原理

  * 
