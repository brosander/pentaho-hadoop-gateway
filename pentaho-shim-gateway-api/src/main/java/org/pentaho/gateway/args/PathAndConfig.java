package org.pentaho.gateway.args;

import org.pentaho.gateway.adapters.MapAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bryan on 3/16/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PathAndConfig {
    private Map<String, String> map = new HashMap<String, String>();
    @XmlElement
    private String path;

    public PathAndConfig() {

    }

    public PathAndConfig(String path, HashMap<String,String> map) {
        this.path = path;
        this.map = map;
    }

    @XmlJavaTypeAdapter(MapAdapter.class)
    public Map<String, String> getConfig() {
        return Collections.unmodifiableMap(map);
    }

    public void setMap(Map<String, String> map) {
        this.map = new HashMap<String, String>(map);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "PathAndConfig{" +
                "map=" + map +
                ", path='" + path + '\'' +
                '}';
    }
}
