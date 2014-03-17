package org.pentaho.gateway.args;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by bryan on 3/16/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SetModifiedTimeParameters {
    @XmlElement
    private PathAndConfig pathAndConfig;
    @XmlElement
    private long modtime;

    public SetModifiedTimeParameters() {

    }

    public SetModifiedTimeParameters(PathAndConfig pathAndConfig, long modtime) {
        this.pathAndConfig = pathAndConfig;
        this.modtime = modtime;
    }

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
