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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;


/**
 * A class used as an example of the Inspector.
 * Has a colection of Objects and primitive types,
 * including a circular reference just for fun.
 *
 * @author Sean Bridges
 * @see <a href="https://github.com/sbridges/object-inspector">more info</a>
 * @version 0.1
 */
public final class ConvolutedClass
{

  public int publicint = 1;
  public static int publicstaticint = 2;
  private int privateint = 3;
  private char privatechar = 'a';
  private float privatefloat = 1.1f;
  private double privatedouble = 2.0;
  private Similiar similar10 = new Similiar(10);
  private Similiar similar20 = new Similiar(20);

  private ConvolutedClass self = this;

  protected String[] someStrings = {"apples", "oranges", "pears"};
  protected int[] someInts = {1,2,3,4,5,4,3,2,1};
  protected String[] nullArray;
  protected int[][] twoDArray = new int[5][5];
  protected Object[] anObjectArray = new Object[5];
  Collection someStuff = new ArrayList(10);
  private Object anObject = new Object();
  private D inheritanceExample = new D();
  private  HashMap hash = new  HashMap();
  private Vector aVector = new Vector();

  public ConvolutedClass()
  {
    someStuff.add(this);
    someStuff.add(someStrings);
    someStuff.add(new Integer(10) );
    someStuff.add(new StringBuffer("the contents"));
    someStuff.add(new Random() );

    for(int i = 0; i < 5; i++)
    {
      for(int j = 0; j < 5; j++)
      {
        twoDArray[i][j] = i * j;
      }
    }

    anObjectArray[0] = anObjectArray;
    anObjectArray[1] = someStrings;
    anObjectArray[2] = this;
    anObjectArray[3] = new Float(2);
    /*
     Note I originially tried stuff.add(stuff), but you get a stack overflow
     in jdk1.3
     To see why try
      ArrayList a = new ArrayList();
      a.add(a);
      a.hashCode();
    */

    hash.put(new Object(), new Integer(5));
    hash.put("a", "e");
    hash.put("b", "f");
    hash.put("c", "g");
    hash.put(null, "?");
    hash.put("?", null);

    aVector.add("First");
    aVector.add(new Integer(2));
  }

}

/**
 * Overrides .equals() to be always equals, and hash code to return 10 always.
 * Shows how the Inspector does not rely on either.
 */
class Similiar
{
  private int myInt;

  Similiar(int anInt)
  {
    myInt = anInt;
  }

  public boolean equals(Object obj)
  {
    return true;
  }

  public int hashCode()
  {
    return 10;
  }

  public String toString()
  {
    return String.valueOf(myInt);
  }

}

class A
{
  public int anAVariable;
}

class B extends A
{
  private int aBVariable;
}

class C extends B
{
  protected boolean aCVariable = false;
}

class D extends C
{
  protected Object aDVariable = new Object();
}


