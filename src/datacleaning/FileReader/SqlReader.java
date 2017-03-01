package datacleaning.FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;


public class SqlReader {
	static String driverClass="com.mysql.jdbc.Driver";  
    static String url = "jdbc:mysql://localhost:3306/test";  
    static String username="root";  
    static String password = "miao"; 
    static String fileName = "C:/Users/think/Desktop/t_prov_city_area_street.sql";  
    /**
     * 执行sql文件，将sql文件中的数据存入test数据库（事先建立好test）
     */
	public static void runSqlFile(){ 
		SQLExec sqlExec = new SQLExec();
		sqlExec.setDriver(driverClass);
		sqlExec.setUrl(url);
		sqlExec.setUserid(username);
		sqlExec.setPassword(password);
		sqlExec.setSrc(new File(fileName));
		sqlExec.setEncoding("utf8");
		//如果有出错的语句继续执行.
		//sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(SQLExec.OnError.class, "continue")));
		//sqlExec.setPrint(true);
		//sqlExec.setOutput(new File("D:/userinfo.txt"));
		sqlExec.setProject(new Project()); 
		sqlExec.execute();
    }
	/**
	 * 从sql文件中获得table name，通过insert into语句找到table
	 * @param fileName
	 *     sql文件的路径
	 * @return
	 *     返回tableName（表名）
	 */
	public static String getTableNameFromSqlFile(String fileName){
		BufferedReader bufferedReader = null;
		if (fileName.endsWith(".sql")) {
			File file = new File(fileName);
			String tempString;
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				while(null != (tempString=bufferedReader.readLine())){
					//通过找到紧跟着insert into的字符串获得table name
					if(tempString.contains("INSERT INTO")){
						String[] words = tempString.split(" ");
						return words[2].replace("`", "");
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else{
			System.out.println("Can't get fileName, because the file type is not sql");
		}
		return null;
	}
	
	public static ArrayList<ArrayList<String>> readInfoFromMySql(String tableName){
		ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
		
		Connection conn = null;
		String sql = "select * from " + tableName;
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, username, password);
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			
			int columnCount = resultSetMetaData.getColumnCount();
			
			ArrayList<String> itemArrayList = new ArrayList<String>();
			for (int i = 1; i <= columnCount; i++) {
				itemArrayList.add(resultSetMetaData.getColumnName(i));
			}
			items.add(itemArrayList);
			
			while(resultSet.next()) {
				ArrayList<String> item = new ArrayList<String>();
				for (int i = 1; i <= columnCount; i++) {
					item.add(resultSet.getString(i));
				}
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
}
