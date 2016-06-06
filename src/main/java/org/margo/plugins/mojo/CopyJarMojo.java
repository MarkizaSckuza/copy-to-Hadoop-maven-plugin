package org.margo.plugins.mojo;

import com.jcraft.jsch.SftpProgressMonitor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.margo.plugins.copier.impl.SFTPFileCopier;

import java.io.File;
import java.util.Arrays;

@Mojo(name = "copyJar")
@Execute(goal = "copyJar", phase = LifecyclePhase.DEPLOY)
public class CopyJarMojo extends AbstractMojo {

    /**
     * Host
     */
    @Parameter(property = "host", required = true)
    private String host;

    /**
     * User name to access the Linux system
     */
    @Parameter(property = "user", required = true)
    private String userName;

    /**
     * Password for access the Linux system
     */
    @Parameter(property = "pass", required = true)
    private String password;

    /**
     * Path to which copy the jar file.
     */
    @Parameter(property = "jar.path", required = true)
    private String jarFileDestination;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    private final SFTPFileCopier SFTPFileCopier;

    public CopyJarMojo() {
        SFTPFileCopier = new SFTPFileCopier();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        String jarName = project.getBuild().getFinalName() + ".jar";
        File buildDirectory = new File(project.getBuild().getDirectory());

        File file = Arrays.stream(buildDirectory.listFiles()).filter(x -> jarName.equals(x.getName())).findFirst().orElse(null);

        if (file == null) {
            getLog().error("There's no jar file in project.");
            throw new MojoFailureException("There's no jar file in project.");
        }

//        try {
//            SFTPFileCopier.copy(host, userName, password, file, jarFileDestination, monitor);
//        } catch (JSchException | SftpException | FileNotFoundException e) {
//            throw new MojoFailureException(e.getMessage());
//        }
    }

    private SftpProgressMonitor monitor = new SftpProgressMonitor() {

        public void init(final int op, final String source, final String target, final long max) {
            getLog().info("sftp start uploading file from: " + source + " to:" + target);
        }

        public boolean count(final long count) {
            getLog().debug("sftp sending bytes: " + count);
            return true;
        }

        public void end() {
            getLog().info("sftp uploading is done.");
        }
    };
}