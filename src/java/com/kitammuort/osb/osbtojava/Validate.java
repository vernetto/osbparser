package com.kitammuort.osb.osbtojava;

public class Validate implements Action {
    String schema;
    String schemaElement; 
    String varName; 
    String location;
    
    public Validate(String schema, String schemaElement, String varName, String location) {
	super();
	this.schema = schema;
	this.schemaElement = schemaElement;
	this.varName = varName;
	this.location = location;
    }
    
}
