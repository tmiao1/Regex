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
		
		//����ԭʼ�ļ�����������ÿһ��
		for (int i = 0; i < fileReadingResult.size(); i++) {
			//ԭʼ�ļ���һ��
			String oneLineString = fileReadingResult.get(i);
			//��������һ��
			ArrayList<String> oneLineRegex = regexResult.get(i);
			//���� �б�־������ƥ��Ľ��, ����23��35��Name��none      none����û�к��κ����Զ���
			ArrayList<String> regexWithFlag = new ArrayList<String>();
			for (String string : oneLineRegex) {
				regexWithFlag.add(string + ";" + "none");
			}
			//�����б�־�����ղ����� ����none��Name
			ArrayList<String> attributeResultWithFlag = new ArrayList<String>();
			for (String string : targetAttributeResult) {
				attributeResultWithFlag.add("none" + ";" + string);
			}
			//����һ���������е�ÿ������
			for(int m = 0; m < regexWithFlag.size(); m++){
				String allAttributeFromRegex = regexWithFlag.get(m);
				String attribute = allAttributeFromRegex.split(";")[2];
				//��������� Ѱ����ͬ����
				for (int n = 0; n < attributeResultWithFlag.size(); n++) {
					String item = attributeResultWithFlag.get(n);
					String compare = item.split(";")[1];
					if (attribute.equals(compare)) {
						allAttributeFromRegex = allAttributeFromRegex.replace("none", compare);
						//�������滻none
						regexWithFlag.set(m, allAttributeFromRegex);
						//�������������滻none
						attributeResultWithFlag.set(n, allAttributeFromRegex);
						
					}
				}
			}
			//���ս�� �������Ժ�λ��
			ArrayList<String> outputResultArrayList = new ArrayList<String>();
			Boolean maybeWrongBoolean = false;
			//���������
			for (int z = 0; z < attributeResultWithFlag.size(); z++) {
				String string = attributeResultWithFlag.get(z);
				String pre = "";
				String next = "";
				
				if (string.split(";")[0].equals("none")) {
					if(z != 0){
						pre = attributeResultWithFlag.get(z-1);
					}else{  //������ĵ�һ�� �� ���򼯵ĵ�һ���Ƚ�
						String nowStringOnRegex = regexWithFlag.get(0);
						String[] strings = nowStringOnRegex.split(";");
						// ������ǿ� ˵����ǰ����ʶ������
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
					//ͨ�����ǰ������� ���������ҵ������� ��ô�жϸ����Ժ�������� ���뵱ǰ���Զ���������ǲ���ҲΪnone Ϊnone���� ��Ϊȱʧ
					if (regexWithFlag.contains(pre)) {
						int now = regexWithFlag.indexOf(pre)+1;
						//ǰһ�����������һ�� ȱʧ
						if (now == regexWithFlag.size()) {
							outputResultArrayList.add("-1;-1;" + string.split(";")[1]);
							continue;
						}
						String nowStringOnRegex = regexWithFlag.get(now);
						String[] strings = nowStringOnRegex.split(";");
						//������ǰ�������
						if (strings[3].equals("none")) {
							outputResultArrayList.add(strings[0] + ";" + strings[1] + ";" + string.split(";")[1]);
							nowStringOnRegex = nowStringOnRegex.replace("none", string.split(";")[1]);
							regexWithFlag.set(now, nowStringOnRegex);
							attributeResultWithFlag.set(z, nowStringOnRegex);
							maybeWrongBoolean = true;
						}
						else {//ȱʧ
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
