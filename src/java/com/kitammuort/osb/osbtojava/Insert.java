package com.kitammuort.osb.osbtojava;

public class Insert implements Action {
    String varName;
    String xpathText;
    String where;
    String xqueryText;
    
    public Insert(String varName, String xpathText, String where, String xqueryText) {
	this.varName = varName;
	this.xpathText = xpathText;
	this.where = where;
	this.xqueryText = xpathText;
    }

}
