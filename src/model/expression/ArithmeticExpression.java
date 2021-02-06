package model.expression;

import model.adt.IDict;
import model.adt.IHeap;
import model.exceptions.InterpreterException;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticExpression implements IExpression {
    private final IExpression lhs;
    private final IExpression rhs;
    int operation;

    /*
    1 - '+'
    2 - '-'
    3 - '*'
    4 - '/'
     */
    public ArithmeticExpression(char _operation, IExpression _lhs, IExpression _rhs) {
        this.lhs = _lhs;
        this.rhs = _rhs;
        switch (_operation) {
            case '+' -> operation = 1;
            case '-' -> operation = 2;
            case '*' -> operation = 3;
            case '/' -> operation = 4;
        }
    }

    @Override
    public String toString() {
        return switch (this.operation) {
            case 1 -> lhs.toString() + " + " + rhs.toString();
            case 2 -> lhs.toString() + " - " + rhs.toString();
            case 3 -> lhs.toString() + " * " + rhs.toString();
            case 4 -> lhs.toString() + " / " + rhs.toString();
            default -> "";
        };

    }

    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterException {
        IValue operand1, operand2;
        operand1 = lhs.eval(symTable, heap);
        if (operand1.getType().equals(new IntType())) {
            operand2 = rhs.eval(symTable, heap);
            if (operand2.getType().equals(new IntType())) {
                IntValue intValue1 = (IntValue) operand1;
                IntValue intValue2 = (IntValue) operand2;
                int number1 = intValue1.getValue();
                int number2 = intValue2.getValue();
                switch (operation) {
                    case 1:
                        return new IntValue(number1 + number2);
                    case 2:
                        return new IntValue(number1 - number2);
                    case 3:
                        return new IntValue(number1 * number2);
                    case 4: {
                        if (number2 == 0) throw new InterpreterException("Division by zero");
                        return new IntValue(number1 / number2);
                    }
                }
            } else
                throw new InterpreterException("Second operand is not an integer.");
        } else
            throw new InterpreterException("First operand is not an integer.");

        return new IntValue();

    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws InterpreterException {
        IType type1, type2;
        type1 = lhs.typeCheck(typeEnvironment);
        type2 = rhs.typeCheck(typeEnvironment);
        if (type1.equals(new IntType())){
            if (type2.equals(new IntType())){
                return new IntType();
            }
            else
                throw new InterpreterException("Second operand is not an Integer.");
        }
        else
            throw new InterpreterException("First operand is not an Integer.");

    }
}
