package com.arun.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public static final Logger LOGGER = LoggerFactory.getLogger(ParallelProcessor.class);

	/**
	 * Execute the list of Supplier objects via CompletableFuture
	 * @param suppliers
	 * @param <T>
	 * @return
	 */
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

	/**
	 * Create a list of Supplier objects from the list of method signatures to bex executed in parallel
	 * @param inputMap
	 * @param <T>
	 * @return
	 */
    public static <T> List<T> genericParallelExecutor(Map<Object, List<Signature>> inputMap) {
		List<Supplier<T>> suppliersList = new ArrayList<>();
    	try {
			for(Entry<Object, List<Signature>> entry: inputMap.entrySet()) {
				for(Signature signature: entry.getValue()) {
					suppliersList.add(SupplierFactory.createGenericReflectionSupplier(entry.getKey(), signature.getName(), signature.getReturnType(), signature));
				}
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return execute(suppliersList);
    }
}
