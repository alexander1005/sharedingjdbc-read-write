package com.ltz.test.databaseSharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.ltz.test.utils.MathUtils;

import java.util.Collection;
import java.util.List;

/**
 * 分表策略
 */
public class UserSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {

  @Override
  public String doEqualSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
    tableNames = MathUtils.sortByIndex(tableNames);
    int sharding = shardingValue.getValue() % tableNames.size();
    // sharding 的值为 0 , 1 , 2 ,tableNames.size()
    return String.valueOf(tableNames.toArray()[sharding]);
  }

  @Override
  public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
    tableNames = MathUtils.sortByIndex(tableNames);
    // 提取tables
    String[] tables = (String[]) tableNames.toArray();
    // 返回结果
    List<String> resultTables = Lists.newArrayList();

    for (Integer value : shardingValue.getValues()) {
      resultTables.add(tables[value % tableNames.size()]);
    }

    return resultTables;
  }

  @Override
  public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
    tableNames = MathUtils.sortByIndex(tableNames);
    // 提取tables
    String[] tables = (String[]) tableNames.toArray();
    // 返回结果
    List<String> resultTables = Lists.newArrayList();

    Range<Integer> valueRange = shardingValue.getValueRange();
    for ( Integer value  = valueRange.lowerEndpoint(); value <= valueRange.upperEndpoint(); value++) {
      resultTables.add(tables[value % tableNames.size()]);
    }

    return resultTables;
  }
}
