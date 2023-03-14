package org.imaginativeworld.simplemvvm.utils;

// WARNING: Not secure. Only use this, if you know what you are doing.
public class EncryptionUtils {
    private static final String PASSWORD = ")@5D$k#*!";
    private static final byte[] ENCRYPTION_KEYS = PASSWORD.getBytes();

    public static byte[] encrypt(byte[] bArr) {
        try {
            int length = ENCRYPTION_KEYS.length;
            int length2 = bArr.length;
            for (int i = 0; i < length2; i++) {
                bArr[i] = (byte) (ENCRYPTION_KEYS[i % length] ^ bArr[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bArr;
    }

    public static byte[] decrypt(byte[] bArr) {
        try {
            int length = ENCRYPTION_KEYS.length;
            int length2 = bArr.length;
            for (int i = 0; i < length2; i++) {
                bArr[i] = (byte) (ENCRYPTION_KEYS[i % length] ^ bArr[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bArr;
    }
}