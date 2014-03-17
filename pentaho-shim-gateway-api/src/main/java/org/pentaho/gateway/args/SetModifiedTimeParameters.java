package org.pentaho.gateway.args;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by bryan on 3/16/14.
 */
@XmlRootElement
public class SetModifiedTimeParameters {
    @XmlElement
    private PathAndConfig pathAndConfig;
    @XmlElement
    private long modtime;

    public PathAndConfig getPathAndConfig() {
        return pathAndConfig;
    }

    public void setPathAndConfig(PathAndConfig pathAndConfig) {
        this.pathAndConfig = pathAndConfig;
    }

    public long getModtime() {
        return modtime;
    }

    public void setModtime(long modtime) {
        this.modtime = modtime;
    }
}
