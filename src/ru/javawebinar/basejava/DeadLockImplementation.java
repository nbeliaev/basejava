package ru.javawebinar.basejava;

public class DeadLockImplementation {
    private static int COMMON_ID;
    private final int id = COMMON_ID;

    public static void main(String[] args) {
        final DeadLockImplementation a = new DeadLockImplementation();
        final DeadLockImplementation b = new DeadLockImplementation();

        new Thread(() -> {
            try {
                doJob(a, b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                doJob(b, a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private DeadLockImplementation() {
        COMMON_ID++;
    }

    static {
        COMMON_ID++;
    }

    private static void doJob(DeadLockImplementation a, DeadLockImplementation b) throws InterruptedException {
        System.out.println("try to lock " + a.id);
        synchronized (a) {
            System.out.println("lock is successful for " + a.id);
            System.out.println("try to lock " + b.id);
            synchronized (b) {
                System.out.println("lock is successful for " + b.id);
                System.out.println("job is successful");
            }
        }
    }
}
