package ast;

import java.util.List;

public class ProgramNode extends ASTNode {
    public List<FunctionNode> functions;

    public ProgramNode(List<FunctionNode> functions) {
        this.functions = functions;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Program");
        for (FunctionNode fn : functions) {
            fn.print(indent + "  ");
        }
    }
}
