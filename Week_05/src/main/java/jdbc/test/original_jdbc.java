package jdbc.test;

import java.sql.*;
import java.util.List;

/**
 * Created by Administrator on 2020/11/16.
 */
public class original_jdbc {

    public static Connection getConnection(String username, String password){
        String driverClassName = "com.mysql.jdbc.Driver";	//启动驱动
        String url = "jdbc:mysql://localhost:3306/test";	//设置连接路径
        Connection con = null;
        try {
            Class.forName(driverClassName); //执行驱动
            con = DriverManager.getConnection(url, username, password); //获取连接
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            return con;
        }
    }

    /**
     * 增
     * @param form
     */
    public void add(User form) {
        Connection con = null;	//连接
        PreparedStatement pstmt = null;	//使用预编译语句
        ResultSet rs = null;	//获取的结果集
        try {
            con = getConnection("root","123456"); //获取连接
            String sql = "INSERT INTO USER VALUES(?,?,?,?)"; //设置的预编译语句格式
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, form.getUserName());
            pstmt.setString(2, form.getPassWord());
            pstmt.setInt(3, form.getAge());
            pstmt.setString(4, form.getGender());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            //关闭资源,倒关
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();  //必须要关
            } catch (Exception e) {}
        }
    }

    /**
     * 查
     * @param username
     * @return
     */
    public User findByUsername(String username){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection("root","123456"); //获取连接
            String sql = "SELECT * FROM USER WHERE username=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setNString(1, username);
            rs = pstmt.executeQuery();
            if(rs == null) {
                return null;
            }
            if(rs.next()) {
                return new User(rs.getString("username"),rs.getString("password"),rs.getInt("age"),rs.getString("gender"));
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            //关闭资源,倒关
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();  //必须要关
            } catch (Exception e) {
            }
        }
    }

    /**
     * 删
     * @param users
     */
    public void del_by_batch(List<User> users) {
        Connection con = null;	//连接
        PreparedStatement pstmt = null;	//使用预编译语句
        ResultSet rs = null;	//获取的结果集
        try {
            con = getConnection("root","123456"); //获取连接
            String sql = "DELETE FROM USER WHERE username=?"; //设置的预编译语句格式
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(sql);
            for(User user : users){
                pstmt.setString(1,user.getUserName());
                pstmt.addBatch();
            }
            int[] results = pstmt.executeBatch();
            con.commit();
            for(int re : results){
                System.out.println(re);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            //关闭资源,倒关
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();  //必须要关
            } catch (Exception e) {}
        }
    }


}
