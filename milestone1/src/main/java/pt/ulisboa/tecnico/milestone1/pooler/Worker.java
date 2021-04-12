package pt.ulisboa.tecnico.milestone1.pooler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.net.Socket;

public abstract class Worker extends Thread {
    public static BlockingQueue<Socket> waitingQueue;

    public void run() {}

    public Worker() {
        waitingQueue = new ArrayBlockingQueue<>(10);
    }

    public void addToWaitingList(Socket Client) {
        waitingQueue.add(Client);
    }
}