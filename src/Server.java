import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public Server() throws SocketException {
        socket = new DatagramSocket(5001);

    }

    public void run()
    {
        for(int i=0;i< buf.length;i++)
        {
            buf[i] = ' ';
        }
        running = true;

        while (running)
        {
            System.out.println("Start");
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String receive = new String(packet.getData(), 0, packet.getLength());
            InetAddress address = packet.getAddress();

            if(receive.equals("end"))
            {
                running = false;
                continue;
            }
            else
            {
                System.out.println(receive);
            }
            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        socket.close();
    }

}