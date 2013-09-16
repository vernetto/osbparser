package com.kitammuort.osb.osbtojava;

public class Error implements Action {
    String errCode;
    String message;
    
    public Error(String errCode, String message) {
	super();
	this.errCode = errCode;
	this.message = message;
    }
    
    
}
