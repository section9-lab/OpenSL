package parser;

import ast.*;

import java.util.ArrayList;
import java.util.List;

public class MiniCASTBuilder extends MiniCBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(MiniCParser.ProgramContext ctx) {
        List<FunctionNode> functions = new ArrayList<>();
        for (MiniCParser.FunctionDeclContext fctx : ctx.functionDecl()) {
            functions.add((FunctionNode) visit(fctx));
        }
        return new ProgramNode(functions);
    }

    @Override
    public ASTNode visitVarDecl(MiniCParser.VarDeclContext ctx) {
        String type = ctx.type().getText();
        String name = ctx.ID().getText();
        return new VarDeclNode(type, name);
    }
    @Override
    public ASTNode visitAssignment(MiniCParser.AssignmentContext ctx) {
        String var = ctx.ID().getText();
        ExprNode expr = (ExprNode) visit(ctx.expr());
        return new AssignNode(var, expr);
    }
    @Override
    public ASTNode visitIfStatement(MiniCParser.IfStatementContext ctx) {
        ExprNode condition = (ExprNode) visit(ctx.expr());
        ASTNode thenBranch = visit(ctx.statement(0));
        ASTNode elseBranch = ctx.statement().size() > 1 ? visit(ctx.statement(1)) : null;
        return new IfNode(condition, thenBranch, elseBranch);
    }
    @Override
    public ASTNode visitPrimary(MiniCParser.PrimaryContext ctx) {
        if (ctx.INT() != null) {
            return new IntLiteralNode(Integer.parseInt(ctx.INT().getText()));
        } else if (ctx.ID() != null) {
            return new VarExprNode(ctx.ID().getText());
        } else { // '(' expr ')'
            return visit(ctx.expr());
        }
    }
//    @Override
//    public ASTNode visitMulExpr(MiniCParser.MulExprContext ctx) {
//        if (ctx.mulExpr() != null) {
//            ExprNode left = (ExprNode) visit(ctx.mulExpr());
//            ExprNode right = (ExprNode) visit(ctx.primary());
//            String op = ctx.op.getText(); // * or /
//            return new BinaryExprNode(op, left, right);
//        } else {
//            return visit(ctx.primary());
//        }
//    }
    @Override
    public ASTNode visitStatement(MiniCParser.StatementContext ctx) {
        if (ctx.varDecl() != null) {
            return visit(ctx.varDecl());
        }
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        }
        if (ctx.ifStatement() != null) {
            return visit(ctx.ifStatement());
        }
        if (ctx.returnStatement() != null) {
            return visit(ctx.returnStatement());
        }
        if (ctx.whileStatement() != null) {
            return visit(ctx.whileStatement());
        }
        if (ctx.block() != null) {
            return visit(ctx.block());
        }
        return null; // 空语句
    }
    @Override
    public ASTNode visitBlock(MiniCParser.BlockContext ctx) {
        List<ASTNode> stmts = new ArrayList<>();
        for (MiniCParser.StatementContext stmtCtx : ctx.statement()) {
            ASTNode stmt = visit(stmtCtx);
            if (stmt != null) {
                stmts.add(stmt);
            }
        }
        return new BlockNode(stmts); // 你可以定义一个 BlockNode 节点
    }


    @Override
    public ASTNode visitFunctionDecl(MiniCParser.FunctionDeclContext ctx) {
        String name = "main"; // 目前只支持 main
        List<ASTNode> stmts = new ArrayList<>();
        for (MiniCParser.StatementContext stmtCtx : ctx.block().statement()) {
            // 为了演示，这里不真正构建子节点，只是打印
//            stmts.add(new ASTNode() {
//                @Override
//                public void print(String indent) {
//                    System.out.println(indent + "[Statement]");
//                }
//            });
            for (MiniCParser.StatementContext statementContext : ctx.block().statement()) {
                ASTNode stmt = visit(statementContext);
                if (stmt != null) {
                    stmts.add(stmt);
                }
            }
        }
        return new FunctionNode(name, stmts);
    }
}
