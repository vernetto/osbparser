package com.kitammuort.osb.osbtojava;

public class Assign implements Action {
    String variable;
    XqueryText xqueryText;
    TransformAbstract transform;

    public Assign(String variable, XqueryText xqueryText) {
	this.variable = variable;
	this.xqueryText = xqueryText;
    }

    public Assign(String variable, TransformAbstract transform) {
	this.variable = variable;
	this.transform = transform;
    }

}
