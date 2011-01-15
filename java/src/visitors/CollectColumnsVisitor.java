package visitors;

import java.util.ArrayList;
import java.util.List;

import visitors.domain.Column;

public class CollectColumnsVisitor extends AbstractVisitor {

  private final List<Column> columns = new ArrayList<Column>();
  
  public List<Column> getColumns() {
    return this.columns;
  }
  
  @Override
  public void visit(Column c) {
    columns.add(c);
  }
}
