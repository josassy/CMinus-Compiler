package parser.parse_classes;

public class Param implements ParseClass {
  String id;
  Boolean hasBrackets;

  public Param(String id, Boolean hasBrackets) {
    this.id = id;
    this.hasBrackets = hasBrackets;
  }  

  public void Print() {
    System.out.println("I'm a Param");
  }

  public String getID() {
    return id;
  }

  public Boolean getHasBrackets() {
    return hasBrackets;
  }
}