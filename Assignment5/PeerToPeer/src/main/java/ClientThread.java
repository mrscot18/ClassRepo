import java.io.BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.*;


public class ClientThread extends Thread {
	private BufferedReader bufferedReader;
	
	public ClientThread(Socket socket) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public void run() {
		while (true) {
			try {
			    JSONObject json = new JSONObject(bufferedReader.readLine());
			    System.out.println("[" + json.getString("username")+"]: " + json.getString("message"));
			} catch (Exception e) {
				interrupt();
				break;
			}
		}
	}

}
