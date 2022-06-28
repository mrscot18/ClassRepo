package mergeSort;
import org.json.JSONObject;
import java.util.PriorityQueue;

public class Bank extends Node {
  private PriorityQueue<Integer> sorted = new PriorityQueue<Integer>();

  int startingMoney = 1000;

  public Bank(int port) {
    super(port);
  }

  public synchronized JSONObject payBack(JSONObject object) {

    sorted.clear();

    for (var val : object.getJSONArray("info")) {
      sorted.add((Integer) val + startingMoney);
    }
//resp - response, haveval - have value
    object.put("resp", true);
    if (sorted.size() > 0) {
      object.put("haveVal", true);
      object.put("val", sorted.peek());
    } else {
      object.put("haveVal", false);
    }
    return object;

  }

  public synchronized JSONObject credit(JSONObject object) {

    if (startingMoney < 150) {

      System.out.println("Sorry, you have reached the limit, there is no more money for credit.");

    }

    sorted.clear();
    for (var val : object.getJSONArray("info")) {
      sorted.add(startingMoney - (Integer) val);
    }

    object.put("resp", true);
    if (sorted.size() > 0) {
      object.put("haveVal", true);
      object.put("val", sorted.peek());
    } else {
      object.put("haveVal", false);
    }
    return object;

  }

  public synchronized JSONObject peek(JSONObject object) {
    object.put("resp", true);
    if (sorted.size() > 0) {
      object.put("haveVal", true);
      object.put("val", sorted.peek());
    } else {
      object.put("haveVal", false);
    }
    return object;
  }

  public JSONObject error(String error) {
    JSONObject ret = new JSONObject();
    ret.put("error", error);
    return ret;
  }

  public static void main(String[] args) {
    new Bank(Integer.valueOf(args[0])).run();
  }
}