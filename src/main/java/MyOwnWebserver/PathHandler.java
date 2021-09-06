package MyOwnWebserver;

import com.sun.net.httpserver.HttpHandler;

public class PathHandler {
    public String path;
    public HttpHandler handler;

    public PathHandler(String path, HttpHandler handler) {
        this.path = path;
        this.handler = handler;
    }
}
