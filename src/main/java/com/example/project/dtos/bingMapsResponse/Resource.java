package com.example.project.dtos.bingMapsResponse;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
public class Resource implements Serializable {
    private Float travelDistance;
    private Integer travelDuration;
}
