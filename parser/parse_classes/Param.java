package parser.parse_classes;

import parser.ParseUtility;
import java.io.Writer;

/**
 * File: Param.java
 * 
 * Represent parameters that are part of a function declaration. Each parameter
 * contains an id string and a boolean flag indicating whether the parameter is
 * an array data type.
 */
public class Param implements ParseClass {
  String id;
  Boolean hasBrackets;

  public Param(String id, Boolean hasBrackets) {
    this.id = id;
    this.hasBrackets = hasBrackets;
  }

  public void Print(Writer out, int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append("int " + id);
    if (hasBrackets) {
      sb.append("[]");
    }
    ParseUtility.IndentedPrintln(sb.toString(), indent, out);
  }
}