
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
public class Server {
    public ArrayList<ServerConnection> connection = new ArrayList<ServerConnection>();
    
    ServerSocket ss;
    public ServerSocket fileserver;
    public static void main(String[] args) {
        new Server();
       
    }
    public Server(){
        try {
            ss = new ServerSocket(5555);
            System.out.println("Server started...");
            while(true){
            Socket s = ss.accept();
            InetAddress sa = s.getLocalAddress();
            String clientip = sa.getHostAddress();
            System.out.println("Client got Connected"+clientip);
            ServerConnection sc = new ServerConnection(s,this,clientip);
            sc.start();
            connection.add(sc);
            
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
 