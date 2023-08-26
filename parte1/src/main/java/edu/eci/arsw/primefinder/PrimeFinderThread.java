package edu.eci.arsw.primefinder;

import java.util.List;

public class PrimeFinderThread extends Thread {

    private final List<Integer> sharedPrimes;
    int a;
    int b;
    boolean stop;

    public PrimeFinderThread(int a, int b, List<Integer> sharedPrimes) {
        super();
        this.a = a;
        this.b = b;
        this.stop = false;
        this.sharedPrimes = sharedPrimes;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i = a; i <= b; i++) {
            synchronized (sharedPrimes) {
                if (isPrime(i)) {
                    sharedPrimes.add(i);
                    System.out.println(Thread.currentThread().getName() + " :" + i);
                }
                if (!stop && System.currentTimeMillis() - startTime > 5000) {
                    stop = true;
                    try {
                        sharedPrimes.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    boolean isPrime(int n) {
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}
