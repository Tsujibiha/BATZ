package com.alanesuhr.booleantoolbox;

import android.util.Log;

import java.util.LinkedList;
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

    public static BoolExpr parse(String s) {
        Queue<Character> in = getTokens(s);
        return parseExpr(in);
    }

    private static BoolExpr parseExpr(Queue<Character> in) {
        Log.d(TAG, "parseExpr called on: " + in.toString());

        BoolExpr cur_term = parseTerm(in);
        while(!in.isEmpty()) { // for each term
            char next = in.remove();
            if (OrChars.indexOf(next) != -1) {
                BoolExpr next_term = parseTerm(in);
                cur_term = BoolExpr.makeOr(cur_term, next_term);
            }
        }
        return cur_term;
    }

    private static BoolExpr parseTerm(Queue<Character> in) {
        Log.d(TAG, "parseTerm called on: " + in.toString());

        boolean inverted = parseNot(in);

        BoolExpr cur_factor = parseFactor(in);
        while(!in.isEmpty()) { // for each factor
            char next = in.remove();
            if(AndChars.indexOf(next) != -1) {
                BoolExpr next_factor = parseFactor(in);
                cur_factor = BoolExpr.makeAnd(cur_factor, next_factor, inverted);
            }
        }
        return cur_factor;
    }

    private static BoolExpr parseFactor(Queue<Character> in) {
        Log.d(TAG, "parseFactor called on: " + in.toString());

        BoolExpr factor;
        if(in.peek() == '(') {
            if(in.isEmpty()) {
                Log.e(TAG, "Unexpected end of input!");
            }
            in.remove(); // throw out '('
            factor = parseExpr(in);
            if(in.isEmpty()) {
                Log.e(TAG, "Unexpected end of input!");
            }
            in.remove(); // throw out ')'
        } else {
            factor = parseConstOrVar(in);
        }
        return factor;
    }

    private static boolean parseNot(Queue<Character> in) {
        Log.d(TAG, "parseNot called on: " + in.toString());

        boolean inverted = false;
        if(!in.isEmpty() && NotChars.indexOf(in.peek()) != -1) { // starts with not
            inverted = true;
            in.remove();
        }
        return inverted;
    }

    private static BoolExpr parseConstOrVar(Queue<Character> in) {
        Log.d(TAG, "parseConstOrVar called on: " + in.toString());

        boolean inverted = parseNot(in);

        char c = in.peek();
        if(TrueChars.indexOf(c) != -1) {
            in.remove();
            return BoolExpr.makeConst(true);
        } else if(FalseChars.indexOf(c) != -1) {
            in.remove();
            return BoolExpr.makeConst(false);
        } else {
            int var = VarChars.indexOf(c);
            if(var != -1) {
                in.remove();
                return BoolExpr.makeVar(BoolExpr.Variable.values()[var], inverted);
            } else {
                Log.e(TAG, "Expected Const or Var, got: " + c);
                return null;
            }
        }
    }

    private static Queue<Character> getTokens (String stringToTokenize){
        Queue<Character> tokens = new LinkedList<Character>();
        for (int i = 0; i < stringToTokenize.length(); i++) {
            Character c = stringToTokenize.charAt(i);
            if (c != ' ') {
                tokens.add(c);
            }
        }

        return tokens;
    }
}
