package visitors.domain;

import java.util.List;

public class Project implements Node {
  private final List<Expression> projections;
  private final Node child;
  
  public Project(List<Expression> projections, Node child) {
    this.projections = projections;
    this.child = child;
  }
  
  public List<Expression> getProjections() {
    return this.projections;
  }
  
  public Node getChild() {
    return this.child;
  }
  
  public String toString() {
    return "Project<" + projections + ">\n" + child;
  }
}
