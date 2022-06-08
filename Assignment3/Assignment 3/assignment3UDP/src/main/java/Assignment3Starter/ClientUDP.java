package Assignment3Starter;

//Client UDP
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.Scanner;

public class ClientUDP {

    // Shows the quotes
    public static void quotes() {

        String[] arr = { "/Users/owner/Desktop/assignment3TCP/img/Captain_America/quote4.png",
                "/Users/owner/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote3.png",
                "/Users/owner/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote2.png",
                "/Users/owner/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote1.png" };
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

        System.out.println("Please enter your name.");

        System.out.println("Enter '1' for your quote");

        Scanner sc = new Scanner(System.in);

        // Creating the socket

        DatagramSocket ds = new DatagramSocket();

        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null;

        // loop for when the user has not said "done"
        while (true) {
            String inp = sc.nextLine();

            // converts String into a byte array.
            buf = inp.getBytes();

            // Creates a datagramPacket for sending
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234);

            // this sends quotes
            ds.send(DpSend);
            if (inp.equals("1")) {
                quotes();
            }
            if (inp.equals("done"))
                break;
        }
    }
}
