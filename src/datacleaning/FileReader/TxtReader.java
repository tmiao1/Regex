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
		//�洢�����Arraylist
		ArrayList<String> result = new ArrayList<String>();
		int count = 0;
		String encoding = "";
		File file = new File(fileName);
		if(!file.exists()){
			return result;
		}
		InputStreamReader reader;
		
		try {
			// ����һ������������reader 
			reader = new InputStreamReader(  
			        new FileInputStream(file),"UTF-8");
			
			// ����һ�����������ļ�����ת�ɼ�����ܶ���������
	        BufferedReader br = new BufferedReader(reader);   
	        
	        String line = "";
	        
	        while (line != null) {  
	        	// һ�ζ���һ������  
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
