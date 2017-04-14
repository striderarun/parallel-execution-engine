package com.arun.parallel;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

public class SupplierFactory {

	public static Supplier<Boolean> createSupplier(BooleanConsumer consumer) {
		return consumer::accept;
	}
	
	public static Supplier<Boolean> createObjectSupplier(Serializable s, ObjectConsumer consumer) {
		return () -> {
			return consumer.accept(s);
		};
	}
	
	public static Supplier<Boolean> createReflectionSupplier(Object obj, String methodName) throws NoSuchMethodException, SecurityException {
		Method method = obj.getClass().getMethod(methodName);
		return () -> {
			try {
				return (Boolean) method.invoke(obj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			return false;
		};		
	}
	
	public static <T,E> Supplier<T> createGenericReflectionSupplier(Object obj, String methodName, Class<T> type, Signature<T,E> signature) throws NoSuchMethodException, SecurityException {
		if (signature.getArgs() != null && signature.getArgs().size() > 0) {
			return createGenericReflectionSupplierWithArgs(obj, methodName, type, signature.getArgs(), signature.getArgTypes().toArray(new Class[signature.getArgTypes().size()]));
		} else {
			return createGenericReflectionSupplierNoArgs(obj, methodName, type);
		}
	}
	
	public static <T,E> Supplier<T> createGenericReflectionSupplierWithArgs(Object obj, String methodName, Class<T> type, List<Object> args, Class<E>... argTypes) throws NoSuchMethodException, SecurityException {
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
	
	public static <T> Supplier<T> createGenericReflectionSupplierNoArgs(Object obj, String methodName, Class<T> type) throws NoSuchMethodException, SecurityException {
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
