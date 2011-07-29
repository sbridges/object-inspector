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




/**
 * Creates a value from a value and a field.
 *
 * @author Sean Bridges
 * @see <a href="https://github.com/sbridges/object-inspector">more info</a>
 * @version 0.1
 */

import java.lang.reflect.Field;

class FieldValue implements Value
{

//----------------------------
	//instance variables
	private Value parentValue; //the instance we belong to
	private Field field; //the field of the object


//----------------------------
	//constructors

	/**
	 * If field is a primitive , then the values returned will
	 * be cast to their respective wrappers.
	 */
	FieldValue(Value parentValue, Field field)
	{
		this.parentValue = parentValue;
		this.field = field;
	}


//----------------------------
	//instance methods
	public Object getValue()
	{
		try
		{
			return field.get(parentValue.getValue());
		}	
		catch(IllegalArgumentException e)
		{	
			//this should never happen
			System.err.println("Error in PrimitiveNode.getValueString()");
			throw new RuntimeException(e.toString());
		}
		catch(IllegalAccessException e)
		{
			//this could happen if we dont have privleges
			//it should have been caught earlier, and the node should
			//be a not accessible node
			return NotAccessibleNode.ACCESS_DENIED;
		}



	}
	

}

