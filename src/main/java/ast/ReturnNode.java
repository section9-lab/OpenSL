package ast;

public class ReturnNode extends ASTNode {
    public final ExprNode expr;

    public ReturnNode(ExprNode expr) {
        this.expr = expr;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "ReturnNode:");
        if (expr != null) {
            expr.print(indent + "  ");
        }
    }
}
