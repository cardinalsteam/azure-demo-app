package com.home.azure.demo.service;

import com.home.azure.demo.PronunciationBlob;
import com.home.azure.demo.domain.Employee;
import com.microsoft.cognitiveservices.speech.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class AzureTextToSpeechHelper {

    //Using Azure text to speech service sdk.
    public Employee callAzureToTransformTextToSpeech(String text){
        //create a azure speech resource/speech services, and get the key from there.
        /*String speechSubscriptionKey = "2f6f7536157744cea209f4398d39cf12";
        String serviceRegion = "westus2";*/
        byte chunks[] = null;
        Blob blob = null;
        String speechSubscriptionKey = "102cb5eceb6d42e6952535c67884693b";

        String serviceRegion = "eastus";
        try {
            SpeechConfig config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);
            config.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff8Khz8BitMonoMULaw);
            config.setSpeechSynthesisVoiceName("en-US-AriaNeural");
            SpeechSynthesizer synth = new SpeechSynthesizer(config);
            assert(config != null);
            assert(synth != null);

            int exitCode = 1;
            Future<SpeechSynthesisResult> task = synth.SpeakTextAsync(text);
            assert(task != null);
            SpeechSynthesisResult result = task.get();
            chunks = result.getAudioData();
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
        String base64String = Base64.encodeBase64String(chunks);
        Employee employee = new Employee();
        String chunkSrtring = new String(chunks);
        employee.setBlob(base64String);
        return employee;
    }
}
