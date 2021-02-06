package model.expression;

import model.adt.IDict;
import model.adt.IHeap;
import model.exceptions.InterpreterException;
import model.types.BooleanType;
import model.types.IType;
import model.values.BooleanValue;
import model.values.IValue;

public class LogicExpression implements IExpression {
    private final IExpression lhs, rhs;
    int operation;
    /*
    1 - '&&'
    2 - '||'
     */

    public LogicExpression(IExpression _lhs, IExpression _rhs, String operation) {
        this.lhs = _lhs;
        this.rhs = _rhs;
        switch (operation) {
            case "&&" -> this.operation = 1;
            case "||" -> this.operation = 2;
        }
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterException {
        IValue operand1, operand2;
        operand1 = lhs.eval(symTable, heap);
        if (operand1.getType().equals(new BooleanType())) {
            operand2 = rhs.eval(symTable, heap);
            if (operand2.getType().equals(new BooleanType())) {
                BooleanValue boolValue1 = (BooleanValue) operand1;
                BooleanValue boolValue2 = (BooleanValue) operand2;
                boolean bool1 = boolValue1.getValue();
                boolean bool2 = boolValue2.getValue();
                switch (operation) {
                    case 1:
                        return new BooleanValue(bool1 && bool2);
                    case 2:
                        return new BooleanValue(bool1 || bool2);
                }
            } else
                throw new InterpreterException("Second operand not ");
        } else
            throw new InterpreterException("First operand not a boolean.");
        return new BooleanValue();
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType type1, type2;
        type1 = lhs.typeCheck(typeEnvironment);
        type2 = rhs.typeCheck(typeEnvironment);
        if (type1.equals(new BooleanType())){
            if (type2.equals(new BooleanType())){
                return new BooleanType();
            }
            else
                throw new InterpreterException("Second operand is not Boolean.");
        }
        else
            throw new InterpreterException("First operand is not Boolean.");
    }

}
