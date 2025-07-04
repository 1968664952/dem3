package cn.com.chinahitech.bjmarket.course.Service.impl;

import cn.com.chinahitech.bjmarket.course.Mapper.CourseMapper;
import cn.com.chinahitech.bjmarket.course.Service.CourseService;
import cn.com.chinahitech.bjmarket.course.entity.Course;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> queryCourseByKeyword(String Keyword){
        List<Course> courseList=null;
        QueryWrapper<Course> wrapper=new QueryWrapper<Course>();

        wrapper.like("course_name",Keyword);

        courseList=courseMapper.selectList(wrapper);

        return courseList;
    }

    @Override
    public List<Course> personalRecom1(List<String> courseName, int cBankId,int grade) {
        List<Course> courseList=null;
        QueryWrapper<Course> wrapper=new QueryWrapper<Course>();

        wrapper.eq("c_bank_id",cBankId)
                .notIn("course_name",courseName)
                .orderByDesc("difficulty")
                .eq("grade",grade);
        courseList=courseMapper.selectList(wrapper);
        return courseList;
    }
    @Override
    public List<Course> personalRecom2(int cBankId,int grade) {
        List<Course> courseList=null;
        QueryWrapper<Course> wrapper=new QueryWrapper<Course>();
        if(grade<4){
            grade+=1;
        }

        wrapper.eq("c_bank_id",cBankId)
                .orderByDesc("difficulty")
                .eq("grade",grade);
        courseList=courseMapper.selectList(wrapper);
        return courseList;
    }
    @Override
    public List<Course> personalRecom3(int cBankId) {
        List<Course> courseList=null;
        QueryWrapper<Course> wrapper=new QueryWrapper<Course>();

        wrapper.eq("c_bank_id",cBankId)
                .orderByDesc("total_play_count");
        courseList=courseMapper.selectList(wrapper);
        return courseList;
    }


    @Override
    public List<Course> queryPGCourse() {
        List<Course> courseList=null;
        QueryWrapper<Course> wrapper=new QueryWrapper<Course>();

        wrapper.eq("c_bank_id","16");

        courseList=courseMapper.selectList(wrapper);

        return courseList;
    }

    @Override
    public int addFavoriteCount(String courseId) {
        UpdateWrapper<Course> wrapper = new UpdateWrapper<Course>();
        wrapper.setSql("favorite_count = favorite_count + 1")  // 直接拼接 SQL 表达式
                .eq("course_id", courseId);
        int result=courseMapper.update(null,wrapper);
        return result;
    }

    @Override
    public int delFavoriteCount(String courseId) {
        UpdateWrapper<Course> wrapper = new UpdateWrapper<Course>();
        wrapper.setSql("favorite_count = favorite_count - 1")  // 直接拼接 SQL 表达式
                .eq("course_id", courseId);
        int result=courseMapper.update(null,wrapper);
        return result;
    }

    @Override
    public List<Course> courseList(int cBankId, int grade, int newOrHot) {
        List<Course> courseList=null;
        QueryWrapper<Course> wrapper=new QueryWrapper<Course>();

        wrapper.eq("c_bank_id",cBankId);
        if (grade!=0){
            wrapper.eq("grade",grade);
        }
        if (newOrHot==0){
            wrapper.orderByDesc("created_at");
        }else {
            wrapper.orderByDesc("total_play_count");
        }

        courseList=courseMapper.selectList(wrapper);
        return courseList;
    }
}
