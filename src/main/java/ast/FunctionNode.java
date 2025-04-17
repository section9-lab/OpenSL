package ast;

import java.util.List;

public class FunctionNode extends ASTNode {
    public String name;
    public List<ASTNode> body;

    public FunctionNode(String name, List<ASTNode> body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Function: " + name);
        for (ASTNode stmt : body) {
            stmt.print(indent + "  ");
        }
    }
}
