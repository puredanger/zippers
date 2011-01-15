package visitors;

import visitors.domain.Column;
import visitors.domain.CompareCriteria;
import visitors.domain.Concat;
import visitors.domain.Expression;
import visitors.domain.Filter;
import visitors.domain.Join;
import visitors.domain.Project;
import visitors.domain.Table;
import visitors.domain.Value;

public class NavigationVisitor implements Visitor {

  private final Visitor visitor;

  public NavigationVisitor(Visitor visitor) {
    this.visitor = visitor;
  }

  @Override
  public void visit(Column c) {
    visitor.visit(c);
  }

  @Override
  public void visit(CompareCriteria cc) {
    visitor.visit(cc);
    cc.getLeftExpression().acceptVisitor(this);
    cc.getRightExpression().acceptVisitor(this);
  }

  @Override
  public void visit(Concat c) {
    visitor.visit(c);
    for (Expression e : c.getArgs()) {
      e.acceptVisitor(this);
    }
  }

  @Override
  public void visit(Filter f) {
    visitor.visit(f);
    f.getCriteria().acceptVisitor(this);
    f.getChild().acceptVisitor(this);
  }

  @Override
  public void visit(Join j) {
    visitor.visit(j);
    j.getCriteria().acceptVisitor(this);
    j.getLeft().acceptVisitor(this);
    j.getRight().acceptVisitor(this);
  }

  @Override
  public void visit(Project p) {
    visitor.visit(p);
    for(Expression projection : p.getProjections()) {
      projection.acceptVisitor(this);
    }
    p.getChild().acceptVisitor(this);
  }

  @Override
  public void visit(Table t) {
    visitor.visit(t);
  }

  @Override
  public void visit(Value<?> v) {
    visitor.visit(v);
  }

}
