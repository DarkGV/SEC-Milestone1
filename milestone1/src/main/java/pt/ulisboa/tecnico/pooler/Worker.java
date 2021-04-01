package pt.ulisboa.tecnico.pooler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collection;

import java.lang.InterruptedException;

import java.net.Socket;

public abstract class Worker extends Thread {
    public static BlockingQueue<Socket> waitingQueue;

    public void run() {}

    public Worker() {
        waitingQueue = new ArrayBlockingQueue<Socket>(10);
    }

    public void add_to_waiting_list(Socket Client) {
        waitingQueue.add(Client);
    }
}





