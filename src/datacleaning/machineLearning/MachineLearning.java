package datacleaning.machineLearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class MachineLearning {
	
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

}
