package org.pentaho.gateway;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by bryan on 3/15/14.
 */
public class Application extends ResourceConfig {
    public Application() {
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
        packages(true, "com.aver.restful", "org.pentaho.gateway");
    }
}
