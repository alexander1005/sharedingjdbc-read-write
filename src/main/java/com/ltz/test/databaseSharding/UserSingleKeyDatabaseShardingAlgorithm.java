package com.ltz.test.databaseSharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.ltz.test.utils.MathUtils;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserSingleKeyDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {


  @Override
  public String doEqualSharding(Collection<String> databaseName, ShardingValue<Integer> shardingValue) {
    databaseName = MathUtils.sortByIndex(databaseName);
    int sharding = shardingValue.getValue() % databaseName.size();
    // i 的值为 0 , 1 , 2 ,databaseName.size()
    return String.valueOf(databaseName.toArray()[sharding]);
  }

  @Override
  public Collection<String> doInSharding(Collection<String> databaseName, ShardingValue<Integer> shardingValue) {
    databaseName = MathUtils.sortByIndex(databaseName);
    // 提取tables
    String[] tables = (String[]) databaseName.toArray();
    // 返回结果
    List<String> resultTables = Lists.newArrayList();

    for (Integer value : shardingValue.getValues()) {
      resultTables.add(tables[value % databaseName.size()]);
    }

    return resultTables;
  }

  @Override
  public Collection<String> doBetweenSharding(Collection<String> databaseName, ShardingValue<Integer> shardingValue) {
    databaseName = MathUtils.sortByIndex(databaseName);
    // 提取tables
    String[] tables = (String[]) databaseName.toArray();
    // 返回结果
    List<String> resultTables = Lists.newArrayList();

    Range<Integer> valueRange = shardingValue.getValueRange();
    for (Integer value = valueRange.lowerEndpoint(); value <= valueRange.upperEndpoint(); value++) {
      resultTables.add(tables[value % databaseName.size()]);
    }

    return resultTables;
  }
}
