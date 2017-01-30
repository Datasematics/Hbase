import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;


public class InsertToPhoenix{




/* Method for creating connection to Phoenix if we call this method it will send the connection instance for phoenix*/
         public static Connection getConnection() throws ClassNotFoundException, SQLException{
//Load the JDBC driver class phoenix
                 Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
                 Connection con;
/*Specify the connection properties 
JDBC url, zookeeper IP address and port number and default hbase meta table “hbase-unsecure”*/
                 con =  DriverManager.getConnection("jdbc:phoenix:103.233.79.140:2181:/hbase-unsecure");

                 return con;
         }


/* this method is for inserting the data to phoenix*/
         public static void insert_to_phoenix( String  record,String tablename) throws Exception {
                // logger.info("record from  insert_to_phoenix"+record);
//call the getconnection method to get the connection
                 Connection con=Phoenix.getConnection();
                 System.out.println(con);

                String values[] =record.split(",");
//Based on number of fields in the record create the PreparedStatement statement 
                 PreparedStatement ps=con.prepareStatement("upsert into "+tablename+" values(?,?)") ;
//After creating the query for inserting based on the data type set the values to //placeholders
                 ps.setInt(1,Integer.parseInt(values[0]));
                 ps.setString(2, values[1]);
//Finally execute the PreparedStatement 
                  ps.executeUpdate();
//commit the changes
          con.commit();

      System.out.println("Value inserted");



              }
