package model.statement;

import model.ProgramState;
import model.adt.*;
import model.exceptions.InterpreterException;
import model.expression.IExpression;
import model.types.IType;
import model.values.IValue;

public class PrintStatement implements IStatement {
    private final IExpression expression;

    public PrintStatement(IExpression _expression) {
        this.expression = _expression;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> executionStack = state.getExecutionStack();
        Dict<String, IValue> symTable = state.getSymTable();
        MyList<IValue> outputList = state.getOutput();
        Heap heap = state.getHeap();
        outputList.add(expression.eval(symTable, heap));
        state.setExecutionStack(executionStack);
        state.setOutput(outputList);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }
}
