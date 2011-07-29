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


import java.util.Iterator;
import java.util.Map;

/**
 * A map node is an object node that displays its values a little more intelligently. 
 */

public class MapNode extends ObjectNode {

  public MapNode(Value value, String name, InspectorNode parent)
  {
    super(value, name, parent);
  }

  public String getValueString()
  {
    Object instance = getValueReference().getValue();
    return instance.getClass().getName() + "\n" + asString(instance);
  }


  private String asString(Object value)
  {
    if(value == null)
      return "<null>";

    Map map = (Map) value;


    Iterator iter = map.keySet().iterator();

    StringBuffer buf = new StringBuffer();
    while(iter.hasNext())
    {
      Object key = iter.next();
      buf.append("  ");
      buf.append(key);
      buf.append("->");
      buf.append(map.get(key));
      if(iter.hasNext())
        buf.append("\n");
    }

    return buf.toString();

  }
}
