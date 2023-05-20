package com.masanz.ldl.util;

import java.sql.*;


public class DbUtil {


//    private static String dbPath = "";
    private static String dbPath = "./db/libreria.db";
    private static final String URL_SQLITE = "jdbc:sqlite:%s";
//    private static final String URL_MYSQL = "jdbc:mysql://%s/%s";
//    private static final String DB_SERVER = "localhost";
//    private static final String DB_NAME = "libreria";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "root";
    private static Connection con;

    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn.isValid(100)) {
                String sql = "SELECT * FROM sqlite_master where type = ? and tbl_name = ? ";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, "table");
                pst.setString(2, "libros");
                ResultSet rs = pst.executeQuery();
                if (rs.next() && rs.getString("name").equals("libros")) {
                    rs.close();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch (Exception e){
            return false;
        }finally {
            closeConnections();
        }
    }

    public static void setDbPath(String path){
        dbPath = path;
    }

    public static Connection getConnection() throws SQLException {
        if (con == null){
//            con = DriverManager.getConnection("jdbc:mysql://"+ DB_SERVER +"/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
//            con = DriverManager.getConnection(String.format(URL_MYSQL, DB_SERVER, DB_NAME), DB_USERNAME, DB_PASSWORD);
            con = DriverManager.getConnection(String.format(URL_SQLITE, dbPath));
        }
        return con;
    }

    public static void closeConnections()  {
        if (con != null) {
            try {
                if (!con.isClosed()){
                    con.close();
                }
            } catch (SQLException e) { }
            finally {
                con = null;
            }
        }
    }

}
