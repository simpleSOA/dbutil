package com.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author: Administrator
 * Date: 2016/1/15 Time: 13:28
 */
public class DataBaseDataSource {
    private final static ThreadLocal<Connection> CONNS = new ThreadLocal<>();

    private static DataSource dataSource;
    static {
        ResourceBundle resource = ResourceBundle.getBundle("datasource-config");
        Map<String,String> config = new HashMap<>();
        for(String key : resource.keySet()){
            config.put(key,resource.getString(key));
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        if (connection == null || connection.isClosed()){
            connection = dataSource.getConnection();
            CONNS.set(connection);
        }
        return connection;
    }
    public final static void closeConnection() {
        Connection conn = CONNS.get();
        try {
            if(conn != null && !conn.isClosed()){
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CONNS.set(null);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
