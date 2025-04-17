package ast;

public class VarExprNode extends ExprNode {
    public String name;

    public VarExprNode(String name) {
        this.name = name;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Var: " + name);
    }
}
