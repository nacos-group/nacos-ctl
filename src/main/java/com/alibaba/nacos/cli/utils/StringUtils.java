package com.alibaba.nacos.cli.utils;

import java.util.ArrayList;
import java.util.List;

/** @author lehr */
public class StringUtils {

  /**
   * 处理命令行输入，主要是考虑"xx xx"等各种分割的情况
   *
   * <p>读取一组字符串，自动按照空格分割，若遇到有"xx xx"，则单独分为一组
   *
   * @param line
   * @return
   */
  public static String[] parseInput(String line) {

    String[] words = line.split(" ");

    List<String> wordList = new ArrayList<>();

    List<String> list = new ArrayList<>();

    for (int i = 0; i < words.length; i++) {

      if (words[i].contains("\"")) {
        // 开始读取""内的内容
        list.add(words[i++]);
        while (i < words.length && !words[i].contains("\"")) {
          list.add(words[i++]);
        }

        // 只给了一个引号无法成对的情况
        if (i >= words.length) {
          wordList.addAll(list);
          break;
        }

        // 把有引号内的字段都拼接起来
        StringBuffer sb = new StringBuffer();
        for (String s : list) {
          sb.append(s).append(" ");
        }
        String result = sb.append(words[i]).toString();
        // 如果开头结尾都是引号，则把引号去掉
        if (result.startsWith("\"") && result.endsWith("\"")) {
          result = result.substring(1, result.length() - 1);
        }
        wordList.add(result);

      } else {
        wordList.add(words[i]);
      }
    }

    return wordList.toArray(list.toArray(new String[0]));
  }


}
