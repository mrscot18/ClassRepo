package mergeSort;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.json.JSONObject;

public abstract class Node implements Runnable {
  private int _port;

  public Node(int port) {
    _port = port;
  }

  public abstract JSONObject credit(JSONObject object);
  public abstract JSONObject payBack(JSONObject object);
  public abstract JSONObject error(String error);
  public abstract JSONObject peek(JSONObject object);


  @Override
  public void run() {
    ServerSocket socket = null;
    try {
      // create a listening socket
      socket = new ServerSocket(_port);
      while (true) {
        Socket conn = null;
        try {
          conn = socket.accept();

          JSONObject root = NetworkUtils.read(conn);

          JSONObject ret = error("");
          if (root.has("method")) {
            switch (root.getString("method")) {

              case ("pay-back"):
                ret = payBack(root);
                break;

              case ("credit"):
                ret = credit(root);
                break;

              case ("peek"):
                ret = peek(root);
                break;

            }
          }

          NetworkUtils.respond(conn, ret);


          conn.close();
        } catch (SocketException | EOFException e) {
        } catch (IOException ex) {
          ex.printStackTrace();
        } finally {
          if (conn != null)
            try {
              conn.close();
            } catch (IOException ex) {
              ex.printStackTrace();
            }
        }
      }
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
  }
}
