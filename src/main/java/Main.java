import backend.JasminCompiler;
import parser.*;
import ast.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import semantic.SemanticAnalyzer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Please provide the path to the 'main.c' file as a command-line argument.");
            return;
        }
        String filePath = args[0];
        String code = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        CharStream input = CharStreams.fromString(code);

        MiniCLexer lexer = new MiniCLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniCParser parser = new MiniCParser(tokens);

        ParseTree tree = parser.program();
        MiniCASTBuilder builder = new MiniCASTBuilder();
        // 构建 AST
        ProgramNode ast = (ProgramNode) builder.visit(tree);

        // 语义分析
        System.out.println("===== Semantic Check =====");
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        analyzer.analyze(ast);

        // 打印 AST
        System.out.println("===== AST =====");
        ast.print("");


        // 输出 Jasmin
        JasminCompiler compiler = new JasminCompiler();
        String jasminCode = compiler.compile(ast);

        System.out.println("===== Jasmin Code =====");
        System.out.println(jasminCode);

        // 写入文件
        System.out.println("===== Writing Jasmin Code to Main.j =====");
        Files.write(Paths.get("Main.j"), Collections.singleton(jasminCode), StandardCharsets.UTF_8);

        //>brew install jasmin

        //>jasmin Main.j
        //Generated: Main.class

        //>java Main
    }
}