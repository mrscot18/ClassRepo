package Assignment3Starter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;

public class ServerUDP {

    // Shows the quotes
    public static void quotes() {

        String[] arr = { "/Users/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote1.png",
                "/Users/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote2.png",
                "/Users/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote3.png",
                "/Users/Desktop/assignmentTCP/assignment3TCP/img/Captain_America/quote4.png" };
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

    public static <BufferedImage> void main(String[] args) throws IOException {
        // Creates a socket
        DatagramSocket ds = new DatagramSocket(1234);
        byte[] receive = new byte[65535];
        byte[] send = new byte[65535];

        DatagramPacket DpReceive = null;
        while (true) {

            if (data(receive).toString().equals("1")) {

                quotes();
            }
            // Creates a DatgramPacket, receives the data.
            DpReceive = new DatagramPacket(receive, receive.length);

            // Recieves the data in byte buffer.
            ds.receive(DpReceive);
            System.out.println("Client:-" + data(receive));

            // Exits the server if the client sends "done"
            if (data(receive).toString().equals("done")) {
                System.out.println("Client is done will be exiting");

                break;
            }

        }

    }

    // Converts bytes into data

    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
