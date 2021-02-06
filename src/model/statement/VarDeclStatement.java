package model.statement;

import model.ProgramState;
import model.adt.Dict;
import model.adt.IDict;
import model.exceptions.InterpreterException;
import model.types.*;
import model.adt.Stack;
import model.values.IValue;

public class VarDeclStatement implements IStatement {
    private final String name;
    private final IType type;

    public VarDeclStatement(String _name, IType _type) {
        this.name = _name;
        this.type = _type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> executionStack = state.getExecutionStack();
        Dict<String, IValue> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new InterpreterException("Variable already declared.");
        } else {
            if (type.equals(new IntType()))
                symTable.add(name, type.defaultValue());
            else if (type.equals(new BooleanType()))
                symTable.add(name, type.defaultValue());
            else if (type.equals(new StringType()))
                symTable.add(name, type.defaultValue());
            else if (type instanceof RefType)
                symTable.add(name, ((RefType) type).defaultValue());

        }
        state.setExecutionStack(executionStack);
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        typeEnvironment.update(name, type);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}
