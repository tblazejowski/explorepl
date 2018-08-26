package com.example.explorepl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Classification of Hotels by province they are located in.
 *
 *
 */

@Entity
public class Province implements Serializable {

    @Id
    private String code;

    @Column
    private String name;

    public Province() {
    }

    public Province(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Province{" +
                "code='" + code + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
