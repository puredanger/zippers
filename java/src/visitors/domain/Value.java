package visitors.domain;

import visitors.Visitor;

public class Value<T> implements Expression {
  private final T value;
  
  public Value(T value) {
    this.value = value;
  }
  
  public T getValue() {
     return this.value;
  }
  
  public String toString() {
    return "Value<" + this.value + ">";
  }
  
  public void acceptVisitor(Visitor v) {
    v.visit(this);
  }
}
