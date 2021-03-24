package parser.parse_classes;

import parser.ParseUtility;

public class Statement implements ParseClass {

  public void Print(int indent) {
    ParseUtility.IndentedPrintln("statement", indent);
  }
}
