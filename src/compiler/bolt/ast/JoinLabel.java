package bolt.ast;

import java.util.List;

/**
 * A label consisting of a confidentiality policy and an integrity policy.
 */
public interface JoinLabel extends Label {
  List<LabelComponent> components();

  JoinLabel components(List<LabelComponent> components);
}
