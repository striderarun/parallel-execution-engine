package com.arun.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arun.parallel.ParallelProcessor;
import com.arun.parallel.Signature;

public class Client {

	public static void dependencyNormalExecutor() {
    	long startTime = System.nanoTime();
    	BusinessService service = new BusinessService();
    	boolean query1 = service.executeQueryOne();
    	List<Integer> query2 = service.executeQueryTwo(1);
    	List<Student> query3 = service.executeQueryThree(Arrays.asList("arun", "aisu"));
    	String query4 = service.executeQueryFour();
    	Integer query5 = service.executeQueryFive();
    	Student query6 = service.execQuerySix("strider",Long.valueOf(20),true);
    	Map<String, Integer> maps = new HashMap<>();
    	maps.put("A Song of Ice and Fire", 7);
    	maps.put("Wheel of Time", 14);
    	maps.put("Middle Earth Legendarium", 5);
		service.execQuerySeven(maps);
    	System.out.println(query1);
    	System.out.println(query2);
    	System.out.println(query3);  
    	System.out.println(query4);    	
    	System.out.println(query5);
    	System.out.println(query6);
    	long executionTime = (System.nanoTime() - startTime) / 1000000;
    	System.out.printf("\nTotal elapsed time is %d", executionTime);
    }
	
	public static <T> void dependencyAdvancedExecutor() {
    	long startTime = System.nanoTime();
    	BusinessService service = new BusinessService();
    	BusinessServiceTwo serviceTwo = new BusinessServiceTwo();
    	Map<Object, List<Signature>> inputMap = new HashMap<>();
    	List<Signature> signatures = new ArrayList<>();
		List<Signature> signaturesTwo = new ArrayList<>();
		Map<String, Integer> maps = new HashMap<>();
		maps.put("A Song of Ice and Fire", 7);
		maps.put("Wheel of Time", 14);
		maps.put("Middle Earth Legendarium", 5);

		signatures.add(Signature.method("executeQueryOne")
				.returnType(Boolean.class)
				.build());

		signatures.add(Signature.method("executeQueryTwo")
				.returnType(List.class)
				.argsList(Arrays.asList(1))
				.argTypes(Arrays.asList(Integer.class))
				.build());

		signatures.add(Signature.method("executeQueryThree")
				.returnType(List.class)
				.argsList(Arrays.asList(Arrays.asList("Arun","Aisu")))
				.argTypes(Arrays.asList(List.class))
				.build());

		signatures.add(Signature.method("executeQueryFour")
				.returnType(String.class)
				.build());

		signatures.add(Signature.method("executeQueryFive")
				.returnType(Integer.class)
				.build());

		signatures.add(Signature.method("execQuerySix")
				.returnType(Student.class)
				.argsList(Arrays.asList("Arun", Long.valueOf(27), true))
				.argTypes(Arrays.asList(String.class, Long.class, Boolean.class))
				.build());

		signatures.add(Signature.method("execQuerySeven")
				.returnType(Void.class)
				.argsList(Arrays.asList(maps))
				.argTypes(Arrays.asList(Map.class))
				.build());

		signaturesTwo.add(Signature.method("q2")
				.returnType(String.class)
				.build());

		inputMap.put(service, signatures);
		inputMap.put(serviceTwo, signaturesTwo);
		List<T> result = new ArrayList<>();
		try {
			result = ParallelProcessor.genericParallelExecutor(inputMap);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		result.forEach(s -> System.out.println(s));
    	long executionTime = (System.nanoTime() - startTime) / 1000000;
    	System.out.printf("\nTotal elapsed time is %d", executionTime);
    }
	
	
	
	public static void main(String[] args) {
//		dependencyNormalExecutor();
		dependencyAdvancedExecutor();
	}

	
}
