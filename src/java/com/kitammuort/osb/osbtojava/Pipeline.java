package com.kitammuort.osb.osbtojava;

public class Pipeline {
    StageList stages = new StageList();
    
    enum typeENUM {request, response, error};
    
    typeENUM pipelineType;
    String name;
    
    public Pipeline(String type, String name) {
	super();
	this.name = name;
	this.pipelineType = typeENUM.valueOf(type);
    }
    
    public Stage stage(String name) {
	Stage stage = new Stage(this, name);
	stages.add(stage);
	return stage;
    }
    

}
