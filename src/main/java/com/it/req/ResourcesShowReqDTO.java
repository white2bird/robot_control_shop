package com.it.req;

import com.it.common.Page;
import lombok.Data;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/20 13:45
 */
@Data
public class ResourcesShowReqDTO extends Page {

    private List<Integer> type;
}
