package ast;

public class WhileNode extends ASTNode {
    public final ExprNode condition;
    public final ASTNode body;

    public WhileNode(ExprNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "WhileNode:");
        condition.print(indent + "  ");
        body.print(indent + "  ");
    }
}
