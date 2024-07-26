package com.baina;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CodingPractices {

	private static Map<String, Long> collect;

	public static void main(String[] args) {

		String str = "naresh is a good boy!";

		collect = Arrays.asList(str.replace(" ", "").split("")).stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		System.out.println(collect);

		List<Entry<String, Long>> collect2 = collect.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toList());

		Entry<String, Long> entry = collect2.get(0);

		System.out.println(entry.getKey() + "------>" + entry.getValue());
	}

}
