package parser.parse_classes;

import java.util.ArrayList;

public class Program implements ParseClass {

  ArrayList<Declaration> decls;

  public void Print(int indent) {
    for (Declaration d : decls) {
      d.Print(indent);
    }
  }
}