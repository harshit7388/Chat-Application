import java.net.*;
import java.io.*;

class Server {

    ServerSocket server;
    Socket socket;

    BufferedReader br; // for reading
    PrintWriter out; // for writing

    public Server() {

        try {
            server = new ServerSocket(7777);
            System.out.println("sever is ready to accept connection");
            System.out.println("waiting");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));// reading
            out = new PrintWriter(socket.getOutputStream()); // writing the data

            startReading();
            startWriting();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 
     */
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
                        System.out.println("chat ended from client");
                        socket.close();
                        break;
                    }
                    System.out.println("client: "+msg);
                
                 
                   
                   
                
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
                while(true && !socket.isClosed()){
                
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                   
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                        
                    }

            
        }
            } catch (Exception e) {
                // TODO: handle exception
                // e.printStackTrace();
                 System.out.println("connection closed");
            }

        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {

        System.out.println("server starting....");
        new Server();
    }
}


