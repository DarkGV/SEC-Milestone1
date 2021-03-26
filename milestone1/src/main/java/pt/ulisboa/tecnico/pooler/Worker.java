package pooler;

import java.util.Queue;
import java.util.LinkedList;
import java.net.Socket;

public abstract class Worker extends Thread {
    Queue<Socket> waitingQueue;

    abstract void main_routine();

    public Worker() {
        waitingList = new LinkedList<>();
    }

    public void wait(Socket Client) {
        waitingList.add(Client);
    }
}