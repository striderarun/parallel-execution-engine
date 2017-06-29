package com.arun.parallel;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
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
	public static <T> Supplier<T> createGenericReflectionSupplier(Object obj, String methodName, Class<? extends Object> type, Signature signature) throws NoSuchMethodException, SecurityException, IllegalAccessException {
		if (signature.getArgs() != null && signature.getArgs().size() > 0) {
			return createGenericReflectionSupplierWithArgs(obj, methodName, type, signature.getArgs(), signature.getArgTypes().toArray(new Class[signature.getArgTypes().size()]));
		} else {
			return createGenericReflectionSupplierNoArgs(obj, methodName, type);
		}
	}

	/**
	 * Generic method which converts a method signature with arguments to a Supplier instance
	 * Uses MethodHandles instead of Reflection API
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
	 * @throws IllegalAccessException
	 */
	public static <T,E> Supplier<T> createGenericReflectionSupplierWithArgs(Object obj, String methodName, Class<? extends Object> type, List<? extends Object> args, Class<E>... argTypes) throws NoSuchMethodException, SecurityException, IllegalAccessException {
		MethodHandle methodHandle = MethodHandles.lookup().findVirtual(obj.getClass(), methodName, MethodType.methodType(type, argTypes));
		MethodHandle ready = methodHandle.bindTo(obj);
		return () -> {
			T t = null;
			try {
				return (T)type.cast(ready.invokeWithArguments(args));
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
			return t;
		};
	}

	/**
	 * Generic method which converts a method signature without arguments to a Supplier instance
	 * Uses MethodHandles instead of Reflection API
	 *
	 * @param obj
	 * @param methodName
	 * @param type
	 * @param <T>
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 */
	public static <T> Supplier<T> createGenericReflectionSupplierNoArgs(Object obj, String methodName, Class<? extends Object> type) throws NoSuchMethodException, SecurityException, IllegalAccessException {
		MethodHandle methodHandle = MethodHandles.lookup().findVirtual(obj.getClass(), methodName, MethodType.methodType(type));
		MethodHandle ready = methodHandle.bindTo(obj);
		return () -> {
			T t = null;
			try {
				return (T)type.cast(ready.invoke());
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
			return t;
		};
	}
	
	
}
