import groovy.io.FileType

import java.io.File

indentLevel=0

def quote(thestring) {
    return '"' + thestring + '"'
}

def quoteEscape(thestring) {
    return quote(escapeString(thestring.toString()))
}

def escapeIfCondition(ifCondition) {
    return escapeQuotes(ifCondition.replace("\n", " "))
}

def escapeString(theString) {
    return theString.replace('\\', '\\\\').replaceAll('"',"'").replaceAll('\n', '" +\n"')  
    //{"'", "\\'"}, {"\"", "\\\""}, {"\\", "\\\\"},{"/", "\\/"}
}


def escapeQuotes(theString) {
    return theString.replaceAll('"', '\\\\"')    
}

def "routing-options"(node) {
    // placeholder, do nothing
}

def "transport-headers"(node) {
    
}

def writeOutput(message) {
    proxyOutput.append(message.replaceAll('\n', '\n' + " ".multiply(indentLevel * 2) ))
}

def produceAllActions(mynodes) {
    for (actionItem in mynodes) {
	indentLevel++
	actionName = actionItem.name()
	// dynamic method invokation... magic of Groovy
	"$actionName"(actionItem)
	indentLevel--
    }
}

// BEGIN specialized rendering for each action

def foreach(node) {
    writeOutput('.foreach(' + quote(node.variable) + ', ' + quote(node.expression.xpathText) + 
	', ' + quote(node."value-variable") + ', ' + quote(node."index-variable") + ', ' + quote(node."total-variable") + ')\n')
    produceAllActions(node.actions.children())
    writeOutput('.endForeach()\n')
}

def reply(node) {
    writeOutput('.reply()\n')
}

def delete(node) {
    writeOutput('.delete(' + quote(node.location.xpathText) + ')\n')
}

def javaCallout(node) {
    params = []
    for (xqueryText in node.expr.xqueryText) {
    	params.add(quoteEscape(xqueryText)) 
    }
    
    writeOutput('.javaCallout(' + quote(node.@varName) + ', ' + quote(node.archive) + 
	', ' + quote(node.className) + ', ' + quote(node.method) + ', new String[] {' + params.join(',') + '}' + ')\n')
}

def insert(node) {
    writeOutput('.insert(' + quote(node.@varName) + ', ' + quote(node.location.xpathText) + ', ' + quote(node.where) + ', ' + quoteEscape(node.expr.xqueryText) + ')\n')
}

def rename(node) {
    writeOutput('.rename(' + quote(node.@varName) + ', ' + quote(node.location.xpathText) + ', ' + quote(node.namespace) + ')\n')
}

def resume(node) {
    writeOutput('.resume()\n')
}

def skip(node) {
    writeOutput('.skip()\n')
}

def log(node) {
    writeOutput('.log("' + node.logLevel + '", ' + quoteEscape(node.expr.xqueryText) + ', ' + quoteEscape(node.message) + ')\n')
}

def ifThenElse(node) {
    for (mycase in node.case) {
	writeOutput('.ifStatement("' + escapeIfCondition(mycase.condition.toString()) + '")\n')
	produceAllActions(mycase.actions.children())
	writeOutput("\n")
	writeOutput(".endIf()\n")
    }
}

def routingTable(node) {
    writeOutput('.routingTable(' + quote(node.expression.xqueryText) + ')\n')
    for (thecase in node."case") {
	writeOutput('.addCase(' + quote(thecase.operator) + ', ' + quoteEscape(thecase.value) +  ')')
	route(thecase.route)
	writeOutput('.endCase()')
    }
    
}

def assign(node) {
    writeOutput('.assign(' + quote(node.@varName) + ', ')
    xquery(node)
    xsltTransform(node)
    writeOutput(')\n')
}

def route(node) {
    writeOutput('.route("' + node.service.@ref + '", "' + node.operation + '", "' + node.outboundTransform."routing-options".qualityOfService + '")\n')
    if (node.outboundTransform."transport-headers".size() > 0) {

        for (header in node.outboundTransform."transport-headers".header) {
	    writeOutput('.addTransportHeader("' + node.outboundTransform."transport-headers"."header-set" + '", "' + header.@name + '", "' + header.xqueryText + '")\n')
        } 
    }
    produceAllActions(node.outboundTransform.children())
    writeOutput('.endRoute()\n')
}

def validate(node) {
    writeOutput('.validate(' + quote(node.schema.@ref) + ', ' +  
	quote(node.schemaElement) + ', ' + quote(node.varName) + ', ' + quote(node.location.xpathText) +  ')\n')
}

def replace(node) {
    writeOutput('.replace("' + node.@varName + '", "' + node.location.xpathText + '", ')
    xquery(node)
    writeOutput(')\n')
}

def xquery(node) {
    if (node.expr.xqueryText.size() > 0) {
	writeOutput(quoteEscape(node.expr.xqueryText))
    }
    xqueryTransform = node.expr.xqueryTransform
    if (xqueryTransform.size() > 0) {
	writeOutput('new XqueryTransform("' + xqueryTransform.resource.@ref + '")')
	indentLevel++
	for (theparam in xqueryTransform.param) {
	    writeOutput('\n.addParameter("' + theparam.@name.toString() + '", "' + escapeQuotes(theparam.path.toString()) + '")')
	}
	indentLevel--
	writeOutput("\n")
	
    }
}

def xsltTransform(node) {
    xsltTransform = node.expr.xsltTransform
    if (xsltTransform.size() > 0) {
	writeOutput('new XSLTTransform("' + xsltTransform.resource.@ref + '")')
	indentLevel++
	for (theparam in xsltTransform.input) {
	    writeOutput('\n.addParameter(' + quoteEscape(theparam.toString()) + ')')
	}
	indentLevel--
	writeOutput("\n")
    }
}


def Error(node) {
    writeOutput('.error("' + node.errCode + '", ' + quoteEscape(node.message) + ')\n')
}

def wsCallout(node) {
    writeOutput('.wsCallout("' + node.service.@ref.toString() + '", "' + node.request.payload.toString() + '", "' +  node.response.payload.toString() + '")\n')
}

def generateProxy(theproxy) {
    //open output file and clear content
    proxyOutput = new File("output.gproxy")
    proxyOutput.write("")
    
    
    // END specialized rendering for each action
    proxy = new File(theproxy)
    proxyName = proxy.name.substring(0, proxy.name.lastIndexOf('.'))
    proxyContent = proxy.text
    
    //parse xml proxy file
    def proxyRecords = new XmlSlurper().parseText(proxyContent)
    
    
    router = proxyRecords.router
    
    //generate header
    writeOutput("import com.kitammuort.osb.osbtojava.*;\n\n")
    writeOutput("public class " + proxyName + " extends Proxy {\n")
    writeOutput(" public void define() {\n")
    
	
    // producing all pipelines
    for (pipeline in router.pipeline) {
	pipelineType = pipeline.@type.toString()
	pipelineName = pipeline.@name.toString()
	writeOutput(sprintf('pipeline ("%1$s",  "%2$s")\n',[ pipelineType, pipelineName ]))
	indentLevel++
	// producing all stages
	for (stage in pipeline.stage) {
	    writeOutput(sprintf('.stage("%1$s")\n',[ stage.@name]))
	    // producing all actions based on type
	    produceAllActions(stage.actions.children())
	    writeOutput(".endStage()\n")
	}
	writeOutput(";\n\n")
	indentLevel--
    
    }
    
    
    for (routerNode in router.flow."route-node") {
	writeOutput('routeNode(' + quote(routerNode.@name) + ')\n')
	// producing all actions based on type
	produceAllActions(routerNode.actions.children())
	writeOutput(";\n\n")
    }
    
    writeOutput(";\n")
    //end define
    writeOutput("}\n")
    //end Proxy class
    writeOutput("}\n")
    
    
    targetFile = 'C:/pierre/workspace/wlproton/src/main/java/' + proxyName + '.java'
    new File(targetFile).delete()
    new File(targetFile) << new File("output.gproxy").text
}


def homedir = 'C:/pierre/downloads/allprojects/'

def dir = new File(homedir)

if (true) {
    dir.eachFileRecurse (FileType.FILES) { file ->
        if (file.name.endsWith(".proxy")) {
    	print "generating " + file.canonicalPath + "\n"
    	generateProxy(file.canonicalPath)
        }
    }
}
else {
    generateProxy(homedir + 'NCube_TerminalsSetupREST/ProxyServices/TerminalsSetupREST_PS.proxy')
}

