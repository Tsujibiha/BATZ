package com.alanesuhr.booleantoolbox;

import android.util.Log;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by benjamin on 10/3/14.
 */
public class BoolExprParse {

    private static final String TAG = "BoolExprParse";

    protected static final String AndChars   = "*^";
    protected static final String OrChars    = "+v";
    protected static final String XorChars   = "@";
    protected static final String NotChars   = "'!";
    protected static final String TrueChars  = "T1";
    protected static final String FalseChars = "F0";
    protected static final String VarChars   = "ABCDWXYZ";

    BoolExpr parseExpr(Queue<Character> in) {
        boolean inverted = parseNot(in);

        BoolExpr cur_term = parseTerm(in);
        while(!in.isEmpty()) { // or each term
            char next = in.remove();
            if (OrChars.indexOf(next) != -1) {
                BoolExpr next_term = parseTerm(in);
                cur_term = BoolExpr.makeOr(cur_term, next_term, inverted);
            }
        }
        return cur_term;
    }

    BoolExpr parseTerm(Queue<Character> in) {
        boolean inverted = parseNot(in);

        BoolExpr cur_factor = parseFactor(in);
        while(!in.isEmpty()) {
            char next = in.remove();
            if(AndChars.indexOf(next) != -1) {
                BoolExpr next_factor = parseFactor(in);
                cur_factor = BoolExpr.makeAnd(cur_factor, next_factor, inverted);
            }
        }
        return cur_factor;
    }

    BoolExpr parseFactor(Queue<Character> in) {
        BoolExpr factor;
        if(in.peek() == '(') {
            factor = parseExpr(in);
            in.remove(); // throw out ')'
        } else {
            factor = parseConstOrVar(in);
        }
        return factor;
    }

    boolean parseNot(Queue<Character> in) {
        boolean inverted = false;
        if(!in.isEmpty() && NotChars.indexOf(in.peek()) != -1) { // starts with not
            inverted = true;
            in.remove();
        }
        return inverted;
    }

    BoolExpr parseConstOrVar(Queue<Character> in) {
        boolean inverted = parseNot(in);

        char c = in.remove();
        if(TrueChars.indexOf(c) != -1) {
            return BoolExpr.makeConst(true);
        } else if(FalseChars.indexOf(c) != -1) {
            return BoolExpr.makeConst(false);
        } else {
            int var = VarChars.indexOf(c);
            if(var != -1) {
                return BoolExpr.makeVar(BoolExpr.Variable.values()[var], inverted);
            } else {
                Log.e(TAG, "Expected Const or Var, got: " + c);
                return null;
            }
        }
    }

    public static Queue<Character> getTokens (String stringToTokenize){
        Queue<Character> tokens = new PriorityQueue<Character>();
        for (int i = 0; i < stringToTokenize.length(); i++) {
            Character c = stringToTokenize.charAt(i);
            if (c != ' ') {
                tokens.offer(c);
            }
        }

        return tokens;
    }
}
