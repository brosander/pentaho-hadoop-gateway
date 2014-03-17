package org.pentaho.gateway.adapters;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by bryan on 3/16/14.
 */
public class MapEntry {
    @XmlElement
    public String key;
    @XmlElement
    public String value;

    public MapEntry() {

    }

    public MapEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
