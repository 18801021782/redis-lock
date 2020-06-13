package com.example.demo.datasource;

/**
 * 实现读写分离（多数据源分离）
 * 这里主要思路如下：
 * 1-将不同的数据源标识记录在ThreadLocal中
 * 2-通过注解标识出当前的service方法使用哪个库
 * 3-通过Spring AOP实现拦截注解并注入不同的标识到threadlocal中
 * 4-获取源的时候通过threadlocal中不同的标识给出不同的sqlSession
 *
 * 标识存放ThreadLocal的实现
 */

public class DbContextHolder {

    public enum DbType{
        MASTER,SLAVE
    }

    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DbType dbType){
        if(dbType==null)throw new NullPointerException();
        contextHolder.set(dbType);
    }

    public static DbType getDbType(){
        return contextHolder.get()==null?DbType.MASTER:contextHolder.get();
    }

    public static void clearDbType(){
        contextHolder.remove();
    }

}
