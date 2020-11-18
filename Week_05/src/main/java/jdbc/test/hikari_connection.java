package jdbc.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2020/11/17.
 */
public class hikari_connection {

    private final static HikariDataSource DS = new HikariDataSource(new HikariConfig("hikari.properties"));

    private Connection getConnection() throws SQLException {
        try {
            return DS.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean stop() throws SQLException {
        DS.close();
        return true;
    }

    public void add(User form) {
        Connection con = null;	//连接
        PreparedStatement pstmt = null;	//使用预编译语句
        ResultSet rs = null;	//获取的结果集
        try {
            con = getConnection(); //获取连接
            if(con == null) {
                throw new SQLException("get connection failed!");
            }
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
            } catch (Exception e) {}
        }
    }

    public User findByUsername(String username){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection(); //获取连接
            if(con == null) {
                throw new SQLException("get connection failed!");
            }
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
            } catch (Exception e) {
            }
        }
    }

    public void del_by_batch(List<User> users) {
        Connection con = null;	//连接
        PreparedStatement pstmt = null;	//使用预编译语句
        ResultSet rs = null;	//获取的结果集
        try {
            con = getConnection(); //获取连接
            if(con == null) {
                throw new SQLException("get connection failed!");
            }
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
            } catch (Exception e) {}
        }
    }
}
