package backend;

import ast.*;

import java.util.*;

public class JasminCompiler {
    private final StringBuilder out = new StringBuilder();
    private final Map<String, Integer> varTable = new HashMap<>();
    private int varIndex = 1;

    private int labelCount = 0;
    private String freshLabel(String base) {
        return base + "_" + (labelCount++);
    }

    public String compile(ProgramNode program) {
        emitHeader();
        for (FunctionNode fn : program.functions) {
            emitFunction(fn);
        }
        return out.toString();
    }

    private void emitHeader() {
        out.append(".bytecode 52.0\n");
        out.append(".class public Main\n");
        out.append(".super java/lang/Object\n\n");
    }

    private void emitFunction(FunctionNode fn) {
        out.append(".method public static main([Ljava/lang/String;)V\n");
        out.append("  .limit stack 20\n");
        out.append("  .limit locals 20\n\n");

        varTable.clear();
        varIndex = 1;

        if (fn.body instanceof BlockNode) {
            BlockNode block = (BlockNode) fn.body;
            for (ASTNode stmt : block.statements) {
                emitStatement(stmt);
            }
        }

        out.append("  return\n");
        out.append(".end method\n\n");
    }

    private void emitStatement(ASTNode stmt) {
        if (stmt instanceof VarDeclNode) {
            emitVarDecl((VarDeclNode) stmt);
        } else if (stmt instanceof AssignNode) {
            emitAssign((AssignNode) stmt);
        } else if (stmt instanceof IfNode) {
            emitIf((IfNode) stmt);
        }else if (stmt instanceof VarDeclNode) {
            VarDeclNode decl = (VarDeclNode) stmt;
            if (!varTable.containsKey(decl.name)) {
                varTable.put(decl.name, varIndex++);
            }
        } else if (stmt instanceof AssignNode) {
            AssignNode assign = (AssignNode) stmt;
            emitExpr(assign.expr);
            int slot = varTable.get(assign.variable);
            out.append("  istore ").append(slot).append("\n");
        } else if (stmt instanceof ReturnNode) {
            ReturnNode returnNode = (ReturnNode) stmt;
            if (returnNode.expr != null) {
                // 生成返回值的汇编代码
                emitExpr(returnNode.expr);
            }
            out.append("  ireturn\n");
        } else if (stmt instanceof IfNode) {
            IfNode ifNode = (IfNode) stmt;
            String labelElse = freshLabel("else");
            String labelEnd = freshLabel("endif");

            emitExpr(ifNode.condition);
            out.append("  ifeq ").append(labelElse).append("\n");
            emitStatement(ifNode.thenBranch);
            out.append("  goto ").append(labelEnd).append("\n");

            out.append(labelElse).append(":\n");
            if (ifNode.elseBranch != null) {
                emitStatement(ifNode.elseBranch);
            }
            out.append(labelEnd).append(":\n");
        } else if (stmt instanceof WhileNode) {
            WhileNode whileNode = (WhileNode) stmt;
            String labelStart = freshLabel("loop");
            String labelEnd = freshLabel("endloop");

            out.append(labelStart).append(":\n");
            emitExpr(whileNode.condition);
            out.append("  ifeq ").append(labelEnd).append("\n");

            emitStatement(whileNode.body);
            out.append("  goto ").append(labelStart).append("\n");
            out.append(labelEnd).append(":\n");
        } else if (stmt instanceof BlockNode) {
            BlockNode block = (BlockNode) stmt;
            for (ASTNode s : block.statements) {
                emitStatement(s);
            }
        }
    }

    private void emitExpr(ExprNode expr) {
        if (expr instanceof IntLiteralNode) {
            IntLiteralNode i = (IntLiteralNode) expr;
            out.append("  ldc ").append(i.value).append("\n");
        } else if (expr instanceof VarExprNode) {
            VarExprNode v = (VarExprNode) expr;
            int slot = varTable.get(v.name);
            out.append("  iload ").append(slot).append("\n");
        } else if (expr instanceof BinaryExprNode) {
            BinaryExprNode bin = (BinaryExprNode) expr;
            emitExpr(bin.left);
            emitExpr(bin.right);
            String op = bin.op;
            if (op.equals("+")) out.append("  iadd\n");
            else if (op.equals("-")) out.append("  isub\n");
            else if (op.equals("*")) out.append("  imul\n");
            else if (op.equals("/")) out.append("  idiv\n");
            else if (op.equals(">")) emitCompare("ifgt");
            else if (op.equals("<")) emitCompare("iflt");
            else if (op.equals("==")) emitCompare("ifeq");
            else throw new RuntimeException("不支持的操作符: " + op);
        }
    }

    private void emitCompare(String op) {
        String labelTrue = freshLabel("true");
        String labelEnd = freshLabel("end");

        out.append("  isub\n");
        out.append("  ").append(op).append(" ").append(labelTrue).append("\n");
        out.append("  ldc 0\n");
        out.append("  goto ").append(labelEnd).append("\n");
        out.append(labelTrue).append(":\n");
        out.append("  ldc 1\n");
        out.append(labelEnd).append(":\n");
    }

    private void emitVarDecl(VarDeclNode varDecl) {
        // 假设我们使用局部变量表来存储变量的索引
        int slot = varTable.get(varDecl.name);
        // 变量声明的操作实际上不需要汇编代码，但需要为后续使用保留一个槽位
    }
    private void emitAssign(AssignNode assign) {
        // 生成表达式的代码
        emitExpr(assign.expr);
        // 查找变量的槽位
        int slot = varTable.get(assign.variable);

        // 使用 istore 指令将值存储到变量槽位
        out.append("  istore ").append(slot).append("\n");
    }
    private void emitIf(IfNode ifNode) {
        String labelThen = freshLabel("then");
        String labelElse = freshLabel("else");
        String labelEnd = freshLabel("end");

        // 生成条件表达式的代码
        emitExpr(ifNode.condition);

        // 如果条件为假，跳到 else 分支
        out.append("  ifeq ").append(labelElse).append("\n");

        // 处理 then 语句块
        out.append(labelThen).append(":\n");
        emitStatement(ifNode.thenBranch);

        // 跳到结束
        out.append("  goto ").append(labelEnd).append("\n");

        // 处理 else 语句块
        out.append(labelElse).append(":\n");
        if (ifNode.elseBranch != null) {
            emitStatement(ifNode.elseBranch);
        }

        // 结束
        out.append(labelEnd).append(":\n");
    }

}
