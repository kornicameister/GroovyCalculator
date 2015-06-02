package org.kornicameister.calculator

class ToRPNConverterTest extends GroovyTestCase {
    void testToRPN() {
        def input = "( 1 + 2 ) * ( 3 / 4 ) ^ ( 5 + 6 )"
        def output = ['1', '2', '+', '3', '4', '/', '5', '6', '+', '^', '*'].join(' ');

        assertEquals(output, ToRPNConverter.toRPN(input));
    }

    void testToRPN2() {
        def input = "1.2 + 3"
        def output = '1.2 3 +'

        assertEquals(output, ToRPNConverter.toRPN(input));
    }

    void testToRPN3() {
        def input = "18 / 9"
        def output = '18 9 /'

        assertEquals(output, ToRPNConverter.toRPN(input));
    }
}
