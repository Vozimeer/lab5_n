package client;

import common.request.Request;
import common.response.Response;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class NetworkClient {
    private InetSocketAddress serverAddress;
    private DatagramChannel channel;
    private int timeoutMs;

    public NetworkClient(String host, int port, int timeoutMs) throws IOException {
        this.serverAddress = new InetSocketAddress(host, port);
        this.timeoutMs = timeoutMs;
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(false);
    }

    public Response sendRequest(Request request) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(request);
        oos.flush();
        byte[] data = bos.toByteArray();

        ByteBuffer buf = ByteBuffer.wrap(data);
        channel.send(buf, serverAddress);

        ByteBuffer receiveBuf = ByteBuffer.allocate(65536);
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < timeoutMs) {
            SocketAddress addr = channel.receive(receiveBuf);
            if (addr != null) {
                receiveBuf.flip();
                byte[] responseData = new byte[receiveBuf.remaining()];
                receiveBuf.get(responseData);

                ByteArrayInputStream bis = new ByteArrayInputStream(responseData);
                ObjectInputStream ois = new ObjectInputStream(bis);
                try {
                    return (Response) ois.readObject();
                } catch (ClassNotFoundException e) {
                    throw new IOException("Неверный формат ответа", e);
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        throw new SocketTimeoutException("Сервер недоступен");
    }

    public void close() throws IOException {
        channel.close();
    }
}