package com.arun.parallel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

public class SupplierFactory {

	/**
	 * Generic method which converts any method signature to instance of a Supplier
	 *
	 * @param obj
	 * @param methodName
	 * @param type
	 * @param signature
	 * @param <T>
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static <T> Supplier<T> createGenericReflectionSupplier(Object obj, String methodName, Class<? extends Object> type, Signature signature) throws NoSuchMethodException, SecurityException {
		if (signature.getArgs() != null && signature.getArgs().size() > 0) {
			return createGenericReflectionSupplierWithArgs(obj, methodName, type, signature.getArgs(), signature.getArgTypes().toArray(new Class[signature.getArgTypes().size()]));
		} else {
			return createGenericReflectionSupplierNoArgs(obj, methodName, type);
		}
	}

	/**
	 * Generic method which converts a method signature with arguments to a Supplier instance
	 *
	 * @param obj
	 * @param methodName
	 * @param type
	 * @param args
	 * @param argTypes
	 * @param <T>
	 * @param <E>
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static <T,E> Supplier<T> createGenericReflectionSupplierWithArgs(Object obj, String methodName, Class<? extends Object> type, List<? extends Object> args, Class<E>... argTypes) throws NoSuchMethodException, SecurityException {
		Method argsMethod = obj.getClass().getMethod(methodName, argTypes);		
		return () -> {
			T t = null;
			try {
				return (T)type.cast(argsMethod.invoke(obj, args.toArray(new Object[args.size()])));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			return t;
		};		
	}

	/**
	 * Generic method which converts a method signature without arguments to a Supplier instance
	 *
	 * @param obj
	 * @param methodName
	 * @param type
	 * @param <T>
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static <T> Supplier<T> createGenericReflectionSupplierNoArgs(Object obj, String methodName, Class<? extends Object> type) throws NoSuchMethodException, SecurityException {
		Method noArgsMethod = obj.getClass().getMethod(methodName);
		return () -> {
			T t = null;
			try {
				return (T)type.cast(noArgsMethod.invoke(obj));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			return t;
		};		
	}
	
	
}
