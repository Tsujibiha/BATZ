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
        if (in.isEmpty()) {
            Log.e(TAG, "Expected an expression.");
            throw new RuntimeException();
        }

        BoolExpr cur_term = parseTerm(in);

        while(!in.isEmpty() && OrChars.indexOf(in.peek()) != -1) { // for each term
            char next = in.remove();
            BoolExpr next_term = parseTerm(in);
            cur_term = BoolExpr.makeOr(cur_term, next_term);
        }
        return cur_term;
    }

    private static BoolExpr parseTerm(Queue<Character> in) {
        BoolExpr cur_factor = parseFactor(in);
        Character c = in.peek();

        while(!in.isEmpty() && (AndChars.indexOf(c) != -1 || NotChars.indexOf(c) != -1 || c == '('
            || TrueChars.indexOf(c) != -1 || FalseChars.indexOf(c) != -1
            || VarChars.indexOf(c) != -1)) {
            // for each factor
            if (AndChars.indexOf(c) != -1) {
                char next = in.remove();
            }

            BoolExpr next_factor = parseFactor(in);
            cur_factor = BoolExpr.makeAnd(cur_factor, next_factor);

            c = in.peek();
        }
        return cur_factor;
    }

    private static BoolExpr parseFactor(Queue<Character> in) {
        boolean inverted = parseNot(in);

        BoolExpr factor;
        if (in.peek() == '(') {
            if(in.isEmpty()) {
                Log.e(TAG, "Unexpected input.");
                throw new RuntimeException();
            }
            in.remove(); // throw out '('
            factor = parseExpr(in);
            if(in.isEmpty()) {
                Log.e(TAG, "Didn't find a closed paren.");
                throw new RuntimeException();
            }
            in.remove(); // throw out ')'
        } else {
            factor = parseConstOrVar(in);
        }

        factor.setInverted(inverted);
        return factor;
    }

    private static boolean parseNot(Queue<Character> in) {
        int numNots = 0;
        while (!in.isEmpty() && NotChars.indexOf(in.peek()) != -1) { // starts with not
            in.remove();
            numNots ++;
        }

        // If the number of nots is even, then it's not inverted. Otherwise it is inverted.
        return (numNots % 2 == 0) ? false : true;
    }

    private static BoolExpr parseConstOrVar(Queue<Character> in) {
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
                in.remove(); // remove var
                return BoolExpr.makeVar(BoolExpr.Variable.values()[var]);
            } else {
                Log.e(TAG, "Expected Const or Var, got: " + c);
                throw new RuntimeException();
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
