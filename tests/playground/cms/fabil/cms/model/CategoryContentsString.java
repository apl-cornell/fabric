package cms.model;

public class CategoryContentsString extends CategoryContents {

  private String text;
  
  public String getText() { return this.text; }
  public void setText(final String text) { this.text = text; }
  
  public CategoryContentsString(CategoryColumn col, CategoryRow row, String value) {
    super(col, row);
    setText(value);
  }
  
  public void accept(Visitor visitor) {
    visitor.visitStringContents(this);
  }

  public String getType() {
    return CategoryColumn.TEXT;
  }

}
