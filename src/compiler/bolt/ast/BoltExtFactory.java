package bolt.ast;

import polyglot.ast.Ext;
import polyglot.ext.jl7.ast.JL7ExtFactory;

/**
 * Extension factory for Bolt extension.
 */
public interface BoltExtFactory extends JL7ExtFactory {
  /**
   * Creates an extension object for {@link LabelComponent}.
   */
  Ext extLabelComponent();

  /**
   * Creates an extension object for {@link Policy}.
   */
  Ext extPolicy();

  /**
   * Creates an extension object for {@link ConfPolicy}.
   */
  Ext extConfPolicy();

  /**
   * Creates an extension object for {@link IntegPolicy}.
   */
  Ext extIntegPolicy();

  /**
   * Creates an extension object for {@link ReaderPolicy}.
   */
  Ext extReaderPolicy();

  /**
   * Creates an extension object for {@link WriterPolicy}.
   */
  Ext extWriterPolicy();

  /**
   * Creates an extension object for {@link Label}.
   */
  Ext extLabel();

  /**
   * Creates an extension object for {@link JoinLabel}.
   */
  Ext extJoinLabel();

  /**
   * Creates an extension object for {@link Principal}.
   */
  Ext extPrincipal();

  /**
   * Creates an extension object for {@link TopPrincipal}.
   */
  Ext extTopPrincipal();

  /**
   * Creates an extension object for {@link BottomPrincipal}.
   */
  Ext extBottomPrincipal();
}
