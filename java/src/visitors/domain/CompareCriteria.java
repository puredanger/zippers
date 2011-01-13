package visitors.domain;

import visitors.Visitor;

public class CompareCriteria implements Criteria {
  private final Expression leftExpression;
  private final Expression rightExpression;
  
  public enum Op { EQUALS, NOT_EQUALS };
  private final Op operator;
  
  public CompareCriteria(Expression leftExpression, Op operator, Expression rightExpression) {
    this.leftExpression = leftExpression;
    this.operator = operator;
    this.rightExpression = rightExpression;
  }

  public Expression getLeftExpression() {
    return leftExpression;
  }

  public Expression getRightExpression() {
    return rightExpression;
  }

  public Op getOperator() {
    return operator;
  }
  
  private String opToString(Op operator) {
    switch (operator) {
      case EQUALS: return "=";
      case NOT_EQUALS: return "<>";
      default: throw new AssertionError("Invalid operator");
    }
  }
  
  public String toString() {
    return leftExpression + opToString(operator) + rightExpression;  
  }
    
  public void acceptVisitor(Visitor v) {
    v.visit(this);
  }
}
