package com.example.project.dtos.bingMapsResponse;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ResourceSet implements Serializable {
    private List<Resource> resources;
}
