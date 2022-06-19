package server;

import java.util.*;
import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

public class PlayerL {

    class Node {
        String name;
        int wins;
        int logins;
    }

    ArrayList<Node> list;

    public PlayerL () {
        list = new ArrayList<>();
    }

    public void insertPlayer (String name) {
        Node temp = null;
        for (Node n : list) {
            if (n.name.equals(name)) {
                temp = n;
            }
        }
        if (temp == null) {
            temp = new Node();
            temp.name = name;
            temp.wins = 0;
            temp.logins = 1;
            list.add(temp);
        } else {
            temp.logins++;
        }
    }

    public void incrementWin (String name) {
        Node temp = null;
        for (Node n : list) {
            if (n.name.equals(name)) {
                n.wins++;
            }
        }
    }

    public synchronized Response getPlayerL () {
        Response.Builder response = Response.newBuilder()
                .setResponseType(Response.ResponseType.LEADER);

        for (Node n : list) {
            Entry e = Entry.newBuilder()
                    .setName(n.name)
                    .setWins(n.wins)
                    .setLogins(n.logins)
                    .build();
            response.addLeader(e);
        }
        return response.build();
    }

}