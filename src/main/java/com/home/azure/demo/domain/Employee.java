package com.home.azure.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public MultipartFile getMultipartFile() {
        return blobOutgoing;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.blobOutgoing = multipartFile;
    }

    private String name;
    private String uid;
    private String email;
    private String blob;
    private MultipartFile blobOutgoing;

    public byte[] getBlobByte() {
        return blobByte;
    }

    public void setBlobByte(byte[] blobByte) {
        this.blobByte = blobByte;
    }

    private byte[] blobByte;
}
