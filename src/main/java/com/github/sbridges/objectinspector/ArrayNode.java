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


import java.lang.reflect.Array;

/**
 * A node created from an array.
 *
 * @author Sean Bridges
 * @see <a href="https://github.com/sbridges/object-inspector">more info</a>
 * @version 0.1
 */

class ArrayNode extends ComplexNode
{

//------------------------------
  //instance variables
  Class startingComponentType;
  Value value;
  int startingSize; //used to track validity.
            //if our size changes we are no longer valid


//------------------------------
  //constructors

  ArrayNode(InspectorNode parent, Value value, String name)
  {
    super(parent,name);
    this.value = value;
    Object instance = value.getValue();

    Class c = instance.getClass();
    if(!c.isArray())
    {
      throw new RuntimeException("Not an array");
    }

    int length = Array.getLength(instance);
    startingSize = length;
    startingComponentType = instance.getClass().getComponentType();
    setNumberOfChildren(startingSize);


  }


  private String asString(Object array)
  {
    int length = Array.getLength(array);
    StringBuffer buf = new StringBuffer("[");
    for(int i = 0; i < length; i++)
    {
      buf.append(Array.get(array,i));
      if(i < length + 1)
        buf.append(",");
    }
    buf.append("]");
    return buf.toString();
  }


//-----------------------------
  //child creation

  protected InspectorNode generateChild(int index)
  {
    InspectorNode newChild;
    String childName = String.valueOf(index) + "           ";
    if(startingComponentType.isPrimitive())
    {
      return new PrimitiveNode(
            this,new ArrayValue(value,index), childName
            );
    }
    else
    {
      return ComplexNode.createComplexNode(
            new ArrayValue(value,index),
            (ComplexNode) getParent(),
            childName);
    }
  }

//-----------------------------
  //InspectorNode methods

  public String getValueString()
  {
    return startingComponentType.getName() + "[" + Array.getLength(value.getValue()) + "]" +
        "\n" + asString(value.getValue());
  }

  public Object getValue()
  {
    return value.getValue();
  }


  public boolean isValid()
  {
    if(super.isValid())
    {
      //if we are null, then we are not valid
      Object instance = value.getValue();
      if(instance == null)
      {
        return false;
      }
      //if our size has changed we are no longer valid
      //since we will have the wrong number of children
      else if(Array.getLength(instance) != startingSize)
      {
        return false;
      }
      //if our component type has changed, then we are no longer valid
      else if( instance.getClass().getComponentType() != startingComponentType)
      {
        return false;
      }
      else
      {
        return true;
      }

    }
    else
    {
      return false;
    }
  }


}
