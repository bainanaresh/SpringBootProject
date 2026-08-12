package com.baina;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

public class PracticeCoding {

	public static void main(String[] args) {
		Set<Integer> set = new LinkedHashSet<>();
		Collections.addAll(set, 1, 3, 4, 4, 5, 2, 7, 11, 9); // duplicates ignored by the set
		List<Integer> list = new ArrayList<>(set);

		System.out.println("original (after dedupe): " + list);

		Collections.sort(list, Comparator.reverseOrder());

		System.out.println("deduped & sorted desc (in-place): " + list);
	}

}
