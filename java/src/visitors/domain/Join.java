package visitors.domain;

import visitors.Visitor;

public class Join implements Node {
  public enum Type {
    INNER, LEFT, RIGHT, FULL
  }

  private final Type type;
  private final Criteria criteria;
  private final Node left;
  private final Node right;

  public Join(Type type, Criteria criteria, Node left, Node right) {
    this.type = type;
    this.criteria = criteria;
    this.left = left;
    this.right = right;
  }

  public Type getType() {
    return type;
  }

  public Node getLeft() {
    return left;
  }

  public Node getRight() {
    return right;
  }

  public Criteria getCriteria() {
    return criteria;
  }

  @Override
  public String toString() {
    return "Join<" + type.name() + " | " + criteria + ">";
  }

  public void acceptVisitor(Visitor v) {
    v.visit(this);
  }
}
