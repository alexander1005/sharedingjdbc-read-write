package com.ltz.test.controller;

import com.ltz.test.dao.Goods0Dao;
import com.ltz.test.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("test")
public class TestController {

  @Autowired
  private Goods0Dao goods0Dao;


  @GetMapping("insert")
  public String insert() {
    Goods record = new Goods();
    record.setUserId(Math.abs(new Random().nextInt()));
    record.setName(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
    record.setAge(23);
    goods0Dao.insert(record);
    return "success";
  }

  @GetMapping("list")
  public List<Goods> list() {
    return goods0Dao.list();
  }
}

