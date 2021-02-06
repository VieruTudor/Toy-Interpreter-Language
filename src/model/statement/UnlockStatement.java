package model.statement;

import model.ProgramState;
import model.adt.Dict;
import model.adt.IDict;
import model.adt.LockTable;
import model.adt.Stack;
import model.exceptions.InterpreterException;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class UnlockStatement implements IStatement {
    String id;

    public UnlockStatement(String id) {
        this.id = id;
    }


    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Stack<IStatement> stack = state.getExecutionStack();
        Dict<String, IValue> symTable = state.getSymTable();
        LockTable<Integer> lockTable = state.getLockTable();

        if (symTable.isDefined(id)) {
            IValue varValue = symTable.search(id);
            if (varValue.getType().equals(new IntType())) {
                IntValue found = (IntValue) symTable.search(id);
                if (!lockTable.exists(found.getValue())) {
                    throw new InterpreterException("Lock is not in table");
                } else {
                    if (lockTable.get(found.getValue()).equals(state.getId())) {
                        lockTable.update(found.getValue(), -1);
                    }
                }
            }
            else {
                throw new InterpreterException("Variable is not of type int");
            }
        }
        else {
            throw new InterpreterException("Variable is not defined");
        }
        state.setExecutionStack(stack);
        state.setSymTable(symTable);
        state.setLockTable(lockTable);

        return null;

    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType varType = typeEnvironment.search(id);
        if (varType.equals(new IntType())){
            return typeEnvironment;
        }
        throw new InterpreterException("Variable not of type int");
    }

    @Override
    public String toString(){
        return "Unlock (" + id + ")";
    }
}
