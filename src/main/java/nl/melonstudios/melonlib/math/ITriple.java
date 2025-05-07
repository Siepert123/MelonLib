package nl.melonstudios.melonlib.math;

public interface ITriple<X, Y, Z> {
    X getValue1();
    void setValue1(X value);

    Y getValue2();
    void setValue2(Y value);

    Z getValue3();
    void setValue3(Z value);

    ITriple<X, Y, Z> copy();
}
