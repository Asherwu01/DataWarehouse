package com.asher.connect.jdbc;

import java.sql.*;

/**
 * @Author Asher Wu
 * @Date 2020/8/10 16:16
 * @Version 1.0
 */
public class KylinTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //Kylin_JDBC 驱动
        String KYLIN_DRIVER = "org.apache.kylin.jdbc.Driver";

        //Kylin_URL
        String KYLIN_URL = "jdbc:kylin://hadoop102:7070/gmall";

        //Kylin的用户名
        String KYLIN_USER = "ADMIN";

        //Kylin的密码
        String KYLIN_PASSWD = "KYLIN";

        //添加驱动信息
        Class.forName(KYLIN_DRIVER);

        //获取连接
        Connection connection = DriverManager.getConnection(KYLIN_URL, KYLIN_USER, KYLIN_PASSWD);

        //预编译SQL
        PreparedStatement ps = connection.prepareStatement("select gender,sum(final_amount_d) from dwd_fact_order_detail a join dwd_dim_user_info_his_view b on a.user_id=b.id group by gender;");

        //执行查询
        ResultSet resultSet = ps.executeQuery();

        //遍历打印
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1)+" "+resultSet.getDouble(2));
        }

    }
}
