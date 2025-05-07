package nl.melonstudios.melonlib.math;

public class TupleImpl<T1, T2> implements ITuple<T1, T2> {
    private T1 value1;
    private T2 value2;

    public TupleImpl(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public T1 getValue1() {
        return this.value1;
    }
    @Override
    public void setValue1(T1 value) {
        this.value1 = value;
    }

    @Override
    public T2 getValue2() {
        return this.value2;
    }
    @Override
    public void setValue2(T2 value2) {
        this.value2 = value2;
    }

    @Override
    public TupleImpl<T1, T2> copy() {
        return new TupleImpl<>(this.value1, this.value2);
    }

    public static <T1, T2> TupleImpl<T1, T2> of(T1 value1, T2 value2) {
        return new TupleImpl<>(value1, value2);
    }
}
