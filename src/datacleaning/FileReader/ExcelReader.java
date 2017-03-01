package datacleaning.FileReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	/**
	 * 解析xls及xlsx文件，最终返回文件内容
	 * @param fileName
	 *     待解析的文件名
	 * @return
	 *     返回二维数组链表，每个二维数组存储一个sheet中的全部内容
	 */
	public ArrayList<ArrayList<ArrayList<String>>> readExcel(String fileName){
		ArrayList<ArrayList<ArrayList<String>>> sheetArrayList = new ArrayList<ArrayList<ArrayList<String>>>();
		InputStream inp = null;
		try {
			inp = new FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			Workbook workbook = WorkbookFactory.create(inp);
			int numOfSheets = workbook.getNumberOfSheets();
			if(numOfSheets != 0){
				ArrayList<ArrayList<String>> items = null;
				//遍历每个sheet
				for (int i = 0; i < numOfSheets; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					items = new ArrayList<ArrayList<String>>();
					//遍历一个sheet的每一行
					for (int j = 0; j <= sheet.getLastRowNum(); j++) {
						ArrayList<String> item = new ArrayList<String>();
						Row row = sheet.getRow(j);
						//如果是空表
						if(row == null){
							continue;
						}
						//遍历一行中的每个格
						for (int k = 0; k < row.getLastCellNum(); k++) {
							Cell cell = row.getCell(k);
							item.add(cell.toString());
						}
						items.add(item);
					}
					//如果items不为空，也就是sheet不为空
					if(items.size() != 0){
						sheetArrayList.add(items);
					}
				}
				
			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sheetArrayList;
	}
	/**
	 * 解析xls及xlsx文件，按照指定概率返回表格中的行
	 * @param fileName
	 *     待解析的文件名
	 * @param percent
	 *     以此比例返回excel中的行，0<percent<1
	 * @return
	 *     返回二维数组链表，每个二维数组存储一个sheet中以制定概率返回的内容
	 */
	public ArrayList<ArrayList<ArrayList<String>>> randomReadExcelByPercent(double percent, String fileName){
		ArrayList<ArrayList<ArrayList<String>>> sheetArrayList = new ArrayList<ArrayList<ArrayList<String>>>();
		InputStream inp = null;
		Random random = new Random();
		try {
			inp = new FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Workbook workbook = WorkbookFactory.create(inp);
			int numOfSheets = workbook.getNumberOfSheets();
			if(numOfSheets != 0){
				ArrayList<ArrayList<String>> items = null;
				//遍历每个sheet
				for (int i = 0; i < numOfSheets; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					items = new ArrayList<ArrayList<String>>();
					//遍历一个sheet的每一行
					for (int j = 0; j <= sheet.getLastRowNum(); j++) {
						ArrayList<String> item = new ArrayList<String>();
						Row row = sheet.getRow(j);
						//如果是空表
						if(row == null){
							continue;
						}
						//以指定概率确定是否保留该行
						double randomNum = random.nextDouble();
						if(randomNum > percent){
							//System.out.println(randomNum);
							continue;
						}
						//遍历一行中的每个格
						for (int k = 0; k < row.getLastCellNum(); k++) {
							Cell cell = row.getCell(k);
							
								item.add(cell.toString());
						}
						items.add(item);
					}
					if(items.size() != 0){
						sheetArrayList.add(items);
					}
				}
			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sheetArrayList;
	}
}
