package com.kitammuort.osb.osbtojava;

public class Block {
    ActionList actions = new ActionList();
    public Block parentBlock = null;
    
    public Block log(String logLevel, String xqueryText, String message) {
	Log log = new Log(logLevel, xqueryText, message);
	actions.add(log);
	return this;
    }
    
    public Block ifStatement(String condition) {
	If ifStatement = new If(condition);
	ifStatement.setParentBlock(this);
	actions.add(ifStatement);
	return ifStatement;
    }
    
    public Block assign(String variable,  String xqueryText) {
	Assign assign = new Assign(variable, new XqueryText(xqueryText));
	actions.add(assign);
	return this;
    }
    
    public Block assign(String variable, TransformAbstract addParameter) {
	Assign assign = new Assign(variable, addParameter);
	actions.add(assign);
	return this;    
    }    
    
    public Block replace(String varName, String xpathText, String xqueryText) {
	Replace replace = new Replace(varName, xpathText, new XqueryText(xqueryText));
	actions.add(replace);
	return this;
    }
    
    public Block replace(String varName, String xpathText, XqueryTransform xqueryTransform) {
	Replace replace = new Replace(varName, xpathText, xqueryTransform);
	actions.add(replace);
	return this;
    }
    
    public Block endIf() {
	return this.parentBlock;
    }


    
    public Route route(String service, String operation, String qos) {
	Route route = new Route(service, operation, qos);
	actions.add(route);
	return route;
    }
    
    public Block endRoute() {
	return this.parentBlock;
    }
    
    public Block error(String errCode, String message) {
	Error error = new Error(errCode, message);
	actions.add(error);
	return this;
    }
    
    public Pipeline endStage() {
	if (this instanceof Stage) {
	    return ((Stage)this).pipeline;
	}
	else {
	    throw new IllegalArgumentException("this is not a stage, endStage() cannot be invoked");
	}
    }
    
    public Block wsCallout(String service, String requestVariable, String responseVariable) {
	WsCallout wsCallout = new WsCallout(service, requestVariable, responseVariable);
	actions.add(wsCallout);
	return this;
    }
    
    public Block validate(String schema, String schemaElement, String varName, String location) {
	Validate validate = new Validate(schema, schemaElement, varName, location);
	actions.add(validate);
	return this;
    }
    
    public Foreach foreach(String variable, String xpathText, String valueVariable, String indexVariable, String totalVariable) {
	Foreach foreach = new Foreach(variable, xpathText, valueVariable, indexVariable, totalVariable);
	actions.add(foreach);
	return foreach;
    }
    
    public Block endForeach() {
	return ((Foreach)this).parentBlock;
    }
    
    public Block reply() {
	Reply reply = new Reply();
	actions.add(reply);
	return this;
    }

    public Block resume() {
	Resume resume = new Resume();
	actions.add(resume);
	return this;
    }
    
    public Block delete(String xpathText) {
	Delete delete = new Delete(xpathText);
	actions.add(delete);
	return this;
    }
    
    public Block insert(String varName, String xpathText, String where, String xqueryText) {
	Insert insert = new Insert(varName, xpathText, where, xqueryText);
	actions.add(insert);
	return this;
    }
    
    public Block skip() {
	Skip skip = new Skip();
	actions.add(skip);
	return this;
    }
    
    public Block javaCallout(String varName, String archive, String className, String method, String[] params) {
	JavaCallout javaCallout = new JavaCallout(varName, archive, className, method, params);
	actions.add(javaCallout);
	return this;
    }
    
    public Block rename(String varName, String xpathText, String namespace) {
	Rename rename = new Rename(varName, xpathText, namespace);
	actions.add(rename);
	return this;
    }
    
    public Block routingTable(String xqueryText) {
	RoutingTable routingTable = new RoutingTable(xqueryText);
	actions.add(routingTable);
	return routingTable;
    }

    public Block addCase(String operator, String value) {
	RoutingCase routingCase = new RoutingCase(operator, value);
	actions.add(routingCase);
	return routingCase;
    }

    public Block endCase() {
	return this.parentBlock;
    }
}
