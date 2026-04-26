package com.melonstudios.melonlib.network;

import io.netty.buffer.ByteBuf;

import java.util.Objects;

public class TrackedByteBuf {
    private final ByteBuf buf;
    private int written = 0;

    public TrackedByteBuf(ByteBuf buf) {
        this.buf = Objects.requireNonNull(buf, "buf");
    }

    public ByteBuf internal() {
        return this.buf;
    }
    public int written() {
        return this.written;
    }

    public void append(int bytes) {
        this.written += bytes;
    }

    public TrackedByteBuf writeBoolean(boolean b) {
        this.buf.writeBoolean(b);
        this.written += Byte.BYTES;
        return this;
    }
    public TrackedByteBuf writeByte(int i) {
        this.buf.writeByte(i);
        this.written += Byte.BYTES;
        return this;
    }
    public TrackedByteBuf writeShort(int i) {
        this.buf.writeShort(i);
        this.written += Short.BYTES;
        return this;
    }
    public TrackedByteBuf writeMedium(int i) {
        this.buf.writeMedium(i);
        this.written += 3;
        return this;
    }
    public TrackedByteBuf writeInt(int i) {
        this.buf.writeInt(i);
        this.written += Integer.BYTES;
        return this;
    }
    public TrackedByteBuf writeLong(long l) {
        this.buf.writeLong(l);
        this.written += Long.BYTES;
        return this;
    }
    public TrackedByteBuf writeChar(int i) {
        this.buf.writeChar(i);
        this.written += Character.BYTES;
        return this;
    }
    public TrackedByteBuf writeFloat(float v) {
        this.buf.writeFloat(v);
        this.written += Float.BYTES;
        return this;
    }
    public TrackedByteBuf writeDouble(double v) {
        this.buf.writeDouble(v);
        this.written += Double.BYTES;
        return this;
    }
    public TrackedByteBuf writeBytes(ByteBuf bytes) {
        this.buf.writeBytes(bytes);
        this.written += bytes.writerIndex();
        return this;
    }
    public TrackedByteBuf writeBytes(ByteBuf bytes, int off) {
        this.buf.writeBytes(bytes, off);
        this.written += bytes.writerIndex() - off;
        return this;
    }
    public TrackedByteBuf writeBytes(ByteBuf bytes, int off, int len) {
        this.buf.writeBytes(bytes, off, len);
        this.written += len;
        return this;
    }
    public TrackedByteBuf writeBytes(byte[] bytes) {
        this.buf.writeBytes(bytes);
        this.written += bytes.length;
        return this;
    }
    public TrackedByteBuf writeBytes(byte[] bytes, int off, int len) {
        this.buf.writeBytes(bytes, off, len);
        this.written += len;
        return this;
    }
}
