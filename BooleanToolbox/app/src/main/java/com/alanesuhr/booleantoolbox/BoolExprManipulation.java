package com.alanesuhr.booleantoolbox;

import android.util.Log;

/**
 * Created by alsuhr on 4/10/14.
 */
public class BoolExprManipulation {
    public static BoolExpr useNANDOnly(BoolExpr originalExpr) {
        BoolExpr result;

        if (originalExpr.getKind() == BoolExpr.Kind.CONST
            || originalExpr.getKind() == BoolExpr.Kind.VAR
            || originalExpr.getKind() == BoolExpr.Kind.BUF) {
            result = originalExpr;
        } else if (originalExpr.getKind() == BoolExpr.Kind.AND) {
            // Modify the and gate
            originalExpr.setInverted(true);
            result = BoolExpr.makeNot(originalExpr);
            originalExpr.setChildA(useNANDOnly(originalExpr.getChildA()));
            originalExpr.setChildB(useNANDOnly(originalExpr.getChildB()));
        } else if (originalExpr.getKind() == BoolExpr.Kind.OR) {
            // Modify the or gate
            BoolExpr childA = useNANDOnly(originalExpr.getChildA());
            BoolExpr childB = useNANDOnly(originalExpr.getChildB());

            childA.setInverted(true);
            childB.setInverted(true);

            result = BoolExpr.makeAnd(childA, childB, true);
        } else {
            result = useNANDOnly(originalExpr);
        } // else if originalExpr is inverted
        // replace originalExpr with a nand gate whose children are the original expression
        // Also remove not-not parent-child relations
        // Also remove inverted-only children

        return result;
    }

    public static BoolExpr useNOROnly(BoolExpr originalExpr) {
        BoolExpr result;

        if (originalExpr.getKind() == BoolExpr.Kind.CONST
            || originalExpr.getKind() == BoolExpr.Kind.VAR
            || originalExpr.getKind() == BoolExpr.Kind.BUF) {
            result = originalExpr;
        } else if (originalExpr.getKind() == BoolExpr.Kind.OR) {
            // Modify the and gate
            originalExpr.setInverted(true);
            result = BoolExpr.makeNot(originalExpr);
            originalExpr.setChildA(useNOROnly(originalExpr.getChildA()));
            originalExpr.setChildB(useNOROnly(originalExpr.getChildB()));
        } else if (originalExpr.getKind() == BoolExpr.Kind.AND) {
            // Modify the or gate
            BoolExpr childA = useNOROnly(originalExpr.getChildA());
            BoolExpr childB = useNOROnly(originalExpr.getChildB());

            childA.setInverted(true);
            childB.setInverted(true);

            result = BoolExpr.makeOr(childA, childB, true);
        } else {
            result = useNOROnly(originalExpr);
        } 

        return result;
    }

}
