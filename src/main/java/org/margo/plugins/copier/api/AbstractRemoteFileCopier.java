package org.margo.plugins.copier.api;

import com.google.common.base.Preconditions;
import org.margo.plugins.copier.exception.CopierException;

import java.io.File;
import java.net.URI;

public abstract class AbstractRemoteFileCopier implements RemoteFileCopier {

    private ThreadLocal<Config> config;

    @Override
    public void copy(File source, URI targetPath) throws CopierException {
        Preconditions.checkNotNull(source);
        Preconditions.checkArgument(source.exists(), "source file must exist");
        Preconditions.checkArgument(source.isFile(), "source file must be a file");
        Preconditions.checkArgument(source.canRead(), "source file must have at least read permission");
        try {
            config = new ThreadLocal<>();
            config.set(new Config(targetPath));
            doCopy(source, targetPath);
        } catch (Exception e) {
            throw new CopierException(e);
        }
    }

    protected abstract void doCopy(File source, URI targetPath) throws Exception;

    protected Config getConfig() {
        return config.get();
    }
}
