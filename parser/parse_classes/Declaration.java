package parser.parse_classes;

import parser.ParseUtility;
import scanner.Token;
import scanner.Token.*;

public class Declaration implements ParseClass {

  protected TokenType type;
  protected String id;

  public Declaration(Token.TokenType type, String id) {
    this.type = type;
    this.id = id;
  }

  public void Print(int indent) {
    ParseUtility.IndentedPrintln(type.toString() + " " + id, indent);
  }

  public String getType() {
    return type.toString();
  }

  public String getId() {
    return id;
  }
}