package com.kitammuort.osb.osbtojava;

public class Foreach extends Block implements Action {
    String variable; 
    String xpathText;
    String valueVariable;
    String indexVariable;
    String totalVariable;
    
    public Foreach(String variable, String xpathText, String valueVariable, String indexVariable, String totalVariable) {
	this.variable = variable;
	this.xpathText = xpathText;
	this.valueVariable = valueVariable;
	this.indexVariable = indexVariable;
	this.totalVariable = totalVariable;
    }

}
