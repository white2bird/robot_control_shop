package com.it.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.Good;
import com.it.req.GoodSearchReqDTO;

import java.util.List;
import java.util.Map;

/**
 * 
 * @date 2024/4/21 18:27
 */
public interface GoodService extends IService<Good> {

    IPage searchGood(GoodSearchReqDTO goodSearchReqDTO);

    Map<Long, String> getGoodNameMap(List<Long> goodList);

    Map<Long, Good> getGoodInfoMap(List<Long> goodList);
}
