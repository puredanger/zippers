package visitors;

import java.util.ArrayList;
import java.util.List;

import visitors.domain.Column;
import visitors.domain.CompareCriteria;
import visitors.domain.Concat;
import visitors.domain.Expression;
import visitors.domain.Filter;
import visitors.domain.Join;
import visitors.domain.Node;
import visitors.domain.Project;
import visitors.domain.Table;
import visitors.domain.Value;

public class Main {

  public static void main(String[] args) {
    Node tree = createTree();
    
    CollectColumnsVisitor colVisitor = new CollectColumnsVisitor();
    NavigationVisitor v = new NavigationVisitor(colVisitor);
    tree.acceptVisitor(v);
    System.out.println("columns = " + colVisitor.getColumns());
  }
  
  public static Node createTree() {
    Table t1 = new Table("Dept");
    Table t2 = new Table("Employees");
    
    CompareCriteria joinCrit = new CompareCriteria(
        new Column("Dept", "ID"), 
        CompareCriteria.Op.EQUALS, 
        new Column("Employees", "Dept_ID"));
    Join j = new Join(Join.Type.INNER, joinCrit, t1, t2);
    
    CompareCriteria filterCrit = new CompareCriteria(
        new Column("Dept", "Name"),
        CompareCriteria.Op.EQUALS,
        new Value<String>("IT"));
    Filter f = new Filter(filterCrit, j);
    
    List<Expression> concatArgs = new ArrayList<Expression>();
    concatArgs.add(new Column("Employees", "FirstName"));
    concatArgs.add(new Value<String>(" "));
    concatArgs.add(new Column("Employees", "LastName"));
    List<Expression> projections = new ArrayList<Expression>();
    projections.add(new Concat(concatArgs));
    Project project = new Project(projections, f);
        
    return project;
  }

}
