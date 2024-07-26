package com.baina;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PracticeCoding {

	public static void main(String[] args) {
		
		List<Integer> list=new ArrayList<>();
		list.add(5);
		list.add(2);

		list.add(7);

		list.add(11);

		list.add(9);
		System.out.println(list);
		Collections.sort(list, Comparator.reverseOrder());
		System.out.println(list);


	}

}
