package org.pentaho.gateway.impl;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.hadoop.HadoopConfigurationBootstrap;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginTypeInterface;
import org.pentaho.gateway.api.HadoopGatewayException;
import org.pentaho.gateway.api.HdfsService;
import org.pentaho.gateway.args.PathAndConfig;
import org.pentaho.gateway.args.RenameParameters;
import org.pentaho.gateway.args.SetModifiedTimeParameters;
import org.pentaho.gateway.args.WriteFileParameters;
import org.pentaho.gateway.hadoopWrappers.HadoopFileSystem;
import org.pentaho.gateway.hadoopWrappers.impl.HadoopFileSystemImpl;
import org.pentaho.gateway.util.Pair;
import org.pentaho.hadoop.shim.ConfigurationException;
import org.pentaho.hadoop.shim.api.Configuration;
import org.pentaho.hadoop.shim.api.fs.FileSystem;
import org.pentaho.hadoop.shim.api.fs.Path;
import org.pentaho.hadoop.shim.spi.HadoopShim;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


@Component
@javax.ws.rs.Path("/hdfs")
@SuppressWarnings("unused")
public class HdfsServiceImpl implements HdfsService {
    private final HadoopShim hadoopShim;

    @SuppressWarnings("unused")
    public HdfsServiceImpl() throws ConfigurationException {
        hadoopShim = new HadoopConfigurationBootstrap() {

            @Override
            protected PluginInterface getPluginInterface() throws KettleException {
                return new PluginInterface() {
                    @Override
                    public String[] getIds() {
                        return new String[0];
                    }

                    @Override
                    public Class<? extends PluginTypeInterface> getPluginType() {
                        return null;
                    }

                    @Override
                    public Class<?> getMainType() {
                        return null;
                    }

                    @Override
                    public List<String> getLibraries() {
                        return null;
                    }

                    @Override
                    public String getName() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public String getImageFile() {
                        return null;
                    }

                    @Override
                    public String getCategory() {
                        return null;
                    }

                    @Override
                    public boolean isSeparateClassLoaderNeeded() {
                        return false;
                    }

                    @Override
                    public boolean isNativePlugin() {
                        return false;
                    }

                    @Override
                    public Map<Class<?>, String> getClassMap() {
                        return null;
                    }

                    @Override
                    public boolean matches(String s) {
                        return false;
                    }

                    @Override
                    public String getErrorHelpFile() {
                        return null;
                    }

                    @Override
                    public URL getPluginDirectory() {
                        try {
                            return new File("/home/bryan/data-integration/plugins/pentaho-big-data-plugin").toURI().toURL();
                        } catch (MalformedURLException e) {
                            return null;
                        }
                    }

                    @Override
                    public String getDocumentationUrl() {
                        return null;
                    }

                    @Override
                    public void setDocumentationUrl(String s) {

                    }

                    @Override
                    public String getCasesUrl() {
                        return null;
                    }

                    @Override
                    public void setCasesUrl(String s) {

                    }

                    @Override
                    public String getForumUrl() {
                        return null;
                    }

                    @Override
                    public void setForumUrl(String s) {

                    }

                    @Override
                    public String getClassLoaderGroup() {
                        return null;
                    }

                    @Override
                    public void setClassLoaderGroup(String s) {

                    }
                };
            }
        }.getProvider().getActiveConfiguration().getHadoopShim();
    }

    @POST
    @javax.ws.rs.Path("/createFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Override
    public void createFile(@FormDataParam("pathAndConfig") PathAndConfig pathAndConfig, @FormDataParam("inputStream") InputStream inputStream) throws HadoopGatewayException {
        OutputStream outputStream = null;
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(pathAndConfig);
            outputStream = pair.getSecond().create(pair.getFirst());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to create file " + pathAndConfig.getPath());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }

    @POST
    @javax.ws.rs.Path("/appendFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Override
    public void appendFile(@FormDataParam("pathAndConfig") PathAndConfig pathAndConfig, @FormDataParam("inputStream") InputStream inputStream) throws HadoopGatewayException {
        OutputStream outputStream = null;
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(pathAndConfig);
            outputStream = pair.getSecond().append(pair.getFirst());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to append to file " + pathAndConfig.getPath());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }

    @POST
    @javax.ws.rs.Path("/readFile")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response readFile(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(pathAndConfig);
            return Response.ok(pair.getSecond().open(pair.getFirst()), MediaType.APPLICATION_OCTET_STREAM).build();
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to read file " + pathAndConfig.getPath());
        }
    }

    @POST
    @javax.ws.rs.Path("/delete")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public void delete(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(pathAndConfig);
            pair.getSecond().delete(pair.getFirst(), true);
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to delete file " + pathAndConfig.getPath());
        }
    }

    @POST
    @javax.ws.rs.Path("/getFileType")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public String getFileType(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(pathAndConfig);
            return pair.getSecond().getFileType(pair.getFirst());
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to get " + pathAndConfig.getPath() + " file type");
        }
    }

    @POST
    @javax.ws.rs.Path("/createFolder")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public void createFolder(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(pathAndConfig);
            pair.getSecond().mkdirs(pair.getFirst());
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to create folder at " + pathAndConfig.getPath());
        }
    }

    @POST
    @javax.ws.rs.Path("/rename")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public void rename(RenameParameters renameParameters) throws HadoopGatewayException {
        org.pentaho.hadoop.shim.api.Configuration configuration = getConfiguration(renameParameters.getPathAndConfig().getConfig());
        try {
            HadoopFileSystem fs = new HadoopFileSystemImpl(hadoopShim.getFileSystem(configuration));
            fs.rename(fs.asPath(renameParameters.getPathAndConfig().getPath()), fs.asPath(renameParameters.getNewPath()));
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to rename " + renameParameters.getPathAndConfig().getPath() + " to " + renameParameters.getNewPath());
        }
    }

    @POST
    @javax.ws.rs.Path("/getLastModifiedTime")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public long getLastModifiedTime(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(pathAndConfig);
            return pair.getSecond().getModificationTime(pair.getFirst());
        } catch (IOException e) {
            throw new HadoopGatewayException("Unablet to get last modified time for " + pathAndConfig.getPath());
        }
    }

    @POST
    @javax.ws.rs.Path("/setLastModifiedTime")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public void setLastModifiedTime(SetModifiedTimeParameters setModifiedTimeParameters) throws HadoopGatewayException {
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(setModifiedTimeParameters.getPathAndConfig());
            pair.getSecond().setTimes(pair.getFirst(), setModifiedTimeParameters.getModtime(), System.currentTimeMillis());
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to set last modified time for " + setModifiedTimeParameters.getPathAndConfig().getPath());
        }
    }

    @POST
    @javax.ws.rs.Path("/children")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public List<String> listChildren(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        try {
            Pair<Path, HadoopFileSystem> pair = getPathAndFileSystem(pathAndConfig);
            return pair.getSecond().listChildren(pair.getFirst());
        } catch (IOException e) {
            throw new HadoopGatewayException("Unable to list children of " + pathAndConfig.getPath());
        }
    }

    private Pair<Path, HadoopFileSystem> getPathAndFileSystem(PathAndConfig pathAndConfig) throws IOException {
        org.pentaho.hadoop.shim.api.Configuration configuration = getConfiguration(pathAndConfig.getConfig());
        FileSystem fs = hadoopShim.getFileSystem(configuration);
        Path path = fs.asPath(pathAndConfig.getPath());
        Class<?> clazz = path.getClass();
        do {
            System.out.println(clazz.getCanonicalName());
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return Pair.<Path, HadoopFileSystem>of(path, new HadoopFileSystemImpl(fs));
    }

    private Configuration getConfiguration(Map<String, String> configurationProperties) {
        org.pentaho.hadoop.shim.api.Configuration configuration = hadoopShim.createConfiguration();
        for (Map.Entry<String, String> entry : configurationProperties.entrySet()) {
            configuration.set(entry.getKey(), entry.getValue());
        }
        return configuration;
    }
}
