package ast;

public class IfNode extends ASTNode {
    public ExprNode condition;
    public ASTNode thenBranch;
    public ASTNode elseBranch; // 可以为 null

    public IfNode(ExprNode condition, ASTNode thenBranch, ASTNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "If");
        System.out.println(indent + "  Condition:");
        condition.print(indent + "    ");
        System.out.println(indent + "  Then:");
        thenBranch.print(indent + "    ");
        if (elseBranch != null) {
            System.out.println(indent + "  Else:");
            elseBranch.print(indent + "    ");
        }
    }
}
