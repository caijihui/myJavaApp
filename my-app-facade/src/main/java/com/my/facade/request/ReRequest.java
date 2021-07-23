package com.my.facade.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReRequest
{
    @NotNull(message = "name 不为空")
    @ApiModelProperty(value = "名称", required = true)
    private String name;


    @NotEmpty
    private String rename;


    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "id值", required = true)
    private Integer id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRename() {
        return rename;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
