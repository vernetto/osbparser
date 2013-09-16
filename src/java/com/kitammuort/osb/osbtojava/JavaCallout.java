package com.kitammuort.osb.osbtojava;

public class JavaCallout implements Action {
    String varName; 
    String archive; 
    String className; 
    String method; 
    String[] params;
    
    public JavaCallout(String varName, String archive, String className, String method, String[] params) {
	this.varName = varName;
	this.archive = archive;
	this.className = className;
	this.method = method;
	this.params = params;
    }

}
