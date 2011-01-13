package visitors.domain;

import visitors.Visitor;

public interface Node {
  public void acceptVisitor(Visitor v);
}
