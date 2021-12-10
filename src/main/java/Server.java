import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class Server {
    public static void main(String[] args) throws IOException {
        UserData userData = new UserData();

        System.out.println("Listen to port 3000: ");
        ServerSocket server = new ServerSocket(3000);
        Socket socket = server.accept();

        try(InputStream is =socket.getInputStream()){
            DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
            String message;
            String request;
            String loginInfo = null;
            boolean isLogin = userData.checkLogin();
            boolean expired = false;
            Calendar start = Calendar.getInstance();

            while (true){
                request = dis.readUTF();
                // get the 30 sec duration
                double duration = (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis())/1000.0;
                System.out.println(duration+"sec passed");
                start = Calendar.getInstance();
                if (duration>30) expired = true;

                System.out.println("Received request from client----" + request);
                OutputStream os =socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
                if (request.equals("get-cookie") && !expired){
                    // compare time

                    if (!isLogin){
                        message = "You are not registered, please choose a username and password e.g. abc/123";
                        System.out.println("Reply sent from Server......>>>>>>");
                        dos.writeUTF(message);
                        dos.flush();
                        loginInfo = dis.readUTF();
                        userData.setUsernamePassword(loginInfo);
                        message= "You are registered and login";
                        isLogin = true;
                    }
                    else{
                        message = "Cookie";  //get cookie class, method
                        System.out.println("Reply sent from Server......>>>>>>");
                    }
                    dos.writeUTF(message);
                    dos.flush();

                }
                else if (request.equals("get-cookie") && expired){
                    message = "Time out, Please login again";
                    dos.writeUTF(message);
                    dos.flush();
                    expired = false;
                    isLogin = false;
                }
            }
        }catch (EOFException e){
            socket.close();
        }
    }
}
