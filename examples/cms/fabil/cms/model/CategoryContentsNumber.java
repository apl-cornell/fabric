package cms.model;

public class CategoryContentsNumber extends CategoryContents {

  private int value;
  
  public int  getValue() { return this.value; }
  public void setValue(final int value) { this.value = value; }
  
  public CategoryContentsNumber(CategoryColumn col, CategoryRow row, int value) {
    super(col, row);
    setValue(value);
  }
  
  public void accept(Visitor visitor) {
    visitor.visitNumberContents(this);
  }

  public String getType() {
    return CategoryColumn.NUMBER;
  }
}
