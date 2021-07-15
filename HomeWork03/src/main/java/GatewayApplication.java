import netty.NettyHttpServer;

import java.util.Arrays;

public class GatewayApplication {
    public static void main(String[] args) {

        String proxyPort = System.getProperty("proxyPort", "8888");

        // 这是多个后端url走随机路由的例子
        String proxyServers = System.getProperty("proxyServers", "http://localhost:8801,http://localhost:8802");
        int port = Integer.parseInt(proxyPort);
        // System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        NettyHttpServer server = new NettyHttpServer(port, Arrays.asList(proxyServers.split(",")));
        // System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:" + port + " for server:" + server.toString());
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
