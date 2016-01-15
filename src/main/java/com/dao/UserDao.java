package com.dao;

import com.datasource.DataBaseDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Administrator
 * Date: 2016/1/15 Time: 14:30
 */
public class UserDao {

    public Integer findByName(String name){
        try {
            Connection conn = DataBaseDataSource.getConnection();
            QueryRunner qr = new QueryRunner();
            String sql = "select uid from t_user where username=?";
            return (Integer) qr.query(conn,sql,new ScalarHandler(1),name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataBaseDataSource.closeConnection();
        }
    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();
        System.out.println(dao.findByName("admin"));
    }
}
