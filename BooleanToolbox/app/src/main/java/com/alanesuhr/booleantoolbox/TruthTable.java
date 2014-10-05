package com.alanesuhr.booleantoolbox;

import android.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import com.alanesuhr.booleantoolbox.BoolExpr;

/**
 * Created by benjamin on 10/3/14.
 */
public class TruthTable {
    /**
     * Must be of size 2^N, where N is the number of variables in the boolean expression.
     */
    public ArrayList<Boolean> values;
    BoolExpr corrExpression;
    protected static final String TAG = "TruthTable";

    public TruthTable(BoolExpr expr) {
        values = new ArrayList<Boolean>();
        corrExpression = expr;

        List<BoolExpr.Variable> vars = expr.getVariablesUsed();
        int numVars = vars.size();
        int numCombs = 1 << numVars;

        // Use the binary representation of all 2^N numbers to get the combinations of the various
        // variables.
        for (int i = 0; i < numCombs; i++) {
            Map<BoolExpr.Variable, Boolean> combination = new HashMap<BoolExpr.Variable, Boolean>();
            int digit = 0;
            // Each digit in the binary representation is a variable's value, so add to the map for
            // each variable.
            for (BoolExpr.Variable var : vars) {
                // Get the digit'th digit of i and convert to a boolean inline because I can.
                combination.put(var, ((i >> digit) & 1) != 0 ? true : false);
                digit ++;
            }

         values.add(expr.eval(combination));
        }
    }

    public String getHTMLTable() {
        String html = "<table border=\"1\">";

        // Header row
        html += "<tr>";
        // Each variable
        ArrayList<String> varHeaderCells = new ArrayList<String>();
        for (BoolExpr.Variable var : corrExpression.getVariablesUsed()) {
            varHeaderCells.add("<td>" + var.toString() + "</td>");
        }

        for (int i = corrExpression.getVariablesUsed().size() - 1; i >= 0; i--) {
            html += varHeaderCells.get(i);
        }

        // Final expression
        html += "<td>" + corrExpression.toString() + "</td></tr>";

        // Each row
        for (int i = 0; i < values.size(); i++) {
            html += "<tr>";
            int digit = 0;
            // For each variable, get the digit'th digit of the index's truth and display it.
            ArrayList<String> varCells = new ArrayList<String>();
            for (BoolExpr.Variable var : corrExpression.getVariablesUsed()) {
                // hakcy
                varCells.add("<td>" + (((i >> digit) & 1) != 0 ? "1" : "0") + "</td>");
                digit ++;
            }
            for (int j = corrExpression.getVariablesUsed().size() - 1; j >= 0; j--) {
                html += varCells.get(j);
            }

            // Expression's value
            html += "<td>" + (values.get(i) ? "1" : "0") + "</td>";
            html += "</tr>";
        }

        html += "</table>";
        return html;
    }

    public BoolExpr getSimplifiedExpr() {
        // Get the original implicants.
        Map<Integer, ArrayList<Boolean>> originalImplicants = new HashMap<Integer, ArrayList<Boolean>>();

        for (int i = 0; i < values.size(); i++) {
            // Only add if value is 1.
            if (values.get(i)) {
                ArrayList<Boolean> digits = new ArrayList<Boolean>();
                int digit = 0;
                // Get each binary version.
                for (BoolExpr.Variable var : corrExpression.getVariablesUsed()) {
                    digits.add(((i >> digit) & 1) != 0 ? true : false);
                }
            }
        }

        Map<Integer, ArrayList<Boolean>> newImplicants = reduceImplicants(originalImplicants);

        // Continue reducing while you can. Reduction on something that is fully reduced isn't bad, though.
        while (!newImplicants.equals(originalImplicants)) {
            originalImplicants = newImplicants;
            newImplicants = reduceImplicants(originalImplicants);
        }

        return null;
    }

    // Later might want to make this Map<Integer, Integer> where the second is the don't cares.
    private Map<Integer, ArrayList<Boolean>> reduceImplicants(Map<Integer, ArrayList<Boolean>> oldImplicants) {
        Map<Integer, ArrayList<Boolean>> newImplicants = new HashMap<Integer, ArrayList<Boolean>>();

        // Do this for every combination of minterms. Later find a way to avoid comparing things twice.
        for (Integer mintermI : oldImplicants.keySet()) {
            for (Integer mintermJ : oldImplicants.keySet()) {
                // First make sure they're not the same minterm.
                if (mintermI != mintermJ) {
                    // Find the Hamming difference of the integer and the don't cares.
                    int hdisInt = hammingDistance(mintermI, mintermJ);
                    int hdisDC = hammingDistance(intFromBoolList(oldImplicants.get(mintermI)),
                                                 intFromBoolList((oldImplicants.get(mintermJ))));
                    if (hdisInt + hdisDC == 1) {
                        // Only one thing is different.
                        if (hdisInt == 1) {
                            // The minterm representation is different. So find the bit that's different.
                            int changedBit = log2(mintermI - mintermJ);
                            // Create a new implicant and add it to the new implicants to return.
                            ArrayList<Boolean> binRep = oldImplicants.get(mintermI);
                            binRep.add(changedBit, true);
                            int newIntRep = mintermI - 1 << changedBit;
                            newImplicants.put(newIntRep, binRep);
                        } else {
                            // The don't care representation is different. So find the bit that's different.
                            // Todo: really don't know what to do in this case or what to replace it with.
                        }
                    }
                }
            }
        }

        return newImplicants;
    }

    private int hammingDistance(int i, int j) {
        // credit goes to wikipedia.
        int distance = 0;
        int val = i^j; // xor
        while (val != 0) {
            distance ++;
            val &= val - 1;
        }

        return distance;
    }

    private int intFromBoolList(ArrayList<Boolean> list) {
        int result = 0;

        // For each value in the list, modify result by adding the value of 2^i if
        // the boolean is true.
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)) {
                result += 1 << i;
            }
        }

        return result;
    }

    private int log2(int x) {
        int count = 0;
        while (x > 1){
            x = x/2;
            count++;
        }
        return count;
    }
}
