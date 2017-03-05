package datacleaning.machineLearning;

import java.util.ArrayList;

import datacleaning.Util.Operations;

public class PrepareWekeFileArff {
	public static void writeArff(ArrayList<ArrayList<String>> finalResult, ArrayList<String> fileReadingResult
			,String fileName){
		FeatureExtraction featureExtraction = new FeatureExtraction();
		
		writeHeadtoArff(fileName);
		
		for (int i = 0; i < finalResult.size(); i++) {
			ArrayList<String> oneLineInFinalResult = finalResult.get(i);
			String onelineInFile = fileReadingResult.get(i);
			
			for (String string : oneLineInFinalResult) {
				int start = Integer.parseInt(string.split(";")[0]);
				int end = Integer.parseInt(string.split(";")[1]);
				String attributeString = string.split(";")[2];
				if (start != -1) {
					String valueString = onelineInFile.substring(start, end);
					ArrayList<String> featureSet = featureExtraction.getFeatureSet(valueString);
					String dataToWrite = Operations.arraylistToString(featureSet, ",") + "," + attributeString;
					Operations.writeOneLineToFile(dataToWrite, fileName);
				}
			}
		}
	}
	
	public static void writeHeadtoArff(String fileName){
		Operations.writeOneLineToFile("@RELATION attributeRecognition", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE length  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE numCount  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE chineseCount  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE containsLocationCount  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE containsFirstNameCount  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE firstTwoNum  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE firstThreeNum  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE containsSpecialWordCount  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE containsSpecialmarkCount  NUMERIC", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE containsAt  {0,1}", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE containsDate  {0,1}", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE onlyNum  {0,1,2}", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE onlyEnglish  {0,1,2}", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE onlyChinese  {0,1,2}", fileName);
		Operations.writeOneLineToFile("@ATTRIBUTE containsDot  {0,1,2}", fileName);
		Operations.writeOneLineToFile("@data", fileName);
	}
	
	public static void writeDataToArff(ArrayList<String> dataToWrite, String fileName){
		
		for (String string : dataToWrite) {
			Operations.writeOneLineToFile(string, fileName);
		}
	}
}
