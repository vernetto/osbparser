package com.kitammuort.osb.osbtojava;

public class XSLTTransform extends TransformAbstract {

    public XSLTTransform(String fileName) {
	super(fileName);
    }
    
    public XSLTTransform addParameter(String param) {
	parameters.put(param, param);
	return this;
    }

}
