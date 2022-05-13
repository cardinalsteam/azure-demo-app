package com.home.azure.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.azure.demo.PronunciationBlob;
import com.home.azure.demo.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NamePronunciationService {
    @Autowired
    private AzureTextToSpeechHelper azureTextToSpeechHelper;
    @Autowired
    private NamePronunciationDBHelper namePronunciationDBHelper;
    public Employee pronunce(String employeeData){
        Employee employee = mapEmployeeStringToObject(employeeData);
        boolean namePresent = false;
        Employee empToReturn =null;
        if (null != employee) {

            namePresent = isNamePresent(employee);
            if(namePresent){
                //call azure standard speech api
                String employeeNameToSpeak = employee.getName().trim();
                empToReturn = new Employee();
               empToReturn = azureTextToSpeechHelper.callAzureToTransformTextToSpeech(employeeNameToSpeak);

            }else if(employee.getUid()!=null){
                //search an employee in the DB using uid. if present then get employee information
                empToReturn = namePronunciationDBHelper.searchEmployeeByUid(employee.getUid().trim());
            }else if(employee.getEmail()!=null){
                //search an employee in the DB using email. if present then get employee information
                empToReturn = namePronunciationDBHelper.searchEmployeeByEmail(employee.getEmail().trim());
            }else{
                // record not found .
                empToReturn = null;
            }


        }

        return empToReturn;
    }

    public void insertEmployeeRecord(Employee employeeData){
        namePronunciationDBHelper.insertEmpoyeeRecord(employeeData);
    }

    private Employee mapEmployeeStringToObject(String employeeData) {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee emp = null;
        try {
            emp = objectMapper.readValue(employeeData, Employee.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return emp;
    }

    private boolean isNamePresent(Employee emp) {
        if(null!= emp.getName() && !"".equals(emp.getName())){
            return true;
        }
        return false;
    }
}
