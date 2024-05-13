package com.it.req;

import lombok.Data;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 14:59
 */
@Data
public class UserRoleUpdateReqDTO {

    private Long userId;

    private List<Long> roleIds;
}
