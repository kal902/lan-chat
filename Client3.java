/** open source simple lan messanger 
 * uses javafx ,if using jdk 11+ you may have to
 * install the javafx package independently.
 * but for lower jdk versions javafx is bundled with the default java
 * package.
 * NOTE: the server code must be excuted\running inorder
 * for this program to work properly
 *  - messages are broadcasted for all connected users,tweek
 *    the server code if you need private chating.
 * thanks..
 * by kaleab .. kaleab902@gmail.com
 **/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Client3 extends Application{
    TextArea mainta;
    TextArea clientsta;
    TextField msgtf,serveriptf,tf3;
    Label chatstatuslbl,connstatuslbl,iplbl,errorlbl;
    Button sendbtn,refreshbtn,connbtn,disconnbtn;
    public DataOutputStream output;
    public DataInputStream input;
    public Socket socket;
    public Boolean stayconn = true;
    public Background background;
    public static void main(String []args){
        Application.launch(args);
    }
    @Override
    public void start(Stage stage){
        
        mainta = new TextArea();
        mainta.setMinSize(264, 389);
        mainta.setMaxSize(265, 390);
        mainta.setEditable(false);
        
        msgtf = new TextField();
        msgtf.setMaxWidth(220);
        msgtf.setMinWidth(220);
        msgtf.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
            String msg = msgtf.getText();
            send(msg);
            Platform.runLater(()->{
                   msgtf.setText("");
            });
            }
        });
        
        sendbtn = new Button("Send");
        sendbtn.setDisable(true);
        sendbtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                String msg = msgtf.getText();
                send(msg);
            }
        });
        
        errorlbl = new Label(" ");
        
        HBox hbox1 = new HBox();
        hbox1.setSpacing(2);
        hbox1.getChildren().addAll(msgtf,sendbtn);
        
        VBox vboxmain1 = new VBox();
        vboxmain1.setSpacing(3);
        vboxmain1.getChildren().addAll(errorlbl,mainta,hbox1);
        
        connstatuslbl = new Label("Offline | your ip:-");
        iplbl = new Label(" ");
        
        HBox hbox2 = new HBox();
        hbox2.setSpacing(3);
        hbox2.getChildren().addAll(connstatuslbl,iplbl);
        
        serveriptf = new TextField();
        serveriptf.setMaxWidth(100);
        serveriptf.setMinWidth(99);
        serveriptf.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Platform.runLater(()->{
                    connbtn.setDisable(false);
                });
            }
        });
        connbtn = new Button("Connect");
        connbtn.setDisable(true);
        connbtn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               new Thread(new Connection()).start();
           }
        });
        
        disconnbtn = new Button("disconnect");
        disconnbtn.setDisable(true);
        disconnbtn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               stayconn = false;
               try {
                   socket.close();
                   input.close();
                   output.close();
                   Platform.runLater(()->{
                       connstatuslbl.setText("offline");
                   });
               } catch (IOException ex) {
                   Platform.runLater(()->{
                       errorlbl.setText("error disconnecting");
                   });
               }
           }
        });
        
        HBox hbox3 = new HBox();
        hbox3.setSpacing(3);
        hbox3.getChildren().addAll(connbtn,disconnbtn);
        
        clientsta = new TextArea();
        clientsta.setEditable(false);
        clientsta.setMaxSize(175,170);
        clientsta.setMinSize(174,169);
        
        tf3 = new TextField();
        tf3.setMaxWidth(80);
        tf3.setMinWidth(79);
        
        chatstatuslbl = new Label("private(Default)");
        
        refreshbtn = new Button("reload");
        refreshbtn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
           
           }
        });
        
        Button startPchatbtn = new Button("send private");
        startPchatbtn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
           
           }
        });
        
        HBox hbox4 = new HBox();
        hbox4.setSpacing(10);
        hbox4.getChildren().addAll(tf3,startPchatbtn,refreshbtn);
        
        VBox vboxmain2 = new VBox();
        vboxmain2.setSpacing(15);
        vboxmain2.getChildren().addAll(hbox2,serveriptf,hbox3,clientsta,hbox4);
        
        try {
            FileInputStream input = new FileInputStream("background.jpg");
            Image image = new Image(input);
            BackgroundImage backgroundimage;
            backgroundimage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,
                                                        BackgroundRepeat.NO_REPEAT,
                                                        BackgroundPosition.CENTER,
                                                        BackgroundSize.DEFAULT);
            background = new Background(backgroundimage);
        } catch (Exception ex) {
           System.out.println("error fetching background image for the frame");
        }
        HBox t1 = new HBox();
        t1.getChildren().addAll(vboxmain1);
        t1.setSpacing(10);
        t1.setBackground(background);
        t1.setStyle("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" + 
                      "-fx-border-insets: 5;" +
                      "-fx-border-radius: 5;" +
                      "-fx-border-color: black;");
        
        Tab tab1 = new Tab("chat",t1);
        Tab tab2 = new Tab("connection",vboxmain2);
        TabPane tabpane = new TabPane();
        tabpane.getTabs().add(tab1);
        tabpane.getTabs().add(tab2);
        HBox root = new HBox(tabpane);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setTitle("LanChat(client)");
        stage.show();
        
        
    }
    
    class Connection implements Runnable{
        
        public Connection(){
            try {
                socket = new Socket("127.0.0.1",5555);
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(serveriptf.getText());
                output.flush();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        connstatuslbl.setText("online");
                        disconnbtn.setDisable(false);
                        sendbtn.setDisable(false);
                    }
                });
                //instantiates DataInputStream object from 
                input = new DataInputStream(socket.getInputStream());
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    errorlbl.setText("error while connecting!\ncheck your servers ip\nor mabe server is offline.");
                });
            }
        }
        //overridden method loops Listening for incoming messages 
        @Override
        public void run(){
            while(stayconn){
                String inputmsg;
                try {
                    inputmsg = input.readUTF();
                    Platform.runLater(() ->{
                    	// appends received message to the main TextArea
                        mainta.appendText(inputmsg+"\n");
                    });
                } catch (IOException ex) {
                    Platform.runLater(()->{
                        errorlbl.setText("connection error");
                    });
                }
            }
            
        }
    }
    // function with (one string parameter) to send message with DataOutputStream to the servers InputStream
    public void send(String msg){
        try{
            
            output.writeUTF(serveriptf.getText()+": "+msg);
            Platform.runLater(new Runnable(){
            	@Override
            	public void run(){
            		mainta.appendText(serveriptf.getText()+": "+msg+"\n");
            	}

            });
        }catch(Exception e){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    errorlbl.setText("error sending");
                }
            });
        }
    }
}
    


