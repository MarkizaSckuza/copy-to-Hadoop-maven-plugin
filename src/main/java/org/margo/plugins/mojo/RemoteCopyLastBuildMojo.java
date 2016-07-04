package org.margo.plugins.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.margo.plugins.copier.RemoteFileCopier;
import org.margo.plugins.copier.RemoteFileCopierProcessor;
import org.margo.plugins.copier.downloader.DownloaderFactory;
import org.margo.plugins.copier.exception.CopierException;
import org.margo.plugins.copier.uploader.UploaderFactory;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

@Mojo(name = "lastBuild")
@Execute(goal = "lastBuild", phase = LifecyclePhase.DEPLOY)
public class RemoteCopyLastBuildMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(property = "toUri", required = true)
    private URI toUri;

    private RemoteFileCopier remoteFileCopier;

    public RemoteCopyLastBuildMojo() {
        remoteFileCopier = new RemoteFileCopierProcessor(new DownloaderFactory(), new UploaderFactory());
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        String buildName = project.getBuild().getFinalName();
        String buildDirPath = project.getBuild().getDirectory();

        File buildDir = new File(buildDirPath);
        File file = Arrays.stream(buildDir.listFiles()).filter(x -> x.getName().contains(buildName)).findFirst().orElse(null);

        if (file == null) {
            getLog().error("There's no build file in project.");
            throw new MojoFailureException("There's no build file in project.");
        }

        try {
            remoteFileCopier.copy(file.toURI(), toUri);
        } catch (CopierException e) {
            throw new MojoFailureException(e.getMessage());
        }

    }
}
