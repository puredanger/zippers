package visitors.domain;

public class Table implements Node  {
  private final String name;
  
  public Table(String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String toString() {
    return "Table<" + name + ">";
  }
}
