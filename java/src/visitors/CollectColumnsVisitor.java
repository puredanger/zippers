package visitors;

import java.util.HashSet;
import java.util.Set;

import visitors.domain.Column;

public class CollectColumnsVisitor extends AbstractVisitor {

  private final Set<Column> columns = new HashSet<Column>();
  
  public Set<Column> getColumns() {
    return this.columns;
  }
  
  @Override
  public void visit(Column c) {
    columns.add(c);
  }
}
