
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Backup {
	 public static Connection getConnection() throws ClassNotFoundException, SQLException{
		 Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
		 Connection con;
		 con =  DriverManager.getConnection("jdbc:phoenix:<ip-address>:2181:/hbase-unsecure");
		 
		 return con;
	 }
	 
	 public static void write() throws ClassNotFoundException, SQLException, IOException{
		 Connection con=getConnection();
		 Statement smt=con.createStatement();
		 String sql="select * from <Table-name>";
		 Date date = new Date() ;
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss") ;
		 File file = new File("/root/phoenix_data/"+dateFormat.format(date) + ".txt") ;
		 FileWriter writer=new FileWriter(file);
		  smt.execute(sql);
		  ResultSet result =  smt.executeQuery(sql);
		  while(result.next()){
			  String s=result.getString(1)+"`!@"+result.getString(2)+"`!@"+result.getString(3)+"`!@"+result.getLong(4)+"`!@"+result.getLong(5)+"`!@"+result.getLong(6)+"`!@"+result.getLong(7)+"`!@"+result.getString(8)+"`!@"+result.getString(9)+"`!@"+result.getString(10)+"`!@"+result.getLong(11)
					  +"`!@"+result.getDate(12)+"`!@"+result.getLong(13)+"`!@"+result.getString(14)+"`!@"+result.getString(15)+"`!@"+result.getString(16)+"`!@"+result.getString(17)+"`!@"+result.getString(18)+"`!@"+result.getLong(19)+"`!@"+result.getString(20)+"`!@"+result.getString(21)+"`!@"+result.getString(22)+"`!@"+result.getString(23)
					  +"`!@"+result.getLong(24)+"`!@"+result.getLong(25)+"`!@"+result.getLong(26)+"`!@"+result.getLong(27)+"`!@"+result.getLong(28)+"`!@"+result.getLong(29)+"`!@"+result.getLong(30)+"`!@"+result.getLong(31)+"`!@"+result.getString(32)+"`!@"+result.getString(33)+"`!@"+result.getString(34)+"`!@"+result.getString(35)+"`!@"+result.getString(36);
			  writer.write(s);
			  writer.write("\n");
		  }
		  writer.close();
		 
	 }
public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
	write();
}
}
