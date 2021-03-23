import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class CounterServer extends Server {

    private static final int DEFAULT_PORT = 8081;

    private Counter counter;

    public CounterServer() {
        super(DEFAULT_PORT);
        counter = new Counter();
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/");
        handler.addServlet(CounterServlet.class, "/counter");
        handler.addServlet(CounterClearServlet.class, "/counter/clear");
        handler.setAttribute("counter", counter);
        setHandler(handler);
    }
}
