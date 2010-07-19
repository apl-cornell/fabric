import java.util.List;


/**
 * Hook to provide data to the tree navigation menu
 *
 * @author Lucas Waye
 */
public interface DataProvider {

  /**
   * @return root name of browser tree
   */
  public Object getRoot();
  
  /**
   * @param obj object to find all children for
   * @return all children of obj
   */
  List<Object> getChildrenForNode(Object obj);
  
  /**
   * @param obj object to get description for
   * @return description of obj
   */
  String getDescriptionForNode(Object obj);
  
}
