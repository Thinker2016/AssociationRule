package weka;

import weka.classifiers.functions.MultilayerPerceptron;

public class FunctionExe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] argv = { "-t", "/home/wenzhao/SoftwareFactory/WEKA/wekaFiles/data/西瓜数据集2.0_exceptId.arff", "-L", "0.3",
				"-M", "0.2", "-N", "500", "-V", "0", "-S", "14", "-E", "20", "-H", "21" };
		MultilayerPerceptron.runClassifier(new MultilayerPerceptron(), argv);
	}

}
