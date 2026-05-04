package com.cledson.springfile;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class App {
    private static Color escuro = new Color(35, 37, 37);
    private static Color ciano = new Color(0, 228, 236);
    private static JButton startServer;
    private static JLabel frase;
    private static JFrame frame;

    public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false"); // desativa o dev-tools

        //System.out.println("======== SpringFile ========\nAcesse: " + getIp() + ":8080");
        frame = new JFrame("SpringFile Host");
        frame.setSize(400, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(escuro);

        frase = new JLabel("<html><strong><span style=\"font-size: 30pt\">SpringFile</span><br><html>");
        frase.setFont(new Font("arial", Font.PLAIN, 14));
        frase.setForeground(ciano);
        frase.setBounds(10, 5, frame.getWidth() - 30, 80);

        startServer = new JButton("Iniciar servidor");
        startServer.setFont(new Font("arial", Font.BOLD, 14));
        startServer.setBackground(ciano);
        startServer.setForeground(escuro);
        startServer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startServer.setFocusPainted(false);
        startServer.setBounds(10, frase.getY() + frase.getHeight() + 10, 200, 30);
        startServer.addActionListener(_ -> startServer(args));

        frame.add(startServer);
        frame.add(frase);
        frame.setVisible(true);
    }

    public static String getIp() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException a) {
            System.out.println("Erro ao obtre seu ip local");
            return null;
        }
    }

    public static void startServer(String[] a) {
        SpringfileApplication.startServer(a);
        frase.setText("<html><strong><span style=\"font-size: 30pt\">SpringFile</span><br>Acesse</strong>: http://localhost:8080<br><strong>Nos outros dispositivos acesse</strong>: " + getIp() + ":8080<html>");

        frame.remove(startServer);
        frame.repaint();
        frame.revalidate();
    }
}