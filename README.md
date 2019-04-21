什么是数据绑定？
=
将HTTP请求中的参数绑定到Handler业务方法的形参。 springMVC框架会自动帮我们把前台页面表单中传递进来的各个参数所包含的数据封装为后端中的一个对象（形式参数）。也就是说，springMVC框架自动将前段传递进来的分散的参数封装为一个POJO。后端则直接可以对该POJO对象进行其他的操作，不用在后端自动来完成转换成对象的过程。<br>
image_file[fjdj](http://)

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
前端数据传递的格式示范：<br>
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
