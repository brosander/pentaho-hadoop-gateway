package org.pentaho.gateway.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bryan on 3/16/14.
 */
public class ListOfStringsAdapter extends XmlAdapter<String[], List<String>> {
    @Override
    public List<String> unmarshal(String[] v) throws Exception {
        return Arrays.asList(v);
    }

    @Override
    public String[] marshal(List<String> v) throws Exception {
        return v.toArray(new String[v.size()]);
    }
}
