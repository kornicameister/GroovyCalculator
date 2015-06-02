package org.kornicameister.calculator

import groovy.swing.SwingBuilder
import org.kornicameister.calculator.util.Operators

import javax.swing.*
import java.awt.*

/**
 *
 * <p>
 *      <small>Class is a part of <b>Calculator</b> and was created at 2015-04-21</small>
 * </p>
 *
 * @author Tomasz
 * @since 0.0.1
 * @version 0.0.1
 */
class SwingCalculator implements Calculator {

    def sb = new SwingBuilder()
    def title = 'SwingCalculator'
    def Stack<String> buffor = new Stack<>();
    def hadException = false;

    public static void main(String[] args) {
        new SwingCalculator();
    }

    SwingCalculator() {
        sb.edt {
            sb.frame(id: 'mainPanel', title: title, size: [400, 800], visible: true, resizable: true) {
                borderLayout()
                textField(id: "display", editable: false, horizontalAlignment: JTextField.RIGHT, constraints: BorderLayout.NORTH)
                panel(
                        constraints: BorderLayout.CENTER
                ) {
                    gridLayout(cols: 4, rows: 6, hgap: 2, vgap: 2)
                    button(text: "(", actionPerformed: { push(it.source.text) })
                    button(text: ")", actionPerformed: { push(it.source.text) })
                    button(text: "<", actionPerformed: { popText() })
                    button(text: "X", actionPerformed: { clear() })

                    label("");
                    button(text: "^", actionPerformed: { push(it.source.text) })
                    button(text: "%", actionPerformed: { push(it.source.text) })
                    label("");

                    button(text: "7", actionPerformed: { push(it.source.text) })
                    button(text: "8", actionPerformed: { push(it.source.text) })
                    button(text: "9", actionPerformed: { push(it.source.text) })
                    button(text: "/", actionPerformed: { push(it.source.text) })

                    button(text: "4", actionPerformed: { push(it.source.text) })
                    button(text: "5", actionPerformed: { push(it.source.text) })
                    button(text: "6", actionPerformed: { push(it.source.text) })
                    button(text: "*", actionPerformed: { push(it.source.text) })

                    button(text: "1", actionPerformed: { push(it.source.text) })
                    button(text: "2", actionPerformed: { push(it.source.text) })
                    button(text: "3", actionPerformed: { push(it.source.text) })
                    button(text: "-", actionPerformed: { push(it.source.text) })

                    button(text: "0", actionPerformed: { push(it.source.text) })
                    button(text: ".", actionPerformed: { push(it.source.text) })
                    button(text: "+", actionPerformed: { push(it.source.text) })
                    button(text: "=", actionPerformed: { calculate() })
                }
            }
            sb.mainPanel.pack();
        }
    }

    void push(text) {
        if (hadException) {
            buffor.clear()
            sb.display.text = ''
            hadException = false
        }
        def isOperator = Operators.isOperator(text as String);
        if (isOperator) {
            if (this.isPreviousAnOperator()) {
                return;
            } else if ((sb.display.text as String).empty && Operators.RIGHT_CURL.symbol.equals(text)) {
                return;
            }
        }
        sb.display.text += text
        if (isOperator) {
            buffor.push(' ')
        }
        buffor.push(text as String)
        if (isOperator) {
            buffor.push(' ')
        }
    }

    boolean isPreviousAnOperator() {
        return Operators.isOperator((sb.display.text as String).split('').last());
    }

    void popText() {
        final String text = sb.display.text as String
        if (text == null || text.empty) {
            return;
        }
        final java.util.List<String> split = new ArrayList<String>();
        split.addAll(text.split(''));

        def last = split.last();
        split.remove(last);

        this.clear();
        split.forEach({ str -> this.push(str) })
    }

    void clear() {
        sb.display.text = "";
        buffor.clear();
    }

    @Override
    def calculate() {
        if(buffor.empty){
            return;
        }
        try {
            sb.display.text = CalculatorRPN.perform(buffor.join(''));
            buffor.clear();
            buffor.add(sb.display.text as String)
        } catch (Exception exp) {
            hadException = true;
            sb.display.text = exp.getMessage();
        }
    }
}