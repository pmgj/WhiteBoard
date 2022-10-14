package org.sample.whiteboardapp;

import java.io.IOException;
import java.nio.ByteBuffer;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/whiteboard", encoders = { FigureEncoder.class }, decoders = { FigureDecoder.class })
public class MyWhiteboard {

    @OnMessage
    public void broadcastFigure(Figure figure, Session session) throws IOException, EncodeException {
        for (Session peer : session.getOpenSessions()) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendObject(figure);
            }
        }
    }

    @OnMessage
    public void broadcastSnapshot(ByteBuffer data, Session session) throws IOException {
        System.out.println("broadcastBinary: " + data);
        for (Session peer : session.getOpenSessions()) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendBinary(data);
            }
        }
    }

    @OnError
    public void error(Session session, Throwable t) {
        System.out.println(t.getMessage());
    }
}
