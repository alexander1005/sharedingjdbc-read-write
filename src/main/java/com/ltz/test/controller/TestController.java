package com.ltz.test.controller;

import com.ltz.test.dao.Goods0Dao;
import com.ltz.test.model.Goods0;
import com.ltz.test.model.Goods0Example;
import org.apache.shardingsphere.api.hint.HintManager;
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

  @GetMapping("list")
  public List<Goods0> list(){
    // 强制路由主库
//    HintManager.getInstance().setMasterRouteOnly();
    return goods0Dao.selectByExample(new Goods0Example());
  }

  @GetMapping("insert")
  public String insert(){
    Goods0 record = new Goods0();
    record.setGoodsName(UUID.randomUUID().toString().replace("-","").substring(0,10));
    record.setGoodsType(new Random().nextLong());
    goods0Dao.insert(record);
    return "success";
  }
}
