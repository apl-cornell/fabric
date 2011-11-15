package jif;

import jif.visit.JifTranslator;
import polyglot.ast.Node;
import polyglot.frontend.*;
import polyglot.frontend.goals.CodeGenerated;
import polyglot.frontend.goals.Goal;
import polyglot.frontend.goals.SourceFileGoal;
import polyglot.main.Options;
import polyglot.types.LoadedClassResolver;
import polyglot.types.SemanticException;
import polyglot.types.SourceClassResolver;
import polyglot.util.InternalCompilerError;

/**
 * This is an extension to jl that is the target language of
 * the jif extension.  The only differences between this language
 * and jl is in the translation pass.  The input language is
 * identical.  This extension cannot live on its own: it only provides
 * the passes after EXC_CHECK_ALL: that is those which deal with
 * translation.
 */
public class OutputExtensionInfo extends JLExtensionInfo {
    ExtensionInfo jifExtInfo;
    
    public OutputExtensionInfo(ExtensionInfo jifExtInfo) {
        this.jifExtInfo = jifExtInfo;        
    }
    
    public Options getOptions() {
        return jifExtInfo.getOptions();
    }
    
    public Scheduler createScheduler() {
        return new OutputScheduler(this);
    }

    public Goal getCompileGoal(final Job job) {
        CodeGenerated output = new CodeGenerated(job) {
            public Pass createPass(ExtensionInfo extInfo) {
                return new OutputPass(this, new JifTranslator(job, typeSystem(),
                                                              nodeFactory(), targetFactory()));
            }            
        };

        output = (CodeGenerated) scheduler.internGoal(output);

        try {
            scheduler().addPrerequisiteDependency(output,
                                                  scheduler().Serialized(job));
        }
        catch (CyclicDependencyException e) {
            // Cannot happen
            throw new InternalCompilerError(e);
        }

        return output;
    }
        
    static protected class OutputScheduler extends JLScheduler {    
        /**
         * Hack to ensure that we track the job for java.lang.Object specially.
         * In particular, ensure that it is submitted for re-writing before
         * any other job.
         */
        protected Job objectJob = null;
        
        public OutputScheduler(OutputExtensionInfo extInfo) {
            super(extInfo);
        }

        /**
         * 
         */
        public Job addJob(Source source, Node ast) {
            Job j = super.addJob(source, ast);
            if ("Object.jif".equals(source.name())) {
                this.objectJob = j;
            }
            return j;
        }
        /**
         * 
         */
        public Job addJob(Source source) {
            Job j = super.addJob(source);
            if ("Object.jif".equals(source.name())) {
                this.objectJob = j;
            }
            return j;
        }

        public Goal TypesInitialized(Job job) {
            Goal g = super.TypesInitialized(job);
            try {
                if (objectJob != null && job != objectJob) {
                    addPrerequisiteDependency(g, TypesInitialized(objectJob));
                }
            }
            catch (CyclicDependencyException e) {
                // Cannot happen
                throw new InternalCompilerError(e);
            }
            return g;
        }
    
        public Goal Parsed(Job job) {
            return internGoal(new SourceFileGoal(job) {
                public Pass createPass(polyglot.frontend.ExtensionInfo extInfo) {
                    return new EmptyPass(this);              
                }
            });
        }
    }
    
    protected void initTypeSystem() {
        try {
            LoadedClassResolver lr;
            lr = new SourceClassResolver(compiler, this, 
                                         jifExtInfo.getJifOptions().constructOutputExtClasspath(),
                                         compiler.loader(), true,
                                         getOptions().compile_command_line_only,
                                         getOptions().ignore_mod_times);
            ts.initialize(lr, this);
        }
        catch (SemanticException e) {
            throw new InternalCompilerError(
                "Unable to initialize type system.", e);
        }
    }
}
