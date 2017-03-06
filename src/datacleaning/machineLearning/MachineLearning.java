package datacleaning.machineLearning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import datacleaning.Util.Operations;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class MachineLearning {
	String fileName = "D:\\temp.arff";
	String modelfileNameString = "D:\\J48.model";
	public void createModel(String fileName, String targetFileName) throws Exception{
		
		Classifier cls = new J48();
		
		Instances inst = new Instances(new BufferedReader(new FileReader(fileName)));
		
		inst.setClassIndex(inst.numAttributes() - 1);
		
		cls.buildClassifier(inst);
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(targetFileName));
		oos.writeObject(cls);
		oos.flush();
		oos.close();
	}
	
	public Classifier loadModel(String fileName) throws ClassNotFoundException, IOException{
		ObjectInputStream oisInputStream;
		oisInputStream = new ObjectInputStream(new FileInputStream(fileName));
		Classifier cls = (Classifier)oisInputStream.readObject();
		oisInputStream.close();
		return cls;
	}
	
	public void doPredict(ArrayList<String> dataToPredict, ArrayList<String> targetAttributeResult) throws Exception{
		FeatureExtraction featureExtraction = new FeatureExtraction();
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		String attributeSet = Operations.arraylistToString(targetAttributeResult, ",");
		PrepareWekeFileArff.writeHeadtoArff(fileName, attributeSet);
		for (String string : dataToPredict) {
			ArrayList<String> featureSet = featureExtraction.getFeatureSet(string);
			String dataToWrite = Operations.arraylistToString(featureSet, ",") + "," + "Name";
			Operations.writeOneLineToFile(dataToWrite, fileName);
		}
		
		Instances inst = new Instances(new BufferedReader(new FileReader(fileName)));
		inst.setClassIndex(inst.numAttributes() - 1);
		
		Classifier cls = loadModel(modelfileNameString);
		for (int j = 0; j < dataToPredict.size(); j++) {
			int position = (int) cls.classifyInstance(inst.instance(j));
			System.out.println(targetAttributeResult.get(position));
		}
	}
}
