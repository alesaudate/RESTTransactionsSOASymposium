/* (c) 2009 Oracle Corporation.  All rights reserved. */
package oracle.cep.example.signalgen;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;

import com.bea.cache.jcache.CacheLoader;
import com.bea.wlevs.ede.api.EventTypeRepository;
import com.bea.wlevs.ede.api.EventType;
import com.bea.wlevs.ede.api.EventProperty;
import com.bea.wlevs.util.Service;

/**
 * Cache loader class to load all the Symbols into the cache.
 * The symbols are loaded from a file defined by symbolsFile property.
 */
public class SymbolsCacheLoader
    implements CacheLoader<String, Object>,
               InitializingBean
{
    private String m_fileName;
    private Map<String, Object> m_symbolsMap = new HashMap<String, Object>();
    private EventTypeRepository m_etr;
    private String m_eventTypeName;

    public void setEventTypeName(String eventTypeName)
    {
        m_eventTypeName = eventTypeName;
    }

    public void setSymbolsFileName(String fileName)
    {
        m_fileName = fileName;
    }

    @Service
    public void setEventTypeRepository(EventTypeRepository etr)
    {
        m_etr = etr;
    }

    public void afterPropertiesSet() throws IOException {
        // create events and load them into memory
        EventType eventType = getEventType();
        EventProperty symbolProperty = eventType.getProperty("symbol");
        // FileReader will check if the file exists and if it is valid
        BufferedReader reader = new BufferedReader(new FileReader(m_fileName));
        try
        {
            String symbol = null;
            while ((symbol=reader.readLine())!=null)
            {
                Object event = eventType.createEvent();
                symbolProperty.setValue(event, symbol);
                m_symbolsMap.put(symbol, event);
            }
        }
        finally
        {
            reader.close();
        }
    }

    private EventType getEventType() {
        EventType eventType = m_etr.getEventType(m_eventTypeName);
        if (eventType==null)
            throw new IllegalArgumentException("Could not find event type [" + m_eventTypeName + "]. Please specify a valid event type name for symbols");
        return eventType;
    }

    // interface method impl
    public Object load(String key) {
        return m_symbolsMap.get(key);
    }

    // interface method impl
    public Map<String, Object> loadAll(Collection<String> keys) {
        Map<String, Object> retVal = new HashMap<String, Object>();
        for (String akey : keys) {
            Object value = m_symbolsMap.get(akey);
            if (value!=null)
                retVal.put(akey, value);
        }

        return retVal;
    }

    // interface method impl
    public Map<String, Object> loadAll() {
        return new HashMap<String, Object>(m_symbolsMap);
    }
}
