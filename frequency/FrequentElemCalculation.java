package frequency;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public abstract class FrequentElemCalculation {
	private TreeSet<Integer> element;
	private HashMap<Integer, HashSet<Integer>> table;
	private int frequentNumRequirement;
	private int grade;
	private boolean notFinished;

	public FrequentElemCalculation(HashMap<TreeSet<Integer>, HashMap<Integer, HashSet<Integer>>> data,
			double frequence) {
		element = data.keySet().iterator().next();
		table = data.get(element);
		frequentNumRequirement = (int) Math.ceil(table.size() * frequence);
	}

	public abstract HashMap<Integer, ? extends List<? extends List<Integer>>> calculateFrequency();

	public TreeSet<Integer> getElement() {
		return element;
	}

	public HashMap<Integer, HashSet<Integer>> getTable() {
		return table;
	}

	public int getFrequentNumRequirement() {
		return frequentNumRequirement;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isNotFinished() {
		return notFinished;
	}

	public void setNotFinished(boolean notFinished) {
		this.notFinished = notFinished;
	}

}
