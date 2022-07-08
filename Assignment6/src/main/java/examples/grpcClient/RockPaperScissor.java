package examples.grpcClient;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerMethodDefinition;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import service.*;
import service.PlayReq.Played;
import service.RockPaperScissorsGrpc.RockPaperScissorsImplBase;
import buffers.RequestProtos.Request;
import buffers.RequestProtos.Request.RequestType;
import buffers.ResponseProtos.Response;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;

public class RockPaperScissor extends RockPaperScissorsImplBase {
    Stack<Integer> game = new Stack<Integer>();

    public RockPaperScissor() {
        super();

    }

    @Override
    public void play(PlayReq p, StreamObserver<PlayRes> pr) {
        int rand = (int) (Math.random() * 3);

        int opponentMove;
        if (rand == 0) {
            opponentMove = Played.ROCK_VALUE;
            game.add(opponentMove);
        } else if (rand == 1) {
            opponentMove = Played.PAPER_VALUE;
            game.add(opponentMove);

        } else if (rand == 2) {

            opponentMove = Played.SCISSORS_VALUE;
            game.add(opponentMove);

        }

        if (p.getPlay().equals(PlayReq.Played.SCISSORS) && game.peek().equals(2)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("Its a tie!" + game.peek());

        }  else if (p.getPlay().equals(PlayReq.Played.ROCK) && game.peek().equals(0)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("Its a tie!" + game.peek());

        }  else if (p.getPlay().equals(PlayReq.Played.PAPER) && game.peek().equals(1)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("Its a tie!" + game.peek());


        } else if (p.getPlay().equals(PlayReq.Played.ROCK) && game.peek().equals(2)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("Rock Won! " + game.peek());
        } else if (p.getPlay().equals(PlayReq.Played.SCISSORS) && game.peek().equals(0)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("Rock wins!" + game.peek());
        } else if (p.getPlay().equals(PlayReq.Played.ROCK) && game.peek().equals(1)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("paper wins!" + game.peek());

        } else if (p.getPlay().equals(PlayReq.Played.PAPER) && game.peek().equals(0)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("Paper won!" + game.peek());

        } else if (p.getPlay().equals(PlayReq.Played.SCISSORS) && game.peek().equals(1)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("Scissors Wins!" + game.peek());

        } else if (p.getPlay().equals(PlayReq.Played.PAPER) && game.peek().equals(2)) {
            System.out.println("Received from client: " + p.getPlay());
            System.out.println("Scissors wins!" + game.peek());

        }

        else {
            System.out.println("Tie?" + game.peek());
        }


        PlayRes.Builder response = PlayRes.newBuilder();

        PlayRes resp = response.build();
        pr.onNext(resp);
        pr.onCompleted();
    }

}
