package com.me.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @author buyulian
 * @date 2020/3/28
 */
public class DriverWrap {

    private static final Logger logger = LoggerFactory.getLogger(DriverWrap.class);


    private String jdbcUrl;

    private Connection connection;

    public DriverWrap(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public static Connection getConnection(String jdbcUrl) {
        try {
            return DriverManager.getConnection(jdbcUrl);
        } catch (SQLException e) {
            throw new RuntimeException("获取 jdbc 连接失败", e);
        }
    }

    private Connection getInnerConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException e) {
                throw new RuntimeException("获取 jdbc 连接失败", e);
            }
        }
        return connection;
    }

    public int update(String sql) {
        Connection connection = getInnerConnection();
        int num = 0;
        try {
            Statement statement = connection.createStatement();
            num = statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("执行 sql 更新失败" + sql, e);
        }
        return num;
    }

    public ResultSet query(String sql) {
        Connection connection = getInnerConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException("执行 sql 更新失败" + sql, e);
        }
    }

    public List<Map<String, Object>> queryReturnList(String sql) {
        Connection connection = getInnerConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            List<Map<String, Object>> list = new LinkedList<>();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();

                ResultSetMetaData rsMeta=rs.getMetaData();
                int columnCount=rsMeta.getColumnCount();
                for (int i=1; i<=columnCount; i++) {
                    map.put(rsMeta.getColumnLabel(i), rs.getObject(i));
                }

                list.add(map);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException("执行 sql 更新失败" + sql, e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("关闭 statement 失败" ,e);
                }
            }
        }
    }

    public void close() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("关闭连接失败" ,e);
        }
    }

}
