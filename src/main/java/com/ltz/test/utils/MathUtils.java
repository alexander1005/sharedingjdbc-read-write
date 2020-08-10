package com.ltz.test.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MathUtils {

  private static Integer getStrIndex(String str) {
    String[] values = str.split("_");
    return Integer.parseInt(values[values.length - 1]);
  }

  public static List<String> sortByIndex(Collection<String> databaseName){
    return databaseName.stream().sorted(Comparator.comparingInt(MathUtils::getStrIndex)).collect(Collectors.toList());
  }
}
