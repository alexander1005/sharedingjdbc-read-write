package com.ltz.test.dao;

import com.ltz.test.model.Goods0;
import com.ltz.test.model.Goods0Example;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface Goods0Dao {
    long countByExample(Goods0Example example);

    int deleteByExample(Goods0Example example);

    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods0 record);

    int insertSelective(Goods0 record);

    List<Goods0> selectByExample(Goods0Example example);

    Goods0 selectByPrimaryKey(Long goodsId);

    int updateByExampleSelective(@Param("record") Goods0 record, @Param("example") Goods0Example example);

    int updateByExample(@Param("record") Goods0 record, @Param("example") Goods0Example example);

    int updateByPrimaryKeySelective(Goods0 record);

    int updateByPrimaryKey(Goods0 record);
}