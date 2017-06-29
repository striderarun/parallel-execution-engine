package com.arun.parallel;

import com.arun.MethodModel;

import java.util.List;

public class Signature {

	private String name;
	private Class<? extends Object> returnType;
	private List<? extends Object> args;
	private List<Class<? extends Object>> argTypes;

	private Signature() {}

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

	public static Signature method(MethodModel methodModel) {
		Signature signature = new Signature();
		signature.name = methodModel.getName();
		signature.returnType = methodModel.getReturnType();
		signature.argTypes = methodModel.getArgTypes();
		signature.args = methodModel.getArgs();
		return signature;
	}
}
