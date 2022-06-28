package mergeSort;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Consensus extends Node {
  private int portL;
  private int portR;
  private String hostL;
  private String hostR;

  public boolean leader_1;
  public boolean leader_2;

  ArrayList<String> po = new ArrayList<String>();

  public Consensus(int port, int left, int right, String leftH, String rightH) {
    super(port);
    portL = left;
    portR = right;
    hostL = leftH;
    hostR = rightH;
  }

  // Pay-back
  public JSONObject payBack(JSONObject object) {

    po.add("lead");
    po.add("not a lead");
    object.put("method", "peek");
    JSONObject resp1 = NetworkUtils.send(hostL, portL, object);
    if (resp1.has("error")) {
      return resp1;
    }

    JSONObject resp2 = NetworkUtils.send(hostR, portR, object);
    if (resp2.has("error")) {
      return resp2;
    }

    Collections.shuffle(po);

    System.out.println("Random values :" + po.get(0));

    String rand = po.get(new Random().nextInt(po.size() - 1));

    System.out.println(rand);

    leader_1 = false;

    object.put("method", "pay-back");
    if (!resp1.getBoolean("haveVal")) {

      leader_2 = true;

      System.out.println("The right node is leader");
      return NetworkUtils.send(hostR, portR, object);

    } else if (!resp2.getBoolean("haveVal")) {

      System.out.println("The left node is leader");
      leader_1 = true;
      return NetworkUtils.send(hostL, portL, object);
    } else if (resp1.getInt("value") < resp2.getInt("value")) {

      leader_1 = true;
      System.out.println("the right node is leader");
      return NetworkUtils.send(hostL, portL, object);
    } else {
      leader_2 = true;
      System.out.println("the left node is leader");
      return NetworkUtils.send(hostR, portR, object);
    }

  }

  // Credit
  public JSONObject credit(JSONObject object) {
    object.put("method", "peek");
    JSONObject resp1 = NetworkUtils.send(hostL, portL, object);
    if (resp1.has("error")) {
      return resp1;
    }

    JSONObject resp2 = NetworkUtils.send(hostR, portR, object);
    if (resp2.has("error")) {
      return resp2;
    }

    Collections.shuffle(po);

    System.out.println("Random nodes :" + po.get(0));

    String rand = po.get(new Random().nextInt(po.size() - 1));

    System.out.println(rand);

    // leader1 = false;
    // leader2 = false;
    object.put("method", "credit");
    if (!resp1.getBoolean("haveVal")) {

      leader_2 = true;

      System.out.println("right node is leader");
      return NetworkUtils.send(hostR, portR, object);

    } else if (!resp2.getBoolean("haveVal")) {

      System.out.println("left node is leader");
      leader_1 = true;
      return NetworkUtils.send(hostL, portL, object);
    } else if (resp1.getInt("value") < resp2.getInt("value")) {

      leader_1 = true;
      System.out.println("right node is leader");
      return NetworkUtils.send(hostL, portL, object);
    } else {
      leader_2 = true;
      System.out.println("left node is leader");
      return NetworkUtils.send(hostR, portR, object);
    }
  }

  public JSONObject peek(JSONObject object) {
    JSONObject resp1 = NetworkUtils.send(hostL, portL, object);
    if (resp1.has("error")) {
      return resp1;
    }

    JSONObject resp2 = NetworkUtils.send(hostR, portR, object);
    if (resp2.has("error")) {
      return resp2;
    }

    if (!resp1.getBoolean("haveVal")) {
      return resp2;
    } else if (!resp2.getBoolean("haveVal")) {
      return resp1;
    } else if (resp1.getInt("value") < resp2.getInt("value")) {
      return resp1;
    } else {
      return resp2;
    }
  }

  public JSONObject error(String error) {
    JSONObject ret = new JSONObject();
    ret.put("error", error);
    return ret;
  }

  public static void main(String[] args) {
    new Consensus(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]), "localhost",
        "localhost")
            .run();
  }

}
