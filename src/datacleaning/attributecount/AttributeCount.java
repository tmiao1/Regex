package datacleaning.attributecount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sun.tools.jar.resources.jar;

public class AttributeCount {
	/**
	 * 正则表达式结果预处理
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
				//其中一个属性在另一个属性中
				else if (end > currentStart && end < currentEnd && start > currentStart) {
					break;
				}
				//两个属性部分重叠
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
	 *        存储统计结果，每个hashmap表示该列的属性组成情况
	 */
	public static void countAttribute(ArrayList<ArrayList<String>> regexResult, ArrayList<HashMap<String, Integer>> countResult){
		
		int j = 1;
		//遍历正则结果每一行
		for (ArrayList<String> oneLine : regexResult) {
			//遍历每个属性的匹配结果
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
	 * 通过统计的结果，分析出最终每一列的属性
	 */
	public static HashMap<String, Integer> analyseAttribute(ArrayList<HashMap<String, Integer>> countResult){
		HashMap<String, Integer> attributeListResult = new HashMap<String,Integer>();
		for (HashMap<String, Integer> hashMap : countResult) {
			Iterator<Entry<String, Integer>> iterator = hashMap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, Integer> entry = iterator.next();
				String attribute = entry.getKey();
				Integer count = entry.getValue();
				if (attributeListResult.get(attribute) != null) {
					count = count + attributeListResult.get(attribute);
					attributeListResult.put(attribute, count);
				}else {
					attributeListResult.put(attribute, count);
				}
			}
		}
		return attributeListResult;
	}
	
	public static List<Map.Entry<ArrayList<String>, Integer>> countRegexResultByLine(ArrayList<ArrayList<String>> regexResult){
		HashMap<ArrayList<String>, Integer> resultHashMap = new HashMap<ArrayList<String>, Integer>();
		int j = 1;
		for (ArrayList<String> regexResultInOneLine : regexResult) {
			ArrayList<String> allAttributeInOneLine = new ArrayList<String>();
			for(String attributeString : regexResultInOneLine){
				String attribute = attributeString.split(";")[2];
				allAttributeInOneLine.add(attribute);
			}
			Collections.sort(allAttributeInOneLine);
			ArrayList<String> tempArrayList = new ArrayList<String>();
			tempArrayList.add("Address");
			tempArrayList.add("Money");
			tempArrayList.add("Name");
			tempArrayList.add("Telephone");
			tempArrayList.add("Unknown");
			tempArrayList.add("Unknown");
			Collections.sort(tempArrayList);
			if (allAttributeInOneLine.equals(tempArrayList)) {
				System.out.println(j);
			}
			if (resultHashMap.containsKey(allAttributeInOneLine)) {
				int count = resultHashMap.get(allAttributeInOneLine);
				resultHashMap.put(allAttributeInOneLine, count+1);
			}else{
				resultHashMap.put(allAttributeInOneLine, 1);
			}
			j = j+1;
		}
		List<Map.Entry<ArrayList<String>, Integer>> list = new ArrayList<Map.Entry<ArrayList<String>, Integer>>(resultHashMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<ArrayList<String>, Integer>>() {  
            //降序排序  
            @Override  
            public int compare(Entry<ArrayList<String>, Integer> o1, Entry<ArrayList<String>, Integer> o2) {  
                //return o1.getValue().compareTo(o2.getValue());  
                return o2.getValue().compareTo(o1.getValue());  
            }  
        }); 
		return list;
	}
}
