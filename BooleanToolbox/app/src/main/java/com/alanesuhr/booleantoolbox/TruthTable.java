package com.alanesuhr.booleantoolbox;
import android.util.Log;
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
    BoolExpr corrExpression;
    protected static final String TAG = "TruthTable";

    public TruthTable(BoolExpr expr) {
        values = new ArrayList<Boolean>();
        corrExpression = expr;

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
}
