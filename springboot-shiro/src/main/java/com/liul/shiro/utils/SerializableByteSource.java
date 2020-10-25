package com.liul.shiro.utils;

import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

public class SerializableByteSource extends SimpleByteSource implements Serializable {
    public SerializableByteSource(String source) {
        super(source);
    }

    public SerializableByteSource(ByteSource source) {
        super(source);
    }
}
