package com.arun.parallel;

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

	public static ReturnType method(String methodName) {
		return new Signature.SignatureBuilder(methodName);
	}

	public interface ReturnType {
		Build returnType(Class<? extends Object> returnType);
	}

	public interface Build {
		Build argsList(List<? extends Object> args);

		Build argTypes(List<Class<? extends Object>> argTypes);

		Signature build();
	}

	public static class SignatureBuilder implements ReturnType, Build {
		private Signature signature = new Signature();

		public SignatureBuilder(String method) {
			signature.name = method;
		}

		public SignatureBuilder returnType(Class<? extends Object> returnType) {
			signature.returnType = returnType;
			return this;
		}

		public SignatureBuilder argsList(List<? extends Object> args) {
			signature.args = args;
			return this;
		}

		public SignatureBuilder argTypes(List<Class<? extends Object>> argTypes) {
			signature.argTypes = argTypes;
			return this;
		}

		public Signature build() {
			return signature;
		}

	}
}
