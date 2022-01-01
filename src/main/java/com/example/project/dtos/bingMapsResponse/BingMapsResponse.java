package com.example.project.dtos.bingMapsResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Getter @Setter
public class BingMapsResponse implements Serializable {
    private List<ResourceSet> resourceSets;
}


