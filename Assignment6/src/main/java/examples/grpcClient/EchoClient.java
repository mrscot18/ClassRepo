package examples.grpcClient;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class EchoClient {
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
  private final RockPaperScissorsGrpc.RockPaperScissorsBlockingStub blockingStub4;
  private final HometownsGrpc.HometownsBlockingStub blockingStub5;
  private final userGrpc.userBlockingStub blockingStub6;

  /** Construct client for accessing server using the existing channel. */
  public EchoClient(Channel channel, Channel regChannel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
    blockingStub4 = RockPaperScissorsGrpc.newBlockingStub(channel);
    blockingStub5 = HometownsGrpc.newBlockingStub(channel);
    blockingStub6 = userGrpc.newBlockingStub(channel);
  }

  public void guessPassword(String username, String password) {

    LogReq lr = LogReq.newBuilder().setUse(username).setPas(password).build();

    ApiRes res = blockingStub6.login(lr);

    System.out.println(res.getResponseMsg());

  }

  public void askServerToParrot(String message) {
    ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
    ServerResponse response;
    try {
      response = blockingStub.parrot(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e.getMessage());
      return;
    }
    System.out.println("Received from server: " + response.getMessage());
  }

  public void askForJokes(int num) {
    JokeReq request = JokeReq.newBuilder().setNumber(num).build();
    JokeRes response;

    try {
      response = blockingStub2.getJoke(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }

    System.out.println("Your jokes: ");
    for (String joke : response.getJokeList()) {
      System.out.println("--- " + joke);
    }
  }

  // hometown

  public void askForHome(int num) {
    HometownsWriteRequest request = HometownsWriteRequest.newBuilder()
            .build();

    HometownsWriteResponse response;

    try {
      response = blockingStub5.write(request);
      System.out.println("--- " + request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }

  }

  // asking to play
  public void askForToPlay(int num) {
    PlayReq request = PlayReq.newBuilder().setPlayValue(num).build();
    PlayRes response;


    try {
      response = blockingStub4.play(request);
      System.out.println("--- " + request.getPlayValue());

      System.out.println(response.getMessage());

    } catch (Exception e) {
      System.out.println("error_____::::" + e);

    }

  }

  public void setJoke(String joke) {
    JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
    JokeSetRes response;

    try {
      response = blockingStub2.setJoke(request);
      System.out.println(response.getOk());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub3.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServer(String name) {
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    try {
      response = blockingStub3.findServer(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServers(String name) {
    FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
    ServerListRes response;
    try {
      response = blockingStub3.findServers(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 5) {
      System.out
              .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)>");
      System.exit(1);
    }
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
    try {
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port] must be an integer");
      System.exit(2);
    }

    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
            .usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();

    try {

      EchoClient client = new EchoClient(channel, regChannel);

      client.askServerToParrot(message);

      int choice;

      do {
        System.out.println("To play Rock Paper Scissors, press 1");
        System.out.println("To input your hometown, press 2");
        System.out.println("To guess that song lyric! press 3");

        Scanner aq = new Scanner(System.in);
        choice = aq.nextInt();

        switch (choice) {

          case (1):
            while (true) {

              //sorry, no error handling here
              BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

              System.out.println("0 = Rock, 1= paper, 2 = Scissors");
              String num = reader.readLine();

              client.askForToPlay(Integer.valueOf(num));

            }

          case (2):
            while (true) {
//sorry no error handling
              BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

              System.out.println("write hometown");
              String num = reader.readLine();

              client.askForHome(Integer.valueOf(num));

            }


          case (3):
            while (true) {
              BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

              System.out.println("You can dannceeee, you can jiveeeee, having the time of your lifeeee");

              String password = reader.readLine();
              client.guessPassword(password, password);

              break;

            }

        }

      } while (true);

    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent
      // leaking these
      // resources the channel should be shut down when it will no longer be used. If
      // it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}
