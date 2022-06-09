package Assignment3Starter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class ClientTCP {

  public static void main(String[] args) throws IOException {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

      @Override
      public void run() {
        System.out.println("Your time is up");
        System.out.println("Congrats! You have finshed this round!");
        System.out.println("The game will not start, reset");
      }
    };

    try (Socket socket = new Socket("localhost", 8880)) {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      // reads from server
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      BufferedReader keybBoard = new BufferedReader(new InputStreamReader(System.in));

      BufferedWriter key = new BufferedWriter(new OutputStreamWriter(System.out));

      System.out.println("You have 1 minute to guess or timer will run out, if you type '1' you get the leaderboard");
      System.out.println("Type 'next' for your next quote, type 'more' for more quotes");
      System.out.println("If you want to give up... type 'answer' to speak to the server");




      // timer.schedule(task, 10000); // 60000 for 1 min
      while (true) {
        String servResponse = input.readLine();// reads server

        if (servResponse != null) {
          System.out.println("Server Says: " + servResponse);
        }

        String cmd = keybBoard.readLine();

        out.println(cmd);
        if (cmd.equals("quit")) {
          System.exit(0);
        }

      }
    }
  }

  public void insertImage(String string, int i, int j) {
  }

  public void newGame(int i) {
  }
}

