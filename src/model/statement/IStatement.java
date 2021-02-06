package model.statement;

import model.ProgramState;
import model.adt.IDict;
import model.exceptions.InterpreterException;
import model.types.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws InterpreterException;
    IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException;
}
