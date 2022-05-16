package com.home.azure.demo.controller;

import com.home.azure.demo.PronunciationBlob;
import com.home.azure.demo.domain.Employee;
import com.home.azure.demo.service.NamePronunciationService;
import org.apache.naming.factory.SendMailFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        Employee emp = namePronunciationService.pronunce(employee);
        List<Employee> emplist = new ArrayList<>();
        if(emp != null){
            emplist.add(emp);
        }
        return emplist;

    }

    @PostMapping("/saveemployee")

    public String saveEmployee(
            @RequestParam("blob")String blob,
            @RequestParam("name")String name,
            @RequestParam("email")String email,
            @RequestParam("uid")String uid) {
        System.out.println(String.valueOf((name)));
        System.out.println(String.valueOf((uid)));
        System.out.println(String.valueOf((email)));
        Employee emp = new Employee();
        emp.setBlob(blob);
        emp.setEmail(email);
        emp.setUid(uid);
        emp.setName(name);
        namePronunciationService.insertEmployeeRecord(emp);
        return "200";
    }

}
