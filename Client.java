import java.net.*;
import java.io.*;

public class Client {


        Socket socket;
        BufferedReader br; // for reading
        PrintWriter out; // for writing

        public Client(){
                try {
                    System.out.println("sending req to server");
                    socket = new Socket("127.0.0.1", 7777);
                    System.out.println("connection done");


                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));// reading
                    out = new PrintWriter(socket.getOutputStream()); // writing the data

            startReading();
            startWriting();

                } catch (Exception e) {
                    // TODO: handle exception
                }
      }
      public void startReading() {
        // creating thread that will read data
        Runnable r1 = () -> {
            System.out.println("reader started");
           try {
            while (true) {
                String msg;
                
                    msg = br.readLine();
                    if (msg.equals("exit")) 
                    {
                        System.out.println("chat ended from server");
                        break;
                    }
                    System.out.println("server: "+msg);
                
                
            }
           } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
            System.out.println("connection closed");
           }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        // creating thread that will take data from user and send to client
        Runnable r2 = () -> {
            System.out.println("writer started");
           try {
            while( !socket.isClosed()){
               
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                out.println(content);
                out.flush();

                if(content.equals("exit")){
                    socket.close();
                    break;
                    
                }
       
    }
    System.out.println("connection closed");
           } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
            System.out.println("connection closed");
           }

        };
        new Thread(r2).start();

    }



    public static void main(String[] args) {
        System.out.println("this is client..");
        new Client();
    }
    
}
