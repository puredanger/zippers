package visitors;

import visitors.domain.Column;
import visitors.domain.CompareCriteria;
import visitors.domain.Concat;
import visitors.domain.Filter;
import visitors.domain.Join;
import visitors.domain.Project;
import visitors.domain.Table;
import visitors.domain.Value;

public class AbstractVisitor implements Visitor {

  @Override
  public void visit(Column c) {
  }

  @Override
  public void visit(CompareCriteria cc) {
  }

  @Override
  public void visit(Concat c) {
  }

  @Override
  public void visit(Filter f) {
  }

  @Override
  public void visit(Join j) {
  }

  @Override
  public void visit(Project p) {
  }

  @Override
  public void visit(Table t) {
  }

  @Override
  public void visit(Value<?> v) {
  }
}
