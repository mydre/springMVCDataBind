package com.imooc.springmvcDataBind.dao;

import com.imooc.springmvcDataBind.entity.Course;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CourseDao {
    private Map<Integer, Course> map = new HashMap<Integer, Course>(  );

    public void add(Course course){
        map.put( course.getId(),course );
    }

    public Collection<Course> getAll(){
        return map.values();
    }
}
