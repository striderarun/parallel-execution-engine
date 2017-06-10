package com.arun.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ParallelProcessor {

    public static <T> List<T> execute(List<Supplier<T>> suppliers) {
    	ExecutorService executor = Executors.newCachedThreadPool();
    	List<CompletableFuture<T>> futures = new ArrayList<>();
        try {
        	for (Supplier<T> supplier: suppliers) {
        		futures.add(CompletableFuture.supplyAsync(supplier, executor));
        	}
        	return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        } finally {
            executor.shutdown();
        }
    }
    
    public static List<Boolean> parallelExecutor(BooleanConsumer... booleanConsumers) {
    	List<Supplier<Boolean>> suppliersList = new ArrayList<>();
    	if (booleanConsumers != null && booleanConsumers.length > 0) {
    		for(int i=0; i<booleanConsumers.length; i++) {
    			suppliersList.add(SupplierFactory.createSupplier(booleanConsumers[i]));
    		}
    	}
    	return execute(suppliersList);
    }
    
    public static List<Boolean> dependentParallelExecutor(Map<Object, List<String>> inputMap) throws NoSuchMethodException, SecurityException {
    	List<Supplier<Boolean>> suppliersList = new ArrayList<>();
    	for(Entry<Object, List<String>> entry: inputMap.entrySet()) {
    		for(String methodName: entry.getValue()) {
        		suppliersList.add(SupplierFactory.createReflectionSupplier(entry.getKey(), methodName));
    		}
    	}
    	return execute(suppliersList);
    }

    public static <T,E> List<T> genericParallelExecutor(Map<Object, List<Signature>> inputMap) throws NoSuchMethodException, SecurityException {
    	List<Supplier<T>> suppliersList = new ArrayList<>();
    	for(Entry<Object, List<Signature>> entry: inputMap.entrySet()) {
    		for(Signature signature: entry.getValue()) {
            	suppliersList.add(SupplierFactory.createGenericReflectionSupplier(entry.getKey(), signature.getName(), signature.getReturnType(), signature));	
        	}
    	}
    	return execute(suppliersList);
    }
   
    
	
}
