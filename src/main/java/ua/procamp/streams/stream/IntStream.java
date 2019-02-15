package ua.procamp.streams.stream;

import ua.procamp.streams.function.IntBinaryOperator;
import ua.procamp.streams.function.IntConsumer;
import ua.procamp.streams.function.IntPredicate;
import ua.procamp.streams.function.IntToIntStreamFunction;
import ua.procamp.streams.function.IntUnaryOperator;

public interface IntStream {

    Double average();

    Integer max();

    Integer min();
    
    IntStream flatMap(IntToIntStreamFunction func);

    long count();

    IntStream filter(IntPredicate predicate);

    void forEach(IntConsumer action);

    IntStream map(IntUnaryOperator mapper);

    int reduce(int identity, IntBinaryOperator op);

    Integer sum();

    int[] toArray();
}
