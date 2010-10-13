package cucumber.mojo;

import cucumber.Runtime;
import cucumber.runtime.Backend;
import cucumber.runtime.java.JavaBackend;
import cucumber.runtime.java.ObjectFactory;
import gherkin.formatter.Formatter;
import gherkin.formatter.PrettyFormatter;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * @goal cucumber
 * @requiresDependencyResolution test
 */
public class CucumberMojo extends AbstractMojo {

    /**
     * @parameter expression="${project}"
     */
    protected MavenProject mavenProject;

    /**
     * @parameter expression="${settings}"
     * @required
     * @readonly
     */
    protected Settings settings;

    /**
     * @parameter expression="${project.basedir}"
     * @required
     */
    protected File baseDirectory;

    /**
     * The project compile classpath.
     *
     * @parameter default-value="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    protected List<String> compileClasspathElements;

    /**
     * The plugin dependencies.
     *
     * @parameter expression="${plugin.artifacts}"
     * @required
     * @readonly
     */
    protected List<Artifact> pluginArtifacts;

    /**
     * The project test classpath
     *
     * @parameter expression="${project.testClasspathElements}"
     * @required
     * @readonly
     */
    protected List<String> testClasspathElements;

    /**
     * @parameter expression="${localRepository}"
     * @required
     * @readonly
     */
    protected ArtifactRepository localRepository;


    /**
     * Features to load.
     *
     * @parameter expression="${cucumber.features}"
     */
    protected String features = "features";

    /**
     * Package holding step definitions.
     *
     * TODO: This seems somewhat awkward.
     * Should the JavaBackend rather just scan the entire classpath for annotations?
     * Or atleast take a List<String> of packages...
     * -oc ( 2010-10-13 )
     *
     * @parameter expression="${cucumber.stepDefinitions}"
     */
    protected String stepDefPackage = "cucumber.runtime.fixtures";

    /**
     * Fully qualified classname of ObjectFactory.
     *
     * @parameter expression="${cucumber.objectFactory}"
     */
    protected String objectFactoryFqcn = "cucumber.runtime.java.pico.PicoFactory";

    public void execute() throws MojoExecutionException, MojoFailureException {

        List<String> listOfFeatures = parseFeatures();
        Backend backend = new JavaBackend(objectFactory(), stepDefPackage);

        try {
            Formatter formatter = new PrettyFormatter(System.out, true);
            Runtime runtime = new Runtime(backend, formatter);
            runtime.execute(listOfFeatures);
        }
        catch (UnsupportedEncodingException e) {
            throw new MojoExecutionException("System doesn't support formatter encoding", e);
        }
        catch (IOException e) {
            throw new MojoExecutionException("Failed executing runtime. Features: <" + features + ">", e);
        }
    }

    @SuppressWarnings({"unchecked"})
    ObjectFactory objectFactory() throws MojoExecutionException {
        try {
            Class<ObjectFactory> clazz = (Class<ObjectFactory>) getClass().getClassLoader().loadClass(objectFactoryFqcn);
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new MojoExecutionException("Couldn't instantiate ObjectFactory: " + objectFactoryFqcn, e);
        }
    }

    List<String> parseFeatures() {
        return Arrays.asList(features.split("[, ]"));
    }


}
