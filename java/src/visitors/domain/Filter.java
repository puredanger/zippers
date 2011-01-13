package visitors.domain;

import visitors.Visitor;

public class Filter implements Node {

  private final Criteria criteria;
  private final Node child;

  public Filter(Criteria criteria, Node child) {
    this.criteria = criteria;
    this.child = child;
  }

  public Criteria getCriteria() {
    return this.criteria;
  }

  public Node getChild() {
    return this.child;
  }

  @Override
  public String toString() {
    return "Filter<" + criteria + ">\n" + child;
  }

  public void acceptVisitor(Visitor v) {
    v.visit(this);
  }
}
