package util;

import java.util.*;
import java.io.*;

public class BuildTheData {

	private HashMap<TreeSet<Integer>, HashMap<Integer, HashSet<Integer>>> data;

	public BuildTheData(String filename) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("没有这个文件。");
			e.printStackTrace();
		}
		TreeSet<Integer> elements = new TreeSet<>();
		HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
		Boolean unfinished = true;
		int lineNum = 1;
		try {
			do {
				String line = br.readLine();
				String[] dataStr = line.trim().split(" ");
				if (dataStr == null || dataStr.length == 0 || dataStr[0] == null || dataStr[0].length() == 0)
					unfinished = false;
				else {
					HashSet<Integer> lineData = new HashSet<>();
					for (int i = 0; i < dataStr.length; i++) {
						Integer elemData = null;
						try {
							elemData = new Integer(dataStr[i]);
						} catch (NumberFormatException e) {
							System.err.println(
									dataStr[i] + "|" + line + "|" + i + "\n" + dataStr[i].length() + "\n" + lineNum);
							e.printStackTrace();
						}
						lineData.add(elemData);
						if (!elements.contains(elemData))
							elements.add(elemData);
					}
					map.put(lineNum, lineData);
					lineNum++;
				}
			} while (unfinished);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("读文件错误.");
			e.printStackTrace();
		}
		if (unfinished == true)
			System.err.println("读取数据失败");
		else {
			data = new HashMap<>();
			data.put(elements, map);
		}
	}

	public HashMap<TreeSet<Integer>, HashMap<Integer, HashSet<Integer>>> getData() {
		return data;
	}

}
