package examples.grpcClient;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerMethodDefinition;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import service.*;
import service.HometownsGrpc.HometownsImplBase;
import service.PlayReq.Played;
import service.RockPaperScissorsGrpc.RockPaperScissorsImplBase;

import java.util.Stack;
import buffers.RequestProtos.Request;
import buffers.RequestProtos.Request.RequestType;
import buffers.ResponseProtos.Response;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Hometowns extends HometownsImplBase {

    ArrayList<Integer> hometo = new ArrayList<Integer>();

    public String city;
    public String region;

    public String name;

    public Hometowns() {
        super();

    }

    public void write(HometownsWriteRequest t, HometownsWriteResponse y) {
        Scanner aq = new Scanner(System.in);
        int choice = aq.nextInt();

        if (t.hasHometown()) {
            System.out.println("Yes!");


            int opponentMove;
            if (choice == 0) {
                opponentMove = name.length();
                hometo.add(opponentMove);
                System.out.println("Opponent move: " + hometo.size());
            }

        }

    }

}
