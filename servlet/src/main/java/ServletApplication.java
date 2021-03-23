public class ServletApplication {

  public static void main(String[] args) throws Exception {
    // run, Jetty, run!
    CounterServer server = new CounterServer();
    server.start();
    server.join();
  }
}
