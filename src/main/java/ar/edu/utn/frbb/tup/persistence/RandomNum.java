package ar.edu.utn.frbb.tup.persistence;

import java.util.Random;

public class RandomNum {
    private static RandomNum instance;
    private Random random;

    private RandomNum() {
        random = new Random();
    }

    public static synchronized RandomNum getInstance() {
        if (instance == null) {
            instance = new RandomNum();
        }
        return instance;
    }

    public int generateRandomNumber (int numMaximo) {
        return random.nextInt(numMaximo);
    }
}
