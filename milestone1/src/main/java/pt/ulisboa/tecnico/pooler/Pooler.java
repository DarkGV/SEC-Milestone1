package pt.ulisboa.tecnico.pooler;

import java.lang.reflect.Class;
import java.lang.reflect.Method;

import java.lang.NoSuchMethodException;

public class Pooler {
    Class workerClass;

    public Pooler(String workerClass) {
        workerClass = Class.forName(this.workerClass);
    }

    public void start_workers(int nmrWorkers) {
        if(nmrWorker == 0) return;

        try {
            Method main_routine = workerClass.getMethod("main_routine");
            main_routine.invoke(workerClass);
        } catch(NoSuchMethodException noSuchMethodExceptionHandler) {
            System.out.println("Method `main_routine` does not exist in class " + workerClass.getName())
                                + ".\nDoes this class extends method worker?"); // Provide some advice
            return; // and stop executing
        }

        start_workers(nmrWorker-1);

    }

    public void get_worker() {

    }

    public void retrieve_worker() {

    }

    public void spinUp() {

    }

    public void spinDown() {

    }
}