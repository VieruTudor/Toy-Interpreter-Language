package model.statement;

import model.ProgramState;
import model.adt.IDict;
import model.adt.Stack;
import model.exceptions.InterpreterException;
import model.types.IType;

public class CompStatement implements IStatement{
    private final IStatement first;
    private final IStatement second;

    public CompStatement(IStatement _first, IStatement _second){
        this.first = _first;
        this.second = _second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> executionStack = state.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        state.setExecutionStack(executionStack);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }

    @Override
    public String toString(){
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
