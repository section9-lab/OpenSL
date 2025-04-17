package parser;// Generated from /Users/shwang/Documents/GitHub/OpenSL/src/main/resources/MiniC.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniCParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MiniCParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#functionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDecl(MiniCParser.FunctionDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MiniCParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MiniCParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(MiniCParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(MiniCParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MiniCParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MiniCParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MiniCParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MiniCParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#addExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(MiniCParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#mulExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(MiniCParser.MulExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MiniCParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#relOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelOp(MiniCParser.RelOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniCParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MiniCParser.TypeContext ctx);
}