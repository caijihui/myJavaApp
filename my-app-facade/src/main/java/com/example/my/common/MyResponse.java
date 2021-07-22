package com.example.my.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MyResponse<T> implements Serializable
{


    @ApiModelProperty(
            value = "响应码,1表示成功",
            example = "1"
    )
    private String responseCode;
    @ApiModelProperty("响应消息")
    private String responseMessage;
    @ApiModelProperty("响应类型，默认error")
    private String responseType;
    @ApiModelProperty("自定义错误数据map")
    private T data;

    public MyResponse(String responseCode, String responseMessage, T data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public MyResponse(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public MyResponse() {
    }

    public static <T> MyResponse<T> returnSuccess(T data){
        return new MyResponse(CommonResponseCode.RC_SUCCESS.getResponseCode(),
                CommonResponseCode.RC_SUCCESS.getResponseMessage(), data);
    }

    public static <T> MyResponse<T> returnError(){
        return new MyResponse(CommonResponseCode.RC_ERROR.getResponseCode(),
                CommonResponseCode.RC_ERROR.getResponseMessage());
    }

}
