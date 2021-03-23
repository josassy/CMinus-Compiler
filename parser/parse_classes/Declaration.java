package parser.parse_classes;

import scanner.Token;
import scanner.Token.*;

public class Declaration implements ParseClass {

  private TokenType type;
  private String id;

  public Declaration(Token.TokenType type, String id) {
    this.type = type;
    this.id = id;
  }

  public void Print() {
    System.out.println("I'm a Declaration");
  }

  public String getType() {
    return type.toString();
  }

  public String getId() {
    return id;
  }
}