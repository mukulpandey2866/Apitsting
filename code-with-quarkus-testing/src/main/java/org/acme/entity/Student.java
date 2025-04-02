package org.acme.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import javax.annotation.processing.Generated;

@Entity
public class Student {
    @Id
    @GeneratedValue
    Long studenStudentId;
    String name;
    String branch;

    public Long getStudenStudentId() {
        return studenStudentId;
    }

    public void setStudenStudentId(Long studenStudentId) {
        this.studenStudentId = studenStudentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
