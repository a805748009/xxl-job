package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface XxlJobUserDao {

    public List<XxlJobUser> findAll();

    public XxlJobUser findByUserName(@Param("userName") String userName);

    public int save(XxlJobUser xxlJobGroup);

    public int update(XxlJobUser xxlJobGroup);

    public int remove(@Param("userName") String userName);

}
