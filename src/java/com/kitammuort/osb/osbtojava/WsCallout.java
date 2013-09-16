package com.kitammuort.osb.osbtojava;

public class WsCallout implements Action {
    String service;
    String requestVariable;
    String responseVariable;
    
    public WsCallout(String service, String requestVariable, String responseVariable) {
	super();
	this.service = service;
	this.requestVariable = requestVariable;
	this.responseVariable = responseVariable;
    }
    
}
