package com.kitammuort.osb.osbtojava;

public class If extends Block implements Action {
    String condition;
    
    public If(String condition) {
	this.condition = condition;
    }

    public void setParentBlock(Block block2) {
	this.parentBlock = block2;
    }

}
