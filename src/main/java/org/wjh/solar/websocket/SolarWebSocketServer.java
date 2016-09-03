package org.wjh.solar.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ServerEndpoint("/socket/message.do")
@EnableWebMvc
public class SolarWebSocketServer {
    private static Log logger = LogFactory.getLog(SolarWebSocketServer.class);

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        // Print the client message for testing purposes
        logger.info("Received: " + message);

        // Send the first message to the client
        session.getBasicRemote().sendText("This is the first server message");

        // Send 100 messages to the client every 2 seconds
        int sentMessages = 0;
        while (sentMessages < 10000) {
            Thread.sleep(2000);
            session.getBasicRemote().sendText("This is an intermediate server message. Count: " + sentMessages);
            sentMessages++;
        }

        // Send a final message to the client
        session.getBasicRemote().sendText("This is the last server message");
    }

    @OnOpen
    public void onOpen() {
        logger.info("Client connected");
    }

    @OnClose
    public void onClose() {
        logger.info("Connection closed");
    }
}
