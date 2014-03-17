package org.pentaho.gateway.args;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;

/**
 * Created by bryan on 3/16/14.
 */
@XmlRootElement
public class WriteFileParameters {
    @XmlElement
    private PathAndConfig pathAndConfig;
    @XmlElement
    private InputStream inputStream;

    public PathAndConfig getPathAndConfig() {
        return pathAndConfig;
    }

    public void setPathAndConfig(PathAndConfig pathAndConfig) {
        this.pathAndConfig = pathAndConfig;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
