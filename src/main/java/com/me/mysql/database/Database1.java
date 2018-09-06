package com.me.mysql.database;

import com.me.mysql.model.Field;
import com.me.mysql.model.Table;

import java.sql.*;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

public class Database1 implements Database {
    private String jdbcConnStr;
    private String database;
    private String isAllTable;
    private String tableNeeded;

    public Database1(String jdbcConnStr, String database, String isAllTable, String tableNeeded) {
        this.jdbcConnStr = jdbcConnStr;
        this.database = database;
        this.isAllTable = isAllTable;
        this.tableNeeded = tableNeeded;
    }

    @Override
    public List<Table> getTableList() {
        List<Table> tableList=null;
        if(isAllTable.equals("true")){
            tableList=getTables();
        }else{
            tableList=getTables(tableNeeded.split(","));
        }
        return tableList;
    }


    private Table getTable(Table table){
        Connection conn = null;
        String sql;

        try {
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(jdbcConnStr);
            Statement stmt = conn.createStatement();
            sql =MessageFormat.format("select COLUMN_NAME,COLUMN_TYPE,COLUMN_COMMENT from COLUMNS WHERE TABLE_NAME=''{0}'' AND TABLE_SCHEMA=''{1}''",table.name,database) ;
            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
            while (rs.next()) {
                Field field=new Field();
                field.name=rs.getString(1);

                String s1=rs.getString(2);
                String[] str=s1.split("\\(");
                field.setJdbcType(str[0].toUpperCase());

                if(str.length<2){
                    field.setTypeNum("");
                }else{
                    int endIndex=str[1].length()-1;
                    field.setTypeNum(str[1].substring(0,endIndex));
                }

                field.setComment(rs.getString(3));
                table.fieldList.add(field);
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("数据库连接关闭异常");
            }
        }
        return table;
    }


    private List<Table> getTables(){
        Connection conn = null;
        String sql;
        List<Table> tableList=new LinkedList<>();
        try {
            conn = DriverManager.getConnection(jdbcConnStr);
            Statement stmt = conn.createStatement();
            sql =MessageFormat.format("select TABLE_NAME,TABLE_COMMENT from TABLES WHERE TABLE_SCHEMA=''{0}''",database);
            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
            while (rs.next()) {
                Table table=new Table();
                table.name=rs.getString(1);
                table.setComment(rs.getString(2));
                table=getTable(table);
                tableList.add(table);
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("数据库连接关闭异常");
            }
        }
        return tableList;
    }

    private List<Table> getTables(String[] tableNames){
        Connection conn = null;
        String sql;
        List<Table> tableList=new LinkedList<>();
        try {
            conn = DriverManager.getConnection(jdbcConnStr);
            Statement stmt = conn.createStatement();
            sql =MessageFormat.format("select TABLE_NAME,TABLE_COMMENT from TABLES WHERE TABLE_SCHEMA=''{0}''",database);
            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
            while (rs.next()) {
                Table table=new Table();
                table.name=rs.getString(1);

                boolean isContain=false;
                for(String str:tableNames){
                    if(table.name.equals(str)){
                        isContain=true;
                        break;
                    }
                }
                if(!isContain){
                    continue;
                }
                table.setComment(rs.getString(2));
                table=getTable(table);
                tableList.add(table);
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("数据库连接关闭异常");
            }
        }
        return tableList;
    }

}
