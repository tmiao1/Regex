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
	 * ����xls��xlsx�ļ������շ����ļ�����
	 * @param fileName
	 *     ���������ļ���
	 * @return
	 *     ���ض�ά��������ÿ����ά����洢һ��sheet�е�ȫ������
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
				//����ÿ��sheet
				for (int i = 0; i < numOfSheets; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					items = new ArrayList<ArrayList<String>>();
					//����һ��sheet��ÿһ��
					for (int j = 0; j <= sheet.getLastRowNum(); j++) {
						ArrayList<String> item = new ArrayList<String>();
						Row row = sheet.getRow(j);
						//����ǿձ�
						if(row == null){
							continue;
						}
						//����һ���е�ÿ����
						for (int k = 0; k < row.getLastCellNum(); k++) {
							Cell cell = row.getCell(k);
							item.add(cell.toString());
						}
						items.add(item);
					}
					//���items��Ϊ�գ�Ҳ����sheet��Ϊ��
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
	 * ����xls��xlsx�ļ�������ָ�����ʷ��ر���е���
	 * @param fileName
	 *     ���������ļ���
	 * @param percent
	 *     �Դ˱�������excel�е��У�0<percent<1
	 * @return
	 *     ���ض�ά��������ÿ����ά����洢һ��sheet�����ƶ����ʷ��ص�����
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
				//����ÿ��sheet
				for (int i = 0; i < numOfSheets; i++) {
					Sheet sheet = workbook.getSheetAt(i);
					items = new ArrayList<ArrayList<String>>();
					//����һ��sheet��ÿһ��
					for (int j = 0; j <= sheet.getLastRowNum(); j++) {
						ArrayList<String> item = new ArrayList<String>();
						Row row = sheet.getRow(j);
						//����ǿձ�
						if(row == null){
							continue;
						}
						//��ָ������ȷ���Ƿ�������
						double randomNum = random.nextDouble();
						if(randomNum > percent){
							//System.out.println(randomNum);
							continue;
						}
						//����һ���е�ÿ����
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
