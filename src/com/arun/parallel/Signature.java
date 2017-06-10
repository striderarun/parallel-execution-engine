package com.arun.parallel;

import java.util.List;

public class Signature {

	private String name;
	private Class<? extends Object> returnType;
	private List<? extends Object> args;
	private List<Class<? extends Object>> argTypes;

	public Signature(String name, Class<? extends Object> returnType) {
		super();
		this.name = name;
		this.returnType = returnType;
	}
	
	public Signature(String name, Class<? extends Object> returnType, List<? extends Object> args, List<Class<? extends Object>> argTypes) {
		this(name, returnType);
		this.args = args;
		this.argTypes = argTypes;
	}

	public String getName() {
		return name;
	}

	public Class<? extends Object> getReturnType() {
		return returnType;
	}

	public List<Class<? extends Object>> getArgTypes() {
		return argTypes;
	}

	public List<? extends Object> getArgs() {
		return args;
	}
}
