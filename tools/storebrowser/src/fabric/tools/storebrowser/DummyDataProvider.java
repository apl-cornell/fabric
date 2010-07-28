package fabric.tools.storebrowser;

import java.util.Arrays;
import java.util.List;

public class DummyDataProvider implements DataProvider {

  @Override
  public Object getRoot() {
    return "fab://store0";
  }
  
  public List<Object> getChildrenForNode(Object obj) {
    if(obj.equals("fab://store0")) {
      return Arrays.asList(new Object[] {"one", "two", "three"});
    }
    return Arrays.asList(new Object[] {"blah", "fang"});
  }
  
  public String getDescriptionForNode(Object obj) {
	  return "info";
  }
  
}
