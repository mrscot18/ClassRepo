package server;

import java.net.*;
import java.io.*;
import java.util.*;
import org.json.*;
import java.lang.*;

import buffers.RequestProtos.Request;
import buffers.RequestProtos.Logs;
import buffers.RequestProtos.Message;
import buffers.ResponseProtos.Response;
import buffers.ResponseProtos.Entry;

class SockBaseServer {
    static String logFilename = "logs.txt";

    ServerSocket serv = null;
    InputStream in = null;
    OutputStream out = null;
    Socket clientSocket = null;
    int port = 9099; // default port
    Game game;
    private String name;
    PlayerL list;
    Tasks tasks = new Tasks();


    public SockBaseServer(Socket sock, Game game, PlayerL initList){
        this.clientSocket = sock;
        this.game = game;
        this.list = initList;
        try {
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        } catch (Exception e){
            System.out.println("Error in constructor: " + e);
        }
    }
    // Handles the communication right now it just accepts one input and then is done you should make sure the server stays open
    // can handle multiple requests and does not crash when the server crashes
    // you can use this server as based or start a new one if you prefer.

    public void playG () throws Exception {
        writeToLog(name, Message.START);
        Response response;
        int i = 1;
        if (game.getWinUpdate()) {
            game.newGame();
        }
        while (!game.getWinUpdate()) {
            try {
                gameTaskResponse(i);
                i++;
            } catch (Exception e) {
                break;
            }
        }
        if (game.getWinUpdate()) {
            try {
                response = Response.newBuilder()
                        .setResponseType(Response.ResponseType.WON)
                        .build();
                response.writeDelimitedTo(out);
                writeToLog(name, Message.WIN);

                this.list.incrementWin(name);
            } catch (Exception e) {
                System.out.println("Issue with client");
            }
        }
    }

    public void gameTaskResponse (int i) throws Exception {
        taskings tasks = tasks.getTasks();
        Request request;
        Response response = Response.newBuilder()
                .setResponseType(Response.ResponseType.TASK)
                .setImage(game.getImage())
                .setTasks(tasks.tasks)
                .build();
        try {
            response.writeDelimitedTo(out);
            request = Request.parseDelimitedFrom(in);
            if (request.getOperationType() == Request.OperationType.ANSWER) {
                String answer = request.getAnswer();
                System.out.print("Answer: " + answer);
                if (answer.equals(tasks.ans)) {
                    System.out.print("You're correct!\n");
                    if (i < game.getHidden()) {
                        for (int j = 0; j < i; j++) {
                            game.replaceOneCharacter();
                        }
                    } else {
                        for (int j = 0; j < game.getHidden(); j++) {
                            game.replaceOneCharacter();
                        }
                    }
                } else {
                    System.out.print(" That was wrong ): ):\n");
                }

            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // Handles the communication right now it just accepts one input and then is done you should make sure the server stays open
    // can handle multiple requests and does not crash when the server crashes
    // you can use this server as based or start a new one if you prefer.
    public void start() throws IOException {
       String name = "";


        System.out.println("Ready...");
        try {
            // read the proto object and put into new objct
            Request op = Request.parseDelimitedFrom(in);
            String result = null;



            // if the operation is NAME (so the beginning then say there is a commention and greet the client)
            if (op.getOperationType() == Request.OperationType.NAME) {
                // get name from proto object
                name = op.getName();

                // writing a connect message to the log with name and CONNENCT
                writeToLog(name, Message.CONNECT);
                System.out.println("Got a connection and a name: " + name);
                this.list.insertPlayer(name);

                while (true) {
                    Response response = Response.newBuilder()
                            .setResponseType(Response.ResponseType.GREETING)
                            .setMessage("Hello " + name + " and welcome. \nWhat would you like to do? \n 1 - to see the leader board \n 2 - to enter a game")
                            .build();
                    response.writeDelimitedTo(out);

                    op = Request.parseDelimitedFrom(in);
                    switch (op.getOperationType()) {
                        case LEADER:
                            sendLeaderBoard();
                            break;
                        case NEW:
                            playG();
                            break;
                        default:
                            break;
                    }
                }
            } else {
                throw new Exception();
            }

        } catch (Exception ex) {

        } finally {
            try {
                if (out != null)  out.close();
                if (in != null)   in.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                System.out.println("There was an issue closing out of the client.");
            }

        }
    }

    public void sendLeaderBoard () {
        Response response = this.list.getPlayerL();
        try {
            System.out.println(response.toString());
            response.writeDelimitedTo(out);
        } catch (IOException e) {
            System.out.println("There was an issue writing to the client");
        }
    }

    /**
     * Replaces num characters in the image. I used it to turn more than one x when the task is fulfilled
     * @param num -- number of x to be turned
     * @return String of the new hidden image
     */
    public String replace(int num) {
        for (int i = 0; i < num; i++){
            if (game.getIdx()< game.getIdxMax())
                game.replaceOneCharacter();
        }
        return game.getImage();
    }


    /**
     * Writing a new entry to our log
     * @param name - Name of the person logging in
     * @param message - type Message from Protobuf which is the message to be written in the log (e.g. Connect)
     * @return String of the new hidden image
     */
    public static void writeToLog(String name, Message message){
        try {
            // read old log file
            Logs.Builder logs = readLogFile();

            // get current time and data
            Date date = java.util.Calendar.getInstance().getTime();

            // we are writing a new log entry to our log
            // add a new log entry to the log list of the Protobuf object
            logs.addLog(date.toString() + ": " +  name + " - " + message);

            // open log file
            FileOutputStream output = new FileOutputStream(logFilename);
            Logs logsObj = logs.build();

            // This is only to show how you can iterate through a Logs object which is a protobuf object
            // which has a repeated field "log"

            for (String log: logsObj.getLogList()){

                System.out.println(log);
            }

            // write to log file
            logsObj.writeTo(output);
        }catch(Exception e){
            System.out.println("Issue while trying to save");
        }
    }

    /**
     * Reading the current log file
     * @return Logs.Builder a builder of a logs entry from protobuf
     */
    public static Logs.Builder readLogFile() throws Exception{
        Logs.Builder logs = Logs.newBuilder();

        try {
            // just read the file and put what is in it into the logs object
            return logs.mergeFrom(new FileInputStream(logFilename));
        } catch (FileNotFoundException e) {
            System.out.println(logFilename + ": File not found.  Creating a new file.");
            return logs;
        }
    }


    public static void main (String args[]) throws Exception {
        Game game = new Game();
        PlayerL 1 = new PlayerL();

        if (args.length != 2) {
            System.out.println("Expected arguments: <port(int)> <delay(int)>");
            System.exit(1);
        }
        int port = 9099; // default port
        int sleepDelay = 10000; // default delay
        Socket clientSocket = null;
        ServerSocket serv = null;

        try {
            port = Integer.parseInt(args[0]);
            sleepDelay = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port|sleepDelay] must be an integer");
            System.exit(2);
        }
        try {
            serv = new ServerSocket(port);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

        while (true) {
            clientSocket = serv.accept();
            SockBaseServer server = new SockBaseServer(clientSocket, game, 1);
            Thread t = new Thread(server);
            t.start();
        }

    }
}
