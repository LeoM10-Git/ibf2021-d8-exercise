import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("Connected to server.....");
        //test

        Socket socket = new Socket("localhost", 3000);

        try(OutputStream os = socket.getOutputStream()){
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        String command = "";

        while (true){
            command = br.readLine();
            dos.writeUTF(command);
            dos.flush();

            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            String reply = dis.readUTF();
            System.out.println(reply);
        }} catch (IOException e) {
           socket.close();
        }
    }
}
