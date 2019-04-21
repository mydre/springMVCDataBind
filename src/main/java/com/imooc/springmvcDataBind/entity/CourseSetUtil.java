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
