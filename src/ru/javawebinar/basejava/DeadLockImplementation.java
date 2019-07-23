package ru.javawebinar.basejava;

public class DeadLockImplementation {

    public static void main(String[] args) {
        final DeadLockImplementation a = new DeadLockImplementation();
        final DeadLockImplementation b = new DeadLockImplementation();
        new Thread(() -> doJob(a, b)).start();
        new Thread(() -> doJob(b, a)).start();
    }

    private static void doJob(DeadLockImplementation a, DeadLockImplementation b) {
        final String threadName = Thread.currentThread().getName();
        System.out.println("try to lock " + threadName);
        synchronized (a) {
            System.out.println("lock is successful for " + threadName);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("try to lock " + threadName);
            synchronized (b) {
                System.out.println("lock is successful for " + threadName);
                System.out.println("job is done");
            }
        }
    }
}
