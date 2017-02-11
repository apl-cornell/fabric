package bolt.ast;

import java.util.List;

/**
 * A label consisting of a join of a series of components.
 */
public interface JoinLabel extends Label {
  List<LabelComponent> components();

  JoinLabel components(List<LabelComponent> components);
}
