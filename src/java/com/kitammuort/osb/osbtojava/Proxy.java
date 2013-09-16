package com.kitammuort.osb.osbtojava;

public abstract class Proxy {
    public PipelineList pipelines = new PipelineList();
    
    public Pipeline pipeline(String type, String name) {
	Pipeline pipeline = new Pipeline(type, name);
	pipelines.add(pipeline);
	return pipeline;
    }
    
    public RouteNode routeNode(String name) {
	RouteNode routeNode = new RouteNode(name);
	return routeNode;
    }
    public abstract void define();
}
