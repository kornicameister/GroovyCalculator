package org.kornicameister.calculator

import org.kornicameister.calculator.util.Associativity
import org.kornicameister.calculator.util.Operators

class ToRPNConverter {
    static def LEFT_CURL = '(' as String;
    static def RIGHT_CURL = ')' as String;

    public static toRPN(final String input) {
        final def inputTokens = input.split(' ');

        final ArrayList<String> out = new ArrayList<String>();
        final Stack<String> stack = new Stack<String>();

        inputTokens.metaClass.eachWithPeek = { closure ->
            def last = null
            delegate?.each { current ->
                if (last) closure(last, current)
                last = current
            }
            if (last) closure(last, null)
        }

        inputTokens.eachWithPeek { token, peek ->
            token = token as String
            peek = peek != null ? peek as String : null

            if (!stack.empty && stack.peek().equals('.')) {
                // . added in previous iteration
                out.add(stack.pop())
            } else if (isOperator(token)) {
                while (!stack.empty && isOperator(stack.peek())) {
                    // [S4]
                    if ((isAssociative(token, Associativity.LEFT) && cmpPrecedence(
                            token, stack.peek()) <= 0)
                            || (isAssociative(token, Associativity.RIGHT) && cmpPrecedence(
                            token, stack.peek()) < 0)) {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token)
            } else if (token.equals(LEFT_CURL)) {
                stack.push(token);
            } else if (token.equals(RIGHT_CURL)) {
                while (!stack.empty && !stack.peek().equals(LEFT_CURL)) {
                    out.add(stack.pop());
                }
                stack.pop();
            } else {
                if ('.'.equals(peek)) {
                    out.add(token)
                    stack.add(peek)
                } else {
                    out.add(token)
                }
            }
        }

        while (!stack.empty()) {
            out.add(stack.pop());
        }

        return out.join(' ')
                .replaceAll('\\s\\.', '.')
                .replaceAll('\\.\\s', '.');
    }

    def static boolean isAssociative(String token, Associativity type) {
        if (!isOperator(token)) {
            throw new IllegalArgumentException("Invalid token: " + token);
        }
        return Operators.find { op ->
            return op.associativity == type
        } != null
    }

    def static int cmpPrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new IllegalArgumentException("Invalid tokens: " + token1 + " " + token2);
        }
        return Operators.forToken(token1).weight - Operators.forToken(token2).weight
    }

    def static boolean isOperator(final String token) {
        return Operators.values().find { op ->
            return op.symbol == token
        } != null;
    }
}