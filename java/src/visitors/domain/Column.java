package visitors.domain;

import visitors.Visitor;

public class Column implements Expression {
  private final String table;
  private final String column;
  
  public Column(String table, String column) {
    this.table = table;
    this.column = column;
  }
  
  public String getTable() {
    return this.table;
  }
  
  public String getColumn() {
    return this.column;
  }
  
  public String toString() {
    return table + "." + column;
  }
  
  public void acceptVisitor(Visitor v) {
    v.visit(this);
  }
}
