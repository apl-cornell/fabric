package cms.model;

public class CategoryContentsLink extends CategoryContents {

  private String address;
  private String label;
  
  public String getAddress() { return this.address; }
  public String getLabel()   { return this.label;   }
  
  public void setAddress(final String address) { this.address = address; }
  public void setLabel(final String label)     { this.label   = label;   }
  
  public CategoryContentsLink(CategoryRow row, CategoryColumn col, String address, String label) {
    super(col, row);
    setAddress(address);
    setLabel(label);
  }
  
  public void accept(Visitor visitor) {
    visitor.visitLinkContents(this);
  }

  public String getType() {
    return CategoryColumn.LINK;
  }

}
