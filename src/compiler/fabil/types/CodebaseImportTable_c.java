package fabil.types;

import java.net.URI;
import java.util.Collection;

import polyglot.main.Report;
import polyglot.types.ImportTable;
import polyglot.types.Importable;
import polyglot.types.Named;
import polyglot.types.NoClassException;
import polyglot.types.Package;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.StringUtil;
import fabil.frontend.CodebaseSource;
import fabric.Topics;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;

public class CodebaseImportTable_c extends ImportTable implements
    CodebaseImportTable {
  protected Codebase codebase;
  protected CodebaseSource source;

  public CodebaseImportTable_c(TypeSystem ts, Package pkg, CodebaseSource source) {
    super(ts, pkg, source.name());
    this.source = source;
    this.codebase = source.codebase();
  }

  public CodebaseImportTable_c(TypeSystem ts, Package pkg) {
    super(ts, pkg);
  }

  /**
   * Find a type by name, searching the import table.
   * 
   * Search order:
   *    absolute name:                                  --> SR
   *    
   *    long name: 
   *       platform type OR offline                     --> SR
   *       otherwise                                    --> CB
   *            if local source                         --> SR, add to CB
   *            
   *    short name:
   *       current package                              (re-enters w/ long name)
   *       imported packages                            (re-enters w/ long name)
   *       null package                                 --> CB
   *            if local source                         --> SR, add to CB  
   *    
   */
  @Override
  public Named find(String name) throws SemanticException {
    if (Report.should_report(TOPICS, 2))
      Report.report(2, this + ".find(" + name + ")");

    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
   
    // Is the name absolute? (i.e. fab://store/onum/pkg.ClassName)
    URI uri = URI.create(name);
    if(uri.isAbsolute()) {
      return super.find(name);
    }
      
    // Is the name long? (i.e. pkg.ClassName)
    if (!StringUtil.isNameShort(name)) {
      if (cbts.localTypesOnly() || SysUtil.isPlatformType(name))
        return super.find(name);
      else {
        FClass fclass = codebase.resolveClassName(name);
        if (fclass == null) {
          // For local source, build codebase lazily
          if (!source.isRemote()) {
            Named n = super.find(name);
            cbts.addRemoteFClass(codebase, n);
            return n;
          }
        }
        String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
        return super.find(prefix + name);
      }
    }
    
    // The class name is short. (i.e. ClassName)
    if(cbts.localTypesOnly())
      return super.find(name);
    else {
      Named n = null;
      try {
        n = super.find(name);
      } catch(NoClassException e) {
        //If this is remote source, it could still
        // be a null-package name in the codebase.
        // Otherwise, the name is not found.
        if(!source.isRemote())
          throw e;
      }
      //XXX: HACK: special-case for the null package since
      // parent calls systemResolver directly.
      if ((n instanceof Importable && ((Importable) n).package_() == null)
          || n == null) {
        if(source.isRemote()) {
          // ignore null package result, use codebase to resolve
          // FIXME: it would be better if to skip directly to
          //            this case for null-package names in remote files.
          //            Currently, we are searching the local classpath,
          //            sourcepath, and codebase path, but will never 
          //            use the result, even if we find something.
          FClass fclass = codebase.resolveClassName(name);          
          if(fclass == null) {
            // Now it is really not found.
            throw new NoClassException(name);
          }
          String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
          return super.find(prefix + name);
        }
        //insert class w/ null package into codebase
        else {
          cbts.addRemoteFClass(codebase, n);
          return n;
        }
      }
      return n;
    }
  }

  @Override
  protected Named cachedFind(String name) throws SemanticException {
    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
      
    URI uri = URI.create(name);
    if (cbts.localTypesOnly() || uri.isAbsolute() || SysUtil.isPlatformType(name)) {
      return super.cachedFind(name);
    }
    
    FClass fclass = codebase.resolveClassName(name);
    if (fclass == null) {
      // For local source, build codebase lazily
      if(!source.isRemote()) {
        Named n = super.cachedFind(name);
        cbts.addRemoteFClass(codebase, n);
        return n;
      }
      return null;
    }
    
    String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
    return super.cachedFind(prefix + name);
  }

  @Override
  protected Named findInPkg(String name, String pkgName)
      throws SemanticException {
    // HACK Ignore java.lang.Object so that fabric.lang.Object takes priority.
    if ("Object".equals(name) && "java.lang".equals(pkgName)) return null;

    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
    // Platform types and local source may use unqualified names for resolution
    // at the fabric layer
    
    if (cbts.localTypesOnly() || SysUtil.isPlatformType(pkgName)) {
      return super.findInPkg(name, pkgName);
    }
    FClass fclass = codebase.resolveClassName(pkgName + "." + name);
    if (fclass == null) {
      // For local source, build codebase lazily
      if(!source.isRemote()) {
        Named n = super.findInPkg(name, pkgName);
        cbts.addRemoteFClass(codebase, n);
        return n;
      } 
      return null;
    }
    
    String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
    return super.findInPkg(name, prefix + pkgName);
  }

  @Override
  protected boolean isVisibleFrom(Named n, String pkgName) {
    // Codebases do not affect package visibility, so remove
    // prefix from pkgName if necessary
    URI uri = URI.create(pkgName);
    if (n instanceof CodebaseClassType && uri.isAbsolute()) {
      Codebase cb = ((CodebaseClassType) n).codebase();
      String cbPart = SysUtil.codebasePrefix(cb);
      return super.isVisibleFrom(n, pkgName.substring(cbPart.length()));
    } else {
      return super.isVisibleFrom(n, pkgName);
    }
  }

  @Override
  protected void lazyImport() throws SemanticException {
    // XXX Basically copied from super class.
    if (lazyImports.isEmpty()) {
      return;
    }

    CodebaseTypeSystem cbts = (CodebaseTypeSystem) ts;
    for (int i = 0; i < lazyImports.size(); i++) {
      String longName = (String) lazyImports.get(i);
      Named n = null;
      try {
        if (Report.should_report(TOPICS, 2))
          Report.report(2, this + ": import " + longName);
        
        if (cbts.localTypesOnly() || SysUtil.isPlatformType(longName))
          n = ts.systemResolver().find(longName);
        else {
          FClass fclass = codebase.resolveClassName(longName);
          if (fclass == null) {
            // For local source, build codebase lazily
            if (!source.isRemote()) {
              n = ts.systemResolver().find(longName);
              if(n != null)
                cbts.addRemoteFClass(codebase, n);
            }
          } 
          else {
            String prefix = SysUtil.codebasePrefix(fclass.getCodebase());
            n = ts.systemResolver().find(prefix + longName);
          }
        }
        
        String shortName = StringUtil.getShortNameComponent(longName);
        map.put(shortName, n);

      } catch (SemanticException e) {
        Position pos = e.position();
        if (pos == null) pos = (Position) lazyImportPositions.get(i);
        if (pos == null) pos = sourcePos;
        throw new SemanticException(pos, e);
      }
    }

  }

  public CodebaseSource source() {
    return source;
  }

  protected static final Collection TOPICS =
      CollectionUtil.list(Topics.mobile, Report.types, Report.resolver,
          Report.imports);

}
