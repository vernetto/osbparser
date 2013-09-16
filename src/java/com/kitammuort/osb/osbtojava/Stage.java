package com.kitammuort.osb.osbtojava;

public class Stage extends Block {
    Pipeline pipeline;
    
    String name;
    public Stage(Pipeline pipeline, String name) {
	this.pipeline = pipeline;
	this.name = name;
    }
    


}
