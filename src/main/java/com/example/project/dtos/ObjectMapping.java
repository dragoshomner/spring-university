package com.example.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ObjectMapping {
    private String Key;
    private String Title;
    private Boolean ShowTable;
    private Boolean ShowNew;
    private Boolean DisableEdit;
    private String InputType;

    private String ChildKey;

    public ObjectMapping(String key, String title, Boolean showTable, Boolean showNew, Boolean disableEdit, String inputType) {
        this.Key = key;
        this.Title = title;
        this.ShowTable = showTable;
        this.ShowNew = showNew;
        this.DisableEdit = disableEdit;
        this.InputType = inputType;
    }
}
