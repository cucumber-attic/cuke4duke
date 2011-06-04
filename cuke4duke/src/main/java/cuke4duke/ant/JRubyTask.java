package cuke4duke.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.Path;

import java.io.File;
import java.util.Iterator;

public class JRubyTask extends Java {
    public JRubyTask() {
        setFork(true);
        setFailonerror(true);
    }
    
    protected boolean hasArgNamed(String s) {
       String[] vmargs = getCommandLine().getVmCommand().getArguments();
       if (vmargs.length > 0) {
	       for (int i = 0; i <= vmargs.length - 1; i++) {
	    	   String arg = vmargs[i];
	    	   if (arg.matches(".*" + s + ".*")) {
	    		   log("Arg " + arg + " matched search string " + s,Project.MSG_VERBOSE);
	    		   return true;
	    	   } else {
	    		   log("Arg " + arg + " did not match search string " + s,Project.MSG_VERBOSE);
	    	   }
	       }
       }
       log("No match for search string " + s,Project.MSG_VERBOSE);
       return false;
    }

    @Override
    public void execute() throws BuildException {
        setClassname("org.jruby.Main");
        setClasspath(getJrubyClasspath());
        ensureJrubyHomeExists();
        setJRubyHome();
        super.execute();
    }

    protected File getJrubyHome() {
        String gemHome = getProject().getProperty("jruby.home");
        if (gemHome == null) {
            throw new BuildException("Please set the jruby.home property in your build script.");
        }
        return new File(gemHome);
    }

    protected File getBinDir() {
        return new File(getJrubyHome(), "bin");
    }

    private void ensureJrubyHomeExists() {
        getJrubyHome().mkdirs();
    }

    private Path getJrubyClasspath() {
        Object jrubyClasspath = getProject().getReference("jruby.classpath");
        if (jrubyClasspath == null || !(jrubyClasspath instanceof Path)) {
            throw new BuildException("Please create a path with id jruby.classpath");
        }
        return (Path) jrubyClasspath;
    }

    private void setJRubyHome() {
        Environment.Variable gemHome = new Environment.Variable();
        gemHome.setKey("GEM_HOME");
        gemHome.setFile(getJrubyHome());
        this.addEnv(gemHome);

        Environment.Variable gemPath = new Environment.Variable();
        gemPath.setKey("GEM_PATH");
        gemPath.setFile(getJrubyHome());
        this.addEnv(gemPath);
    }
}
