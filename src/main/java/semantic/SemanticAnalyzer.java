package semantic;

import ast.*;

import java.util.HashMap;
import java.util.Map;

public class SemanticAnalyzer {
    private final Map<String, String> symbolTable = new HashMap<>();

    public void analyze(ASTNode node) {
        if (node instanceof ProgramNode) {
            ProgramNode program = (ProgramNode) node;
            for (FunctionNode fn : program.functions) {
                analyzeFunction(fn);
            }
        }
    }

    private void analyzeFunction(FunctionNode fn) {
        symbolTable.clear(); // 每个函数独立作用域

        if (fn.body instanceof BlockNode) {
            BlockNode block = (BlockNode) fn.body;
            for (ASTNode stmt : block.statements) {
                analyzeStatement(stmt);
            }
        }
    }

    private void analyzeStatement(ASTNode stmt) {
        if (stmt instanceof VarDeclNode) {
            VarDeclNode varDecl = (VarDeclNode) stmt;
            if (symbolTable.containsKey(varDecl.name)) {
                throw new RuntimeException("变量重复声明: " + varDecl.name);
            }
            symbolTable.put(varDecl.name, varDecl.type);
        } else if (stmt instanceof AssignNode) {
            AssignNode assign = (AssignNode) stmt;
            if (!symbolTable.containsKey(assign.variable)) {
                throw new RuntimeException("变量未声明: " + assign.variable);
            }
            analyzeExpr(assign.expr);
        } else if (stmt instanceof IfNode) {
            IfNode ifNode = (IfNode) stmt;
            analyzeExpr(ifNode.condition);
            analyzeStatement(ifNode.thenBranch);
            if (ifNode.elseBranch != null) {
                analyzeStatement(ifNode.elseBranch);
            }
        } else if (stmt instanceof WhileNode) {
            WhileNode whileNode = (WhileNode) stmt;
            analyzeExpr(whileNode.condition);
            analyzeStatement(whileNode.body);
        } else if (stmt instanceof ReturnNode) {
            ReturnNode ret = (ReturnNode) stmt;
            if (ret.expr != null) {
                analyzeExpr(ret.expr);
            }
        } else if (stmt instanceof BlockNode) {
            BlockNode block = (BlockNode) stmt;
            for (ASTNode s : block.statements) {
                analyzeStatement(s);
            }
        }
    }

    private void analyzeExpr(ExprNode expr) {
        if (expr instanceof BinaryExprNode) {
            BinaryExprNode bin = (BinaryExprNode) expr;
            analyzeExpr(bin.left);
            analyzeExpr(bin.right);
        } else if (expr instanceof VarExprNode) {
            VarExprNode v = (VarExprNode) expr;
            if (!symbolTable.containsKey(v.name)) {
                throw new RuntimeException("变量未声明: " + v.name);
            }
        } else if (expr instanceof IntLiteralNode) {
            // ok
        }
    }
}
