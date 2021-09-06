import MyOwnWebserver.MyServer;
import MyOwnWebserver.WebRoute;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
        MyServer.start(Application.class, 8080);

    }

    @WebRoute("/hello")
    public String returnHelloWorld(){
        return "hello world";
    }

    @WebRoute("/")
    public String returnTest(){
        return "tfgfgfbt";
    }
}
