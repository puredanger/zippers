package visitors.domain;

import java.util.List;

public class Concat implements Expression {
  private final List<Expression> args;
  
  public Concat(List<Expression> args) {
    this.args = args;
  }
  
  public List<Expression> getArgs() {
    return this.args;
  }
  
  public String toString() {
    StringBuilder s = new StringBuilder(args.get(0).toString());
    for(int i=1; i<args.size(); i++) {
      s.append(" || ");
      s.append(args.get(i).toString());
    }
    return s.toString();
  }
}
