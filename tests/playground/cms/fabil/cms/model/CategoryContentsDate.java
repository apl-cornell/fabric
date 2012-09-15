package cms.model;

import java.util.Date;

public class CategoryContentsDate extends CategoryContents {

  private Date value;
  
  public Date getDate()                { return this.value; }
  public void setDate(final Date date) { this.value = date; }
  
  public void accept(Visitor visitor) {
    visitor.visitDateContents(this);
  }

  public String getType() {
    return CategoryColumn.DATE;
  }
  
  public CategoryContentsDate(CategoryColumn column, CategoryRow row, Date value) {
    super(column, row);
    setDate(value);
  }

}
