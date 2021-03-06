package com.home.azure.demo.blob;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.sql.Connection;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;

@Service
public class MyBlobService {

    @Value("${azure.myblob.url}")
    private String azureUrl;
    @Value("${azure.myblob.container}")
    private String containerName;
    @Value("${azure.myblob.storageconnectionstring}")
    private String storageConnectionString;

    byte[] array1;
    byte[] array2;

    private BlobContainerClient containerClient(){

        BlobServiceClient serviceClient = new BlobServiceClientBuilder().connectionString(azureUrl).buildClient();
        BlobContainerClient blobContainerClient = serviceClient.getBlobContainerClient(containerName);
        return  blobContainerClient;
    }

    public List<String> listBlobs(){
List<String> blobNames = new ArrayList<>();
        BlobContainerClient containerClient = containerClient();
        for (BlobItem blob: containerClient.listBlobs() ) {
            blobNames.add(blob.getName());
        }

        return  blobNames;
    }

    public void uploadFile(String blob, String audioFilenameRequest){
        try {
            byte[] bytes = blob.getBytes();
            System.out.println("lenght:: " + bytes.length);
            String audioFileName = audioFilenameRequest;
            this.array1 = bytes;
            CloudBlobContainer containerReference = getCloudBlobContainer();

            //Getting a blob reference

            CloudBlockBlob blockBlobReference = containerReference.getBlockBlobReference(audioFileName);

            blockBlobReference.uploadFromByteArray(bytes,0,bytes.length);
            System.out.println("upload to Azure cloud blob is done!!!!");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }

    public byte[] downloadFile(String audioFileName) {
        File downloadedFile = null;
        byte[] audioByteArray = new byte[472179];
        try {

            CloudBlobContainer containerReference = getCloudBlobContainer();
            //Getting a blob reference
            CloudBlockBlob blockBlobReference = containerReference.getBlockBlobReference(audioFileName);
            blockBlobReference.downloadToByteArray(audioByteArray,0);
            System.out.println("download from Azure cloud blob is done!!!!:: Size : " + audioByteArray.length);
            this.array2 = audioByteArray;
           if(Arrays.equals(array1, array2)){
               System.out.println("arrays are equal");
           }
           else{
               System.out.println("arrays are not equal");
           }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }

        return audioByteArray;
    }

    //Using Azure text to speech service sdk.
    public void textToSpeech(String text){
makeConnectionUsingYbClusterAwareDataSource();
        String speechSubscriptionKey = "102cb5eceb6d42e6952535c67884693b";

                String serviceRegion = "eastus";
        try {
            SpeechConfig config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);

             config.setSpeechSynthesisVoiceName("en-US-AriaNeural");
             SpeechSynthesizer synth = new SpeechSynthesizer(config);

            assert(config != null);
            assert(synth != null);

            int exitCode = 1;

            Future<SpeechSynthesisResult> task = synth.SpeakTextAsync(text);
            assert(task != null);

            SpeechSynthesisResult result = task.get();
            assert(result != null);

            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                System.out.println("Speech synthesized to speaker for text [" + text + "]");
                exitCode = 0;
            }
        else if (result.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you set the speech resource key and region values?");
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException exception: " + e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("ExecutionException exception: " + e.getMessage());
        }


    }

    private CloudBlobContainer getCloudBlobContainer() throws URISyntaxException, InvalidKeyException, StorageException {
        CloudStorageAccount cloudStorageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient cloudBlobClient = cloudStorageAccount.createCloudBlobClient();
        CloudBlobContainer containerReference = cloudBlobClient.getContainerReference(containerName);
        containerReference.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        return containerReference;
    }


    public static void makeConnectionUsingYbClusterAwareDataSource() {
        //System.out.println("Now, Lets create 10 connections using YbClusterAwareDataSource and Hikari Pool");
        String yburl = "jdbc:yugabytedb://20.121.116.153:5433,20.62.193.199:5433,20.119.92.88:5433/yugabyte?user=yugabyte&password=Hackathon22!";

        try {
            Connection conn = DriverManager.getConnection(yburl);
            Statement stmt = conn.createStatement();
            Class.forName("com.yugabyte.Driver");
            System.out.println("Connected to the YugabyteDB Cluster successfully.");
           // stmt.execute("DROP TABLE IF EXISTS employee");
            /*stmt.execute("CREATE TABLE IF NOT EXISTS employee" +
                    "  (id int primary key, name varchar, age int, language text)");*/
           // System.out.println("Created table employee");
            System.out.println("insert into table employees");
            String insertStr = "INSERT INTO employees.employees  VALUES ('U775352', 'Daniel.Martinez7@wellsfargo.com', 'Daniel Martinez', '')";
            stmt.execute(insertStr);
            System.out.println("EXEC: " + insertStr);

            ResultSet rs = stmt.executeQuery("select * from employees.employees");
            while (rs.next()) {
                System.out.println(String.format("Query returned: uid = %s, email = %s, name = %s, blob = %s",
                        rs.getString("uid"), rs.getString("email"), rs.getString("name"), rs.getString("audio")));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
