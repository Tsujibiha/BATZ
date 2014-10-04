package com.alanesuhr.booleantoolbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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

    public enum Variable {
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
                break;
            case OR:
                result = this.childA.eval(values) || this.childB.eval(values);
                break;
            case XOR:
                result = this.childA.eval(values) != this.childB.eval(values);
                break;
            case CONST:
                result = this.constant;
                break;
            case VAR:
                result = values.get(this.variable);
                break;
        }
        if (this.inverted) {
            result = !result;
        }
        return result;
    }

    @Override
    public String toString() {
        String out = "";
        boolean needParen = this.inverted; // need to put parentheses around output
        switch (this.kind) {
            case AND:
                out = this.childA.toString() + "*" + this.childB.toString();
                if(this.childA.kind == Kind.OR || this.childA.kind == Kind.XOR ||
                   this.childB.kind == Kind.OR || this.childB.kind == Kind.XOR) {
                    needParen = true;
                }
                break;
            case OR:
                out = this.childA.toString() + "+" + this.childB.toString();
                break;
            case XOR:
                out = this.childA.toString() + "@" + this.childB.toString();
                break;
            case CONST:
                out = this.constant ? "T" : "F";
                needParen = false;
                break;
            case VAR:
                out = this.variable.toString();
                needParen = false;
                break;
        }
        if(needParen) {
            out = "(" + out + ")";
        }
        if(this.inverted) {
            out = "!" + out;
        }
        return out;
    }

    public void setInverted(boolean i) {
        this.inverted = i;
    }



    public List<Variable> getVariablesUsed() {
        Set<Variable> set = new HashSet<Variable>(Variable.values().length);
        if(this.kind == Kind.VAR) {
            set.add(this.variable);
        } else if(this.kind != Kind.CONST) {
            set.addAll(this.childA.getVariablesUsed());
            set.addAll(this.childB.getVariablesUsed());
        }

        List out = new ArrayList(Variable.values().length);
        out.addAll(set);
        Collections.sort(out);
        Collections.reverse(out);

        return out;
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