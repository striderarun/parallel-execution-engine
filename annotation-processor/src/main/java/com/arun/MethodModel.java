package com.arun;

import java.util.List;

public class MethodModel {

    String name;
    String typeParamsSignature;
    Class<? extends Object> returnType;
    List<Class<? extends Object>> argTypes;
    List<? extends Object> args;
    List<String> argTypeNames;
    List<String> argNames;
    String returnTypeString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeParamsSignature() {
        return typeParamsSignature;
    }

    public void setTypeParamsSignature(String typeParamsSignature) {
        this.typeParamsSignature = typeParamsSignature;
    }

    public Class<? extends Object> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<? extends Object> returnType) {
        this.returnType = returnType;
    }

    public List<Class<? extends Object>> getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(List<Class<? extends Object>> argTypes) {
        this.argTypes = argTypes;
    }

    public List<? extends Object> getArgs() {
        return args;
    }

    public void setArgs(List<? extends Object> args) {
        this.args = args;
    }

    public List<String> getArgTypeNames() {
        return argTypeNames;
    }

    public void setArgTypeNames(List<String> argTypeNames) {
        this.argTypeNames = argTypeNames;
    }

    public String getReturnTypeString() {
        return returnTypeString;
    }

    public void setReturnTypeString(String returnTypeString) {
        this.returnTypeString = returnTypeString;
    }

    public List<String> getArgNames() {
        return argNames;
    }

    public void setArgNames(List<String> argNames) {
        this.argNames = argNames;
    }
}
