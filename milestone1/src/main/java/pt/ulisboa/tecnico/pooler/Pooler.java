package pt.ulisboa.tecnico.pooler;

import java.lang.reflect.*;
import java.lang.Thread;
import java.lang.InterruptedException;

import java.util.ArrayList;

import java.net.Socket;

import java.lang.NoSuchMethodException;

public class Pooler {
    int instantiatedWorkers = 10;
    Class workerClass;
    ArrayList<Thread> workers;

    public Pooler(String workerClassName) {
        workerClass = Class.forName(workerClassName);
        workers = new ArrayList<>();
    }

    public void start_workers() {
        start_workers(0);
    }

    public void insertClient(Socket client) {
        Class[] add_to_waiting_list_parameters = new Class[1];
        add_to_waiting_list_parameters[0] = Socket.class;
        Method add_to_waiting_list = workerClass.getMethod("add_to_waiting_list", add_to_waiting_list_parameters);

        add_to_waiting_list.invoke(null, client); // invoke the class
    }

    public int number_of_workers() {
        return instantiatedWorkers;
    }

    public void spinUp() {
        Thread worker = new Thread(workerClass.newInstance());
        worker.start();
        workers.add(worker);
        instantiatedWorkers++;
    }

    public void spinDown() {
        workers.remove(--instantiatedWorkers);
    }

    // Private code
    private void start_workers(int nmrWorkers) {
        if(nmrWorkers == instantiatedWorkers) return;

        try {
            Thread worker = new Thread(workerClass.newInstance());
            worker.start(); // before saving it to the list of threads, try start it
            workers.add(worker);
        } catch(NoSuchMethodException noSuchMethodExceptionHandler) {
            System.out.println("Method `main_routine` does not exist in class " + workerClass.getName()
                                + ".\nDoes this class extends class worker?"); // Provide some advice
            return; // and stop executing
        }

        start_workers(nmrWorkers+1);
    }

    private void gracefullyStopThread(Thread worker) {
        worker.interrupt();
        worker.join();
    }
}
