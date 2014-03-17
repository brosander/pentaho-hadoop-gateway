package org.pentaho.gateway.ret;

import org.pentaho.gateway.adapters.ListOfStringsAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan on 3/16/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StringListWrapper {
    @XmlElement
    private List<String> strings = new ArrayList<String>();

    public StringListWrapper() {

    }

    @Override
    public String toString() {
        return "StringListWrapper{" +
                "strings=" + strings +
                '}';
    }

    public StringListWrapper(List<String> list) {
        this.strings = list;
    }

    @XmlJavaTypeAdapter(ListOfStringsAdapter.class)
    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}
