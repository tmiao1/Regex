package datacleaning.attributecount;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.Now;

public class ResultOperation {
	public void combineSameAttribute(){
		
	}
	public void rePositionUnknown(){
		
	}
	public static void outputReusltToConsole(ArrayList<String> fileReadingResult,
			ArrayList<ArrayList<String>> regexResult,
			ArrayList<String> targetAttributeResult){
		
		//遍历原始文件及正则结果的每一行
		for (int i = 0; i < fileReadingResult.size(); i++) {
			//原始文件的一行
			String oneLineString = fileReadingResult.get(i);
			//正则结果的一行
			ArrayList<String> oneLineRegex = regexResult.get(i);
			//构造 有标志的正则匹配的结果, 例：23；35；Name；none      none代表没有和任何属性对齐
			ArrayList<String> regexWithFlag = new ArrayList<String>();
			for (String string : oneLineRegex) {
				regexWithFlag.add(string + ";" + "none");
			}
			//构造有标志的最终并集， 例：none；Name
			ArrayList<String> attributeResultWithFlag = new ArrayList<String>();
			for (String string : targetAttributeResult) {
				attributeResultWithFlag.add("none" + ";" + string);
			}
			//遍历一行正则结果中的每个属性
			for(int m = 0; m < regexWithFlag.size(); m++){
				String allAttributeFromRegex = regexWithFlag.get(m);
				String attribute = allAttributeFromRegex.split(";")[2];
				//遍历结果集 寻找相同属性
				for (int n = 0; n < attributeResultWithFlag.size(); n++) {
					String item = attributeResultWithFlag.get(n);
					String compare = item.split(";")[1];
					if (attribute.equals(compare)) {
						allAttributeFromRegex = allAttributeFromRegex.replace("none", compare);
						//讲属性替换none
						regexWithFlag.set(m, allAttributeFromRegex);
						//将整个正则结果替换none
						attributeResultWithFlag.set(n, allAttributeFromRegex);
						
					}
				}
			}
			//最终结果 包含属性和位置
			ArrayList<String> outputResultArrayList = new ArrayList<String>();
			Boolean maybeWrongBoolean = false;
			//遍历结果集
			for (int z = 0; z < attributeResultWithFlag.size(); z++) {
				String string = attributeResultWithFlag.get(z);
				String pre = "";
				String next = "";
				
				if (string.split(";")[0].equals("none")) {
					if(z != 0){
						pre = attributeResultWithFlag.get(z-1);
					}else{  //结果集的第一个 和 正则集的第一个比较
						String nowStringOnRegex = regexWithFlag.get(0);
						String[] strings = nowStringOnRegex.split(";");
						// 如果否是空 说明当前属性识别有误
						if (strings[3].equals("none")) {
							outputResultArrayList.add(strings[0] + ";" + strings[1] + ";" + string.split(";")[1]);
							nowStringOnRegex = nowStringOnRegex.replace("none", string.split(";")[1]);
							regexWithFlag.set(z, nowStringOnRegex);
							attributeResultWithFlag.set(z, nowStringOnRegex);
							maybeWrongBoolean = true;
						}
						else {
							outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
						}
					}
					//通过结果前面的属性 在正则集中找到该属性 那么判断该属性后面的属性 即与当前属性对其的属性是不是也为none 为none有误 不为缺失
					if (regexWithFlag.contains(pre)) {
						int now = regexWithFlag.indexOf(pre)+1;
						//前一个属性是最后一个 缺失
						if (now == regexWithFlag.size()) {
							outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
							continue;
						}
						String nowStringOnRegex = regexWithFlag.get(now);
						String[] strings = nowStringOnRegex.split(";");
						//正则结果前面的有误
						if (strings[3].equals("none")) {
							outputResultArrayList.add(strings[0] + ";" + strings[1] + ";" + string.split(";")[1]);
							nowStringOnRegex = nowStringOnRegex.replace("none", string.split(";")[1]);
							regexWithFlag.set(now, nowStringOnRegex);
							attributeResultWithFlag.set(z, nowStringOnRegex);
							maybeWrongBoolean = true;
						}
						else {//缺失
							outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
						}
					}else{
						outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
					}
				}else{
					String start = string.split(";")[0];
					String end = string.split(";")[1];
					String attr = string.split(";")[2];
					outputResultArrayList.add(start + ";" + end + ";" + attr);
				}
			}
			if (maybeWrongBoolean) {
				System.out.println(fileReadingResult.get(i));
				System.out.println(outputResultArrayList);
			}
			
		}
	}
}
