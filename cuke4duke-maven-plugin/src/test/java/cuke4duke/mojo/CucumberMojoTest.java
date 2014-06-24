package cuke4duke.mojo;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Proxy;
import org.apache.maven.settings.Settings;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CucumberMojoTest {

    private CucumberMojo mojo;

    @Before
    public void setUp() {
        mojo = new CucumberMojo();
        mojo.launchDirectory = new File(".");
        mojo.mavenProject = new MavenProject();
        mojo.mavenProject.setFile(new File("../."));
        mojo.compileClasspathElements = new ArrayList<String>();
        mojo.pluginArtifacts = new ArrayList<Artifact>();
        mojo.testClasspathElements = new ArrayList<String>();
        mojo.localRepository = new DefaultArtifactRepository("", "", new DefaultRepositoryLayout());
        
        
        Proxy proxy = new Proxy();
        proxy.setActive(true);
        proxy.setHost("myproxy.com");
        proxy.setPort(8080);
        proxy.setProtocol("http");
        proxy.setPassword("secret");
        proxy.setUsername("theuser");
        Settings settings = new Settings();
        settings.setProxies(Arrays.asList(proxy));
        mojo.settings = settings;
        
    }

    @Test
    public void shouldAddCucumberArgs() {
        String cucumberArg = "testArg";
        mojo.cucumberArgs = new ArrayList<String>();
        mojo.cucumberArgs.add(cucumberArg);
        assertTrue(mojo.allCucumberArgs().contains(cucumberArg));
    }

    @Test
    public void shouldAllowZeroAddCucumberArgs() {
        mojo.extraCucumberArgs = null;
        mojo.allCucumberArgs();
    }

    @Test
    public void shouldSplitAddCucumberArgsIntoRealCucumberArgs() {
        mojo.extraCucumberArgs = "arg1 arg2 arg3";
        assertEquals("arg1 arg2 arg3 features", mojo.allCucumberArgs());
    }

    @Test
    public void shouldIgnoreNullJvmArg() throws MojoExecutionException {
        mojo.jvmArgs = Arrays.asList("-Dfoo=bar", null, "");
        assertEquals(Arrays.asList("-Dfoo=bar", ""), Arrays.asList(mojo.cucumber("").getCommandLine().getVmCommand().getArguments()));
    }
    
    @Test
    public void shouldAddCredentialsToProxyURL(){
    	String proxyArg = mojo.getProxyArg().trim();
    	assertEquals("--http-proxy http://theuser:secret@myproxy.com:8080", proxyArg);
    }
    

}
