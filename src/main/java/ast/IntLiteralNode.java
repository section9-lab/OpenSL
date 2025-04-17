package ast;
public class IntLiteralNode extends ExprNode {
    public int value;

    public IntLiteralNode(int value) {
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "IntLiteral: " + value);
    }
}
