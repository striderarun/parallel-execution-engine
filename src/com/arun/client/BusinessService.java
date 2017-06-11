package com.arun.client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BusinessService {

	public Boolean executeQueryOne() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return false;
	}
	
	public List<Integer> executeQueryTwo(Integer i) {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return Arrays.asList(1,2,3);
	}
	
	public List<Student> executeQueryThree(List<String> names) {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return names.stream().map(s -> new Student(s, Long.valueOf(20), true)).collect(Collectors.toList());
	}
	
	public String executeQueryFour() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return "done";
	}
	
	public Integer executeQueryFive() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return 2;
	}

	public Student execQuerySix(String name, Long age, Boolean passed) {
		Student student = new Student();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		student.setName(name);
		student.setAge(age);
		student.setHasPassed(passed);
		return student;
	}

	public void execQuerySeven(Map<String, Integer> map) {
		System.out.println("Executing seven");
		for(Map.Entry<String, Integer> entry: map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}
