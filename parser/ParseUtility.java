package parser;

import java.io.IOException;
import java.io.Writer;

public class ParseUtility {
  public static void IndentedPrintln(String str, int indent, Writer out) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      sb.append("  ");
    }
    sb.append(str);
    try {
      out.write(sb.toString() + "\n");
    } catch (IOException e) {
      System.out.println("Couldn't write to output file");
    }
  }
}