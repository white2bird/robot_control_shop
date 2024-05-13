package com.it.req;

import lombok.Data;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 13:28
 */
@Data
public class RoleMenuUpdateReqDTO {

    private Long roleId;

    private List<Long> menuId;
}
