import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.mortbay.util.ajax.JSON;
import org.pentaho.gateway.adapters.ListOfStringsAdapter;
import org.pentaho.gateway.adapters.MapAdapter;
import org.pentaho.gateway.args.PathAndConfig;
import org.pentaho.gateway.args.WriteFileParameters;
import org.pentaho.gateway.ret.StringListWrapper;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestTest {
    /*public static void main(String[] args) {
        Client c = ClientBuilder.newClient();

        // plain text
        WebTarget t = c.target("http://localhost:8080/jaxrs/api/timeoftheday/asplaintext/mathew");
        Invocation.Builder builder = t.request();
        System.out.println("Plain Text=>> " + builder.get(String.class));

        // json
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().nonPreemptive().build();
        c.register(feature);
        t = c.target("http://localhost:8080/jaxrs/api/timeoftheday/asjson/mathew");
        builder = t.request().property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "johndoe").property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "password");
        System.out.println("JSON=>> " + builder.get(String.class));

        // xml
        t = c.target("http://localhost:8080/jaxrs/api/timeoftheday/asxml/mathew");
        builder = t.request().property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "admin").property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "password");
        System.out.println("XML=>> " + builder.get(String.class));
    }*/

    public static void main(String[] args) throws IOException {
        Client c = ClientBuilder.newClient();
        c.register(MapAdapter.class);
        c.register(MultiPartFeature.class);
        c.register(ListOfStringsAdapter.class);
        c.register(PathAndConfig.class);
        // plain text
        WebTarget t = c.target("http://localhost:8080/jaxrs/services/hdfs/createFile");
        Invocation.Builder builder = t.request();
        PathAndConfig pathAndConfig = new PathAndConfig();
        pathAndConfig.setPath("blah");
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart().field("pathAndConfig", pathAndConfig, MediaType.APPLICATION_XML_TYPE);
        formDataMultiPart = formDataMultiPart.field("inputStream", new FileInputStream("/home/bryan/.bashrc"), MediaType.APPLICATION_OCTET_STREAM_TYPE);
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
        System.out.println("Plain Text=>> " + builder.post(Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA_TYPE), String.class));
        t = c.target("http://localhost:8080/jaxrs/services/hdfs/readFile");
        builder = t.request();
        InputStream is = builder.post(Entity.entity(pathAndConfig, MediaType.APPLICATION_XML_TYPE), InputStream.class);
        System.out.println(IOUtils.toString(is));
        t = c.target("http://localhost:8080/jaxrs/services/hdfs/children");
        builder = t.request();
        pathAndConfig = new PathAndConfig();
        pathAndConfig.setPath("blah");
        Map<String, String> config = new HashMap<String, String>();
        config.put("test", "123");
        config.put("me", "hungry");
        pathAndConfig.setMap(config);
        builder.accept(MediaType.APPLICATION_JSON_TYPE);
//        form.param("configurationProperties", new PathAndConfig());
        System.out.println("Plain Text=>> " + builder.post(Entity.entity(pathAndConfig, MediaType.APPLICATION_XML_TYPE), String.class));
    }
}