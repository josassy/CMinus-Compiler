package parser;

public class ParseUtility {
  public static void IndentedPrintln(String str, int indent) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      sb.append(" ");
    }
    sb.append(str);
    System.out.println(sb.toString());
  }
}