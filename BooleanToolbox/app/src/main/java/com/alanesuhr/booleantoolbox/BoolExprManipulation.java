package com.alanesuhr.booleantoolbox;

import android.util.Log;

/**
 * Created by alsuhr on 4/10/14.
 */
public class BoolExprManipulation {
    public static BoolExpr useNANDOnly(BoolExpr originalExpr) {
        return nandConvertNots(nandConvertAndOr(originalExpr));
    }

    private static BoolExpr nandConvertAndOr(BoolExpr originalExpr) {
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

            childA = BoolExpr.makeNot(childA);
            childB = BoolExpr.makeNot(childB);

            result = BoolExpr.makeAnd(childA, childB, true);
        } else {
            result = useNANDOnly(originalExpr);
        }
        return result;
    }

    private static BoolExpr nandConvertNots(BoolExpr originalExpr) {
        BoolExpr result;

        if (originalExpr.getKind() == BoolExpr.Kind.BUF) {
            if (originalExpr.getChildA().getKind() == BoolExpr.Kind.BUF) {
                result = nandConvertNots(originalExpr.getChildA().getChildA());
            } else {
                BoolExpr child = nandConvertNots(originalExpr.getChildA());
                result = BoolExpr.makeAnd(child, child, true);
            }
        } else if (originalExpr.getKind() == BoolExpr.Kind.CONST
            || originalExpr.getKind() == BoolExpr.Kind.VAR) {
            result = originalExpr;
        } else {
            result = originalExpr;
            result.setChildA(nandConvertNots(result.getChildA()));
            result.setChildB(nandConvertNots(result.getChildB()));
        }

        return result;
    }

    public static BoolExpr useNOROnly(BoolExpr originalExpr) {
        return norConvertNots(norConvertAndOr(originalExpr));
    }

    private static BoolExpr norConvertAndOr(BoolExpr originalExpr) {
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

            childA = BoolExpr.makeNot(childA);
            childB = BoolExpr.makeNot(childB);

            result = BoolExpr.makeOr(childA, childB, true);
        } else {
            result = useNOROnly(originalExpr);
        }

        return result;
    }

    private static BoolExpr norConvertNots(BoolExpr originalExpr) {
        BoolExpr result;

        if (originalExpr.getKind() == BoolExpr.Kind.BUF) {
            if (originalExpr.getChildA().getKind() == BoolExpr.Kind.BUF) {
                result = nandConvertNots(originalExpr.getChildA().getChildA());
            } else {
                BoolExpr child = nandConvertNots(originalExpr.getChildA());
                result = BoolExpr.makeOr(child, child, true);
            }
        } else if (originalExpr.getKind() == BoolExpr.Kind.CONST
            || originalExpr.getKind() == BoolExpr.Kind.VAR) {
            result = originalExpr;
        } else {
            result = originalExpr;
            result.setChildA(nandConvertNots(result.getChildA()));
            result.setChildB(nandConvertNots(result.getChildB()));
        }

        return result;
    }

}
