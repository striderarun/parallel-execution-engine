package com.arun.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.arun.parallel.ParallelProcessor;
import com.arun.parallel.Signature;

public class Client {

	private static boolean f1() {
		//Do something
    	try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    private static boolean f2() {
    	//Do something
    	try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    private static boolean f3() {
    	//Do something
    	try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
	public static void normalExecutor() {
    	long startTime = System.nanoTime();
    	boolean condition1 = f1();
    	boolean condition2 = f2();
    	boolean condition3 = f3();
    	System.out.println(condition1);
    	System.out.println(condition2);
    	System.out.println(condition3);
    	long executionTime = (System.nanoTime() - startTime) / 1000000;
    	System.out.printf("\nTotal elapsed time is %d", executionTime);
    }
	
	public static void parallelExecutor() {
    	long startTime = System.nanoTime();
		List<Boolean> result = ParallelProcessor.parallelExecutor(() -> f1(), () -> f2(), () -> f3());
		result.forEach(s -> System.out.println(s));
    	long executionTime = (System.nanoTime() - startTime) / 1000000;
    	System.out.printf("\nTotal elapsed time is %d", executionTime);
    }
	
	public static void dependencyNormalExecutor() {
    	long startTime = System.nanoTime();
    	BusinessService service = new BusinessService();
    	boolean query1 = service.executeQueryOne();
    	boolean query2 = service.executeQueryTwo(1);
    	boolean query3 = service.executeQueryThree("arun");
    	String query4 = service.executeQueryFour();
    	Integer query5 = service.executeQueryFive();
    	
    	System.out.println(query1);
    	System.out.println(query2);
    	System.out.println(query3);  
    	System.out.println(query4);    	
    	System.out.println(query5);    	
    	long executionTime = (System.nanoTime() - startTime) / 1000000;
    	System.out.printf("\nTotal elapsed time is %d", executionTime);
    }
	
	
	
	@SuppressWarnings(value = { "unchecked" })
	public static <T,E> void dependencyAdvancedExecutor() {
    	long startTime = System.nanoTime();
    	BusinessService service = new BusinessService();
    	Map<Object, List<Signature>> inputMap = new HashMap<>();
    	List<Signature> signatures = new ArrayList<>();
    	signatures.add(new Signature("executeQueryOne", Boolean.class));
    	signatures.add(new Signature("executeQueryTwo", Boolean.class, Arrays.asList(1), Arrays.asList(Integer.class)));
    	signatures.add(new Signature("executeQueryThree", Boolean.class, Arrays.asList("Arun"), Arrays.asList(String.class)));
    	signatures.add(new Signature("executeQueryFour", String.class));
    	signatures.add(new Signature("executeQueryFive", Integer.class));

    	inputMap.put(service, signatures);
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
//		normalExecutor();
//		parallelExecutor();
//		dependencyNormalExecutor();
		dependencyAdvancedExecutor();
	}

	
}
