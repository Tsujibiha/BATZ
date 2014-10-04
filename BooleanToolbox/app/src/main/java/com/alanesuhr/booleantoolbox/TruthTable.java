package com.alanesuhr.booleantoolbox;

/**
 * Created by benjamin on 10/3/14.
 */
public class TruthTable {
  /**
   * Must be of size 2^N, where N is the number of variables in the boolean expression.
   */
  public ArrayList<boolean> values;

  public TruthTable(BoolExpr expr) {
    Set<Variable> vars = expr.getVariablesUsed();
    int numVars = vars.size();
    int numCombs = Math.exp(2, numVars);

    for (int i = 0; i < numCombs; i++) {
      Binary b = i.toBinary();

      Map<Variable, boolean> combination;
      int radix = 0;
      for (Variable var : expr.Vars) {
        combination.add(var, b.valueat(radix));
        place ++;
      }

      values.add(BoolExpr.eval(combination));
    }
  }
}
