package com.cn.util;

import java.util.UUID;

public class UUIDUtil {
    public static UUID getUUID(){
        return UUID.randomUUID();
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
