import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.util.LinkedList;
import org.json.*;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;

/**
 * SERVER
 * This is the ServerThread class that has a socket where we accept clients contacting us.
 * We save the clients ports connecting to the server into a List in this class. 
 * When we wand to send a message we send it to all the listening ports
 */

public class ServerThread extends Thread{
	private ServerSocket serverSocket;
	private Set<Socket> listeningSockets = new HashSet<Socket>();
	private LinkedList<Integer> port_new = new LinkedList<>();
	private String allPorts;
	private Peer peer;

	
	public ServerThread(String portNum) throws IOException {
		serverSocket = new ServerSocket(Integer.valueOf(portNum));
	}

	public void run() {
		try {
			while (true) {
				Socket sock = serverSocket.accept();
				listeningSockets.add(sock);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				JSONObject json = new JSONObject(bufferedReader.readLine());

				if(json.getString("enter").equals("1")){
					allPorts = json.getInt("data") + " ";
					System.out.println(allPorts);
					port_new.add(json.getInt("data"));
					System.out.println(json.toString());
					Socket socket;
					try{
						socket = new Socket("localhost", json.getInt("data"));
						new ClientThread(socket).start();
						PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

						JSONObject json2 = new JSONObject();
						json2.put("enter","2");
						sout.println(json2.toString());
					}catch(Exception e){
						System.out.println("1");
					}
				}

				else if(json.getString("enter").equals("2")){
					listToString(port_new);
				}
				else if(json.getString("enter").equals("3")){
				}
				else if(json.getString("enter").equals("4")){
				}
				else{
					System.out.println("Sorry, incorrect JSON type");
					System.exit(2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendMessage(String message) {
		try {
			System.out.println( "..." +  listeningSockets.size() + message);
			for (Socket s : listeningSockets) {
				PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
				out.println(message);
		     }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public LinkedList<Integer> getport_new() {
		return port_new;
	}
	public void setport_new(LinkedList<Integer> port_new) {
		this.port_new = port_new;
	}



	public boolean listToString(LinkedList<Integer> list) throws IOException {
		String answer = "";
		for (Integer sock : list) {
			String temp  = list.get(s).toString();
			answer += " " + temp;
		}
		System.out.println("This list as a string: " + answer);
		return false;
	}

}

