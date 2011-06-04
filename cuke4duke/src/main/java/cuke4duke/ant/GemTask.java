package cuke4duke.ant;

import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

public class GemTask extends JRubyTask {
    private String args = "";

    public void execute() throws BuildException {
    	if (! hasArgNamed("Xmx")) {
    		log("Found no existing -Xmx arg, setting to 384m",Project.MSG_VERBOSE);
    		createJvmarg().setValue("-Xmx384m");
    	} else {
    		log("Found an existing -Xmx arg, not using default",Project.MSG_VERBOSE);
    	}
        createArg().setValue("-S");
        createArg().setValue("gem");
        getCommandLine().createArgument().setLine(args);
        createArg().setValue("--install-dir");
        createArg().setFile(getJrubyHome());
        createArg().setValue("--no-ri");
        createArg().setValue("--no-rdoc");

        try {
            super.execute();
        } catch (Exception e) {
            throw new BuildException("Failed to run gem with arguments: " + args, e);
        }
    }

    public void setArgs(String args) {
        this.args = args;
    }
}
