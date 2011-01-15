package visitors;

import visitors.domain.Column;
import visitors.domain.CompareCriteria;
import visitors.domain.Concat;
import visitors.domain.Filter;
import visitors.domain.Join;
import visitors.domain.Project;
import visitors.domain.Table;
import visitors.domain.Value;

public interface Visitor {
  void visit(Column c);
  void visit(CompareCriteria cc);
  void visit(Concat c);
  void visit(Filter f);
  void visit(Join j);
  void visit(Project p);
  void visit(Table t);
  void visit(Value<?> v);
}
