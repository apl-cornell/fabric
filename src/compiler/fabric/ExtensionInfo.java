package fabric;

import polyglot.lex.Lexer;
import fabric.parse.Lexer_c;
import fabric.parse.Grm;
import fabric.ast.*;
import fabric.types.*;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.frontend.*;
import polyglot.main.*;

import java.util.*;
import java.io.*;

/**
 * Extension information for ../../fabric extension.
 */
public class ExtensionInfo extends jif.ExtensionInfo {
    static {
        // force Topics to load
        Topics t = new Topics();
    }

    protected fabil.ExtensionInfo targetExt;
    
    public String defaultFileExtension() {
        return "fab";
    }

    public String compilerName() {
        return "fabc";
    }

    public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
        Lexer lexer = new Lexer_c(reader, source, eq);
        Grm grm = new Grm(lexer, (FabricTypeSystem) ts, (FabricNodeFactory) nf, eq);
        return new CupParser(grm, source, eq);
    }

    protected NodeFactory createNodeFactory() {
        return new FabricNodeFactory_c();
    }

    protected TypeSystem createTypeSystem() {
        return new FabricTypeSystem_c(targetExt.typeSystem());
    }

}
