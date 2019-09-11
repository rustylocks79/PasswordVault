import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Account {

    private String id,username,password;

    public Account(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account(String fileLine) {
        String[] tokens = fileLine.split(",");
        if(tokens.length != 3) { throw new IllegalArgumentException("File Line not properly formatted. "); }
        id = tokens[0];
        username = tokens[1];
        password = tokens[2];
    }

    public void writeToFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write("id: " + id + "\n");
            writer.write("username: " + username + "\n");
            writer.write("password: " + password + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return id + "," + username + "," + password;
    }
}
