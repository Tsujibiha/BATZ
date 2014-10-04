package com.alanesuhr.booleantoolbox;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * Created by benjamin on 10/3/14.
 */
public class BoolExprParse {

    private static final String AndChars  = "*^";
    private static final String OrChars   = "+v";
    private static final String XorChars  = "@";
    private static final String NotChars  = "'!";

    BoolExpr parseExpr(Queue<Character> in) {
        char next = in.remove();
        boolean inverted = false;
        if(NotChars.indexOf(next) != -1) { // starts with not
            inverted = true;
            next = in.remove();
        }

        if(OrChars.indexOf(in.peek()) != -1) { // has an or

    }
}
