package org.kornicameister.calculator

import org.kornicameister.calculator.util.Operators

import static org.kornicameister.calculator.util.Operators.isOperator;

class CalculatorRPN {
    private static final double ZERO = Double.valueOf(0.0)
    private Stack<Double> buf = new Stack<>();

    def static String perform(final String val) {
        return new CalculatorRPN().exec(val);
    }

    def String exec(String val) {
        val = ToRPNConverter.toRPN(val);
        def strings = val.trim().split('\\s')

        for (String str : strings) {
            if (isNumber(str)) {
                this.buf.push(Double.parseDouble(str));
            } else if (isOperator(str)) {
                if (this.buf.empty) {
                    throw new Exception('Provide data');
                } else if (this.buf.size() < 2) {
                    throw new Exception('Too few arguments too calculate')
                } else {
                    this.calculate(str);
                }
            }
        }

        return this.buf.last().toString()
    }

    def void calculate(String val) {
        def pop1 = this.buf.pop()
        def pop2 = this.buf.pop()

        switch (val) {
            case Operators.ADD.symbol:
                this.buf.push(pop1 + pop2)
                break;
            case Operators.SUBSTRACT.symbol:
                this.buf.push(pop2 - pop1)
                break;
            case Operators.DIV.symbol:
                if (pop1.equals(ZERO)) {
                    throw new ArithmeticException('Cannot divide by 0')
                }
                this.buf.push(pop2 / pop1)
                break;
            case Operators.MUL.symbol:
                this.buf.push(pop2 * pop1)
                break;
            case Operators.POWER.symbol:
                this.buf.push(pop2.power(pop1) as Double)
                break;
            case Operators.MOD.symbol:
                this.buf.push(pop2.mod(pop1) as Double)
                break;
        }
    }

    private static isNumber(String op) {
        try {
            Double.parseDouble(op);
            return true;
        } catch (ignore) {
        }
        return false;
    }
}
