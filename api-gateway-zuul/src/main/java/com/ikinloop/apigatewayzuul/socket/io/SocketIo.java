package com.ikinloop.apigatewayzuul.socket.io;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * @author Reace
 * @createTime 31 12:29
 * @description
 */
public class SocketIo{

    protected static Class<? extends Socket> socketClass = Socket.class;

    //全局唯一
    public static volatile Socket socket= null;

    //threadlocal 线程隔离
    public static final ThreadLocal<? extends Socket> threadLocal = new ThreadLocal<Socket>(){
        protected Socket initialValue() {
            try {
                return (Socket) SocketIo.socketClass.newInstance();
            } catch (Throwable var) {
                throw new RuntimeException(var);
            }
        }
    };
    public static Socket getCurrentSocket() {
        if (socket != null) {
            return socket;
        } else {
            Socket socket = (Socket)threadLocal.get();
            return socket;
        }
    }

    public static void setSocketClass(Class<? extends Socket> clazz) {
        socketClass = clazz;
    }

    public static void getSocket(String[] args) throws URISyntaxException {
        socket = IO.socket("http://localhost");
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                socket.emit("foo","hi");
                socket.disconnect();
            }
        }).on("event", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {

            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {

            }
        });

        socket.connect();

        /*JSONObject obj = new JSONObject();
        obj.put("hello","server");
        obj.put("binary",new Byte[42]);
        socket.emit("foo",obj);

        //receiving an object
        socket.on("foo", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                JSONObject receive = (JSONObject) objects[0];
            }
        });*/
    }

    public void sendAnObject(){
        //send message
        JSONObject obj = new JSONObject();
        obj.put("hello","server");
        obj.put("binary",new Byte[42]);
        socket.emit("foo",obj);

        //receiving an object
        socket.on("foo", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                JSONObject receive = (JSONObject) objects[0];
            }
        });
    }


}
