package model.statement;

import model.ProgramState;
import model.adt.IDict;
import model.exceptions.InterpreterException;
import model.adt.Stack;
import model.types.IType;

public class NopStatement implements IStatement {

    public NopStatement(){}

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> executionStack = state.getExecutionStack();
        state.setExecutionStack(executionStack);
        return null;
        // Shouldn't this be empty?
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "\n";
    }
}
