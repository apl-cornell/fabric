package cms.model;

public class CategoryContentsLink extends CategoryContents {

  private String address;
  private String tlabel;
  
  public String getAddress() { return this.address; }
  public String getTheLabel()   { return this.tlabel;   }
  
  public void setAddress(final String address) { this.address = address; }
  public void setTheLabel(final String label)     { this.tlabel   = label;   }
  
  public CategoryContentsLink(CategoryRow row, CategoryColumn col, String address, String label) {
    super(col, row);
    setAddress(address);
    setTheLabel(label);
  }
  
  public void accept(Visitor visitor) {
    visitor.visitLinkContents(this);
  }

  public String getType() {
    return CategoryColumn.LINK;
  }

}
