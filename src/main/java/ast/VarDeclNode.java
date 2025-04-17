package ast;

public class VarDeclNode extends ASTNode {
    public String type;
    public String name;

    public VarDeclNode(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "VarDecl: " + type + " " + name);
    }
}
