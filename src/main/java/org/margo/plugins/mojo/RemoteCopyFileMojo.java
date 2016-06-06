package org.margo.plugins.mojo;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.margo.plugins.copier.RemoteFileCopierProcessor;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.impl.HDFSFileCopier;

import java.io.File;
import java.net.URI;

/**
 * Goal which copies input file to the specified Hadoop path
 */
@Mojo(name = "copyFile")
@Execute(goal = "copyFile", phase = LifecyclePhase.DEPLOY)
public class RemoteCopyFileMojo extends AbstractMojo {

    public static final String DEFAULT_PORT = "8020";

    /**
     * Host
     */
    @Parameter(property = "host", required = true)
    private String host;

    /**
     * Port
     */
    @Parameter(property = "port")
    private String port;

    /**
     * Path from which copy the input file.
     */
    @Parameter(property = "input.file", required = true)
    private File inputFile;

    @Parameter(property = "uri", required = true)
    private URI uri;

    /**
     * Path to which copy the input file.
     */
    @Parameter(property = "input.dest.path", required = true)
    private String inputFileDestination;

    private HDFSFileCopier hdfsFileCopier;

    public RemoteCopyFileMojo() {
        hdfsFileCopier = new HDFSFileCopier();
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        port = StringUtils.isEmpty(port) ? DEFAULT_PORT : port;
        try {
            RemoteFileCopierProcessor.getInstance().copy(inputFile, uri);
        } catch (CopierException e) {
            throw new MojoFailureException(e.getMessage());
        }
    }
}
