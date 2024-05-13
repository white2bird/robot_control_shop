package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.Good;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 18:25
 */
public interface GoodMapper extends BaseMapper<Good> {

    Boolean subGoodCount(Long goodId, Long count);
}
