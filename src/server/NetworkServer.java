package server;

import common.request.Request;
import common.response.Response;
import java.io.*;
import java.net.*;

public class NetworkServer {
    private int port;
    private ServerCommandInvoker invoker;

    public NetworkServer(int port, ServerCommandInvoker invoker) {
        this.port = port;
        this.invoker = invoker;
    }

    public void start() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                byte[] buf = new byte[65536];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
                ObjectInputStream ois = new ObjectInputStream(bis);
                Request request = (Request) ois.readObject();

                System.out.println("Получена команда: " + request.getCommandName());
                Response response = invoker.execute(request);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(response);
                oos.flush();
                byte[] responseData = bos.toByteArray();

                DatagramPacket responsePacket = new DatagramPacket(
                        responseData, responseData.length,
                        packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }
}