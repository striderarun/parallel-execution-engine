package com.arun.client;

import java.util.concurrent.TimeUnit;

public class BusinessService {

	public Boolean executeQueryOne() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return false;
	}
	
	public Boolean executeQueryTwo(Integer i) {
		try {
			TimeUnit.SECONDS.sleep(1);
			if (i >10) {
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return false;
	}
	
	public Boolean executeQueryThree(String name) {
		try {
			TimeUnit.SECONDS.sleep(1);
			if(name.equals("Arun")) {
				return false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return true;
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
}
