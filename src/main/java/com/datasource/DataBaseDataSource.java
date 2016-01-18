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
        return connection;
    }
}
