package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import util.BuildTheData;
import frequency.Apriori;
import frequency.Eclat;

public class DataMining_AssociationRule_MainFunction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 1)
			System.err.println("必须有一个参数.");
		else {
			long buildBegin = System.currentTimeMillis();
			BuildTheData bd = new BuildTheData(args[0]);
			long buildEnd = System.currentTimeMillis();
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("Data"));
				bw.write(bd.getData().toString());
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HashMap<String, HashMap<Integer, ? extends List<? extends List<Integer>>>> frequencyCollection = new HashMap<>();
			Apriori ap = new Apriori(bd.getData(), 0.9);
			long calculateBeginAp = System.currentTimeMillis();
			frequencyCollection.put("AP", ap.calculateFrequency());
			long calculateEndAp = System.currentTimeMillis();
			Eclat ec = new Eclat(bd.getData(), 0.9);
			long calculateBeginEc = System.currentTimeMillis();
			frequencyCollection.put("EC", ec.calculateFrequency());
			long calculateEndEc = System.currentTimeMillis();
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("FrequncyCollection"));
				String[] keyStr = { "AP", "EC" };
				for (String key : keyStr) {
					HashMap<Integer, ? extends List<? extends List<Integer>>> frequentMap = frequencyCollection
							.get(key);
					for (int i = 1; i <= frequentMap.size(); i++) {
						bw.write("频繁" + i + "项集:\n" + frequentMap.get(i).toString() + "\n\n");
					}
					bw.write('\n');
				}
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("构建待挖掘数据集所用时间:" + (buildEnd - buildBegin) + "ms");
			System.out.println("Apriori算法计算频繁项集所用时间:" + (calculateEndAp - calculateBeginAp) + "ms");
			System.out.println("Eclat算法计算频繁项集所用时间:" + (calculateEndEc - calculateBeginEc) + "ms");
		}
	}

}
