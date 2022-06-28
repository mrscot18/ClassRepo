package mergeSort;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import javax.lang.model.element.PackageElement;


public class Client {

  public static int message_ID = 0;

  // this the code to pay-back the credit owed
  public static JSONObject payBack(int amnt) {
    JSONArray arr = new JSONArray();

    arr.put(amnt);

    JSONObject req = new JSONObject();
    req.put("method", "pay-back");
    req.put("info", arr);
    return req;
  }

  //  credit code
  public static JSONObject credit(int amnt) {
    JSONArray arr = new JSONArray();

    arr.put(amnt);

    JSONObject req = new JSONObject();
    req.put("method", "credit");
    req.put("info", arr);
    return req;
  }

  public static JSONObject peek() {
    JSONObject req = new JSONObject();
    req.put("method", "peek");
    return req;
  }

  public static void Test(int port, String host) {

    System.out.println("Please type in your bank account ID #");

    Scanner id = new Scanner(System.in);

    int ID = id.nextInt();

    System.out.println("Your ID has been accepted!");

    JSONObject resp = null;
    int choice;
    do {
      System.out.println("For pay-back, press 1");
      System.out.println("For credit, press 2");

      Scanner aq = new Scanner(System.in);
      choice = aq.nextInt();

      JSONObject reqeust = null;
 //code for when the option 1 or 2 has been chosen
      switch (choice) {

        case (1):
        //payback option
          System.out.println("Please type in an amount to add to the bank.");
          Scanner s = new Scanner(System.in);
          int pick = s.nextInt();
          resp = NetworkUtils.send(host, port, payBack(pick));
          System.out.println("You've added this much to your account:$" + resp.get("info"));
          reqeust = NetworkUtils.send(host, port, payBack(pick));
          System.out.println("Your total amount after pay-back is $" + resp.getInt("value"));
          System.out.println("  ");
          break;

        case (2):
          //credit option
          System.out.println("Please input the amnt you'd like credited.");
          Scanner pScanner = new Scanner(System.in);
          int picck = pScanner.nextInt();
          resp = NetworkUtils.send(host, port, credit(picck));
          System.out.println("You have selected this much for credit:$" + resp.get("info"));
          reqeust = NetworkUtils.send(host, port, credit(picck));
          System.out.println("Your total amount after the credit:$" + resp.getInt("value"));
          System.out.println("  ");
          break;

        case (3):
          reqeust = peek();
          break;

      }

    } while (true);

  }

  public static void main(String[] args) {

    Test(Integer.valueOf(args[0]), args[1]);

  }
}
