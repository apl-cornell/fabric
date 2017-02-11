package bolt.ast;

import java.util.List;

/**
 * A label consisting of a meet of a series of components.
 */
public interface MeetLabel extends Label {
  List<LabelComponent> components();

  MeetLabel components(List<LabelComponent> components);
}
