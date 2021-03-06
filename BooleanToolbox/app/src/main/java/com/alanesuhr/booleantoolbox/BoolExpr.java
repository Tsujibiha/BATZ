package com.alanesuhr.booleantoolbox;

import android.util.Log;
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
        BUF,
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

    private String TAG = "BoolExpr";

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
            case BUF:
                result = this.childA.eval(values);
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
                String outA = this.childA.toString();
                String outB = this.childB.toString();
                if(this.childA.kind == Kind.OR || this.childA.kind == Kind.XOR) {
                    outA = "(" + outA + ")";
                }
                if(this.childB.kind == Kind.OR || this.childB.kind == Kind.XOR) {
                    outB = "(" + outB + ")";
                }
                out = outA + "*" + outB;
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
            case BUF:
                out = this.childA.toString();
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


    public Kind getKind() {
        return this.kind;
    }

    public BoolExpr getChildA() {
        return this.childA;
    }

    public BoolExpr getChildB() {
        return this.childB;
    }

    public boolean getConstant() {
        return this.constant;
    }

    public BoolExpr.Variable getVariable() { return this.variable; }

    public boolean getInverted() {
        return this.inverted;
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

    public BoolExpr getSimplifiedExpr() {
        return (new TruthTable(this)).getSimplifiedExpr();
    }

    public void setChildA(BoolExpr expr) {
        if (this.kind == Kind.CONST || this.kind == kind.VAR) {
            Log.d(TAG, "Const or var cannot have child");
            throw new RuntimeException();
        }
        this.childA = expr;
    }

    public void setChildB(BoolExpr expr) {
        if (this.kind == Kind.CONST || this.kind == kind.VAR || this.kind == kind.BUF) {
            Log.d(TAG, "Const, var, buf cannot have child");
            throw new RuntimeException();
        }

        this.childB = expr;
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

    public static BoolExpr makeNot(BoolExpr a) {
        BoolExpr out = new BoolExpr();
        out.kind = Kind.BUF;
        out.inverted = true;
        out.childA = a;
        return out;
    }

    public static BoolExpr makeBuf(BoolExpr a) {
        BoolExpr out = new BoolExpr();
        out.kind = Kind.BUF;
        out.inverted = false;
        out.childA = a;
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