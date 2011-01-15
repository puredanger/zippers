package visitors;

import java.util.ArrayList;
import java.util.List;

import visitors.domain.Column;
import visitors.domain.CompareCriteria;
import visitors.domain.Concat;
import visitors.domain.Filter;
import visitors.domain.Join;
import visitors.domain.Node;
import visitors.domain.Project;
import visitors.domain.Table;
import visitors.domain.Value;

public class CollectAllVisitor implements Visitor {

  private List<Node> visited = new ArrayList<Node>();
  
  public List<Node> getVisited() {
    return this.visited;
  }
  
  @Override
  public void visit(Column c) {
    this.visited.add(c);
  }

  @Override
  public void visit(CompareCriteria cc) {
    this.visited.add(cc);
  }

  @Override
  public void visit(Concat c) {
    this.visited.add(c);
  }

  @Override
  public void visit(Filter f) {
    this.visited.add(f);
  }

  @Override
  public void visit(Join j) {
    this.visited.add(j);
  }

  @Override
  public void visit(Project p) {
    this.visited.add(p);
  }

  @Override
  public void visit(Table t) {
    this.visited.add(t);
  }

  @Override
  public void visit(Value<?> v) {
    this.visited.add(v);
  }
}
