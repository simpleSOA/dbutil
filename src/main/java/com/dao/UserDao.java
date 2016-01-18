package com.dao;

import com.datasource.DataBaseDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Administrator
 * Date: 2016/1/15 Time: 14:30
 */
public class UserDao {

    public String findByName(String name,int tableIndex){
        Connection conn = null;
        try {
            conn = DataBaseDataSource.getConnection();
            QueryRunner qr = new QueryRunner();
            String sql = "select Email from u_user_info"+tableIndex+" where Email=? and Platform='Fotor'";
            return (String) qr.query(conn,sql,new ScalarHandler(1),name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] emails = new String[10000];
        for(int i=0;i<10000;i++){
            emails[i] = RandomStringUtils.randomNumeric(9)+"@qq.com";
        }
        UserDao dao = new UserDao();
        for(String email : emails){
            int index = webUserRoute(email,true);
            long start = System.currentTimeMillis();
            dao.findByName(email,index);
            System.out.println(" spent time:"+(System.currentTimeMillis()-start));
        }
    }

    public static int webUserRoute(String param,boolean first){
        if(param == null || param.length() == 0){
            throw new IllegalArgumentException("The param must not be null!");
        }
        if(first){
            return (int)param.charAt(0)%10;
        }
        return (int)param.charAt(param.length()-1)%10;
    }
}
