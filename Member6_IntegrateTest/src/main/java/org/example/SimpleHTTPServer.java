package org.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.InetSocketAddress;

public class SimpleHTTPServer {
    public static void main(String[] args) throws IOException {
        // Create HTTP server, listening on port 9090
        HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        
        // Set up file handler
        server.createContext("/", new FileHandler());
        
        // Start server
        server.start();
        System.out.println("Server running at http://localhost:9090");
        System.out.println("Press Ctrl+C to stop the server");
    }
    
    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Get request path
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            // Build file path
            Path filePath = Paths.get("src/main/webapp" + path);
            
            try {
                // Read file content
                byte[] response = Files.readAllBytes(filePath);
                
                // Set response headers
                exchange.getResponseHeaders().set("Content-Type", getContentType(filePath));
                exchange.sendResponseHeaders(200, response.length);
                
                // Send response
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            } catch (IOException e) {
                // Handle file not found
                String errorResponse = "404 Not Found";
                exchange.sendResponseHeaders(404, errorResponse.length());
                OutputStream os = exchange.getResponseBody();
                os.write(errorResponse.getBytes());
                os.close();
            }
        }
        
        private String getContentType(Path filePath) {
            String fileName = filePath.getFileName().toString();
            if (fileName.endsWith(".html")) {
                return "text/html";
            } else if (fileName.endsWith(".css")) {
                return "text/css";
            } else if (fileName.endsWith(".js")) {
                return "application/javascript";
            } else if (fileName.endsWith(".png")) {
                return "image/png";
            } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                return "image/jpeg";
            } else {
                return "application/octet-stream";
            }
        }
    }
}