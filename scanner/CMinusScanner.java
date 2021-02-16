package scanner;

import java.io.*;

public class CMinusScanner {
  //private BufferedReader inFile;
  private Token nextToken;
  private BufferedReader inFile;

  public enum StateType {
    START,
    INASSIGN,
    INCOMMENT,
    INNUM,
    INID,
    DONE
  }

  public CMinusScanner(BufferedReader file) {
    inFile = file;
    nextToken = getToken();
  }

  public Token getNextToken() {
    Token returnToken = nextToken;
    if (nextToken.getType() != Token.TokenType.EOF_TOKEN)
      nextToken = getToken();
    return returnToken;
  }

  public Token viewNextToken() {
    return nextToken;
  }

  public Token getToken() {
    //Start in Start State
    //Iterate till Done
    //When Done, create Token containing TokenType and Token Data
      //Maybe check for keywords
    //If Error, return TokenType = ERROR_TOKEN
    return null;
  }
}