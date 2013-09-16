package com.kitammuort.osb.osbtojava;

public class RoutingCase extends Block implements Action {
    String operator;
    String value;
    Route route;
    
    public RoutingCase(String operator, String value) {
	super();
	this.operator = operator;
	this.value = value;
    }
    
}
