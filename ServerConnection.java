
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServerConnection extends Thread{
    Server server;
    Socket socket;
    DataOutputStream output;
    DataInputStream input;
    String thisclientip;
    public String thisclientname;
    public ServerConnection(Socket socket,Server server,String id){
       this.server = server;
       this.socket = socket;
       this.thisclientip = id;
       
    }
    
    public void sendStringToClient(String msg,int index,ServerConnection cl){
        try{
            output.writeUTF(msg);
            output.flush();
        }catch(Exception e){System.out.println("Error while sending to a Client");}
    }
    
    public void sendStringToAllClients(String msg){
        try{
            for(int index=0;index < server.connection.size();index++){
                ServerConnection sc = server.connection.get(index);
                
                    sc.sendStringToClient(msg,index,sc);
                
            }
        }catch(Exception e){System.out.println("Error while BroadCasting");}
    }
    
    @Override
    public void run(){
        try {
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            try{
           thisclientname = input.readUTF().toLowerCase();
       }catch(Exception e){System.out.println("error reciving client name");}
            while(true){
                
                while(input.available() == 0){
                    try{
                        Thread.sleep(1);
                    }catch(Exception e){System.out.println("error while trying to sleep the thread");}
                }
                String str = input.readUTF();
                sendStringToAllClients(str);
            }
            
        } catch (Exception ex) {System.out.println("error in the run method,probably input");}
    }
    
}
