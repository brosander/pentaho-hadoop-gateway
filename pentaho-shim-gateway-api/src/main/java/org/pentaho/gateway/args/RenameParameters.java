package org.pentaho.gateway.args;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by bryan on 3/16/14.
 */
@XmlRootElement
public class RenameParameters {
    @XmlElement
    private PathAndConfig pathAndConfig;
    @XmlElement
    private String newPath;

    public PathAndConfig getPathAndConfig() {
        return pathAndConfig;
    }

    public void setPathAndConfig(PathAndConfig pathAndConfig) {
        this.pathAndConfig = pathAndConfig;
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }
}
