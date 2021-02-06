package model.statement;

import model.ProgramState;
import model.adt.Dict;
import model.adt.Heap;
import model.adt.IDict;
import model.exceptions.InterpreterException;
import model.expression.IExpression;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private final IExpression expression;
    private final String varName;

    public ReadFile(IExpression _expression, String _varName) {
        this.expression = _expression;
        this.varName = _varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Dict<String, IValue> symTable = state.getSymTable();
        Dict<String, BufferedReader> fileTable = state.getFileTable();
        Heap heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new InterpreterException(String.format("Variable %s is not present in symbol table.", varName));

        IValue value = symTable.search(varName);
        if (!value.getType().equals(new IntType()))
            throw new InterpreterException(String.format("Variable %s is not IntType", varName));

        value = expression.eval(symTable, heap);
        if (!value.getType().equals(new StringType()))
            throw new InterpreterException("Expression does not evaluate to StringType");

        StringValue stringValue = (StringValue) value;
        if (!fileTable.isDefined(stringValue.getValue()))
            throw new InterpreterException("File table does not contain this value.");

        BufferedReader reader = fileTable.search(stringValue.getValue());
        try {
            String line = reader.readLine();
            if (line == null)
                line = "0";
            symTable.update(varName, new IntValue(Integer.parseInt(line)));
        } catch (IOException e) {
            throw new InterpreterException("Could not read from file.");
        }

        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType varType = typeEnvironment.search(varName);
        IType expType = expression.typeCheck(typeEnvironment);
        if (varType.equals(new IntType())){
            if(expType.equals(new StringType())){
                return typeEnvironment;
            }
            else
                throw new InterpreterException("File must be of String type.");
        }
        else
            throw new InterpreterException("Variable must be of Integer type.");
    }

    @Override
    public String toString(){
        return "ReadFile: " + expression.toString() + " " + varName;
    }
}
