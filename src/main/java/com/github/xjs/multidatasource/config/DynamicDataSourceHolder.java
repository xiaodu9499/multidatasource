package com.github.xjs.multidatasource.config;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceHolder {

    /*
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new  ThreadLocal<String>();

    /*
     * 管理所有的数据源name,用于数据源的判断
     */
    public static final List<String> DATA_SOURCE_NAMES = new ArrayList<String>();

    /**
     * @Description: 设置数据源的变量
     * @param dateSourceName
     * @return void
     * @throws
     */
    public static void setCurrentDateSourceName(String dateSourceName){
        CONTEXT_HOLDER.set(dateSourceName);
    }

    /**
     * @Description: 获得数据源的变量
     * @return String
     * @throws
     */
    public static String getCurrentDateSourceName(){
        return CONTEXT_HOLDER.get();
    }

    /**
     * @Description: 清空所有的数据源变量
     * @return void
     * @throws
     */
    public static void clearCurrentDateSourceName(){
        CONTEXT_HOLDER.remove();
    }

    /**
     * @Description: 判断数据源是否已存在
     * @param dateSourceName
     * @return boolean
     * @throws
     */
    public static boolean existDateSource(String dateSourceName){
        return DATA_SOURCE_NAMES.contains(dateSourceName);
    }
}
