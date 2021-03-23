import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CounterServlet extends HttpServlet {

    private static String SUBSTRACTION_HEADER_NAME = "Subtraction-Value";

    private Counter counter;

    @Override
    public void init() throws ServletException {
        counter = (Counter) getServletContext().getAttribute("counter");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println(counter.get());
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        counter.increment();
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            counter.decrementByValue(Integer.parseInt(req.getHeader(SUBSTRACTION_HEADER_NAME)));
            resp.setStatus(HttpServletResponse.SC_OK);
        }catch (NumberFormatException ex){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
