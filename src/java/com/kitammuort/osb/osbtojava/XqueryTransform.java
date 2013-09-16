package com.kitammuort.osb.osbtojava;



public class XqueryTransform extends TransformAbstract {

    public XqueryTransform(String fileName) {
	super(fileName);
    }
    
    public XqueryTransform addVariableAssignment(String variableName, XqueryText xqueryText) {
	// TODO
	return this;
    }

    public XqueryTransform addParameter(String parameterName, String parameterValue) {
	parameters.put(parameterName, parameterValue);
	return this;
    }
}
