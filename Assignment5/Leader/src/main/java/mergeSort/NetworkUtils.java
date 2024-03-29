package mergeSort;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class NetworkUtils {

  public static JSONObject send(String host, int port, JSONObject message) {
    Socket socket = null;
    try {
      socket = new Socket(host, port);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      out.println(message.toString());
      String line = in.readLine();
      JSONTokener tokener = new JSONTokener(line);
      JSONObject root = new JSONObject(tokener);

      in.close();
      out.close();
      socket.close();

      return root;
    } catch (SocketException | EOFException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (socket != null)
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    return null;
  }

  public static JSONObject read(Socket conn) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line = in.readLine();
    JSONTokener tokener = new JSONTokener(line);
    JSONObject root = new JSONObject(tokener);
    return root;
  }

  public static void respond(Socket conn, JSONObject message) throws IOException {
    PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
    out.println(message.toString());
  }
}
