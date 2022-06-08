package Assignment3Starter;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerSideTCP {
    static ServerSocket listenServerSocket;
    static DataInputStream dis;
    static DataOutputStream dos;
    static Socket soc;
    Boolean connected = false;

    public class person {
        String name;
        int points;

        person(String name, int points) {
            this.name = name;
            this.points = points;
        }
    }

    // Finds the highest scores
    public static <K, V extends Comparable<V>> Map.Entry<K, V> getMaxScore(Map<K, V> hashMap) {

        Map.Entry<K, V> highest_Score = null;

        for (Map.Entry<K, V> tempHighest : hashMap.entrySet()) {
            if (highest_Score == null || tempHighest.getValue().compareTo(highest_Score.getValue()) > 0) {
                highest_Score = tempHighest;
            }
        }
        return highest_Score;
    }

    // prints the leaderboard
    public static void printScore(Map<String, Integer> scores) {

        System.out.print("Leaderboard: ");
        if (scores.isEmpty()) {
            System.out.println("No score right now");
        } else {
            System.out.println(scores);
        }
    }

    // prints quotes
    public static void quotes() {

        String[] arr = { "/Users/owner/Desktop/assignment3TCP/img/Captain_America/quote4.png",
                "/Users/owner/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote3.png",
                "/Users/owner/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote2.png",
                "/Users/owner/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote1.png",};
        Random r = new Random();
        int randomNumber = r.nextInt(arr.length);
        System.out.println(arr[randomNumber]);

        var frame = new JFrame();
        var icon = new ImageIcon(arr[randomNumber]);

        var label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String args[]) throws IOException {

        // leaderboard
        Map<String, Integer> leaderboard = new HashMap<>();


        // creates a server socket
        listenServerSocket = new ServerSocket(8880);
        System.out.println("Waiting for the client...");
        soc = listenServerSocket.accept();
        System.out.println("Connection has been established");


        PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));

        // timer
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                System.out.println("Time is up");
                System.out.println("Time is up");
                out.println("The time is up, type next in order to start over.");

                out.println("Press 1 for leaderboard");
                out.println("Time is up, press 1 for the leaderboard");
                out.println("Thank you for playing! Don't forget 1, is the leaderboard.");
            }
        };

        //information for the game

        out.println("Please enter your name:");
        int points = 0;
        // next = false;
        String name = in.readLine().trim();
        if (name != null) {

            int count = 0;

            out.println("Hello! ," + name + " Your first quote is being sent, enter next if you want another quote");

            leaderboard.put(name, points);

            quotes();

            boolean correct = false;
            timer.schedule(task, 9000);
            while (true) {

                String req = in.readLine().trim();
                if (req.equals("captain america") || req.equals("Homer Simpson")) {

                    out.println("You are correct!");
                    int oldpoints = points;
                    points = points + 5;
                    leaderboard.replace(name, oldpoints, points);

                } else if (req.equals("next")) {

                    quotes();

                } else if (req.equals("more")) {

                    quotes();

                } else if (req.equals("1")) {

                    out.println("Live LeaderBoard:  " + leaderboard);

                } else if (req.equals("2")) {
                    quotes();
                } else {

                    out.println("You have found a bug, I'm sorry! ");
                }
                if (req.equals("answer")) {
                    out.println("That would be too easy!");
                }

                printScore(leaderboard);
                getMaxScore(leaderboard);

            }
        }

    }

}
