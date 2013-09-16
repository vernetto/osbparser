package com.kitammuort.osb.osbtojava;

import java.util.HashMap;

public abstract class TransformAbstract implements Action {
    String fileName;
    HashMap<String, String> parameters = new HashMap<String, String>();
    
    public TransformAbstract(String fileName) {
	super();
	this.fileName = fileName;
    }

}
