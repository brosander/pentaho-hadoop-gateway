package org.pentaho.gateway.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bryan on 3/16/14.
 */
public class MapAdapter extends XmlAdapter<MapEntry[], Map<String, String>> {
    @Override
    public Map<String, String> unmarshal(MapEntry[] v) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        for (MapEntry entry : v) {
            map.put(entry.key, entry.value);
        }
        return map;
    }

    @Override
    public MapEntry[] marshal(Map<String, String> v) throws Exception {
        List<MapEntry> entries = new ArrayList<MapEntry>(v.size());
        for (Map.Entry<String, String> entry : v.entrySet()) {
            entries.add(new MapEntry(entry.getKey(), entry.getValue()));
        }
        return entries.toArray(new MapEntry[entries.size()]);
    }
}
