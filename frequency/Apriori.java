package frequency;

import java.util.*;

public class Apriori extends FrequentElemCalculation {

	public Apriori(HashMap<TreeSet<Integer>, HashMap<Integer, HashSet<Integer>>> data, double frequence) {
		super(data, frequence);
		// TODO Auto-generated constructor stub
	}

	public HashMap<Integer, ArrayList<LinkedList<Integer>>> calculateFrequency() {
		setGrade(0);
		setNotFinished(true);
		HashMap<Integer, ArrayList<LinkedList<Integer>>> frequentElemMap = new HashMap<>();
		while (isNotFinished()) {
			ArrayList<LinkedList<Integer>> preFrequentList = frequentElemMap.get(getGrade());
			ArrayList<LinkedList<Integer>> frequentList = connect(preFrequentList);
			frequentList = clip(frequentList, preFrequentList);
			frequentList = vertify(frequentList);
			if (!frequentList.isEmpty()) {
				setGrade(getGrade() + 1);
				frequentElemMap.put(getGrade(), frequentList);
			} else
				setNotFinished(false);
		}
		return frequentElemMap;
	}

	public ArrayList<LinkedList<Integer>> connect(ArrayList<LinkedList<Integer>> preFrequentList) {
		ArrayList<LinkedList<Integer>> frequentList = new ArrayList<>();
		if (getGrade() == 0) {
			for (int elem : getElement()) {
				LinkedList<Integer> fe = new LinkedList<>();
				fe.add(elem);
				frequentList.add(fe);
			}
		} else {
			for (int i = 0; i < preFrequentList.size() - 1; i++) {
				for (int j = i + 1; j < preFrequentList.size(); j++) {
					LinkedList<Integer> list1 = preFrequentList.get(i);
					LinkedList<Integer> list2 = preFrequentList.get(j);
					ListIterator<Integer> litr1 = list1.listIterator();
					ListIterator<Integer> litr2 = list2.listIterator();
					int countDown = getGrade();
					boolean canConnect = true;
					while (canConnect && --countDown > 0 && litr1.hasNext())
						if (litr1.next() != litr2.next())
							canConnect = false;
					if (canConnect) {
						LinkedList<Integer> list3 = new LinkedList<>();
						list3.addAll(list1);
						list3.add(list2.getLast());
						frequentList.add(list3);
					}
				}
			}
		}
		return frequentList;
	}

	public ArrayList<LinkedList<Integer>> clip(ArrayList<LinkedList<Integer>> material,
			ArrayList<LinkedList<Integer>> preFrequentList) {
		if (getGrade() == 0)
			return material;
		ArrayList<LinkedList<Integer>> frequentList = new ArrayList<>();
		for (LinkedList<Integer> elemList : material) {
			int mod, num = 0;
			for (LinkedList<Integer> preElemList : preFrequentList) {
				mod = 0;
				ListIterator<Integer> litr = elemList.listIterator();
				ListIterator<Integer> preLitr = preElemList.listIterator();
				while (preLitr.hasNext() && mod < 2) {
					if (litr.next() != preLitr.next()) {
						mod++;
						preLitr.previous();
					}
				}
				if (mod < 2) {
					num++;
				}
			}
			if (num == getGrade() + 1)
				frequentList.add(elemList);
		}
		return frequentList;
	}

	public ArrayList<LinkedList<Integer>> vertify(ArrayList<LinkedList<Integer>> material) {
		int tableSize = getTable().size();
		ArrayList<LinkedList<Integer>> product = new ArrayList<>();
		for (LinkedList<Integer> elemList : material) {
			int num = 0;
			for (int i = 1; i <= tableSize; i++) {
				boolean contains = true;
				HashSet<Integer> set = getTable().get(i);
				for (Integer elem : elemList) {
					if (!set.contains(elem)) {
						contains = false;
						break;
					}
				}
				if (contains)
					num++;
			}
			if (num >= getFrequentNumRequirement())
				product.add(elemList);
		}
		return product;
	}

}
