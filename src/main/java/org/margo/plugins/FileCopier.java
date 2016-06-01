package org.margo.plugins;

import com.google.common.base.Preconditions;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class FileCopier {

    public void copy(String host, String port, File inputFile, String inputFileDestination) throws IOException, URISyntaxException {
        Preconditions.checkArgument(inputFile.isFile(), "Source path must be a file.");

        Configuration configuration = new Configuration();

        FileSystem hdfs = FileSystem.get(new URI("hdfs://" + host + ":" + port), configuration);

        Path path = new Path("hdfs://" + host + ":" + port + "/" + inputFileDestination);

        InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
        OutputStream out = hdfs.create(path);
        IOUtils.copyBytes(in, out, 4096, true);
    }
}
