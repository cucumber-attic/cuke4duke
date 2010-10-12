package cucumber.mojo;

import cucumber.runtime.java.pico.PicoFactory;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class CucumberMojoTest {
    private CucumberMojo mojo;

    @Before
    public void setup() {
        mojo = new CucumberMojo();
        mojo.baseDirectory = new File(".");
        mojo.mavenProject = new MavenProject();
        mojo.mavenProject.setFile(new File("../."));
        mojo.compileClasspathElements = new ArrayList<String>();
        mojo.pluginArtifacts = new ArrayList<Artifact>();
        mojo.testClasspathElements = new ArrayList<String>();
        mojo.localRepository = new DefaultArtifactRepository("", "", new DefaultRepositoryLayout());
    }

    @Test
    public void shouldParseFeaturesAsStringIntoList() {
        mojo.features = "features features/file.feature,src/features/file2.feature:2";

        assertThat(mojo.parseFeatures(), hasItems("features", "features/file.feature", "src/features/file2.feature:2"));
    }

    @Test
    public void shouldDefaultToPicoFactory() throws MojoExecutionException {
        assertThat(mojo.objectFactory(), instanceOf(PicoFactory.class));    
    }

    @Test(expected = MojoExecutionException.class)
    public void shouldThrowExceptionIfUnknownObjectFactory() throws MojoExecutionException {
        mojo.objectFactoryFqcn = "unknown.ObjectFactory";
        mojo.objectFactory();    
    }

    @Test
    public void shouldDefaultToUseStepDefinitionsFrom_cucumber_runtime_fixtures() {
        assertThat(mojo.stepDefPackage, equalTo("cucumber.runtime.fixtures"));
    }
}
