package connection;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.util.Arrays;

import exceptions.MaxSizeBufferException;
import org.springframework.util.SerializationUtils;

public class UDP implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int PACKET_MAX_LENGTH = 2400;
    private DatagramSocket udpSocket;
    private byte[] msgBuffer;
    private int serverPort;
    private InetAddress serverIPAddress;

    public UDP(int clientPort, int serverPort, InetAddress serverIPAddress) throws SocketException{
        this.serverPort = serverPort;
        this.serverIPAddress = serverIPAddress;
        udpSocket = new DatagramSocket(clientPort);
    }
    public static byte[][] divideArray(byte[] source, int chunksize) {

        byte[][] ret = new byte[(int)Math.ceil(source.length / (double)chunksize)][chunksize];

        int start = 0;

        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(source,start, start + chunksize);
            start += chunksize ;
        }
        return ret;
    }

    public void send(NetPackage netPackage) throws MaxSizeBufferException,IOException {
        msgBuffer = SerializationUtils.serialize(netPackage);
        byte[][] msgBuf2 = divideArray(msgBuffer,2048);
        System.out.println(msgBuf2.length);
        for (int i = 0; i<msgBuf2.length;i++) {
            BufferPacket bp;
            if (i+1==msgBuf2.length)
                bp = new BufferPacket(msgBuf2[i],true);
            else
                bp = new BufferPacket(msgBuf2[i],false);
            byte[] msgBuf = SerializationUtils.serialize(bp);
            DatagramPacket packet = new DatagramPacket(msgBuf, msgBuf.length);
            packet.setAddress(serverIPAddress);
            packet.setPort(serverPort);
            udpSocket.send(packet);
        }
    }

    public <T> T receive() throws IOException,IllegalArgumentException{
        try{
            msgBuffer = new byte[PACKET_MAX_LENGTH];
            DatagramPacket packet = new DatagramPacket(msgBuffer, PACKET_MAX_LENGTH);
            packet.setAddress(serverIPAddress);
            packet.setPort(serverPort);
            udpSocket.setSoTimeout(5000);
            udpSocket.receive(packet);
            T response = (T) SerializationUtils.deserialize(packet.getData());
            return response;
        }
        catch (SocketTimeoutException ex){
            throw new SocketTimeoutException();
        }
        catch (IOException ex){
            throw new IOException();
        }
        catch (IllegalArgumentException ex){
            ex.printStackTrace();
            System.err.println("Object couldn't be deserialised");
           throw new IllegalArgumentException();
        }
    }
    public void close(){
        udpSocket.close();
    }
}