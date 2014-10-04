package com.alanesuhr.booleantoolbox;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Created by benjamin on 10/3/14.
 */
public class BoolExprParse {

    private static final String AndChars = "*^";
    private static final String OrChars = "+v";
    private static final String XorChars = "@";
    private static final String NotChars = "'!";

    BoolExpr parseExpr(Queue<Character> in) {
        char next = in.remove();
        boolean inverted = false;
        if (NotChars.indexOf(next) != -1) { // starts with not
            inverted = true;
            next = in.remove();
        }

        if (OrChars.indexOf(in.peek()) != -1) { // has an or

        }
    }

    public static Queue<Character> getTokens (String stringToTokenize){
        Queue<Character> tokens = new PriorityQueue<Character>();
        for (int i = 0; i < stringToTokenize.length(); i++) {
            Character c = stringToTokenize.charAt(i);
            if (AndChars.indexOf(c) != -1 || OrChars.indexOf(c) != -1 || XorChars.indexOf(c) != -1
                || NotChars.indexOf(c) != -1) {
                tokens.offer(c);
            }
        }

        return tokens;
    }
}
