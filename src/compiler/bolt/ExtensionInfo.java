package bolt;

import java.io.Reader;
import java.util.Set;

import bolt.ast.BoltExtFactory_c;
import bolt.ast.BoltLang_c;
import bolt.ast.BoltNodeFactory;
import bolt.ast.BoltNodeFactory_c;
import bolt.parse.Grm;
import bolt.parse.Lexer_c;
import bolt.types.BoltTypeSystem;
import bolt.types.BoltTypeSystem_c;
import polyglot.ast.NodeFactory;
import polyglot.ext.jl5.ast.JL5ExtFactory_c;
import polyglot.ext.jl7.JL7ExtensionInfo;
import polyglot.ext.jl7.ast.JL7ExtFactory_c;
import polyglot.frontend.CupParser;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source;
import polyglot.lex.Lexer;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;

/**
 * Extension information for Bolt extension.
 */
public class ExtensionInfo extends JL7ExtensionInfo {
  static {
    // force Topics to load
    @SuppressWarnings("unused")
    Topics t = new Topics();
  }

  @Override
  public String defaultFileExtension() {
    return "bolt";
  }

  @Override
  public String compilerName() {
    return "boltc";
  }

  @Override
  public Parser parser(Reader reader, Source source, ErrorQueue eq) {
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm = new Grm(lexer, ts, nf, eq);
    return new CupParser(grm, source, eq);
  }

  @Override
  public Set<String> keywords() {
    return new Lexer_c(null).keywords();
  }

  @Override
  protected NodeFactory createNodeFactory() {
    return new BoltNodeFactory_c(BoltLang_c.INSTANCE,
        new BoltExtFactory_c(new JL7ExtFactory_c(new JL5ExtFactory_c())));
  }

  @Override
  protected TypeSystem createTypeSystem() {
    return new BoltTypeSystem_c();
  }

  @Override
  public Scheduler createScheduler() {
    return new BoltScheduler(this);
  }

  @Override
  public BoltTypeSystem typeSystem() {
    return (BoltTypeSystem) super.typeSystem();
  }

  @Override
  public BoltNodeFactory nodeFactory() {
    return (BoltNodeFactory) super.nodeFactory();
  }

}
