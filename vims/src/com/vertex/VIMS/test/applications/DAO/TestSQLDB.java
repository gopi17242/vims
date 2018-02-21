package com.vertex.VIMS.test.applications.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestSQLDB
{
   public void dbConnect(String db_connect_string,
            String db_userid,
            String db_password)
   {
      try {
         Class.forName("net.sourceforge.jtds.jdbc.Driver");
         Connection conn = DriverManager.getConnection(db_connect_string,
                  db_userid, db_password);
         System.out.println("connected");
         Statement statement = conn.createStatement();
         String queryString = "select * FROM vims_user.USER_TEST where USER_ID='"+"admin"+"'";
         ResultSet rs = statement.executeQuery(queryString);
         while (rs.next()) {
            System.out.println(rs.getString(2));
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args)
   {
	   TestSQLDB connServer = new TestSQLDB();
      connServer.dbConnect("jdbc:jtds:sqlserver://192.168.1.23:1433;\\\\SQLEXPRESS;databaseName=I_VIMS_PROD", "vims_user1","Vertex@123");
   }
}