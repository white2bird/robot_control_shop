package com.it.req;

import com.it.common.Page;
import lombok.Data;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 18:55
 */
@Data
public class GoodSearchReqDTO extends Page {

    private String goodName;
}
