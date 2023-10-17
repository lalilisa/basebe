package com.example.chatapplication.socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.Polling;
import io.socket.engineio.client.transports.WebSocket;

import java.net.URISyntaxException;

public class ClientTest {
    public static void main(String[] args) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.transports = new String[]{WebSocket.NAME, Polling.NAME};
        options.reconnectionAttempts = 2;
        options.reconnectionDelay = 1000;
        options.timeout = 500;

        String host="http://192.168.1.29:9090";
        String namespace="/chat";
        String query="?token=abc123";
        //Connect to the specified uri
        final Socket socket = IO.socket(host+namespace+query, options);

        //listening event connect
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.send("hello");
                System.out.println("connected");
            }
        });

        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("connection closed");
            }
        });


        socket.connect();

    }

}
