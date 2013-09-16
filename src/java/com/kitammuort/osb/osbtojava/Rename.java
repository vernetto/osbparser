package com.kitammuort.osb.osbtojava;

public class Rename implements Action {
    String varName;
    String xpathText;
    String namespace;
    
    public Rename(String varName, String xpathText, String namespace) {
	this.varName = varName;
	this.xpathText = xpathText;
	this.namespace = namespace;
    }

}
