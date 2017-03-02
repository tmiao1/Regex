package datacleaning.attributecount;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.org.apache.xpath.internal.operations.And;

public class AttributeCount {
	/**
	 * ������ʽ���Ԥ����
	 */
	public static ArrayList<String> regexResultPretreatment(ArrayList<String> result){
		ArrayList<String> sortedResultArrayList = new ArrayList<String>();
		for (String oneAttrubute : result) {
			Integer start = Integer.parseInt(oneAttrubute.split(";")[0]);
			Integer end = Integer.parseInt(oneAttrubute.split(";")[1]);
			String attribute = oneAttrubute.split(";")[2];
			if (sortedResultArrayList.size() == 0) {
				sortedResultArrayList.add(oneAttrubute);
				continue;
			}
			for (int i = 0; i < sortedResultArrayList.size(); i++) {
				Integer currentStart = Integer.parseInt(sortedResultArrayList.get(i).split(";")[0]);
				Integer currentEnd = Integer.parseInt(sortedResultArrayList.get(i).split(";")[1]);
				String currentAttribute = sortedResultArrayList.get(i).split(";")[2];
				if (end < currentStart) {
					sortedResultArrayList.add(i, start + ";" + end + ";" + attribute);
					break;
				}
				else if (start > currentEnd && i+1 == sortedResultArrayList.size()) {
					sortedResultArrayList.add(start + ";" + end + ";" + attribute);
				}
				//����һ����������һ��������
				else if (end > currentStart && end < currentEnd && start > currentStart) {
					break;
				}
				//�������Բ����ص�
				else if (end > currentEnd && start < currentEnd) {
					break;
				}
				else if (end > currentStart && start < currentStart) {
					break;
				}
			}
		}
		return sortedResultArrayList;
	}
	public void regexResultSort(ArrayList<String> result){
		
	}
	/**
	 * 
	 * @param regexResult
	 *        
	 * @param countResult
	 *        �洢ͳ�ƽ����ÿ��hashmap��ʾ���е�����������
	 */
	public void countAttribute(ArrayList<ArrayList<String>> regexResult, ArrayList<HashMap<String, Integer>> countResult){
		
		int j = 1;
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
						if(attribute.equals("Money") && i == 0){
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
