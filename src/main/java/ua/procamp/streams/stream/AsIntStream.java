package ua.procamp.streams.stream;

import ua.procamp.streams.function.IntBinaryOperator;
import ua.procamp.streams.function.IntConsumer;
import ua.procamp.streams.function.IntPredicate;
import ua.procamp.streams.function.IntToIntStreamFunction;
import ua.procamp.streams.function.IntUnaryOperator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AsIntStream implements IntStream {

    private static List<Integer> list;

    private IntPredicate predicate;

    private IntUnaryOperator mapper;

    private IntToIntStreamFunction func;

    private AsIntStream() {
    }

    public static IntStream of(int... values) {
        of(values);
        list = asList(values);
        return new AsIntStream();
    }

    private <T> Dataset<T> of(Collection<T> collection) {
        return new Dataset<>(generatorContext -> {
            for (T item : collection) {
                generatorContext.emit(item);
            }
        });
    }

    private interface Generator<T> {
        void generate(GeneratorContext<T> context);
    }

    private interface GeneratorContext<T> {
        void emit(T value);
    }

    private class Dataset<T> {

        private final Generator<T> generator;

        private Dataset(Generator<T> generator) {
            this.generator = generator;
        }
    }

    @Override
    public Double average() {
        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return ((double) sum()) / count();
    }

    @Override
    public Integer max() {
        return reduce(list.get(0), (x, y) -> x > y ? x : y);
    }

    @Override
    public Integer min() {
        return reduce(list.get(0), (x, y) -> x < y ? x : y);
    }

    @Override
    public long count() {
        return list.size();
    }

    @Override
    public Integer sum() {
        return reduce(0, (x, y) -> x + y);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        this.predicate = predicate;
        return this;
    }

    @Override
    public void forEach(IntConsumer action) {
        if (predicate != null) {
            forEach(predicate, action);
        }

        if (mapper != null) {
            forEach(mapper, action);
        }

        if (func != null) {
            forEach(func, action);
        }

        if (predicate == null && mapper == null && func == null) {
            for (int element : list) {
                action.accept(element);
            }
        }
    }

    private void forEach(IntPredicate predicate, IntConsumer action) {
        for (int element : list) {
            if (predicate.test(element)) {
                action.accept(element);
            }
        }
    }

    private void forEach(IntUnaryOperator mapper, IntConsumer action) {
        for (int element : list) {
            action.accept(mapper.apply(element));
        }
    }

    private void forEach(IntToIntStreamFunction func, IntConsumer action) {
        List<IntStream> streams = new ArrayList<>();
        for (int element : list) {
            streams.add(func.applyAsIntStream(element));
        }
        list.clear();
        for (IntStream stream : streams) {
            list.addAll(asList(stream.toArray()));
        }
        for (int element : list) {
            action.accept(element);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        this.mapper = mapper;
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        this.func = func;
        return this;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        AtomicInteger result = new AtomicInteger(identity);
        forEach(value -> result.set(op.apply(result.get(), value)));
        return result.get();
    }

    @Override
    public int[] toArray() {
        List<Integer> newList = new ArrayList<>();
        forEach(newList::add);
        int[] array = new int[newList.size()];
        for (int i = 0; i < newList.size(); i++) {
            array[i] = newList.get(i);
        }
        return array;
    }

    private static List<Integer> asList(int... values) {
        List<Integer> list = new ArrayList<>();
        for (int value : values) {
            list.add(value);
        }
        return list;
    }
}
