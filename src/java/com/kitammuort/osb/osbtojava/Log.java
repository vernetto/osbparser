package com.kitammuort.osb.osbtojava;

public class Log implements Action {
    enum LogLevelENUM {debug, error};
    
    public LogLevelENUM logLevel;
    public String xqueryText;
    public String message;
    
    public Log(String logLevel, String xqueryText, String message) {
	this.logLevel = LogLevelENUM.valueOf(logLevel);
	this.message = message;
	this.xqueryText = xqueryText;
    }
}
