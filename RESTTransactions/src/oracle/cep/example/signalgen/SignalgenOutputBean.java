/* (c) 2009 Oracle Corporation.  All rights reserved. */
package oracle.cep.example.signalgen;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.bea.wlevs.ede.api.EventRejectedException;
import com.bea.wlevs.ede.api.StreamSink;
import com.bea.wlevs.ede.api.SuspendableBean;
import com.bea.wlevs.ede.api.ResumableBean;
import com.bea.wlevs.ede.api.EventTypeRepository;
import com.bea.wlevs.ede.api.EventType;
import com.bea.wlevs.util.Service;

public class SignalgenOutputBean
    extends HttpServlet
    implements StreamSink,
               SuspendableBean,
               ResumableBean
{
    private static final long serialVersionUID = 3574891093509082217L;
    private static int MAX_SIZE = 10;
    private SimpleDateFormat m_dateFormatter = new SimpleDateFormat("HH:mm:ss");
    private DecimalFormat m_decimalFormatter = new DecimalFormat("0.00");

    private ArrayBlockingQueue<String> m_queuePercentage = new ArrayBlockingQueue<String>(MAX_SIZE);
    private ArrayBlockingQueue<String> m_queueTrend = new ArrayBlockingQueue<String>(MAX_SIZE);
    private boolean m_queuePercentageFull = false;
    private boolean m_queueTrendFull = false;

    private long[] m_histogram = new long[10];
    private long m_eventCounter = 0;
    private StatsCollector m_statsCollector;
    private Timer m_timer;
    private EventTypeRepository m_etr;
    private String m_timestampProp;

    private void initializeStats() {
        for (int i = 0; i < m_histogram.length; i++) {
            m_histogram[i] = 0;
        }

        m_statsCollector = new StatsCollector();
        m_timer = new Timer();
        m_timer.schedule(m_statsCollector, 0, 1000);
    }

    @Service
    public void setEventTypeRepository(EventTypeRepository etr) {
        m_etr = etr;
    }

    public void setTimestampProperty(String timestampProp) {
        m_timestampProp = timestampProp;
    }

    public void suspend() {
        if (m_timer != null) {
            m_timer.cancel();
        }
    }

    public void onInsertEvent(Object event) throws EventRejectedException {
        m_eventCounter++;
        long currentLatency = (System.nanoTime()-getTimestamp(event))/1000;
        String typeName = m_etr.getEventType(event).getTypeName();
        if (typeName.equals("PercentTick")) {
            addEvent(m_queuePercentage, m_queuePercentageFull, event);
        } else {
            addEvent(m_queueTrend, m_queueTrendFull, event);
        }

        addLatency(currentLatency);
    }

    private long getTimestamp(Object event) {
        return (Long) m_etr.getEventType(event).getPropertyValue(event, m_timestampProp);
    }

    private void addLatency(long latency) {
        int lat = (int) (latency / 100);
        if (lat < 10) {
            m_histogram[lat]++;
        } else {
            m_histogram[9]++;
        }
        m_statsCollector.addLatency(latency);
    }

    private void addEvent(ArrayBlockingQueue<String> queue, boolean queueFull, Object event) {
        try {
            String msg = toJSON(event);
            if (queueFull) {
                queue.take();
                queue.put(msg);
            } else {
                while (true) {
                    if (queue.offer(msg)) {
                        break;
                    } else {
                        queueFull = true;
                        queue.take();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toJSON(Object event) {
        EventType eventType = m_etr.getEventType(event);
        StringBuffer result = new StringBuffer();
        try {
            if ("PercentTick".equals(eventType.getTypeName())) {
                result.append("{");
                result.append("Time: '" + m_dateFormatter.format(new Date(
                        (Long) eventType.getPropertyValue(event, m_timestampProp))) + "',");
                result.append("Symbol: '" + eventType.getPropertyValue(event, "symbol") + "',");
                result.append("Price: '$" + m_decimalFormatter.format(eventType.getPropertyValue(event, "lastPrice")) + "',");
                result.append("Change: '" + m_decimalFormatter.format((Double) eventType.getPropertyValue(event, "percentLastPrice")) + "'");
                result.append("}");
            } else if ("TrendTick".equals(eventType.getTypeName())) {
                result.append("{");
                result.append("Time: '" + m_dateFormatter.format(new Date(
                        (Long) eventType.getPropertyValue(event, m_timestampProp))) + "',");
                result.append("Symbol: '" + eventType.getPropertyValue(event, "symbol") + "',");
                result.append("Price: '$" + m_decimalFormatter.format(eventType.getPropertyValue(event, "lastPrice")) + "',");
                result.append("Trend: '" + eventType.getPropertyValue(event, "trendLastPrice") + "'");
                result.append("}");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Service(serviceType = HttpService.class)
    public void setHttpService(HttpService httpService) {
        try {
            httpService.registerServlet("/signalgeneration", this, null, null);
        } catch (ServletException e) {
            System.out.println("ERROR: Could not register servlet: "
                    + e.getMessage());
            e.printStackTrace();
        } catch (NamespaceException e) {
            System.out.println("ERROR: Could not register servlet: "
                    + e.getMessage());
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        try
        {
            doMainPage(request, response);
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        try
        {
            doMainPage(request, response);
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
    }

    private void doMainPage(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);

        PrintWriter out = response.getWriter();
        StringBuffer buffer = new StringBuffer("[");

        if (request.getParameterMap().size() == 0) {
            String path = request.getPathInfo();
            InputStream stream = null;
            try {
                stream = getClass().getClassLoader().getResourceAsStream(path);
                if (stream != null) {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = stream.read(buf)) > 0) {
                        String str = new String(buf, 0, len);
                        out.write(str);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,
                            "Page not found.");
                }
            } finally {
                if (stream != null)
                    stream.close();
            }
        } else if (request.getParameter("histogram") != null) {
            if (generateHistogram(buffer) == false) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } else if (request.getParameter("pct") != null) {
            generateDashboardTableEntry(buffer, m_queuePercentage.iterator());
        } else if (request.getParameter("trend") != null) {
            generateDashboardTableEntry(buffer, m_queueTrend.iterator());
        } else if (request.getParameter("lat") != null) {
            generateLatencyInfo(buffer);
        } else {

        }

        buffer.append("];");

        if (buffer.length() > 3) {
            out.println(buffer.toString());
        }
    }

    private void generateLatencyInfo(StringBuffer buffer) {
        generateDashboardTableEntry(buffer, m_statsCollector.getLatencies());
    }

    @SuppressWarnings({ "unchecked" })
    private void generateDashboardTableEntry(StringBuffer buffer, Iterator i) {
        while (i.hasNext()) {
            String element = (String) i.next();
            buffer.append(element);
            if (i.hasNext())
                buffer.append(",");
        }
    }

    private boolean generateHistogram(StringBuffer buffer) {
        buffer.append("{label: \"0-100\", value: " + m_histogram[0]
                + "},{label: \"100-200\", value: " + m_histogram[1]
                + "},{label: \"200-300\", value: " + m_histogram[2]
                + "},{label: \"300-400\", value: " + m_histogram[3]
                + "},{label: \"400-500\", value: " + m_histogram[4]
                + "},{label: \"500-600\", value: " + m_histogram[5]
                + "},{label: \"600-700\", value: " + m_histogram[6]
                + "},{label: \"700-800\", value: " + m_histogram[7]
                + "},{label: \"800-900\", value: " + m_histogram[8]
                + "},{label: \"900+\", value: " + m_histogram[9] + "}");
        return true;
    }

    public void beforeResume() throws Exception {
        initializeStats();
    }

    // Maintains statistics for every TimerTask interval
    private class StatsCollector extends TimerTask {

        private int MAX_LATENCIES = 20;

        private long counter = 0;
        private long totalLatency = 0;
        private long lastTimestamp;
        private long lastThroughput;
        private long lastAverageLatency;
        boolean queueFull = false;

        private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(MAX_LATENCIES);

        public StatsCollector() {
            lastTimestamp = System.currentTimeMillis();
        }

        public void addLatency(long latency) {
            totalLatency += latency;
            counter++;
        }

        public Iterator<String> getLatencies() {
            return queue.iterator();
        }

        public void run() {
            long now = System.currentTimeMillis();
            lastThroughput = (long) ((double) counter / ((double) (now - lastTimestamp) / 1000.0));
            lastAverageLatency = (long) (counter > 0 ? totalLatency / counter
                    : 0);
            addAverageLatency(toJSON(now, lastAverageLatency));
            System.out.println("Throughput (msg per second): " + lastThroughput
                            + ". Average latency (microseconds): " + lastAverageLatency);
            lastTimestamp = now;
            totalLatency = 0;
            counter = 0;
        }

        private String toJSON(long timestamp, long latency) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("{");
            buffer.append("Timestamp: " + timestamp + ", ");
            buffer.append("ServerLatency: " + latency);
            buffer.append("}");
            return buffer.toString();
        }

        public void addAverageLatency(String msg) {
            try {
                if (queueFull) {
                    queue.take();
                    queue.put(msg);
                } else {
                    while (true) {
                        if (queue.offer(msg)) {
                            break;
                        } else {
                            queueFull = true;
                            queue.take();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
