package com.ltz.test.dao;

import com.ltz.test.model.Goods;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Goods0Dao {

    int insert(Goods record);

    List<Goods> list();
}