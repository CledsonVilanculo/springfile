package com.cledson.springfile;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
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

        frame = new JFrame("SpringFile Host");
        frame.setSize(400, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(escuro);
        frame.setIconImage(new ImageIcon(App.class.getResource("/static/icon.png")).getImage());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FileManager.deleteTempFiles();
                System.exit(0);
            }
        });

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

    /**
     * @return Devolve o IP do computador em <code>String</code>, caso dê <code>UnknownHostException</code> o retorno será <code>null</code>
     */
    public static String getIp() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException a) {
            System.out.println("Erro ao obtre seu ip local");
            return null;
        }
    }

    /**
     * Chama a função para iniciar o servidor do SpringBoot, e depois troca a infomacao no JFrame, mostrando o IP
     * @param a os argumentos (<code>args</code>)
     */
    public static void startServer(String[] a) {
        SpringfileApplication.startServer(a);
        frase.setText("<html><strong><span style=\"font-size: 30pt\">SpringFile</span><br>Acesse</strong>: http://localhost:8080<br><strong>Nos outros dispositivos acesse</strong>: " + getIp() + ":8080<html>");

        frame.remove(startServer);
        frame.repaint();
        frame.revalidate();
        openLocalSite();
    }

    /**
     * Abre o localhost automaticamente assim que o SpringBoot é iniciado
     */
    public static void openLocalSite() {
        try {
            URI url = new URI("http://localhost:8080");
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(url);
        } catch (Exception a) {
            System.out.println("Erro ao abrir o site automaticamente\n" + a);
        }
    }
}