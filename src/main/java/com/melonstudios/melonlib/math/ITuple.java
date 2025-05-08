package com.melonstudios.melonlib.math;

public interface ITuple<T1, T2> {
    T1 getValue1();
    void setValue1(T1 value);

    T2 getValue2();
    void setValue2(T2 value2);

    ITuple<T1, T2> copy();
}
