import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class CounterClearServlet extends HttpServlet {
    private Counter counter;

    private static String AUTH_COOKIE = "hh-auth";

    @Override
    public void init() throws ServletException {
        counter = (Counter) getServletContext().getAttribute("counter");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isAuthorized(req.getCookies())){
            counter.reset();
            resp.setStatus(HttpServletResponse.SC_OK);
        }else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    protected boolean isAuthorized(Cookie[] cookies){
        if (cookies == null){
            return false;
        }
        Optional<Cookie> auth = Arrays.stream(cookies)
                .filter(this::cookieIsValid)
                .findAny();
        return auth.isPresent();
    }

    protected boolean cookieIsValid(Cookie cookie){
        return cookie.getName().equals(AUTH_COOKIE) && cookie.getValue().length() > 10;
    }
}
