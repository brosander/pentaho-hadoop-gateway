package org.pentaho.gateway.hadoopWrappers.impl;

import org.apache.hadoop.fs.FileStatus;
import org.pentaho.gateway.hadoopWrappers.HadoopFileSystem;
import org.pentaho.hadoop.shim.api.fs.FileSystem;
import org.pentaho.hadoop.shim.api.fs.Path;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan on 3/16/14.
 */
public class HadoopFileSystemImpl implements HadoopFileSystem {
    private final FileSystem delegate;
    private final Class<?> pathClass;
    private final Method listStatus;
    private final Method getFileStatus;
    private final Method rename;
    private final Method mkdirs;
    private final Method open;
    private final Method append;
    private final Method create;
    private final Method statusGetPath;
    private final Method statusGetModificationTime;
    private final Method statusIsDir;
    private final Method setTimes;

    public HadoopFileSystemImpl(FileSystem delegate) throws IOException {
        this.delegate = delegate;
        try {
            pathClass = Class.forName(org.apache.hadoop.fs.Path.class.getCanonicalName(), true, delegate.getDelegate().getClass().getClassLoader());
            listStatus = delegate.getDelegate().getClass().getMethod("listStatus", pathClass);
            getFileStatus = delegate.getDelegate().getClass().getMethod("getFileStatus", pathClass);
            rename = delegate.getDelegate().getClass().getMethod("rename", pathClass, pathClass);
            mkdirs = delegate.getDelegate().getClass().getMethod("mkdirs", pathClass);
            open = delegate.getDelegate().getClass().getMethod("open", pathClass);
            append = delegate.getDelegate().getClass().getMethod("append", pathClass);
            create = delegate.getDelegate().getClass().getMethod("create", pathClass);
            statusGetPath = Class.forName(FileStatus.class.getCanonicalName(), true, delegate.getDelegate().getClass().getClassLoader()).getMethod("getPath");
            statusGetModificationTime = Class.forName(FileStatus.class.getCanonicalName(), true, delegate.getDelegate().getClass().getClassLoader()).getMethod("getModificationTime");
            statusIsDir = Class.forName(FileStatus.class.getCanonicalName(), true, delegate.getDelegate().getClass().getClassLoader()).getMethod("isDir");
            setTimes = delegate.getDelegate().getClass().getMethod("setTimes", pathClass, long.class, long.class);
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        } catch (NoSuchMethodException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Object getDelegate() {
        return delegate.getDelegate();
    }

    @Override
    public Path asPath(String s) {
        return delegate.asPath(s);
    }

    @Override
    public Path asPath(Path path, String s) {
        return delegate.asPath(path, s);
    }

    @Override
    public Path asPath(String s, String s2) {
        return delegate.asPath(s, s2);
    }

    @Override
    public boolean exists(Path path) throws IOException {
        return delegate.exists(path);
    }

    @Override
    public boolean delete(Path path, boolean b) throws IOException {
        return delegate.delete(path, b);
    }

    @Override
    public List<String> listChildren(Path path) throws IOException {
        try {
            Object statusArray = listStatus.invoke(getDelegate(), path);
            if (statusArray.getClass().isArray()) {
                int length = Array.getLength(statusArray);
                List<String> result = new ArrayList<String>(length);
                for (int i = 0; i < length; i++) {
                    Object element = Array.get(statusArray, i);
                    result.add(String.valueOf(statusGetPath.invoke(element)));
                }
                return result;
            } else {
                throw new IOException("listStatus returned object that was not an array");
            }
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof  IOException) {
                throw (IOException)e.getCause();
            }
            throw new IOException(e.getCause());
        }
    }

    @Override
    public void setTimes(Path path, long modtime, long atime) throws IOException {
        try {
            setTimes.invoke(getDelegate(), path, modtime, atime);
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof  IOException) {
                throw (IOException) e.getCause();
            }
            throw new IOException(e.getCause());
        }
    }

    @Override
    public long getModificationTime(Path path) throws IOException {
        try {
            return (Long)statusGetModificationTime.invoke(getFileStatus.invoke(getDelegate(), path));
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new IOException(e.getCause());
        }
    }

    @Override
    public void rename(Path oldPath, Path newPath) throws IOException {
        try {
            rename.invoke(getDelegate(), oldPath, newPath);
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof  IOException) {
                throw (IOException) e.getCause();
            }
            throw new IOException(e.getCause());
        }
    }

    @Override
    public void mkdirs(Path path) throws IOException {
        try {
            mkdirs.invoke(getDelegate(), path);
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new IOException(e);
        }
    }

    @Override
    public String getFileType(Path path) throws IOException {
        try {
            Object status = getFileStatus.invoke(getDelegate(), path);
            if (status == null) {
                return "IMAGINARY";
            }
            boolean result = (Boolean) statusIsDir.invoke(status);
            if (result) {
                return "FOLDER";
            }
            return "FILE";
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new IOException(e.getCause());
        }
    }

    @Override
    public InputStream open(Path path) throws IOException {
        try {
            return (InputStream) open.invoke(getDelegate(), path);
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new IOException(e.getCause());
        }
    }

    @Override
    public OutputStream append(Path path) throws IOException {
        try {
            return (OutputStream) append.invoke(getDelegate(), path);
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new IOException(e.getCause());
        }
    }

    @Override
    public OutputStream create(Path path) throws IOException {
        try {
            return (OutputStream) create.invoke(getDelegate(), path);
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new IOException(e.getCause());
        }
    }
}
