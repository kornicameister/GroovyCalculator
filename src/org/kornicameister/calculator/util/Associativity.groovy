package org.kornicameister.calculator.util


enum Associativity {
    LEFT(0),
    RIGHT(1);

    int value;

    Associativity(final int value) {
        this.value = value;
    }
}
