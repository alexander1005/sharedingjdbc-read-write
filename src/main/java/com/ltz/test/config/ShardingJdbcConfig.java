package com.ltz.test.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ltz.test.databaseSharding.UserSingleKeyDatabaseShardingAlgorithm;
import com.ltz.test.databaseSharding.UserSingleKeyTableShardingAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Configuration
public class ShardingJdbcConfig {

  @Value("${master.url}")
  public String masterUrl;
  @Value("${master1.url}")
  public String master1Url;

  @Value("${master.driver-class-name}")
  public String masterDriver;
  @Value("${master1.driver-class-name}")
  public String master1Driver;

  @Value("${master.username}")
  public String masterUser;
  @Value("${master1.username}")
  public String master1User;

  @Value("${master.password}")
  public String masterPassword;
  @Value("${master1.password}")
  public String master1Password;



  @Bean
  @Lazy(value = false)
  public DataSourceRule dataSourceRule() {
    HashMap<String, DataSource> dataSourceMap = Maps.newHashMap();
    dataSourceMap.put("sharding_0", sharding_0());
    dataSourceMap.put("sharding_1", sharding_1());
    return new DataSourceRule(dataSourceMap);
  }


  public DruidDataSource sharding_0(){
    DruidDataSource druidDataSource = new DruidDataSource();
    druidDataSource.setUrl(masterUrl);
    druidDataSource.setDriverClassName(masterDriver);
    druidDataSource.setUsername(masterUser);
    druidDataSource.setPassword(masterPassword);
    return druidDataSource;
  }

  public DruidDataSource sharding_1(){
    DruidDataSource druidDataSource = new DruidDataSource();
    druidDataSource.setUrl(master1Url);
    druidDataSource.setDriverClassName(master1Driver);
    druidDataSource.setUsername(master1User);
    druidDataSource.setPassword(master1Password);
    return druidDataSource;
  }



  @Bean
  public ShardingRule shardingRule(DataSourceRule dataSourceRule) {
    return ShardingRule.builder()
        .dataSourceRule(dataSourceRule)
        .tableShardingStrategy(userTableShardingStrategy())
        .databaseShardingStrategy(userDatabaseShardingStrategy())
        .tableRules(Lists.newArrayList(userTableRule(dataSourceRule))).build();
  }

  /**
   * user表 分表规则
   * @return
   */
  @Bean
  public TableRule userTableRule(DataSourceRule dataSourceRule) {
    TableRule.TableRuleBuilder builder = TableRule.builder("t_user");
    // 原始表
    ArrayList<String> actualTables = Lists.newArrayList("t_user_0", "t_user_1", "t_user_2");
    builder.actualTables(actualTables);
    // 分库策略
    builder.databaseShardingStrategy(userDatabaseShardingStrategy());
    // 分表策略
    builder.tableShardingStrategy(userTableShardingStrategy());
    builder.dynamic(false);
    builder.dataSourceRule(dataSourceRule);
    TableRule build = builder.build();
    return build;
  }

  /**
   * 分库策略
   * @return 分库策略
   */
  public DatabaseShardingStrategy userDatabaseShardingStrategy() {
    return new DatabaseShardingStrategy
        ("user_id", new UserSingleKeyDatabaseShardingAlgorithm());
  }
  /**
   * 分表策略
   * @return 分表策略
   */
  public TableShardingStrategy userTableShardingStrategy() {
    return new TableShardingStrategy
        ("user_id", new UserSingleKeyTableShardingAlgorithm());
  }

  @Bean
  @Primary
  public DataSource shardingDataSource(DataSourceRule dataSourceRule) throws SQLException {
    DataSource dataSource = new ShardingDataSource(shardingRule(dataSourceRule));
    return dataSource;
  }
}
