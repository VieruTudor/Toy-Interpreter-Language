package model.statement;

import model.ProgramState;
import model.adt.Dict;
import model.adt.IDict;
import model.exceptions.InterpreterException;
import model.expression.IExpression;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStatement {
    private final IExpression expression;

    public CloseReadFile(IExpression _expression) {
        this.expression = _expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IValue value = expression.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new InterpreterException("Expression does not evaluate to a string.");

        StringValue fileName = (StringValue) value;
        Dict<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName.getValue()))
            throw new InterpreterException("File is not present in the file table");

        BufferedReader reader = fileTable.search(fileName.getValue());
        try {
            reader.close();
        } catch (IOException e) {
            throw new InterpreterException("File could not be closed.");
        }

        fileTable.remove(fileName.getValue());
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType expType = expression.typeCheck(typeEnvironment);
        if (expType.equals(new StringType()))
            return typeEnvironment;
        else
            throw new InterpreterException("File path must be a string");
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
