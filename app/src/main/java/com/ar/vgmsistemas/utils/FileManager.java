package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.entity.Preferencia;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileManager {

    private static final int BUFFER_SIZE = 1024;

    public static boolean isSdPresent() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public void saveFile(InputStream is, String path) throws Exception {
        OutputStream out = new FileOutputStream(new File(path));
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = is.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        is.close();
        out.flush();
        out.close();
    }

    public void copyFiles(String source, String target) throws Exception {
        // Getting file channels
        FileChannel in = new FileInputStream(source).getChannel();
        FileChannel out = new FileOutputStream(target).getChannel();

        // JavaVM does its best to do this as native I/O operations.
        in.transferTo(0, in.size(), out);

        // Closing file channels will close corresponding stream objects as well.
        out.close();
        in.close();
    }


    public static void fastChannelCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            // prepare the buffer to be drained
            buffer.flip();
            // write to the channel, may block
            dest.write(buffer);
            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear()
            buffer.compact();
        }
        // EOF will leave buffer in fill state
        buffer.flip();
        // make sure the buffer is fully drained.
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }

        src.close();
        dest.close();
    }

    public static void unZip(String pZipFile, String pFile) throws Exception {
        try (ZipFile zipFile = new ZipFile(pZipFile)) {
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();
                System.out.println("Unzipping: " + zipEntry.getName());
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                int size;
                byte[] buffer = new byte[2048];
                //FileOutputStream fos = new FileOutputStream(zipEntry.getName());
                FileOutputStream fos = new FileOutputStream(pFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);
                while ((size = bis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, size);
                }
                bos.flush();
                bos.close();
                fos.close();
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < sourceLocation.listFiles().length; i++) {

                copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);

            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from in stream to out stream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }

    }

    public static void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();

    }
}
