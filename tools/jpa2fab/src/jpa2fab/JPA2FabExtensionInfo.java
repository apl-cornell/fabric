package jpa2fab;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.tools.FileObject;

import jpa2fab.del.FabILOutputDelFactory_c;
import jpa2fab.ext.JPA2FabILExtFactory_c;
import polyglot.ast.NodeFactory;
import polyglot.ext.jl5.ExtensionInfo;
import polyglot.ext.jl5.JL5Options;
import polyglot.ext.jl5.ast.J5Lang_c;
import polyglot.ext.jl5.ast.JL5ExtFactory_c;
import polyglot.ext.jl5.ast.JL5NodeFactory_c;
import polyglot.ext.jl5.types.JL5TypeSystem_c;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source.Kind;
import polyglot.frontend.TargetFactory;
import polyglot.frontend.goals.Goal;
import polyglot.main.OptFlag;
import polyglot.main.OptFlag.Arg;
import polyglot.main.OptFlag.Switch;
import polyglot.main.Options;
import polyglot.main.UnhandledArgument;
import polyglot.main.UsageError;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import codebases.frontend.LocalSource;
import fabil.FabILOptions;
import fabil.ast.FabILNodeFactory_c;
import fabil.extension.FabILExtFactory_c;
import fabric.OutputExtensionInfo;

public class JPA2FabExtensionInfo extends ExtensionInfo {

  @Override
  public String defaultFileExtension() {
    return "java";
  }

  @Override
  public String compilerName() {
    return "jpa2fab";
  }

  @Override
  protected NodeFactory createNodeFactory() {
    return new JL5NodeFactory_c(J5Lang_c.instance, new JL5ExtFactory_c(
        new JPA2FabILExtFactory_c()));
  }

  @Override
  public TargetFactory targetFactory() {
    JPA2FabOptions opt = (JPA2FabOptions) getOptions();
    if (target_factory == null) {
      if (opt.outputFabIL()) {
        target_factory =
            new TargetFactory(extFileManager(), getOptions().outputLocation(),
                "fil", getOptions().output_stdout);
      } else {
        return super.targetFactory();
      }
    }

    return target_factory;
  }

  @Override
  protected TypeSystem createTypeSystem() {
    return new JL5TypeSystem_c();
  }

  private static Arg<?> convertArg(FabILOptions opt, Arg<?> arg) {
    if (arg.flag().ids().contains("-classpath")) {
      OptFlag<List<URI>> flag =
          (OptFlag<List<URI>>) OptFlag.lookupFlag("-classpath", opt.flags());
      List<File> files = (List<File>) arg.value();
      arg = flag.createArg(arg.next(), FilesToURIs(files));
    } else if (arg.flag().ids().contains("-sourcepath")) {
      OptFlag<List<URI>> flag =
          (OptFlag<List<URI>>) OptFlag.lookupFlag("-sourcepath", opt.flags());
      List<File> files = (List<File>) arg.value();
      arg = flag.createArg(arg.next(), FilesToURIs(files));
    } else if (arg.flag().ids().contains("-bootclasspath")) {
      OptFlag<List<URI>> flag =
          (OptFlag<List<URI>>) OptFlag
              .lookupFlag("-bootclasspath", opt.flags());
      List<File> files = (List<File>) arg.value();
      arg = flag.createArg(arg.next(), FilesToURIs(files));
    } else if (arg.flag().ids().contains("-addbootcp")) {
      OptFlag<List<URI>> flag =
          (OptFlag<List<URI>>) OptFlag.lookupFlag("-addbootcp", opt.flags());
      List<File> files = (List<File>) arg.value();
      arg = flag.createArg(arg.next(), FilesToURIs(files));
    }
    return arg;
  }

  private static List<URI> FilesToURIs(List<File> files) {
    List<URI> uris = new ArrayList<URI>(files.size());
    for (File f : files) {
      uris.add(f.toURI());
    }
    return uris;
  }

  @Override
  public Goal getCompileGoal(Job job) {
    JPA2FabScheduler scheduler = (JPA2FabScheduler) this.scheduler;
    Goal g = null;
    if (((JPA2FabOptions) getOptions()).outputFabIL()) {
      g = scheduler.FabILGenerated(job);
    } else {
      g = scheduler.RemoveJava5isms(job);
    }
    return g;
  }

  @Override
  public FileSource createFileSource(FileObject f, Kind kind)
      throws IOException {
    URI ns = ((fabil.ExtensionInfo) outputExtensionInfo).localNamespace();
    return new LocalSource(f, kind, ns);
  }

  @Override
  public polyglot.frontend.ExtensionInfo outputExtensionInfo() {
    if (this.outputExtensionInfo == null) {
      if (((JPA2FabOptions) getOptions()).outputFabIL()) {
        this.outputExtensionInfo = new OutputExtensionInfo(this) {
          @Override
          protected NodeFactory createNodeFactory() {
            return new FabILNodeFactory_c(new FabILExtFactory_c(),
                new FabILOutputDelFactory_c());
          }

          @Override
          protected Options createOptions() {
            Options parentOpts = parent.getOptions();
            FabILOutputOptions opt = new FabILOutputOptions(this);
            // filter the parent's options by the ones this extension understands
            try {
              List<Arg<?>> arguments = parentOpts.filterArgs(opt.flags());
              List<Arg<?>> newargs =
                  new ArrayList<OptFlag.Arg<?>>(arguments.size());
              for (Arg<?> arg : arguments) {
                newargs.add(convertArg(opt, arg));
              }
              opt.processArguments(newargs, Collections.<String> emptySet());
            } catch (UsageError e) {
              throw new InternalCompilerError(
                  "Got usage error while configuring output extension", e);
            }
            return opt;
          }
        };
      } else {
        this.outputExtensionInfo = new OutputExtensionInfo(this);
      }
    }
    return outputExtensionInfo;
  }

  @Override
  public Scheduler createScheduler() {
    return new JPA2FabScheduler(this);
  }

  @Override
  protected Options createOptions() {
    return new JPA2FabOptions(this);
  }

  public static class JPA2FabOptions extends JL5Options {
    protected FabILOptions fabOpt;
    protected Boolean outputFabIL;

    public JPA2FabOptions(polyglot.frontend.ExtensionInfo extension) {
      super(extension);
      fabOpt = new FabILOptions(extension);
      flags().addAll(fabOpt.flags());
    }

    @Override
    protected void postApplyArgs() {
      super.postApplyArgs();
      this.removeJava5isms = true;
      if (outputFabIL()) this.output_source_only = true;
    }

    @Override
    protected void populateFlags(Set<OptFlag<?>> flags) {
      super.populateFlags(flags);
      flags.add(new Switch(new String[] { "-output-fabil", "--output-fabil" },
          "Output FabIL source only."));
    }

    @Override
    protected void handleArg(Arg<?> arg) throws UsageError {
      try {
        if (arg.flag().ids().contains("-output-fabil")) {
          this.outputFabIL = (Boolean) arg.value();
        } else {
          super.handleArg(arg);
        }
      } catch (UnhandledArgument ua) {
        // fabil options will be handled after the hand-off
        if (!fabOpt.flags().contains(ua.argument().flag())) {
          throw ua;
        }
      }
    }

    public boolean outputFabIL() {
      return outputFabIL;
    }
  }

}
