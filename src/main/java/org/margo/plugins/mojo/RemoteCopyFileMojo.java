package org.margo.plugins.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.margo.plugins.copier.RemoteFileCopier;
import org.margo.plugins.copier.RemoteFileCopierProcessor;
import org.margo.plugins.copier.downloader.DownloaderFactory;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.uploader.UploaderFactory;

import java.net.URI;

/**
 * Goal which copies file from specified uri to specified uri
 */
@Mojo(name = "copy")
@Execute(goal = "copy", phase = LifecyclePhase.DEPLOY)
public class RemoteCopyFileMojo extends AbstractMojo {

    @Parameter(property = "fromUri", required = true)
    private URI fromUri;

    @Parameter(property = "toUri", required = true)
    private URI toUri;

    private RemoteFileCopier remoteFileCopier;

    public RemoteCopyFileMojo() {
        remoteFileCopier = new RemoteFileCopierProcessor(new DownloaderFactory(), new UploaderFactory());
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            remoteFileCopier.copy(fromUri, toUri);
        } catch (CopierException e) {
            throw new MojoFailureException(e.getMessage());
        }
    }
}
