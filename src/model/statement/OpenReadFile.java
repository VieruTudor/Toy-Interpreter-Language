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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFile implements IStatement {
    private final IExpression expression;

    public OpenReadFile(IExpression _expression){
        this.expression = _expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IValue value = expression.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new InterpreterException("Expression does not evaluate to a string.");

        StringValue fileName = (StringValue) value;
        Dict<String, BufferedReader> fileTable = state.getFileTable();
        if(fileTable.isDefined(fileName.getValue()))
            throw new InterpreterException("File is already open.");

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName.getValue()));
        } catch (FileNotFoundException e) {
            throw new InterpreterException("File could not be opened.");
        }

        fileTable.add(fileName.getValue(), reader);
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
        return "OpenReadFile " + expression.toString();
    }
}
