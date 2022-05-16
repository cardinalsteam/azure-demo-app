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
