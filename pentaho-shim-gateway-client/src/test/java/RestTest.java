import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.mortbay.util.ajax.JSON;
import org.pentaho.gateway.adapters.ListOfStringsAdapter;
import org.pentaho.gateway.adapters.MapAdapter;
import org.pentaho.gateway.api.HadoopGatewayException;
import org.pentaho.gateway.api.HdfsService;
import org.pentaho.gateway.args.PathAndConfig;
import org.pentaho.gateway.args.SetModifiedTimeParameters;
import org.pentaho.gateway.args.WriteFileParameters;
import org.pentaho.gateway.client.ShimGatewayClient;
import org.pentaho.gateway.client.impl.ShimGatewayClientImpl;
import org.pentaho.gateway.ret.StringListWrapper;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestTest {

    public static void main(String[] args) throws IOException, HadoopGatewayException {
        ShimGatewayClient shimGatewayClient = new ShimGatewayClientImpl();
        HdfsService service = shimGatewayClient.getHdfsService();
        service.delete(new PathAndConfig("blah2", new HashMap<String, String>()));
        service.createFile(new PathAndConfig("blah2", new HashMap<String, String>()), new FileInputStream("/home/bryan/.bashrc"));
        System.out.println(IOUtils.toString(service.readFile(new PathAndConfig("blah2", new HashMap<String, String>()))));
        System.out.println(service.listChildren(new PathAndConfig("/home/bryan/github/pentaho-hadoop-gateway/", new HashMap<String, String>())));
        //service.setLastModifiedTime(new SetModifiedTimeParameters(new PathAndConfig("blah2", new HashMap<String, String>()), System.currentTimeMillis()));
        System.out.print(service.getLastModifiedTime(new PathAndConfig("blah2", new HashMap<String, String>())));
    }
}