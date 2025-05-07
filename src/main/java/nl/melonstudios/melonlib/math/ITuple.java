package nl.melonstudios.melonlib.math;

public interface ITuple<X, Y> {
    X getValue1();
    void setValue1(X value);

    Y getValue2();
    void setValue2(Y value2);

    ITuple<X, Y> copy();
}
