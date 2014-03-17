package org.pentaho.gateway.hadoopWrappers;

import org.apache.hadoop.fs.FileStatus;
import org.pentaho.hadoop.shim.api.fs.FileSystem;
import org.pentaho.hadoop.shim.api.fs.Path;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by bryan on 3/16/14.
 */
public interface HadoopFileSystem extends FileSystem {
    public List<String> listChildren(Path path) throws IOException;

    public void setTimes(Path path, long modtime, long atime) throws IOException;

    public long getModificationTime(Path path) throws IOException;

    public void rename(Path oldPath, Path newPath) throws IOException;

    public void mkdirs(Path path) throws IOException;

    public String getFileType(Path path) throws IOException;

    public InputStream open(Path path) throws IOException;

    public OutputStream append(Path path) throws IOException;

    public OutputStream create(Path path) throws IOException;
}
