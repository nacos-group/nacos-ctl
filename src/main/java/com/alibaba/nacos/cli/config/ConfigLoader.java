package com.alibaba.nacos.cli.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 从文件读取配置的工具类，配合注解使用
 *
 * @author lehr
 */
public class ConfigLoader {
    
    private static final String CONF_PATH = "conf.properties";
    
    private static Map<String, String> tinyDb = new HashMap<>();
    
    private static void readFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(CONF_PATH), StandardCharsets.UTF_8);
            Map<String, String> confs = lines.stream().peek(s -> s.trim()).filter(s -> !s.startsWith("#"))
                    .filter(s -> s.contains("=")).map(s -> s.split("=")).filter(sa -> sa.length == 2)
                    .collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()));
            tinyDb.putAll(confs);
        } catch (Exception e) {
            //...不存在就不加载
        }
    }
    
    private static Map<Class, Function<String, Object>> handler = new HashMap<>();
    
    static {
        handler.put(int.class, Integer::valueOf);
        handler.put(Integer.class, Integer::valueOf);
        handler.put(float.class, Float::valueOf);
        handler.put(Float.class, Float::valueOf);
        handler.put(Boolean.class, Boolean::valueOf);
        handler.put(boolean.class, Boolean::valueOf);
        handler.put(double.class, Double::valueOf);
        handler.put(Double.class, Double::valueOf);
        handler.put(String.class, o -> o);
        readFile();
    }
    
    public static void preload(Map<String, String> params) {
        tinyDb.putAll(params);
    }
    
    public static boolean fill(Object o) {
        boolean flag = true;
        for (Field f : o.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(FromPropertie.class)) {
                String name = f.getName();
                String value = tinyDb.get(name);
                if (value != null) {
                    try {
                        Function<String, Object> caster = handler.get(f.getType());
                        if (caster != null) {
                            Object castedVal = handler.get(f.getType()).apply(value);
                            f.setAccessible(true);
                            f.set(o, castedVal);
                        }
                    } catch (IllegalAccessException e) {
                        System.out.println("failed to load config '" + f.getName() + "' from value '" + value + "'");
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }
    
    /**
     * @author lehr
     */
    @Documented
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FromPropertie {
    
    }
}
