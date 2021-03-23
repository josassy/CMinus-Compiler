package parser.parse_classes;

import java.util.ArrayList;

public class CompStatement extends Statement {
    ArrayList<Declaration> decls;
    ArrayList<Statement> stmts;

    public CompStatement(ArrayList<Declaration> decls, ArrayList<Statement> stmts) {
        this.decls = decls;
        this.stmts = stmts;
    }

    public void Print() {
        System.out.println("I'm a CompStatement");
      }

    public ArrayList<Declaration> getDecls() {
        return decls;
    }

    public ArrayList<Statement> getStmts() {
        return stmts;
    }
}