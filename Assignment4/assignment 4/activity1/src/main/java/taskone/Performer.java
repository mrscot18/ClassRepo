/**
  File: Performer.java
 Author: Madison Scott, Summer 2022
  Description: Performer class in package taskone.
*/

package taskone;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Class: Performer 
 * Description: Threaded Performer for server tasks.
 */
class Performer {

    private StringList state;
    private Socket conn;

    public Performer(Socket sock, StringList strings) {
        this.conn = sock;
        this.state = strings;
    }

    public JSONObject add(String str) {
        JSONObject json = new JSONObject();
        json.put("datatype", 1);
        json.put("type", "add");
        state.add(str);
        json.put("data", state.toString());
        return json;
    }

    public JSONObject pop() {
        JSONObject json = new JSONObject();
        json.put("datatype", 1);
        json.put("type", "pop");
        String temp = state.pop();
        json.put("data", temp);
        return json;
    }
    public JSONObject display() {
        JSONObject json = new JSONObject();
        json.put("datatype",1);
        json.put("type","display");
        String temp = state.display();
        json.put("data", temp);
        return json;
    }
    public JSONObject count() {
        JSONObject json = new JSONObject();
        json.put("datatype", 1);
        json.put("type", "count");
        String count =  state.count();
        json.put("data", count);
        return json;
    }

    public JSONObject switch_elements (String in) {
        JSONObject json = new JSONObject();
        json.put("datatype",1);
        json.put("type","switch");
        if (!state.switch_elements(in)) {
            json.put("data", "null");
            return json;
        }
        json.put("data","success");
        return json;
    }
    public static JSONObject error(String err) {
        JSONObject json = new JSONObject();
        json.put("error", err);
        return json;
    }

    public void doPerform() {
        boolean quit = false;
        OutputStream out = null;
        InputStream in = null;
        try {
            out = conn.getOutputStream();
            in = conn.getInputStream();
            System.out.println("Server connected to client:");
            while (!quit) {
                byte[] messageBytes = NetworkUtils.receive(in);
                JSONObject message = JsonUtils.fromByteArray(messageBytes);
                JSONObject returnMessage = new JSONObject();
   
                int choice = message.getInt("selected");
                    switch (choice) {
                        case(0):
                            quit = true;
                        case (1):
                            String inStr = (String) message.get("data");
                            returnMessage = add(inStr);
                            break;
                        case (2):
                            returnMessage = pop();
                            break;
                        case (3):
                            returnMessage = display();
                            break;
                        case (4):
                            returnMessage = count();
                            break;
                        case (5):
                            String temp = (String) message.get("data");
                            returnMessage = switch_elements(temp);
                            break;

                        default:
                            returnMessage = error("Invalid selection: " + choice 
                                    + " is not an option");
                            break;
                    }
                // we are converting the JSON object we have to a byte[]
                byte[] output = JsonUtils.toByteArray(returnMessage);
                NetworkUtils.send(out, output);
            }
            // close the resource
            System.out.println("close the resources of client ");
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
