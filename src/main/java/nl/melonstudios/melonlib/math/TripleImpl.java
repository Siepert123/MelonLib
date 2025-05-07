package nl.melonstudios.melonlib.math;

public class TripleImpl<T1, T2, T3> implements ITriple<T1, T2, T3> {
    private T1 value1;
    private T2 value2;
    private T3 value3;

    public TripleImpl(T1 value1, T2 value2, T3 value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
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
    public void setValue2(T2 value) {
        this.value2 = value;
    }

    @Override
    public T3 getValue3() {
        return this.value3;
    }
    @Override
    public void setValue3(T3 value) {
        this.value3 = value;
    }

    @Override
    public TripleImpl<T1, T2, T3> copy() {
        return new TripleImpl<>(this.value1, this.value2, this.value3);
    }
}
