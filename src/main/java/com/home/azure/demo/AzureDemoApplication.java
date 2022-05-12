package com.home.azure.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.azure.demo.blob.MyBlobService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
//@RestController
//@RequiredArgsConstructor
public class AzureDemoApplication {


	/*private final MyBlobService myBlobService;


	@GetMapping("/message")
	public String message(String blob){


		return  "This application is deployed in azure !";
	}

	@GetMapping("/allblobnames")
	public List<String> getBlobNames(){
	return myBlobService.listBlobs();
	}

	@GetMapping("/download")
	public ByteArrayOutputStream downloadAudio(){
		byte[] bytes = myBlobService.downloadFile("cat.png");
		ByteArrayOutputStream baos = null;
		//convert to image if you want

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			BufferedImage bImage2 = ImageIO.read(bis);
			//ImageIO.write(bImage2, "png", new File("output.jpg") );
			 baos = new ByteArrayOutputStream();
			ImageIO.write(bImage2,"png",baos);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos;
	}
	@PostMapping("/audiofile")
	public @ResponseBody String uploadBlob(@RequestParam("file")MultipartFile multipartFile){
		myBlobService.uploadFile(multipartFile);
		return "";
	}

    @GetMapping("/texttospeech")
    public void textToSpeech(){
		List<String> list = new ArrayList<>();
		list.addAll(Arrays.asList("Ravi Garlapati","Mansoor Altaf","Daniel Martinez","Sarita Shetty"));
		//String name = ;
		for (String name:list ) {
			myBlobService.textToSpeech(name);
		}

    }

	@PostMapping("/pronunce")

	public void  pronunce(@RequestBody final String employee){
		ObjectMapper objectMapper = new ObjectMapper();
		Employee emp = null;

		if(null!= employee){
			try {
				 emp = objectMapper.readValue(employee,Employee.class);
				myBlobService.textToSpeech(emp.getName());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

	}*/
	public static void main(String[] args) {
		SpringApplication.run(AzureDemoApplication.class, args);
	}

}
