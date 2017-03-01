package datacleaning.attributecount;

import java.util.ArrayList;
import java.util.HashMap;

public class AttributeCount {
	
	/**
	 * 
	 * @param regexResult
	 *        
	 * @param countResult
	 *        存储统计结果，每个hashmap表示该列的属性组成情况
	 */
	public void countAttribute(ArrayList<ArrayList<String>> regexResult, ArrayList<HashMap<String, Integer>> countResult){
		
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
						Integer attributeCount = tempItem.get(attribute);
						tempItem.put(attribute, attributeCount+1);
					}else{
						tempItem.put(attribute, 1);
					}
					countResult.set(i, tempItem);
				}
			}
		}
	}
}
