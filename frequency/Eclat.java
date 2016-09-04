package frequency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

public class Eclat extends FrequentElemCalculation {

	public Eclat(HashMap<TreeSet<Integer>, HashMap<Integer, HashSet<Integer>>> data, double frequence) {
		super(data, frequence);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<Integer, ArrayList<LinkedList<Integer>>> calculateFrequency() {
		// TODO Auto-generated method stub
		setGrade(0);
		setNotFinished(true);
		HashMap<Integer, LinkedHashMap<LinkedList<Integer>, HashSet<Integer>>> frequentElemMap = new HashMap<>();
		LinkedHashMap<LinkedList<Integer>, HashSet<Integer>> preFrequentMap = null;
		while (isNotFinished()) {
			LinkedHashMap<LinkedList<Integer>, HashSet<Integer>> frequentMap = connect(preFrequentMap);
			if (!frequentMap.isEmpty()) {
				setGrade(getGrade() + 1);
				frequentElemMap.put(getGrade(), frequentMap);
				preFrequentMap = frequentMap;
			} else
				setNotFinished(false);
		}
		HashMap<Integer, ArrayList<LinkedList<Integer>>> mapReturned = new HashMap<>();
		for (int i = 1; i <= getGrade(); i++) {
			Set<LinkedList<Integer>> set = frequentElemMap.get(i).keySet();
			ArrayList<LinkedList<Integer>> frequentList = new ArrayList<>(set.size());
			frequentList.addAll(set);
			mapReturned.put(i, frequentList);
		}
		return mapReturned;
	}

	public LinkedHashMap<LinkedList<Integer>, HashSet<Integer>> connect(
			LinkedHashMap<LinkedList<Integer>, HashSet<Integer>> preFrequentMap) {
		LinkedHashMap<LinkedList<Integer>, HashSet<Integer>> frequentMap = new LinkedHashMap<>();
		if (getGrade() == 0) {
			for (int elem : getElement()) {
				HashSet<Integer> occur = new HashSet<>();
				for (int i = 1; i <= getTable().size(); i++) {
					if (getTable().get(i).contains(elem)) {
						occur.add(i);
					}
				}
				if (occur.size() >= getFrequentNumRequirement()) {
					LinkedList<Integer> key = new LinkedList<>();
					key.add(elem);
					frequentMap.put(key, occur);
				}
			}
		} else {
			@SuppressWarnings("unchecked")
			LinkedList<Integer>[] preFrequentArray = new LinkedList[0];
			preFrequentArray = preFrequentMap.keySet().toArray(preFrequentArray);
			for (int i = 0; i < preFrequentArray.length - 1; i++) {
				for (int j = i + 1; j < preFrequentArray.length; j++) {
					LinkedList<Integer> list1 = preFrequentArray[i];
					LinkedList<Integer> list2 = preFrequentArray[j];
					ListIterator<Integer> litr1 = list1.listIterator();
					ListIterator<Integer> litr2 = list2.listIterator();
					int countDown = getGrade();
					boolean canConnect = true;
					while (canConnect && --countDown > 0 && litr1.hasNext())
						if (litr1.next() != litr2.next())
							canConnect = false;
					if (canConnect) {
						HashSet<Integer> set1 = preFrequentMap.get(list1);
						HashSet<Integer> set2 = preFrequentMap.get(list2);
						HashSet<Integer> set3 = new HashSet<>();
						for (Integer elem : set1) {
							if (set2.contains(elem))
								set3.add(elem);
						}
						if (set3.size() >= getFrequentNumRequirement()) {
							LinkedList<Integer> list3 = new LinkedList<>();
							list3.addAll(list1);
							list3.add(list2.getLast());
							frequentMap.put(list3, set3);
						}
					}
				}
			}
		}
		return frequentMap;
	}

}
