package com.glab.black.horse.bitmap.utils;

import org.roaringbitmap.RoaringBitmap;

import java.io.*;

public class BitmapUtils {

    public static byte[] serialize(RoaringBitmap rb) {
        rb.runOptimize();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bos)) {
            rb.serialize(dos);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    public static RoaringBitmap deserialize(byte[] arr) {
        RoaringBitmap rb = new RoaringBitmap();
        try (ByteArrayInputStream bos = new ByteArrayInputStream(arr);
             DataInputStream dis = new DataInputStream(bos)) {
            rb.deserialize(dis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rb;
    }


}
