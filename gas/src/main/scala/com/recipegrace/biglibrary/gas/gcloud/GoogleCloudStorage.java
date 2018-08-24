package com.recipegrace.biglibrary.gas.gcloud;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.Tuple;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.CopyWriter;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.ComposeRequest;
import com.google.cloud.storage.Storage.CopyRequest;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.common.collect.ImmutableMap;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.google.api.gax.paging.Page;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GoogleCloudStorage {


    private static StorageOptions.Builder optionsBuilder = StorageOptions.newBuilder();
    private static Storage storage = optionsBuilder.build().getService();
    public static Storage getStorage (){
        return storage;
    }
    public static void downloadFolder(String bucketName, String prefix, Path downloadTo) throws IOException {


        String prefixCalculated = prefix.endsWith("/") ? prefix : prefix+"/" ;
        Page<Blob> blobs = storage.list(bucketName, BlobListOption.currentDirectory(),
        BlobListOption.prefix(prefixCalculated));
        Files.createDirectories(downloadTo);

        for (Blob blob : blobs.iterateAll()) {
            String[] lastSegment =blob.getName().split("/");
            writeBlob(blob,Paths.get(downloadTo.toString(),lastSegment[lastSegment.length-1]));

          }
    }

    public static String uploadFile(String bucketName, String objectName,byte[] content ) throws IOException {



        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(bucketName, objectName)
                                // Modify access list to allow all users with link to read file
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
                                .build(),content);
        // return the public download link
        return blobInfo.getMediaLink();
    }

    private static void writeBlob(Blob blob,Path downloadTo) throws IOException{
        if (blob == null) {
            System.out.println("No such object");
            return;
        }
          PrintStream writeTo = System.out;
          if (downloadTo != null) {
            writeTo = new PrintStream(new FileOutputStream(downloadTo.toFile()));
          }
          if (blob.getSize() < 1_000_000) {
            // Blob is small read all its content in one request
            byte[] content = blob.getContent();
            writeTo.write(content);
          } else {
            // When Blob size is big or unknown use the blob's channel reader.
            try (ReadChannel reader = blob.reader()) {
              WritableByteChannel channel = Channels.newChannel(writeTo);
              ByteBuffer bytes = ByteBuffer.allocate(64 * 1024);
              while (reader.read(bytes) > 0) {
                bytes.flip();
                channel.write(bytes);
                bytes.clear();
              }
            }
          }
          if (downloadTo == null) {
            writeTo.println();
          } else {
            writeTo.close();
          }
    }

    public static void download(String bucketName, String objectName, Path downloadTo) throws IOException {
        BlobId blobId = BlobId.of(bucketName, objectName);
        Blob blob = storage.get(blobId);
        if(blob==null) downloadFolder(bucketName, objectName, downloadTo);
        else writeBlob(blob,downloadTo);

      }
}