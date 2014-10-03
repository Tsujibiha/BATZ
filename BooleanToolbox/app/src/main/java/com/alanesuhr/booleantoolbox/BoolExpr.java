package com.alanesuhr.booleantoolbox;

import java.util.Map;

/**
 * Created by benjamin on 10/3/14.
 */
public class BoolExpr {

    /**
     * Operation implemented by this
     */
    enum Op {
        AND,
        OR
    }

    /**
     * Evaluates this
     * @return value of this
     */
    boolean eval(Map<Character, Boolean> values) {
        return false;
    }

    /**
     * Generates a truth table
     */
    TruthTable getTruthTable() {
        return null;
    }
}
