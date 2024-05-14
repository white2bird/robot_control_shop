package com.it.req;

import io.swagger.annotations.ApiModel;

/**
 * @author 
 * @date 2024/4/5 23:33
 */
@ApiModel(value = "设备请求体")
public class DeviceReqDTO {

    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
