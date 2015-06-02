package org.kornicameister.calculator

/**
 *
 * <p>
 *      <small>Class is a part of <b>Calculator</b> and was created at 2015-06-02</small>
 * </p>
 *
 * @author Tomasz
 * @since 0.0.1
 * @version 0.0.1
 */
class CalculatorRPNTest extends GroovyTestCase {

    void testExec() {
        def String input = '3 + 0.6 * 5'
        def String output = '6.0'

        assertEquals(output, CalculatorRPN.perform(input));
    }

    void testExec2() {
        def String input = '3 + ( ( 0.6 * 5 ) ^ 2 )'
        def String output = '12.0'

        assertEquals(output, CalculatorRPN.perform(input));
    }

}
