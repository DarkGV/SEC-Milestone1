package pt.ulisboa.tecnico.milestone1.pooler;

import java.lang.reflect.*;
import java.lang.Thread;
import java.lang.InterruptedException;

import java.util.ArrayList;

import java.net.Socket;

// Java exception
import java.lang.reflect.InvocationTargetException;
import java.lang.ClassNotFoundException;
import java.lang.NoSuchMethodException;
import java.lang.IllegalAccessException;
import java.lang.InstantiationException;

public class Pooler {
    int instantiatedWorkers = 10;
    Class<?> workerClass;
    ArrayList<Thread> workers;

    public Pooler(String workerClassName) throws ClassNotFoundException {
        workerClass = Class.forName(workerClassName);
        workers = new ArrayList<>();
    }

    public void startWorkers() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        startWorkers(0);
    }

    public void insertClient(Socket client) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?>[] addToWaitingListParameters = new Class<?>[1];
        addToWaitingListParameters[0] = Socket.class;
        Method addToWaitingList = workerClass.getMethod("addToWaitingList", addToWaitingListParameters);

        addToWaitingList.invoke(null, client); // invoke the class
    }

    public int getNumberOfWorkers() {
        return instantiatedWorkers;
    }

    public void spinUp() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Thread worker = new Thread((Worker)workerClass.getDeclaredConstructor().newInstance());
        worker.start();
        workers.add(worker);
        instantiatedWorkers++;
    }

    public void spinDown() {
        workers.remove(--instantiatedWorkers);
    }

    // Private code
    private void startWorkers(int nmrWorkers) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        if(nmrWorkers == instantiatedWorkers) return;

        try {
            Thread worker = new Thread((Worker)workerClass.getDeclaredConstructor().newInstance());
            worker.start(); // before saving it to the list of threads, try start it
            workers.add(worker);
        } catch(NoSuchMethodException noSuchMethodExceptionHandler) {
            System.out.println("Method `main_routine` does not exist in class " + workerClass.getName()
                    + ".\nDoes this class extends class worker?"); // Provide some advice
            return; // and stop executing
        }

        startWorkers(nmrWorkers+1);
    }

    private void gracefullyStopThread(Thread worker) {
        try {
            worker.interrupt();
            worker.join();
        } catch(InterruptedException interrupted) {
            System.out.println("Gracefully stopped the thread");
        }
    }
}
