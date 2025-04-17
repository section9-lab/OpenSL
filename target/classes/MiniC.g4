// MiniC.g4 - A simplified C grammar for ANTLR
grammar MiniC;

// program: 一组函数定义（目前只支持 main）
program: functionDecl+;

// 函数定义：仅支持 int main()
functionDecl
    : 'int' 'main' '(' ')' block
    ;

// 语句块：用 { ... } 包围的若干语句
block
    : '{' statement* '}'
    ;

// 支持的语句类型
statement
    : varDecl               // 变量声明
    | assignment            // 赋值语句
    | ifStatement           // if / if-else
    | whileStatement        // while 循环
    | returnStatement       // return
    | block                 // 嵌套语句块
    | ';'                   // 空语句
    ;

// 变量声明：int x;
varDecl
    : type ID ';'
    ;

// 赋值语句：x = 表达式;
assignment
    : ID '=' expr ';'
    ;

// if 语句：支持可选的 else 分支
ifStatement
    : 'if' '(' expr ')' statement ('else' statement)?
    ;

// while 循环
whileStatement
    : 'while' '(' expr ')' statement
    ;

// return 语句
returnStatement
    : 'return' expr? ';'
    ;


// 表达式：添加了比较运算符
expr
    : addExpr (relOp addExpr)?       // 支持 a > b, a == b 等
    ;

// 加减层级
addExpr
    : mulExpr (('+' | '-') mulExpr)*
    ;

// 乘除层级
mulExpr
    : primary (('*' | '/') primary)*
    ;

// 最基本的单元
primary
    : '(' expr ')'
    | ID
    | INT
    ;

// 关系运算符定义
relOp
    : '>'
    | '<'
    | '>='
    | '<='
    | '=='
    | '!='
    ;


// 支持的类型：目前只有 int 和 char
type
    : 'int'
    | 'char'
    ;

// 词法规则：

// 标识符（变量名）
ID  : [a-zA-Z_][a-zA-Z_0-9]*;

// 整数常量
INT : [0-9]+;

// 忽略空白字符
WS  : [ \t\r\n]+ -> skip;

// 支持 // 注释
COMMENT: '//' ~[\r\n]* -> skip;
