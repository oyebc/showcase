package com.fivehundredpx.showcase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static byte[] getByteArray(InputStream inStream)
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte [] dataChunk = new byte[1024];
        try {
            while ((nRead = inStream.read(dataChunk, 0, dataChunk.length)) != -1)
            {
                buffer.write(dataChunk, 0, nRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toByteArray();
    }
}
