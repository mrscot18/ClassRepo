package examples.grpcClient;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerMethodDefinition;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import service.*;
import service.userGrpc.userImplBase;

import java.util.Stack;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import buffers.RequestProtos.Request;
import buffers.RequestProtos.Request.RequestType;
import buffers.ResponseProtos.Response;

public class Song extends userImplBase {

    @Override
    public void login(LogReq req, StreamObserver<ApiRes> resObserver) {
        System.out.println("login");
        String username = req.getUse();
        String password = req.getPas();

        while (true) {
            ApiRes.Builder response = ApiRes.newBuilder();
            if (username.equals("Dancing Queen!!")) {
                System.out.println("Yay! Youre right!");
                response.setResCode(0).setResponseMsg("Yess! Your right!");

            } else {
                response.setResCode(0).setResponseMsg("Ill give you a hint...ABBA! Press 3 to try again");
            }

            resObserver.onNext(response.build());
            resObserver.onCompleted();
        }

    }
}