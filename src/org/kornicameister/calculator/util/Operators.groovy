package org.kornicameister.calculator.util

public enum Operators {
    ADD('+', Associativity.LEFT, 0),
    SUBSTRACT('-', Associativity.LEFT, 0),
    DIV('/', Associativity.LEFT, 5),
    MUL('*', Associativity.LEFT, 5),
    MOD('%', Associativity.LEFT, 5),
    POWER('^', Associativity.RIGHT, 10),
    LEFT_CURL('(', Associativity.LEFT, -1),
    RIGHT_CURL(')', Associativity.RIGHT, -1);

    String symbol;
    Associativity associativity;
    int weight;

    Operators(final String symbol,
              final Associativity associativity,
              final int weight) {
        this.symbol = symbol;
        this.associativity = associativity;
        this.weight = weight;
    }

    static Operators forToken(final String token) {
        return (Operators) Operators.find { op ->
            return op.symbol.equals(token);
        }
    }

    static boolean isOperator(final String token) {
        return forToken(token) != null;
    }

    static List<String> symbols() {
        final List<String> symbols = new ArrayList<>();
        values().each { op ->
            symbols.add(op.symbol)
        }
        return symbols;
    }
}
