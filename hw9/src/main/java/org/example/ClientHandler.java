package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            if (!authenticate()) {
                socket.close();
                return;
            }

            String message;
            while ((message = in.readLine()) != null) {
                if ("/time".equalsIgnoreCase(message)) {
                    out.println(java.time.LocalTime.now());
                } else {
                    out.println("Неизвестная команда");
                }
            }
        } catch (IOException e) {
            System.out.println("Клиент отключился");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean authenticate() throws IOException {
        out.println("Введите логин:");
        String login = in.readLine();
        out.println("Введите пароль:");
        String password = in.readLine();

        if ("user1".equals(login) && "1234".equals(password)) {
            out.println("Добро пожаловать, " + login + "!");
            return true;
        } else {
            out.println("Неверный логин или пароль. Соединение разорвано.");
            return false;
        }
    }
}
