package parser.parse_classes;

import parser.ParseUtility;

public class Param implements ParseClass {
  String id;
  Boolean hasBrackets;

  public Param(String id, Boolean hasBrackets) {
    this.id = id;
    this.hasBrackets = hasBrackets;
  }  

  public void Print(int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append(id);
    if (hasBrackets) {
      sb.append("[]");
    }
    ParseUtility.IndentedPrintln(sb.toString(), indent);
  }

  public String getID() {
    return id;
  }

  public Boolean getHasBrackets() {
    return hasBrackets;
  }
}