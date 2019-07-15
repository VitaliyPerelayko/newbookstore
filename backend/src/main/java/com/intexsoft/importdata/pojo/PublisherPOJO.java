package com.intexsoft.importdata.pojo;

import com.opencsv.bean.CsvBindByName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "publisher")
public class PublisherPOJO implements ObjectsForBindings {

    @NotBlank(message = "publisherPOJO.name.notBlank")
    @Size(max = 50, message = "publisherPOJO.name.size")
    @CsvBindByName(column = "name")
    private String name;

    public PublisherPOJO(@NotBlank(message = "publisherPOJO.name.notBlank")
                         @Size(max = 50, message = "publisherPOJO.name.size") String name) {
        this.name = name;
    }

    public PublisherPOJO() {
    }

    @Override
    public String getUniqueValue() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublisherPOJO that = (PublisherPOJO) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
