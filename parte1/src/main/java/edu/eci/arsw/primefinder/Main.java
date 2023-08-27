package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int threadsNumber = 3;
        int lastNumber = 30000000;
        List<PrimeFinderThread> threads = new ArrayList<>();
        int numberPrimesThread = lastNumber / threadsNumber;
        List<Integer> primes = new ArrayList<>();
        for (int i = 0; i <= threadsNumber - 1; i++) {
            PrimeFinderThread pft;
            if (i == threadsNumber - 1) {
                pft = new PrimeFinderThread(numberPrimesThread * i, lastNumber, primes);
            } else {
                pft = new PrimeFinderThread(numberPrimesThread * i, numberPrimesThread * (i + 1) - 1, primes);
            }
            threads.add(pft);
        }
        for (PrimeFinderThread pft : threads) {
            pft.start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.print("Numero de primos encontrados hasta el momento: " + primes.size() + "\n");
        System.out.print("Presione enter para continuar");
        (new Scanner(System.in)).nextLine();
        for (PrimeFinderThread thread : threads) thread.resumeExecution();
        synchronized (primes) {
            primes.notifyAll();
        }
    }
}