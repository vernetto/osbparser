package com.kitammuort.osb.osbtojava;

public class Replace implements Action {
    String varName;
    String xpathText;
    XqueryText xqueryText;
    XqueryTransform xqueryTransform;
    
    public Replace(String varName, String xpathText, XqueryText xqueryText) {
	super();
	this.varName = varName;
	this.xpathText = xpathText;
	this.xqueryText = xqueryText;
    }

    public Replace(String varName, String xpathText, XqueryTransform xqueryTransform) {
	super();
	this.varName = varName;
	this.xpathText = xpathText;
	this.xqueryTransform = xqueryTransform;
    }
    
}
