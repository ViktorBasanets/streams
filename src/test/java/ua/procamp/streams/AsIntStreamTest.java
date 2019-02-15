package ua.procamp.streams;

import org.junit.Before;
import org.junit.Test;
import ua.procamp.streams.stream.AsIntStream;
import ua.procamp.streams.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AsIntStreamTest {

    private IntStream intStream;

    @Before
    public void init() {
        intStream = AsIntStream.of(-1, 0, 1, 11, 3, 2);
    }

    @Test
    public void testCalculateAverageValueArrayElements() {
        System.out.print("average - ");
        double expResult = 2.7;
        double actResult = intStream.average();
        assertEquals(expResult, actResult, 0.1);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testGetMaxElementOfArray() {
        System.out.print("max - ");
        int expResult = 11;
        int actResult = intStream.max();
        assertEquals(expResult, actResult);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testGetMinElementOfArray() {
        System.out.print("min - ");
        int expResult = -1;
        int actResult = intStream.min();
        assertEquals(expResult, actResult);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testCountingArrayElements() {
        System.out.print("count - ");
        long expResult = 6;
        long actResult = intStream.count();
        assertEquals(expResult, actResult);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testSummingArrayElements() {
        System.out.print("sum - ");
        long expResult = 16;
        long actResult = intStream.sum();
        assertEquals(expResult, actResult);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testFilteringArrayElements() {
        System.out.print("filter - ");
        int[] expResult = {11, 3, 2};
        int[] actResult = intStream.filter(element -> element > 1).toArray();
        assertArrayEquals(expResult, actResult);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testForEach() {
        System.out.print("forEach - ");
        StreamAppTest testForEach = new StreamAppTest();
        testForEach.init();
        testForEach.testStreamForEach();
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testMap() {
        System.out.print("map - ");
        int[] expResult = {-2, 0, 2, 22, 6, 4};
        int[] actResult = intStream
                .map(element -> element * 2)
                .toArray();
        assertArrayEquals(expResult, actResult);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testFlatMap() {
        System.out.print("flatMap - ");
        int[] expResult = {-2, -1, 0, -1, 0, 1, 0, 1, 2, 10, 11, 12, 2, 3, 4, 1, 2, 3};
        int[] actResult = intStream
                .flatMap(element -> AsIntStream.of(element - 1, element, element + 1))
                .toArray();
        assertArrayEquals(expResult, actResult);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testReducingArrayElements() {
        System.out.print("reduce - ");
        long expResult = -66;
        long actResult = intStream
                .filter(x -> x != 0)
                .reduce(1, (x, y) -> x * y);
        assertEquals(expResult, actResult);
        System.out.println("Passed the TEST!");
    }

    @Test
    public void testToArrayStreamElements() {
        System.out.print("toArray - ");
        int[] expResult = {-1, 0, 1, 11, 3, 2};
        int[] result = StreamApp.streamToArray(intStream);
        assertArrayEquals(expResult, result);
        System.out.println("Passed the TEST!");
    }
}
