package datacleaning.attributecount;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.org.apache.xpath.internal.operations.And;

public class AttributeCount {
	/**
	 * ������ʽ���Ԥ����
	 */
	public void regexResultPretreatment(){
		
	}
	
	/**
	 * 
	 * @param regexResult
	 *        
	 * @param countResult
	 *        �洢ͳ�ƽ����ÿ��hashmap��ʾ���е�����������
	 */
	public void countAttribute(ArrayList<ArrayList<String>> regexResult, ArrayList<HashMap<String, Integer>> countResult){
		
		int j = 0;
		//����������ÿһ��
		for (ArrayList<String> oneLine : regexResult) {
			//����ÿ�����Ե�ƥ����
			for (int i = 0; i < oneLine.size(); i++) {
				String attribute = oneLine.get(i).split(";")[2];
				if(countResult.size() == 0){
					HashMap<String, Integer> firstItem = new HashMap<String, Integer>();
					firstItem.put(attribute, 1);
					countResult.add(firstItem);
				}else if (countResult.size() <= i) {
					HashMap<String, Integer> currentItem = new HashMap<String, Integer>();
					currentItem.put(attribute, 1);
					countResult.add(currentItem);
				}else {
					HashMap<String, Integer> tempItem = countResult.get(i);
					if (tempItem.containsKey(attribute)) {
						if(attribute.equals("Telephone") && i == 0){
							System.out.println(j);
						}
						Integer attributeCount = tempItem.get(attribute);
						tempItem.put(attribute, attributeCount+1);
					}else{
						tempItem.put(attribute, 1);
					}
					countResult.set(i, tempItem);
				}
			}
			j = j+1;
		}
	}
	/**
	 * ͨ��ͳ�ƵĽ��������������ÿһ�е�����
	 */
	public void analyseAttribute(ArrayList<HashMap<String, Integer>> countResult){
		ArrayList<String> attributeListResult = new ArrayList<String>();
		
	}
}
