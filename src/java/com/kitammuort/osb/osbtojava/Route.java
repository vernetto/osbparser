package com.kitammuort.osb.osbtojava;

public class Route extends Block implements Action {
    String service;
    String operation;
    String qos;
    TransportHeaderList transportHeaderList = new TransportHeaderList();
    

    public Route(String service, String operation, String qos) {
	this.service = service;
	this.operation = operation;
	this.qos = qos;
    }
    
    public Route addTransportHeader(String headerSet, String name, String xqueryText) {
	TransportHeader transportHeader = new TransportHeader(headerSet, name, new XqueryText(xqueryText));
	transportHeaderList.add(transportHeader);
	return this;
    }

}
