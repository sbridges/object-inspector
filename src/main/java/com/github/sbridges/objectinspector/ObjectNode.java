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



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * A node created from an object.
 * The most generic type of not primitive node, classes such as ArrayNode and
 * are more specific.
 *
 * @author Sean Bridges
 * @see <a href="https://github.com/sbridges/object-inspector">more info</a>
 * @version 0.1
 */

class ObjectNode extends ComplexNode
{

//------------------------------
  //instance variables
  private Value value;
  boolean isNull; //mark wether we were created as a null
  Class myClass; //our class, used to check validity
  TreeSet fieldsAndNames = new TreeSet(new FieldAndNameComparator());


//------------------------------
  //constructors

  ObjectNode(Value value, String name, InspectorNode parent)
  {

    super(parent,name);
    this.value = value;
    init(value.getValue());
  }

  /**
   * Create a new Object node with a null instance
   */
  ObjectNode(String name, InspectorNode parent)
  {
    super(parent, name);
  }

//------------------------------
  //instance methods


  /**
   * To prevent cycles we need a collection of all the objects that we have already
   * mapped.
   */
  private void init( Object instance)
  {
    if(instance == null)
    {
      isNull = true;
      return;
    }
    isNull = false;
    myClass = instance.getClass();

    //create our descendents

    String pre ="";
    Class aClass = myClass;
    do
    {
      Field[] myFields = aClass.getDeclaredFields();

      for(int i = 0; i < myFields.length; i++)
      {
        fieldsAndNames.add(new FieldAndName(myFields[i], pre));
      }//end for all myFields

      if(aClass != Object.class)
      {
        aClass = aClass.getSuperclass();
        String newName = aClass.getName();
        int dotPos = newName.lastIndexOf('.');
        if(dotPos < 0)
          dotPos = 0;
        else
          dotPos++;
        newName = newName.substring(dotPos,newName.length());
        pre = newName  + "." + pre;
      }

    }while(aClass != Object.class );

    setNumberOfChildren(fieldsAndNames.size() );

  }//end init

//---------------------------
  //child generation

  protected InspectorNode generateChild(int index)
  {

    Iterator it = fieldsAndNames.iterator();
    int i = 0;
    while(i < index)
    {

      it.next();
      i++;
    }
    FieldAndName fn = (FieldAndName) it.next();
    return createNode(fn.field,this,value,fn.name);
  }

//---------------------------
  //printing



  public String getValueString()
  {

    Object instance = value.getValue();
    if(instance == null)
    {
      return "<null>";
    }
    return instance.getClass().getName() + "\n" + instance.toString();

  }

  public Object getValue()
  {
    return value.getValue();
  }

  protected Value getValueReference()
  {
    return value;
  }

  public boolean isValid()
  {
    if(super.isValid() )
    {
      Object myValue = value.getValue();
      if(myValue == null)
      {
        return isNull;
      }
      else
      {
        return myValue.getClass() == myClass;
      }

    }
    else
    {
      return false;
    }
  }

}

class FieldAndName
{
  public Field field;
  public String name;

  FieldAndName(Field field, String name)
  {
    this.field = field;
    this.name = name;
  }
}

class FieldAndNameComparator implements Comparator
{
  public int compare(Object o1, Object o2)
  {
    if(o1 == o2)
    {
      return 0;
    }

    FieldAndName f1 = (FieldAndName) o1;
    FieldAndName f2 = (FieldAndName) o2;

    //first is based on depth in hierarchy
    //find where the dots are, the larger index of the last dot
    //the higher in the hierarchy
    int l1 = f1.name.lastIndexOf('.');
    int l2 = f2.name.lastIndexOf('.');
    if(l1 != l2)
    {
      return l1 - l2;
    }

    //second comparison, finals are lower
    boolean s1 = Modifier.isFinal((f1.field.getModifiers()));
    boolean s2 = Modifier.isFinal((f2.field.getModifiers()));
    if(s1 != s2)
    {
      if(s1)
        return 1;
      else
        return -1;
    }

    //third is name
    return f1.field.getName().compareTo(f2.field.getName());

  }

  public boolean equals(Object obj)
  {
    return obj == this;
  }



}
