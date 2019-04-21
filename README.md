什么是数据绑定？
=
将HTTP请求中的参数绑定到Handler业务方法的形参。 springMVC框架会自动帮我们把前台页面表单中传递进来的各个参数所包含的数据封装为后端中的一个对象（形式参数）。也就是说，springMVC框架自动将前段传递进来的分散的参数封装为一个POJO。后端则直接可以对该POJO对象进行其他的操作，不用在后端自动来完成转换成对象的过程。<br>
![流程](https://github.com/mydre/springMVC/blob/master/src/main/webapp/picture/Snip20190420_2.png)
基本数据类型的数据绑定
-
在controller中可以这样定义：<br>
```java
    @RequestMapping(value = "/baseType")
    @ResponseBody
    public String baseType(@RequestParam(value = "id",required = false)int id){
        return "id" + id;
    }
```
包装数据类型：<br>
```java
    @RequestMapping(value = "/packageType")
    @ResponseBody
    public String packageType(@RequestParam(value = "id",required = false) Integer id){
        return "id" + id;
    }
```
数组数据类型：<br>
```java
    @RequestMapping(value = "/arrayType")
    @ResponseBody
    public String arrayType(String[] name){
        StringBuffer stringBuffer = new StringBuffer(  );
        for(String na:name){
            stringBuffer.append(na).append( " " );
        }
        return stringBuffer.toString();
    }
```
POJO数据类型(前提是首先在entity包中定义实体类，如Course)：<br>
Course类将前端传递进来的数据包装起来了，如数据可以是id，name和price<br>
```java
    @RequestMapping("/pojoType")
    public ModelAndView pojoType(Course course){
        courseDao.add(course);
        ModelAndView modelAndView = new ModelAndView(  );
        modelAndView.addObject("courses",courseDao.getAll() );
        modelAndView.setViewName( "index" );
        return modelAndView;
    }
```
绑定List数据类型，除了在controller中处理，还需要些写包装数据的类<br>
前端数据传递的格式示范：
```jsp
<input type="text" class="form-control" name="courseList[0].id" placeholder="请输入课程编号">
<input type="text" class="form-control" name="courseList[1].id" placeholder="请输入课程编号">
```
```java
package com.imooc.springmvcDataBind.entity;

import java.util.List;

public class CourseListUtil {
    private List<Course> courseList;

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
```
```java
    @RequestMapping("/listType")
    public ModelAndView listType(CourseListUtil courseListUtil){
        ModelAndView modelAndView = new ModelAndView(  );
        for(Course course: courseListUtil.getCourseList()){
            courseDao.add(course);
        }
        modelAndView.addObject( "courses",courseDao.getAll() );
        modelAndView.setViewName( "index" );
        return modelAndView;
    }
```
绑定map数据类型
```java
<input type="text" class="form-control" name="mapCourse['one'].price" placeholder="请输入课程价格">
```
```java
package com.imooc.springmvcDataBind.entity;

import java.util.Map;

public class CourseMapUtil {
    private Map<String,Course> mapCourse;

    public Map<String, Course> getMapCourse() {
        return mapCourse;
    }

    public void setMapCourse(Map<String, Course> mapCourse) {
        this.mapCourse = mapCourse;
    }
}
```
```java
    @RequestMapping("/mapType")
    public ModelAndView mapType(CourseMapUtil courseMapUtil){
        ModelAndView modelAndView = new ModelAndView(  );
        Map<String,Course> map = courseMapUtil.getMapCourse();
        Set<String> set = map.keySet();
        for(String key:set){
            Course course = map.get(key);
            courseDao.add( course );
        }
        modelAndView.addObject( "courses",courseDao.getAll() );
        modelAndView.setViewName( "index" );
        return modelAndView;
    }
```
绑定set数据类型
```html
<input type="text" class="form-control" name="courseSet[1].price" placeholder="请输入课程价格">
```
```java
package com.imooc.springmvcDataBind.entity;

import java.util.HashSet;
import java.util.Set;

public class CourseSetUtil {
    private Set<Course> courseSet = new HashSet<Course>(  );

    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(Set<Course> courseSet) {
        this.courseSet = courseSet;
    }

    public CourseSetUtil(){//必须两个空的Course对象，不然不能完成数据的绑定
        this.courseSet.add( new Course() );
        this.courseSet.add( new Course() );
    }
}
```
```java
    @RequestMapping("/setType")
    public ModelAndView setType(CourseSetUtil courseSetUtil){
        Set<Course> set = courseSetUtil.getCourseSet();
        //将set中的course对象取出，然后使用courseDao进行add
        for(Course course:set){
            courseDao.add( course );
        }
        ModelAndView modelAndView = new ModelAndView(  );
        modelAndView.addObject( "courses",courseDao.getAll() );
        modelAndView.setViewName( "index" );
        return modelAndView;
    }
```
绑定json数据类型<br>
引入jar包
```xml
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.8.11.3</version>
    </dependency>
```
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">

    $(function(){
        var course = {
            "id":"8",
            "name":"SSM框架整合",
            "price":"200"
        };
        $.ajax({
            url:"jsonType",
            data:JSON.stringify(course), //这里的data表示想要发送的数据
            type:"post",
            contentType:"application/json;charse=UTF-8",
            dataType:"json", //dataType指定后台返回给前台的数据的数据类型
            success:function(data){//这个data是后它返回的数据
                alert(data.name+"---"+data.price);
            }
        })
    })

</script>
<body>

</body>
</html>
```
```java
    @RequestMapping("/jsonType")
    @ResponseBody
    public Course jsonType(@RequestBody Course course){
        course.setPrice( course.getPrice() + 100);
        return course;
    }
```
附：
-
pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.imooc</groupId>
  <artifactId>springMVCDataBind</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>springMVCDataBind Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.7</maven.compiler.source>
    <maven.compiler.target>1.7</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>4.3.1.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
    </dependency>
    <dependency>
      <groupId>jstl</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.8.11.3</version>
    </dependency>
  </dependencies>

  <build>
    <finalName>springMVCDataBind</finalName>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.1.0</version>
        </plugin>
        <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.8.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-war-plugin</artifactId>
          <version>3.2.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
```
web.xml
```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>*.css</url-pattern>
  </servlet-mapping>

  <servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>*.js</url-pattern>
  </servlet-mapping>

  <servlet>
    <servlet-name>SpringMVCDataBind</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--配置springmvc.xml的路径-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springMVCDataBind.xml</param-value>
    </init-param>
  </servlet>
  <!--配置对应的servletmapping，/表示过滤掉所有的请求-->
  <servlet-mapping>
    <servlet-name>SpringMVCDataBind</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
```
springMVCDataBind.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd">

<!--    &lt;!&ndash;配置HandlerMapping&ndash;&gt;
    <bean id="handlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <prop key="/test">testHandler</prop>
            </props>
        </property>
    </bean>

    &lt;!&ndash;配置Handler&ndash;&gt;
    <bean id="testHandler" class="com.imooc.springmvc.handler.MyHandler"></bean>-->

    <!--配置视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--配置视图解析器-->
        <property name="prefix" value="/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>

    <context:component-scan base-package="com.imooc.springmvcDataBind"></context:component-scan>

    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"></bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
</beans>
```
