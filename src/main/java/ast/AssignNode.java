package ast;

public class AssignNode extends ASTNode {
    public String variable;
    public ExprNode expr;

    public AssignNode(String variable, ExprNode expr) {
        this.variable = variable;
        this.expr = expr;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Assign: " + variable + " =");
        expr.print(indent + "  ");
    }
}
