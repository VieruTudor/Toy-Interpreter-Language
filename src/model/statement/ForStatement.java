package model.statement;

import model.ProgramState;
import model.adt.IDict;
import model.adt.Stack;
import model.exceptions.InterpreterException;
import model.expression.IExpression;
import model.expression.RelationalExpression;
import model.expression.VariableExpression;
import model.types.IType;
import model.types.IntType;

public class ForStatement implements IStatement{
    IExpression expression1;
    IExpression expression2;
    IExpression expression3;
    IStatement statement;

    public ForStatement(IExpression _expression1, IExpression _expression2, IExpression _expression3, IStatement _statement){
        this.expression1 = _expression1;
        this.expression2 = _expression2;
        this.expression3 = _expression3;
        this.statement = _statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> stack =state.getExecutionStack();
        stack.push(new CompStatement(
                new VarDeclStatement("v", new IntType()),
                new CompStatement(
                        new AssignStatement("v", expression1),
                        new WhileStatement(
                                new RelationalExpression(
                                        new VariableExpression("v"),
                                        expression2,
                                        "<"),
                                new CompStatement(
                                    statement,
                                    new AssignStatement("v", expression3)
                                )

                        )
                )
        ));
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IDict<String, IType> table1 = new VarDeclStatement("v", new IntType()).typeCheck(typeEnvironment);
        IType varType = table1.search("v");
        IType expType1 = expression1.typeCheck(table1);
        IType expType2 = expression2.typeCheck(table1);
        IType expType3 = expression3.typeCheck(table1);
        if (varType.equals(new IntType())) {
            if (expType1.equals(new IntType())) {
                if (expType2.equals(new IntType())) {
                    if (expType3.equals(new IntType())) {
                        statement.typeCheck(typeEnvironment);
                        return typeEnvironment;
                    }
                    throw new InterpreterException("Expression 3 not of type int");
                }
                throw new InterpreterException("Expression 2 not of type int");
            }
            throw new InterpreterException("Expression 1 not of type int");
        }
        throw new InterpreterException("v not of type int");
    }

    @Override
    public String toString(){
        return "for (v = " + expression1.toString() + "; " + "v < " + expression2.toString() + "; " + "v = " + expression3.toString() + ";)"
                + "{" + statement.toString() + "}";
    }
}
