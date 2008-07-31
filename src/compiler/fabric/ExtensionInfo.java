package fabric;

import java.io.Reader;

import polyglot.ast.NodeFactory;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Parser;
import polyglot.lex.Lexer;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import fabric.ast.FabricNodeFactory;
import fabric.ast.FabricNodeFactory_c;
import fabric.parse.Grm;
import fabric.parse.Lexer_c;
import fabric.types.FabricTypeSystem;
import fabric.types.FabricTypeSystem_c;

/**
 * Extension information for ../../fabric extension.
 */
public class ExtensionInfo extends jif.ExtensionInfo {
    static {
        // force Topics to load
        new Topics();
    }

    protected fabil.ExtensionInfo targetExt;
    
    @Override
    public String defaultFileExtension() {
        return "fab";
    }

    @Override
    public String compilerName() {
        return "fabc";
    }

    @Override
    public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
        Lexer lexer = new Lexer_c(reader, source, eq);
        Grm grm = new Grm(lexer, (FabricTypeSystem) ts, (FabricNodeFactory) nf, eq);
        return new CupParser(grm, source, eq);
    }

    @Override
    protected NodeFactory createNodeFactory() {
        return new FabricNodeFactory_c();
    }

    @Override
    protected TypeSystem createTypeSystem() {
        return new FabricTypeSystem_c(targetExt.typeSystem());
    }

}
