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
            try {
                this.detener(startTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            if (isPrime(i)) this.addPrime(i);
        }
    }

    public void addPrime(int primeNumber) {
        synchronized (sharedPrimes) {
            sharedPrimes.add(primeNumber);
            System.out.println(Thread.currentThread().getName() + " :" + primeNumber);
        }
    }

    public void detener(long startTime) throws InterruptedException {
        synchronized (sharedPrimes) {
            while (!stop && System.currentTimeMillis() - startTime >= 5000) {
                sharedPrimes.wait();
            }
        }
    }

    public void resumeExecution() {
        this.stop = true;
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
