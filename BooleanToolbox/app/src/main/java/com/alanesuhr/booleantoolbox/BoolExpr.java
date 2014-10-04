package com.alanesuhr.booleantoolbox;

import java.util.Map;

/**
 * Created by benjamin on 10/3/14.
 */
public class BoolExpr {

    /**
     * Operation implemented by this
     */
    enum Kind {
        AND,
        OR,
        XOR,
        CONST,
        VAR
    }

    enum Variable {
        A,
        B,
        C,
        D,
        W,
        X,
        Y,
        Z
    }

    /**
     * True if expression is inverted
     */
    private boolean inverted = false;

    private Kind kind;

    private BoolExpr childA, childB;

    private Variable variable;

    private boolean constant;

    private BoolExpr() {

    }

    /**
     * Evaluates this
     *
     * @return value of this
     */
    public boolean eval(Map<Variable, Boolean> values) {
        boolean result = true;
        switch (this.kind) {
            case AND:
                result = this.childA.eval(values) && this.childB.eval(values);
            case OR:
                result = this.childA.eval(values) || this.childB.eval(values);
            case XOR:
                result = this.childA.eval(values) != this.childB.eval(values);
            case CONST:
                result = this.constant;
            case VAR:
                result = values.get(this.variable);
        }
        if (this.inverted) {
            result = !result;
        }
        return result;
    }

    public void setInverted(boolean i) {
        this.inverted = i;
    }

    public Set<Variable> getVariablesUsed() {
      return null;
    }


    public static BoolExpr makeAnd(BoolExpr a, BoolExpr b, boolean inv) {
        BoolExpr out = new BoolExpr();
        out.kind = Kind.AND;
        out.childA = a;
        out.childB = b;
        out.inverted = inv;
        return out;
    }

    public static BoolExpr makeOr(BoolExpr a, BoolExpr b, boolean inv) {
        BoolExpr out = new BoolExpr();
        out.kind = Kind.OR;
        out.childA = a;
        out.childB = b;
        out.inverted = inv;
        return out;
    }

    public static BoolExpr makeXor(BoolExpr a, BoolExpr b, boolean inv) {
        BoolExpr out = new BoolExpr();
        out.kind = Kind.XOR;
        out.childA = a;
        out.childB = b;
        out.inverted = inv;
        return out;
    }

    public static BoolExpr makeConst(boolean c) {
        BoolExpr out = new BoolExpr();
        out.kind = Kind.CONST;
        out.constant = c;
        out.inverted = false;
        return out;
    }

    public static BoolExpr makeVar(Variable v, boolean inv) {
        BoolExpr out = new BoolExpr();
        out.kind = Kind.VAR;
        out.variable = v;
        out.inverted = inv;
        return out;
    }

    public static BoolExpr makeAnd(BoolExpr a, BoolExpr b) {
        return makeAnd(a, b, false);
    }

    public static BoolExpr makeOr(BoolExpr a, BoolExpr b) {
        return makeOr(a, b, false);
    }

    public static BoolExpr makeXor(BoolExpr a, BoolExpr b) {
        return makeXor(a, b, false);
    }

    public static BoolExpr makeVar(Variable v) {
        return makeVar(v, false);
    }
}