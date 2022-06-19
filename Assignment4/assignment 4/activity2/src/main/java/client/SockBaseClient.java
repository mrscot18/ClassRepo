package client;

import java.net.*;
import java.io.*;

import org.json.*;

import buffers.RequestProtos.Request;
import buffers.ResponseProtos.Response;
import buffers.ResponseProtos.Entry;

import java.util.*;
import java.util.stream.Collectors;

class SockBaseClient {
    private Socket serverSock;
    private OutputStream out;
    private InputStream in;
    private int i1;
    private int i2;
    private int port;
    private BufferedReader stdin;

    public static void main (String args[]) throws Exception {
        Socket serverSock = null;
        OutputStream out = null;
        InputStream in = null;
        int i1=0, i2=0;
        int port = 9099; // default port
        stdin = null;

        //ask leader message
        private Request askLeaderMessageBuilder () {
            Request op = Request.newBuilder()
                    .setOperationType(Request.OperationType.LEADER)
                    .build();
            return op;
        }

        //ask enter game message
        private Request askEnterGameMessageBuilder () {
            Request op = Request.newBuilder()
                    .setOperationType(Request.OperationType.NEW)
                    .build();
            return op;
        }

        //Shows the leaderboard
        private void leaderBoard() {
            try {
                Request op = askLeaderMessageBuilder();
                op.writeDelimitedTo(out);
                Response response = Response.parseDelimitedFrom(in);
                System.out.println("Welcome to the leaderboard!");
                for (Entry lead: response.getLeaderList()){
                    System.out.println(lead.getName() + ": " + lead.getWins());
                }
                System.out.println("\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //plays the game
        private void playG () {
            Request op;
            boolean cont = true;
            try {
                op = askEnterGameMessageBuilder();
                op.writeDelimitedTo(out);
            } catch (IOException e) {
                System.out.println("There was an issue writing to client");
                return;
            }
            while (cont) {
                try {
                    Response response = Response.parseDelimitedFrom(in);
                    if (response.getResponseType() == Response.ResponseType.TASK) {
                        System.out.println(" image \n" + response.getImage());
                        System.out.println("Complete the following task: " + response.getTasks());
                        System.out.print("Enter your answer to the task:");
                        Scanner scan = new Scanner(System.in);
                        String line = scan.nextLine();
                        op = answerResponse(line);
                        op.writeDelimitedTo(out);

                    } else if (response.getResponseType() == Response.ResponseType.WON) {
                        System.out.println("Congratulations! You won!");
                        cont = false;
                    } else {
                        cont = false;
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("Sorry, there was an issue");
                    cont = false;
                    break;
                }
            }
        }

        private Request answerResponse (String string) {
            Request op = Request.newBuilder()
                    .setOperationType(Request.OperationType.ANSWER)
                    .setAnswer(string)
                    .build();
            return op;
        }

        private void start (String host, int port) {
            System.out.println("Please provide your name for the server. ( ͡❛ ͜ʖ ͡❛)");
            String strToSend = "";
            try {
                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                String strToSend = stdin.readLine();
            } catch (Exception e) {
                System.out.println("Exiting, there was an issue.");
                System.exit(1);
            }

            Request op = Request.newBuilder()
                    .setOperationType(Request.OperationType.NAME)
                    .setName(strToSend).build();
            Response response;
            try {
                // connect to the server
                serverSock = new Socket(host, port);

                // write to the server
                out = serverSock.getOutputStream();
                in = serverSock.getInputStream();

                op.writeDelimitedTo(out);

                boolean cont = true;
                while (cont) {
                    // read from the server
                    response = Response.parseDelimitedFrom(in);

                    // print the server response.
                    System.out.println(response.getMessage());

                    int choice;
                    try {
                       String strToSend = stdin.readLine();
                        choice = Integer.parseInt(strToSend);
                        if (!(choice >= 0 ))
                            throw new Exception();
                    } catch (Exception e) {
                        System.out.println("Try again.");
                        continue;
                    }
                    switch (choice) {
                        case 0:
                            cont = false;
                            break;
                        case 1:
                            leaderBoard();
                            break;
                        case 2:
                            playG();
                            break;
                        default:
                            System.out.println("exiting");
                            cont = false;
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Exiting");
                System.exit(1);
            }
        }

        public static void main (String args[]) throws Exception {

            SockBaseClient client = new SockBaseClient();
            int port = -1;

            if (args.length != 2) {
                System.out.println("Expected arguments: <host(String)> <port(int)>");
                System.exit(1);
            }
            String host = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                System.out.println("[Port] must be integer");
                System.exit(2);
            }

            client.start(host, port);


        }
    }

