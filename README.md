# OpenSL编译器

> 将 C 语言编译字节码, 运行在 JVM 解释器上

🔧 1. 词法分析（Lexer）
用手写或借助工具（如 Flex、ANTLR）把 C 源码拆解成 token 流。

🧠 2. 语法分析（Parser）
用手写或语法工具（如 Bison、ANTLR）构建语法树或抽象语法树（AST）。

🌲 3. 构建 AST
将解析结果转化为抽象语法树，便于后续处理。

🏗️ 4. 中间表示（IR）
考虑用一个简单的中间表示（如三地址码）作为过渡，便于做优化和生成 Java 字节码。

🔄 5. 字节码生成（CodeGen）
将 IR 转换为 Java 字节码：

可以直接使用 ASM、BCEL 等字节码库；
或者生成 .class 文件，或 .jar，然后交给 JVM 执行。
🧪 6. 运行与测试
用简单的 C 程序（变量、分支、循环、函数等）做单元测试，在 JVM 上运行并验证行为。


```

MiniCCompiler/
├── parser/                      ← 由 ANTLR 自动生成的解析器文件
│   ├── MiniCLexer.java
│   ├── MiniCParser.java
│   ├── MiniCBaseVisitor.java
│   └── ...
├── parser/                      ← AST 构建器
│   └── MiniCASTBuilder.java
│
├── ast/                         ← AST 节点定义
│   ├── ASTNode.java
│   ├── ProgramNode.java
│   ├── FunctionNode.java
│   ├── BlockNode.java
│   ├── VarDeclNode.java
│   ├── AssignNode.java
│   ├── IfNode.java
│   ├── ReturnNode.java
│   ├── ExprNode.java
│   ├── BinaryExprNode.java
│   ├── IntLiteralNode.java
│   └── VarExprNode.java
│
├── semantic/                    ← 语义分析器
│   ├── SymbolTable.java
│   └── SemanticAnalyzer.java
│
├── backend/                     ← 字节码生成器（Jasmin）
│   └── JasminCompiler.java
│
├── Main.java                    ← 主程序入口，包含读取源文件、调用 parser → analyzer → compiler
├── Main.j                       ← 编译后生成的 Jasmin 汇编代码
└── main.c                       ← 输入的 C 源代码（简化版 MiniC）
```
