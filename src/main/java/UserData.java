import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserData {
    private String usernamePassword;


    public void writeUserData() throws IOException {
        FileWriter writer = new FileWriter("users.txt", true); //make the writer in append mode
        writer.write(usernamePassword+"\n");
        writer.flush();
        writer.close();
    }

    public void setUsernamePassword(String usernamePassword) throws IOException {
        this.usernamePassword = usernamePassword;
        writeUserData();
    }

    public boolean checkLogin() throws IOException {
        FileReader reader = new FileReader("users.txt");
        BufferedReader br= new BufferedReader(reader);
        if (null != br.readLine()) return true;
        else return false;
    }

    public String getUsernamePassword() {
        return usernamePassword;
    }
}

