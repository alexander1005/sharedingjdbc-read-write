package com.ltz.test.dao;

import com.ltz.test.model.Goods;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Goods0Dao {

    int insert(Goods record);
}