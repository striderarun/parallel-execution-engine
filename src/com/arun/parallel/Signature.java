package com.arun.parallel;

import java.util.List;

public class Signature<T, E> {

	private String name;
	private Class<T> returnType;
	private List<Class<E>> argTypes;
	private List<Object> args;
	
	public Signature(String name, Class<T> returnType) {
		super();
		this.name = name;
		this.returnType = returnType;
	}
	
	public Signature(String name, Class<T> returnType, List<Object> args, List<Class<E>> argTypes) {
		this(name, returnType);
		this.args = args;
		this.argTypes = argTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<T> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<T> returnType) {
		this.returnType = returnType;
	}

	public List<Class<E>> getArgTypes() {
		return argTypes;
	}

	public void setArgTypes(List<Class<E>> argTypes) {
		this.argTypes = argTypes;
	}

	public List<Object> getArgs() {
		return args;
	}

	public void setArgs(List<Object> args) {
		this.args = args;
	}
	
	
	
}
