package pooler;

import java.util.concurrent.BlockingQueue;
import java.util.Collection;

import java.lang.InterruptedException;

import java.net.Socket;

public abstract class Worker extends Thread {
    public static BlockingQueue<Socket> waitingQueue;

    public Worker() {
        waitingQueue = new BlockingQueue<>();
    }

    public void add_to_waiting_list(Socket Client) {
        waitingQueue.add(Client);
    }
}
