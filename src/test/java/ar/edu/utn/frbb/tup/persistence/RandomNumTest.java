package ar.edu.utn.frbb.tup.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomNumTest {
    @Test
    public void testRandomNumber() {
        int numMaximo = 2;
        int numeroRandom = RandomNum.getInstance().generateRandomNumber(numMaximo);
        assertEquals(numeroRandom < numMaximo && numeroRandom >= 0, true);
    }
}
