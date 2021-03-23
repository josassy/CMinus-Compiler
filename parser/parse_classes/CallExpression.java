package parser.parse_classes;

import java.util.ArrayList;

public class CallExpression extends Expression {
    String id;
    ArrayList<Expression> args;

    public CallExpression(String id, ArrayList<Expression> args) {
        this.id = id;
        this.args = args;
    }

    public void Print() {
        System.out.println("I'm a CallExpression");
      }

    public CallExpression(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public ArrayList<Expression> getArgs() {
        return args;
    }
}