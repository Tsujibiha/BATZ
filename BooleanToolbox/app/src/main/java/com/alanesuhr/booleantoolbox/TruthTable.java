package com.alanesuhr.booleantoolbox;
import java.util.ArrayList;
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
    BoolExpr expr;

    public TruthTable(BoolExpr expr) {
        Set<BoolExpr.Variable> vars = expr.getVariablesUsed();
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
        String html = "<table>";

        // Header row
        html += "<tr>";
        // Each variable
        for (BoolExpr.Variable var : expr.getVariablesUsed()) {
            html += "<td>" + var.toString() + "</td>";
        }
        // Final expression
        html += "<td>" + expr.toString() + "</td></tr>";

        // Each row
        for (int i = 0; i < values.size(); i++) {
            html += "<tr>";
            int digit = 0;
            // For each variable, get the digit'th digit of the index's truth and display it.
            for (BoolExpr.Variable var : expr.getVariablesUsed()) {
                // hakcy
                html += "<td>" + (((i >> digit) & 1) != 0 ? "1" : "0") + "</td>";
            }
            // Expression's value
            html += "<td>" + (values.get(i) ? "1" : "0") + "</td>";
            html += "</tr>";
        }

        html += "</table>";
        return html;
    }
}
