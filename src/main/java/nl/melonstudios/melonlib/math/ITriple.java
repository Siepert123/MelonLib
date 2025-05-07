package nl.melonstudios.melonlib.math;

public interface ITriple<T1, T2, T3> {
    T1 getValue1();
    void setValue1(T1 value);

    T2 getValue2();
    void setValue2(T2 value);

    T3 getValue3();
    void setValue3(T3 value);

    ITriple<T1, T2, T3> copy();
}
