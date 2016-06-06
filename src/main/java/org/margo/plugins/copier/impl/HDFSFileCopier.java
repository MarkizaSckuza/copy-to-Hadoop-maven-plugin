package org.margo.plugins.copier.impl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.margo.plugins.copier.api.AbstractRemoteFileCopier;

import java.io.*;
import java.net.URI;

public class HDFSFileCopier extends AbstractRemoteFileCopier {
    @Override
    protected void doCopy(File source, URI targetPath) throws IOException {
        FileSystem hdfs = FileSystem.get(targetPath, new Configuration());
        InputStream in = new BufferedInputStream(new FileInputStream(source));
        OutputStream out = hdfs.create(new Path(targetPath));
        IOUtils.copyBytes(in, out, 4096, true);
    }
}
