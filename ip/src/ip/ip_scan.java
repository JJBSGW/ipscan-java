package ip;

import java.net.*;
import java.io.*;

public class ip_scan {

    public static void main(String[] args) {
        String ipAddress = "127.0.0.1"; // 目的IP地址
        int startPort = 1; // 起始端口
        int endPort = 65535; // 结束端口

        for (int port = startPort; port <= endPort; port++) {
            Thread t = new Thread(new PortScannerRunnable(ipAddress, port));
            t.start();
        }
    }

    // Runnable接口用于端口扫描
    static class PortScannerRunnable implements Runnable {
        private String ipAddress;
        private int port;

        public PortScannerRunnable(String ipAddress, int port) {
            this.ipAddress = ipAddress;
            this.port = port;
        }

        public void run() {
            try {
                // 扫描TCP连接
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ipAddress, port), 1000);
                socket.close();
                System.out.println("TCP Port " + port + " is open");

                // 扫描UDP连接
                DatagramSocket datagramSocket = new DatagramSocket();
                datagramSocket.connect(InetAddress.getByName(ipAddress), port);
                System.out.println("UDP Port " + port + " is open");
            } catch (IOException e) {
                // 如果连接失败，说明端口不可达
                // System.out.println("Port " + port + " is closed");
            }
        }
    }
}