package com.imooc.springmvcDataBind.controller;

import com.imooc.springmvcDataBind.dao.CourseDao;
import com.imooc.springmvcDataBind.entity.Course;
import com.imooc.springmvcDataBind.entity.CourseListUtil;
import com.imooc.springmvcDataBind.entity.CourseMapUtil;
import com.imooc.springmvcDataBind.entity.CourseSetUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@Controller
public class DataBindController {

    @Resource
    private CourseDao courseDao;

    @RequestMapping(value = "/baseType")
    @ResponseBody
    public String baseType(@RequestParam(value = "id",required = false)int id){
        return "id" + id;
    }


    @RequestMapping(value = "/packageType")
    @ResponseBody
    public String packageType(@RequestParam(value = "id",required = false) Integer id){
        return "id" + id;
    }


    @RequestMapping(value = "/arrayType")
    @ResponseBody
    public String arrayType(String[] name){
        StringBuffer stringBuffer = new StringBuffer(  );
        for(String na:name){
            stringBuffer.append(na).append( " " );
        }
        return stringBuffer.toString();
    }


    @RequestMapping("/pojoType")
    public ModelAndView pojoType(Course course){
        courseDao.add(course);
        ModelAndView modelAndView = new ModelAndView(  );
        modelAndView.addObject("courses",courseDao.getAll() );
        modelAndView.setViewName( "index" );
        return modelAndView;
    }


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

    @RequestMapping("/jsonType")
    @ResponseBody
    public Course jsonType(@RequestBody Course course){
        course.setPrice( course.getPrice() + 100);
        return course;
    }
}
