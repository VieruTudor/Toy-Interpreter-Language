package model.expression;

import model.adt.IDict;
import model.adt.IHeap;
import model.exceptions.InterpreterException;
import model.types.BooleanType;
import model.types.IType;
import model.types.IntType;
import model.values.BooleanValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExpression{
    private final IExpression rhs, lhs;
    String operation;

    public RelationalExpression(IExpression _lhs, IExpression _rhs, String operation){
        this.lhs = _lhs;
        this.rhs = _rhs;
        this.operation = operation;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterException {
        IValue value1 = lhs.eval(symTable, heap);
        if (!value1.getType().equals(new IntType()))
            throw new InterpreterException("First operand is not of IntType");
        IValue value2 = rhs.eval(symTable, heap);
        if (!value2.getType().equals(new IntType()))
            throw new InterpreterException("Second operand is not of IntType");

        return switch(operation){
            case "==" -> new BooleanValue(((IntValue) value1).getValue() == ((IntValue) value2).getValue());
            case "<" -> new BooleanValue(((IntValue) value1).getValue() < ((IntValue) value2).getValue());
            case ">" -> new BooleanValue(((IntValue) value1).getValue() > ((IntValue) value2).getValue());
            case "<=" -> new BooleanValue(((IntValue) value1).getValue() <= ((IntValue) value2).getValue());
            case ">=" -> new BooleanValue(((IntValue) value1).getValue() >= ((IntValue) value2).getValue());
            case "!=" -> new BooleanValue(((IntValue) value1).getValue() != ((IntValue) value2).getValue());
            default -> new BooleanValue(false);
        };
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType type1, type2;
        type1 = lhs.typeCheck(typeEnvironment);
        type2 = rhs.typeCheck(typeEnvironment);
        if (type1.equals(new IntType())){
            if (type2.equals(new IntType())){
                return new BooleanType();
            }
            else
                throw new InterpreterException("Second operand is not an Integer.");
        }
        else
            throw new InterpreterException("First operand is not an Integer.");
    }

    @Override
    public String toString(){
        return lhs.toString() + " " + operation + " " + rhs.toString();
    }
}
