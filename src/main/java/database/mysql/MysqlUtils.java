package database.mysql;

import java.sql.*;

public class MysqlUtils {

    static String url = "jdbc:mysql://192.168.159.39:3306/information_schema?"
            + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
    public static void main(String[] args) {
        getColumns();
    }
    public static void getTable(){
        Connection conn = null;
        String sql;
        try {
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            System.out.println("创建数据表成功");
            sql = "select TABLE_NAME,TABLE_COMMENT from TABLES WHERE TABLE_SCHEMA='lbs_master'";
            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("数据库连接错误");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("数据库连接关闭异常");
            }
        }
    }

    public static void getColumns(){
        Connection conn = null;
        String sql;

        try {
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            System.out.println("创建数据表成功");
            sql = "select COLUMN_NAME,COLUMN_TYPE,COLUMN_COMMENT from COLUMNS WHERE TABLE_NAME='goods'";
            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2)+"\t"+rs.getString(3));// 入如果返回的是int类型可以用getInt()
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("数据库连接错误");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("数据库连接关闭异常");
            }
        }
    }
}
