import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DatabaseConnection {
	public static void insertData(String instanceid,String metric_name,int metric_value){
		DateFormat timeStampvalue = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
     	Calendar calobj = Calendar.getInstance();
     	String timeStamp=timeStampvalue.format(calobj.getTime());
     	
        Connection conn = null;
 		PreparedStatement preparedStatement = null;

 		try {
 			conn = DriverManager
 			.getConnection("jdbc:mysql://amaze.cv7bdmmve4xe.us-west-2.rds.amazonaws.com:3306/Amaze?user=Amaze&password=Amazecloud");
 			//.getConnection("jdbc:mysql://localhost:3306/CMPE273?user=root&password=pass");
 			conn.setAutoCommit(false);
 			preparedStatement = conn
						.prepareStatement("insert into metric_data values (?,?,?,?,?);");
				preparedStatement.setString(1, metric_name);
				preparedStatement.setString(2, instanceid);
				preparedStatement.setLong(3, calobj.getTimeInMillis());
				preparedStatement.setDouble(4, metric_value);
				preparedStatement.setString(5, timeStamp);		
				preparedStatement.executeUpdate();
 			
 			}catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.commit();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				}
			}
	}

}
