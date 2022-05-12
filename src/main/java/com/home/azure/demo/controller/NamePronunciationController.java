package com.home.azure.demo.controller;

import com.home.azure.demo.domain.Employee;
import com.home.azure.demo.service.NamePronunciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NamePronunciationController {

   @Autowired
    private final NamePronunciationService namePronunciationService;

    public NamePronunciationController(NamePronunciationService namePronunciationService) {
        this.namePronunciationService = namePronunciationService;
    }


    @GetMapping("/message")
    public String message(String blob) {


        return "This application is deployed in azure !";
    }

   /* @GetMapping("/allblobnames")
    public List<String> getBlobNames() {
        return myBlobService.listBlobs();
    }

    @GetMapping("/download")
    public ByteArrayOutputStream downloadAudio() {
        byte[] bytes = myBlobService.downloadFile("cat.png");
        ByteArrayOutputStream baos = null;
        //convert to image if you want

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            BufferedImage bImage2 = ImageIO.read(bis);
            //ImageIO.write(bImage2, "png", new File("output.jpg") );
            baos = new ByteArrayOutputStream();
            ImageIO.write(bImage2, "png", baos);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos;
    }

    @PostMapping("/audiofile")
    public @ResponseBody
    String uploadBlob(@RequestParam("file") MultipartFile multipartFile) {
        myBlobService.uploadFile(multipartFile);
        return "";
    }

    @GetMapping("/texttospeech")
    public void textToSpeech() {
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList("Ravi Garlapati", "Mansoor Altaf", "Daniel Martinez", "Sarita Shetty"));
        //String name = ;
        for (String name : list) {
            myBlobService.textToSpeech(name);
        }

    }*/

    @PostMapping("/pronunce")

    public List<Employee> pronunce(@RequestBody final String employee) {
        List<Employee> employees = new ArrayList<>();
        Employee responseObj = namePronunciationService.pronunce(employee);
        if(null!=responseObj){
           employees.add(responseObj);
        }
        return employees;

    }

    @PostMapping("/saveemployee")

    public void saveEmployee(@RequestBody final String employee) {
        namePronunciationService.insertEmployeeRecord(employee);
    }
}
