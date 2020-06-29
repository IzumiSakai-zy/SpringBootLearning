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
* 配置类实例
* 
  ```java
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
  

***************

### Web开发

* 总体的三步

  * 创建springboot项目，并导入需要的启动项

  * 在配置文件中指定少量的配置

  * 自己编写业务代码
  
* webjar

  * 作用：像引入spring一样引入web相关依赖资源

  * 网址： https://www.webjars.org/

  * 使用：

    * ```xml
      <dependency>
          <groupId>org.webjars</groupId>
          <artifactId>jquery</artifactId>
          <version>3.3.1</version>
      </dependency>
      ```
  
* 静态资源映射规则

  * 所有  localhost:8080/webjar/**  的请求都去  classpath:META-INF/resources/webjars/  下找资源
  * 举例 ：http://localhost:8080/webjars/jquery/3.3.1/jquery.js 可以访问到导入的jQuery源码
  
* 静态资源目录映射(classpaht:/代表resources目录)
  * 四个目录
    * classpah:/META-INF/resources/
    * classpath:/resources/
    * classpath:/static/
    * /
  * 当访问 localhost:8080/** 任意资源找不到时就会去上面四个路径下找、
  * 这也是种目录映射
  
* 欢迎页映射

  * localhost:8080/** 任意访问映射到四个静态目录的index.html，没有Index.html则报错

******************

### ThymeLeaf模板引擎

* 导入依赖

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>
  ```
  
* 版本太低使用更高版本

  ```xml
  <properties>
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
      <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
      <java.version>1.8</java.version>
      <thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
      <!-- 布局功能的支持程序  thymeleaf3主程序  layout2以上版本 -->
      <!-- thymeleaf2   layout1-->
      <thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
  </properties>
  ```
  * 其中要注意主版本与布局版本要搭配使用
  * 这是修改springboot默认自动配置版本的方法
  
* 自动配置的目录规则

  * 只要把HTML页面放在 classpath:/templates/ 文件夹下就能自动渲染
  
* 正式使用

  * 配置Controller类

    ```java 
    @org.springframework.stereotype.Controller
    public class Controller {
        @RequestMapping("/success")
        public String success(Map<String,String> map){
            map.put("name","IzumiSakai");
            return "success";
        }
    }
    ```
  ```
    
    * 因为是映射到和HTML页面，因此不能加 @ResponseBody注解
    * 类的注解好像只能加 @Controller ，不能用@Component
    * 方法的参数可以加东西
  ```
  
* 在 classpath:/templates/下创建success.html页面
  
    ```HTML
    <!DOCTYPE html>
    <!-- 导入命名空间使有语法提示 -->
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>success</title>
    </head>
    <body>
        <h1>成功访问</h1>
        <!--使用thymeleaf语法获取值-->
        <div th:text="${name}" />
    </body>
    </html>
  ```
******************
### thymeleaf语法规则

*  th:**   使用th里的值来替换原生默认的值
  
  * 比如： th:id=${id} , th:class=${class} , th:text=${text}
* ${}   底层就是OGNL表达式
* th:each 和 [[**]] 的使用    

   * ```html
     <h2 th:text="${user}" th:each="user:${users}" />
     
     <h2 th:each="user:${users}" >[[${user}]]</h2>
     ```
     ```java
     map.put("array", Arrays.asList("IzumiSakai","ZY","Name"));
     ```
   * th:each标签在那个里面，那个就要重复多少次。此例h2标签会重复多次

*******************

### SpringMVC自动配置

* springboot已经配置好了springMVC大多数配置
* 自动配置 ViewResolver（视图解析器） ： 根据方法的返回值生成视图view对象，视图决定如何渲染（转发or重定向）
* 自动注册 converter（转换器）和formatter（格式化器）；分别用于字符串转java类型和所有类型转字符串
* springboot自动配置模式
  * 先看用户没有自己配置一个类，如果有就用用户的，如果没有就由springboot默认配置一个类
* @AutoWired配置在方法上实际上是在注入参数
* @EnableWebMvc  全面接管springMVC配置，默认配置全部失效

******************

### Web开始

* 导入资源   entity和dao资源、static下的静态资源、templates下的HTML资源

* 修改默认启动项

  ```java
  @RequestMapping({"/","/index.html"})
      public String login(){
          return "login";
      }
  ```
  
* 导入webjars的bootstrap依赖并根据映射法使用thymeleaf导入css依赖

  * 注意：@{}不能省

  ```xml
  <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>bootstrap</artifactId>
      <version>4.0.0</version>
  </dependency>
  ```
  ```html
  <link  th:href="@{/webjars/bootstrap/4.0.0/css/bootstrap.css}" rel="stylesheet">
  ```

* bug解决

  * 读取不到templates下的文件   ——  删除target目录重新编译
  * 最新版的springboot不用修改thymeleaf版本，如果在pom中修改了版本会访问失败
  * 复制来的login.html有乱码是因为里面有thyleaf表达式，使用默认的就不会乱码

*********************

### 国际化配置

* 在类目录下创建  i18n  的文件夹

* 在  i18n  下创建login.properties、login_zh_CN.properties、logini_en_US.properties文件（idea检查到在配置国际化会自动在上层添加相应的文件夹）

* 进入Resource Bundle视图开始配置

* 在  application.yml  中进行配置(其中 i18n 是自定义在类路径下的包名)

  * 注意是 i18n/login 而不是 i18n.login
  
  ```yaml
  spring:
    messages:
      basename: i18n/login
  ```
  
* 使用 #{} 来取值，其中#{}专门用于取这种值
  
  ```html
  <!--th:text="#{tip}"-->
  <h1 class="h3 mb-3 font-weight-normal" th:text="#{tip}" >Please sign in</h1>
  
  <!--th:text="#{password}"-->
  <label class="sr-only" th:text="#{password}">Password</label>
  
  <!--th:placeholder="#{password}"-->
  <input type="password" name="password" class="form-control" placeholder="Password" th:placeholder="#{password}" required="">
  
  <label>
       <input type="checkbox" value="remember-me"/>[[#{remember}]]
  </label>
  ```
  
* 修改idea的file encoding 设置，不然properties文件的中文会乱码(修改后properties文件中文会乱码，还要再次编写)

* 把 language=zh_CN  作为请求头传入请求域

  * th:href="@{/index.html(language='zh_CN')}"     其中写地址href使用 @{} 符号，里面（）相当于？，zh_CN作为值要打单引号

  ```html
  <a class="btn btn-sm" th:href="@{/index.html(language='zh_CN')}">中文</a>
  <a class="btn btn-sm" th:href="@{/index.html(language='en_US')}">English</a>
  ```
  
* 虽然已经配置好但是springMVC不会使用请求头的 language=zh_CN，还要修改springMVC默认配置

  ```java
  //实现LocaleResolver接口，这个接口配置的是区域解析器
  public class MyLocaleResolver implements LocaleResolver {
      @Override
      public Locale resolveLocale(HttpServletRequest httpServletRequest) {
          String language = httpServletRequest.getParameter("language");
          if (language!=null){
              String[] strings = language.split("_");
              //其中zh代表语言，CN代表地区
              Locale locale=new Locale(strings[0],strings[1]);
              return locale;
          }
          return new Locale("en","US");
      }
  
      @Override
      public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {}
  }
  ```

  

  ```java
  @Configuration
  public class MyMvcConfig extends WebMvcConfigurerAdapter {
      //进行映射
      @Override
      public void addViewControllers(ViewControllerRegistry registry) {
          registry.addViewController("/").setViewName("login");
      }
  	
      //把自己的区域解析器装入spring容器生效
      @Bean
      public LocaleResolver localeResolver(){
          return new MyLocaleResolver();
      }
  }
  ```
******************
### 登录

* 在application.yml配置文件中禁用掉thymeleaf的缓存

  ```yaml
  thymeleaf:
      cache: false
  ```
  
* 设置form表单的action      th:action="@{/user/login}"

* 编写 /user/login  的业务逻辑

  * 重定向使用   return "redirect:/dashboard.html";
  
  ```java
  @Controller
  public class LoginController {
  
      @RequestMapping(value = "/user/login",method = RequestMethod.POST)
      public String login(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Map<String,Object> map){
  
          if ("IzumiSakai".equals(username)&&"123456".equals(password))
              //重定向
              return "redirect:/dashboard.html";
          map.put("error","用户名或密码错误");
          return "login";
      }
  }
  ```
  
* 因为使用重定向，因此还要添加  /dashboard.html   到templates文件夹下的dashboard.html的映射

  ```java
  @RequestMapping("/dashboard.html")
  public String dashborad(){  return "dashboard";  }
  ```

* 编写login.html提示的错误信息

  * 其中  #strings 是取用strings的方法

  ```html
  <div>
     <label style="color: red" th:if="${error}!=null" th:text="${error}"></label>
  </div>
  //第二种写法
  <label style="color: red" th:if="${not #strings.isEmpty(error)}" th:text="${error}"></label>
  ```
  
* dashboad.html里面要把所有thymeleaf删除了才能使用

* 这样直接可以直接登录后台，不安全，后面会做拦截器

* bug

  * 用户名密码错误跳转导致静态资源不显示——所有静态资源路径前面要加 / 才行
  * 字符串相等要使用  equal()方法，不能使用 ==
  * dashboad.html侧边栏无法显示，目前不知是啥问题

*******************

### 拦截器

* 作用：实现访问拦截，必须登录具有合法权限才能访问

* 在自己编写的Controler里面添加session，用于传递username消息

  ```java
  public String login(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      Map<String,Object> map,
                      HttpSession session){//添加session参数
      if ("IzumiSakai".equals(username)&&"123456".equals(password)){
         session.setAttribute("username",username);
         return "redirect:/dashboard.html";
      }
         map.put("error","用户名或密码错误");
         return "login";
  }
  ```

* 编写一个自己的拦截器

  * 拦截器类必须实现 HandlerInterceptor 接口
  * 这个接口有三个方法，其中 preHandle  是目标地址跳转之前执行的方法
* 不拦截返回 true，拦截返回 false
  
  ```java
  public class LoginHandlerInterceptor implements HandlerInterceptor {
      //目标方法执行之前
      @Override
      public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
          String username = (String) httpServletRequest.getSession().getAttribute("username");
          if (username!=null){
              return true;
          }else {
              httpServletRequest.setAttribute("error","没有访问权限，请先登录");
              httpServletRequest.getRequestDispatcher("/").forward(httpServletRequest,httpServletResponse);
              return false;
          }
      }
  
      //目标方法执行之后
      @Override
      public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
  
      }
  
      @Override
      public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
  
      }
  }
  ```
  
* 把这个过滤器添加到配置类

  * 因为返回值不是对象，就不用@Bean添加进容器
  * 这个方法是父类 WebMvcConfigurerAdapter 的一个方法
  * 可以添加那些拦截，那些不拦截
  * 高版本的springboot还会拦截静态资源，还要做额外拦截
  * /**   表示拦截所有路径，代表 localhost:8080/**

  ```java
  @Configuration
  public class MyMvcConfig extends WebMvcConfigurerAdapter {
      public void addInterceptors(InterceptorRegistry registry) {
          super.addInterceptors(registry);
          //静态资源；  *.css , *.js/SpringBoot已经做好了静态资源映射
          registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                  .excludePathPatterns("/index.html","/","/user/login");
      }
  }
  ```

* 以前知识点

  * session可以在一次会话中传递消息   session.setAttribute("username",username);
  * 转发的语句   httpServletRequest.getRequestDispatcher("/").forward(httpServletRequest,httpServletResponse);

* bug

  * 判断用户名时只用判断是否为空就行   if (username!=null)

*************

### Restful风格CRUD
* 基础增删改查风格
  * 目的：统一URI访问路径风格
  * 查询 —— emp/{id}---GET
  * 添加 —— emp---POST
  * 修改 —— emp/{id}---PUT
  * 删除 —— emp/{id}---DELETE
* 详细要求
  * 查询所有 —— emps---GET
  * 查询某一个(来到修改页面) —— emps/1---GET
  * 来到添加页面 —— emp---GET
  * 添加 —— emp---POST
  * 来到修改页面(进行消息回写) —— emp/1---GET
  * 修改 —— emp---PUT
  * 删除 —— emp/1---DELETE

****************

### 整合mybatis

* mybatis-spring-boot-stater这种mybatis放在前面的情况说明不是spring官方出的

* 用springboot初始化器创建一个新项目，勾选spring web、thymeleaf、JDBC API、mybatis framework、MySQL driver

* 对照数据库表结构编写一个java bean实体类Account

* 配置yml配置文件

  * 配置 spring.datasource

  * 配置mybati.mapper-location。下例表示mapper文件在类路径下mybatis文件夹下所有xml文件

  * ```yaml
    server:
      port: 8080
    
    spring:
      datasource:
        username: root
        password: 542270191MSzyl
        url: jdbc:mysql://localhost:3306/useraccount?useUnicode=true&characterEncoding=utf8
        driver-class-name: com.mysql.jdbc.Driver
    mybatis:
      mapper-locations: classpath:mybatis/*.xml
    ```
  
* 配置dao文件

  * 要添加@Respository和@Mapper注解，放入容器并注明这是mapper

  ```java
  @Repository
  @Mapper
  public interface AccountDao {
      List<Account> findAll();
  
      Account findById(Integer id);
  
      void add(Account account);
  
      void deleteById(Integer id);
  
      void update(Account account);
  }
  ```

* 配置mapper.xml文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.demo.repository.AccountDao" >
      <resultMap id="account" type="com.demo.entity.Account">
          <id property="id" column="id" />
          <result property="userId" column="user_id" />
          <result property="money" column="money" />
      </resultMap>
      <select id="findAll" resultMap="account">
          select * from account
      </select>
  
      <select id="findById" resultMap="account" parameterType="java.lang.Integer">
          select * from account where id=#{id}
      </select>
  
      <insert id="add" parameterType="com.demo.entity.Account" >
          insert into account (user_id,money) values (#{userId},#{money})
      </insert>
  
      <delete id="deleteById" parameterType="java.lang.Integer" >
          delete from account where id=#{id}
      </delete>
  
      <update id="update" parameterType="com.demo.entity.Account">
          update account set user_id=#{userId},money=#{money} where id=#{id}
      </update>
  </mapper>
  ```
  
* 在controller类中测试

  * 可以在URL中传入参数
  
  ```java
  @Controller
  public class AccountController {
      @Autowired
      AccountDao accountDao;
  
      @ResponseBody
      @RequestMapping("/findAll")
      public String findAll(){
          return accountDao.findAll().toString();
      }
  
      @ResponseBody
      @RequestMapping("/findById/{id}")
      public String findById(@PathVariable("id") Integer id){
          return accountDao.findById(id).toString();
      }
  
      @ResponseBody
      @RequestMapping("/add")
      public String add(){
          Account account=new Account();
          account.setUserId(2);
          account.setMoney(2000.0);
          accountDao.add(account);
          return "添加成功";
      }
  
      @ResponseBody
      @RequestMapping("/deleteById/{id}")
      public String deleteById(@PathVariable("id") Integer id){
          accountDao.deleteById(id);
          return "删除成功";
      }
  
      @ResponseBody
      @RequestMapping("/update")
      public String update(){
          Account account=new Account();
          account.setId(1);
          account.setUserId(3);
          account.setMoney(3988.1);
          accountDao.update(account);
          return "更新成功";
      }
}
  ```
********************
### 手写Web-CURD

* 搭建好基本的框架，导入必要的依赖，详情见上面整合mybatis

* 写Controller（如下所示）

  * 注意方法类型是get，请求域设置参数此处使用Map
  
  * ```java
    @Controller
    public class AccountController {
        @Autowired
        private AccountMapper accountMapper;
    
        @RequestMapping(value = "/accounts",method = RequestMethod.GET)
        public String list(Map<String,Object> map){
            List<Account> accounts = accountMapper.findAll();
            System.out.println(accounts.get(0));
            map.put("accounts",accounts);
            return "list";
        }
    }
    ```
  
* 写HTML（如下所示）

  * 注意遍历的写法
  * 获得的值要写"[[${account.id}]]"，而不能写"[[${id}]]"

  ```HTML
  <table>
      <th>
          <td>Id</td>
          <td>用户Id</td>
          <td>金额</td>
      </th>
      <tr th:each="account:${accounts}">
          <td>[[${account.id}]]</td>
          <td>[[${account.userId}]]</td>
          <td>[[${account.money}]]</td>
      </tr>
  </table>
  ```
  
* Spring MVC会自动根据HTML返回的参数自动填充Java Bean对象

  ```java
  @RequestMapping(value = "/user",method = RequestMethod.POST)
  //自动填充了User对象
  public String userAdd(User user){
      userMapper.insert(user);
      System.out.println("POST方法执行");
      return "redirect:/users";
  }
  ```

* 删除操作HTML页面内容

  * 注意地址的写法" th:action="@{/user/}+${user.id}" "做了一个拼接

  * 因为form和a标签都只支持get和post的请求，其他请求不支持。因此要实现其他的请求方式需要使用到springMVC的配置，通过解析"_method"的属性值来确认请求方式，必须进行表单嵌套，且表单的提交方式必须是post。
  * 要在application.yml做如下配置"spring.mvc.hiddrenmethod.enabled=true"开启隐藏请求方式配置

  ```HTML
  <form class="float-left" th:action="@{/user/}+${user.id}" th:method="post">
      <input type="hidden" name="_method" th:value="delete">
      <input type="submit" class="btn btn-sm btn-danger" th:value="删除"/>
  </form>
  ```

* 从路径上获取参数的方式

  ```java
  @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
  public String userDelete(@PathVariable("id") Integer id){}
  ```

* HTML单选框写法

  * 必须写" th:checked="${user.gender}==true" "，不能写" th:checked="checked" th:if="${user.gender}==true" "。后面这种写法会让后面一个单选框直接消失

  ```HTML
  <input  name="gender" th:checked="${user.gender}==true" type="radio" value="1">男
  ```

* 小知识点
  * 请求域中存值使用"Map, Model, ModelMap"，这几个都可以
  * 显示性别方法——th:text="${emps.gender}==true? '男' : '女' "
  * 日期格式化——th:text="${#dates.format(emps.date, 'yyyy-MM-dd')}"  其中#是取用thymeleaf中的内置对象
  * 重定向——"redirect:/users"，转发——"forward:/users
  * 默认日期的格式是以"/"作为分隔，如"2000/1/1"，如果不写"/"提交会报错。但是可以在application.yml修改格式，如" spring.mvc.format.date='yyyy-MM-dd' "
  * Controller的@RequestMapper()可以通过不同的访问方式来区分
  * Controller中获取HTML返回的参数使用HttpServletRequst类
  * a标签才能实现跳转，button标签不能跳转
  * 三元表达式写法——th:text="${user.gender}==true?'男':'女'"（注意大括号是在中间结束而不是在最后）
  
* bug

  * HTML页面内"@{}、${}"这种符号一定要有"th:"才能用，不然 " action="@{/main}" "这种写法是错误的