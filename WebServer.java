import com.sun.net.httpserver.*;
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class WebServer {
    static final String page = """
    <!DOCTYPE html>
    <html>
    <head>
        <link rel="stylesheet" href="/stylesheet.css" />
    </head>
    <body>
    <div id="cool-container">
        <h1 onclick="fetch(new Request('button pressed'))">cool soup B)</h1>
    </div>
    <img src="https://gamepedia.cursecdn.com/minecraft_gamepedia/f/f3/Steve.png?version=ab23f658e943d030b6d0772e3c353565" />
    </body>
    </html>
    """,
    css = """
    h1 {
        text-align: center;
        color: pink;
        transition: 1s;
    }
    h1:hover {
        color: blue;
    }
    @keyframes jump {
        from {
            padding-bottom: 0;
        }
        to {
            padding-bottom: 2%;
        }
    }
    img {
        position:fixed;
        right:5%;
        bottom:0;
        height:10%;
    }
    img:hover {
        animation-iteration-count: infinite;
        animation-timing-function: ease-out;
        animation-direction: alternate;
        animation-duration: 0.25s;
        animation-name: jump;
    }
    """;

    public static void main(String... args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8053);
        HttpServer server = HttpServerProvider.provider().createHttpServer(address, 0);

        HttpHandler handler = exchange -> {
            System.out.println(exchange.getRequestURI());

            exchange.sendResponseHeaders(200, 0);
            Headers headers = exchange.getResponseHeaders();

            boolean isCssRequest = exchange.getRequestURI().getPath().endsWith("stylesheet.css");

            String contentType = isCssRequest ? "text/css" : "text/html";

            headers.put("Content-Type", Arrays.asList(contentType, "charset=utf-8"));

            OutputStream bodyStream = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(bodyStream);

            if(isCssRequest)
                writer.write(css);
            else
                writer.write(page);

            writer.close();
        };
        server.createContext("/", handler);

        server.start();
    }
}
