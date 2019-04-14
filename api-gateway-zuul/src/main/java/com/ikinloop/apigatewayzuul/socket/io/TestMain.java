package com.ikinloop.apigatewayzuul.socket.io;

import java.net.URISyntaxException;

/**
 * @author Reace
 * @createTime 31 13:28
 * @description
 */
public class TestMain {

    public static void main(String[] args) throws URISyntaxException {
        SocketIo.getSocket(args);
        SocketIo socketIo = new SocketIo();
        socketIo.sendAnObject();
    }
}
