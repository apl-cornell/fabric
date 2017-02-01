package bolt.ast;

import java.util.List;

import polyglot.ext.jl7.ast.JL7NodeFactory;
import polyglot.util.Position;

/**
 * NodeFactory for Bolt extension.
 */
public interface BoltNodeFactory extends JL7NodeFactory {
  /**
   * @return a {@link Label} representing the empty label.
   */
  Label emptyLabel(Position pos);

  JoinLabel JoinLabel(Position pos, LabelComponent... components);

  JoinLabel JoinLabel(Position pos, List<LabelComponent> components);

  /**
   * @return a confidentiality policy representing public information.
   */
  ConfPolicy publicPolicy(Position pos);

  ReaderPolicy ReaderPolicy(Position pos, Principal owner, Principal reader);

  /**
   * @return an integrity policy representing untrusted information.
   */
  IntegPolicy untrustedPolicy(Position pos);

  WriterPolicy WriterPolicy(Position pos, Principal owner, Principal writer);

  TopPrincipal TopPrincipal(Position pos);

  BottomPrincipal BottomPrincipal(Position pos);
}
