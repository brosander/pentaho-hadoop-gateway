package org.pentaho.gateway.client.impl;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.mortbay.util.ajax.JSON;
import org.pentaho.gateway.adapters.ListOfStringsAdapter;
import org.pentaho.gateway.adapters.MapAdapter;
import org.pentaho.gateway.api.HadoopGatewayException;
import org.pentaho.gateway.api.HdfsService;
import org.pentaho.gateway.args.PathAndConfig;
import org.pentaho.gateway.args.RenameParameters;
import org.pentaho.gateway.args.SetModifiedTimeParameters;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bryan on 3/17/14.
 */
public class HdfsServiceClientImpl implements HdfsService {
    private final Client client = initClient();

    private Client initClient() {
        Client c = ClientBuilder.newClient();
        c.register(MapAdapter.class);
        c.register(MultiPartFeature.class);
        c.register(ListOfStringsAdapter.class);
        c.register(PathAndConfig.class);
        return c;
    }

    @Override
    public void createFile(PathAndConfig pathAndConfig, InputStream inputStream) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/createFile");
        Invocation.Builder builder = t.request();
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart().field("pathAndConfig", pathAndConfig, MediaType.APPLICATION_XML_TYPE);
        formDataMultiPart = formDataMultiPart.field("inputStream", inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        System.out.println("Plain Text=>> " + builder.post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA_TYPE), String.class));
    }

    @Override
    public void appendFile(PathAndConfig pathAndConfig, InputStream inputStream) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/appendFile");
        Invocation.Builder builder = t.request();
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart().field("pathAndConfig", pathAndConfig, MediaType.APPLICATION_XML_TYPE);
        formDataMultiPart = formDataMultiPart.field("inputStream", inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        System.out.println("Plain Text=>> " + builder.post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA_TYPE), String.class));
    }

    @Override
    public InputStream readFile(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/readFile");
        Invocation.Builder builder = t.request();
        return builder.post(Entity.entity(pathAndConfig, MediaType.APPLICATION_XML_TYPE), InputStream.class);
    }

    @Override
    public void delete(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/delete");
        Invocation.Builder builder = t.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        System.out.println("Plain Text=>> " + builder.post(Entity.entity(pathAndConfig, MediaType.APPLICATION_XML_TYPE), String.class));
    }

    @Override
    public String getFileType(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/getFileType");
        Invocation.Builder builder = t.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        return builder.post(Entity.entity(pathAndConfig, MediaType.APPLICATION_XML_TYPE), String.class);
    }

    @Override
    public void createFolder(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/createFolder");
        Invocation.Builder builder = t.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        System.out.println("Plain Text=>> " + builder.post(Entity.entity(pathAndConfig, MediaType.APPLICATION_XML_TYPE), String.class));
    }

    @Override
    public void rename(RenameParameters renameParameters) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/rename");
        Invocation.Builder builder = t.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        System.out.println("Plain Text=>> " + builder.post(Entity.entity(renameParameters, MediaType.APPLICATION_XML_TYPE), String.class));
    }

    @Override
    public long getLastModifiedTime(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/getLastModifiedTime");
        Invocation.Builder builder = t.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        return Long.valueOf(builder.post(Entity.entity(pathAndConfig, MediaType.APPLICATION_XML_TYPE), String.class));
    }

    @Override
    public void setLastModifiedTime(SetModifiedTimeParameters setModifiedTimeParameters) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/setLastModifiedTime");
        Invocation.Builder builder = t.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        System.out.println("Plain Text=>> " + builder.post(Entity.entity(setModifiedTimeParameters, MediaType.APPLICATION_XML_TYPE), String.class));
    }

    @Override
    public List<String> listChildren(PathAndConfig pathAndConfig) throws HadoopGatewayException {
        WebTarget t = client.target("http://localhost:8080/jaxrs/services/hdfs/children");
        Invocation.Builder builder = t.request();
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        String strResult = builder.post(Entity.entity(pathAndConfig, MediaType.APPLICATION_XML_TYPE), String.class);
        Object objResult = JSON.parse(strResult);
        if (objResult != null) {
            if (objResult.getClass().isArray()) {
                int length = Array.getLength(objResult);
                List<String> result = new ArrayList<String>(length);
                for (int i = 0; i < length; i++) {
                    result.add(String.valueOf(Array.get(objResult, i)));
                }
                return result;
            } else {
                throw new HadoopGatewayException("Result was not array");
            }
        } else {
            throw new HadoopGatewayException("Null list returned");
        }
    }
}
