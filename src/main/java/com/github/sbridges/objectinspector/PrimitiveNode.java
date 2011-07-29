package com.github.sbridges.objectinspector;

/*
 * Copyright 2000 Sean Bridges. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * 
 * 
 */



import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;


/**
 * A node created from a primitive such as an int or float.
 *
 * @author Sean Bridges
 * @see <a href="https://github.com/sbridges/object-inspector">more info</a>
 * @version 0.1
 */

class PrimitiveNode implements InspectorNode
{

//----------------------------
	//instance variables
	private Value value;
	private InspectorNode parent; //our parent
	private String name;

//-------------------------------
	//constructors

	/**
	 * Create a primitive node based on the instance and 
	 * field.
	 * The field should be accessible.
	 */
	PrimitiveNode(InspectorNode parent, Value value, String name)
	{
		this.name = name;
		this.parent =  parent;
		this.value = value;
	}

//-----------------------------------
	//Tree Node methods

	public Enumeration children()
	{
		return new Vector().elements();
	}

	public TreeNode getChildAt(int childIndex)
	{
		throw new ArrayIndexOutOfBoundsException();
	}

	public int getChildCount()
	{
		return 0;
	}

	public int getIndex(TreeNode node)
	{
		return -1;
	}

	public boolean getAllowsChildren()
	{
		return false;
	}

	public boolean isLeaf()
	{
		return true;
	}

	public TreeNode getParent()
	{
		return parent;
	}

//-----------------------------
	//InspectorNode
	public String getValueString()
	{	
		return value.getValue().toString();
	}

	public Object getValue()
	{
		return value.getValue();
	}

	public boolean isValid()
	{	
		/*
		 * We are valid as long as our parent is valid, 
		 * since we calculate our value by accessing our parents field
		 */
		return parent.isValid();
	}

	public String toString()
	{
		return name;
	}

}

