package model.statement;

import model.ProgramState;
import model.adt.Dict;
import model.adt.IDict;
import model.adt.Stack;
import model.exceptions.InterpreterException;
import model.types.IType;
import model.values.IValue;

public class Fork implements IStatement {
    private final IStatement statement;

    public Fork(IStatement _statement) {
        this.statement = _statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Dict<String, IValue> symTableCopy = state.getSymTable().getCopy();
        Stack<IStatement> newExecutionStack = new Stack<>();
        newExecutionStack.push(statement);

        ProgramState newProgramState = new ProgramState(newExecutionStack, symTableCopy, state.getOutput(), state.getFileTable(), state.getHeap(), state.getLockTable());
        System.out.println(newProgramState.toString());
        newProgramState.setID();
        return newProgramState;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        statement.typeCheck(typeEnvironment.getCopy());
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "Fork (" + statement.toString() + ")";
    }
}
