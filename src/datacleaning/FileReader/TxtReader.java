package datacleaning.FileReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TxtReader {
	
	public ArrayList<String> readTxtFile(String fileName){
		//存储结果，Arraylist
		ArrayList<String> result = new ArrayList<String>();
		int count = 0;
		String encoding = "";
		File file = new File(fileName);
		if(!file.exists()){
			return result;
		}
		InputStreamReader reader;
		
		try {
			// 建立一个输入流对象reader 
			reader = new InputStreamReader(  
			        new FileInputStream(file),"UTF-8");
			
			// 建立一个对象，它把文件内容转成计算机能读懂的语言
	        BufferedReader br = new BufferedReader(reader);   
	        
	        String line = "";
	        
	        while (line != null) {  
	        	// 一次读入一行数据  
	            line = br.readLine(); 
	            if(line != null){
		            result.add(line);
	            }
	            count = count+1;
	            if(count >= Integer.MAX_VALUE-8){
	            	System.out.println("data too big to read, get part of data");
	            	break;
	            }
	        }
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
