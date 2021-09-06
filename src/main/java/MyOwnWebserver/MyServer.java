package MyOwnWebserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyServer {


    public static void start(Class applicationClass, int port) throws IOException, InstantiationException, IllegalAccessException {
        Object o = applicationClass.newInstance();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        for (PathHandler handler :
                getHandlers(applicationClass)) {
            server.createContext(handler.path, handler.handler);
        }
        server.setExecutor(null);
        server.start();
        }


    private static List<PathHandler> getHandlers(Class applicationClass) throws InstantiationException, IllegalAccessException {
        Object o = applicationClass.newInstance();
        return Arrays.stream(applicationClass.getDeclaredMethods())
                .filter(i -> i.isAnnotationPresent(WebRoute.class)).map(i -> new PathHandler(i.getAnnotation(WebRoute.class).value(), new HttpHandler() {
                    @Override
                    public void handle(HttpExchange t) throws IOException {
                        String response = null;
                        try {
                            response = (String) i.invoke(o);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                }))
                .collect(Collectors.toList());
    }
}
