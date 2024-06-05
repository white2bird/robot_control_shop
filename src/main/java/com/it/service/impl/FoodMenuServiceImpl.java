package com.it.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.context.UserContext;
import com.it.convert.FoodMenuConvert;
import com.it.entity.FoodMenu;
import com.it.entity.FoodType;
import com.it.entity.LikeRelation;
import com.it.entity.MenuTypeRelation;
import com.it.entity.User;
import com.it.mapper.FoodMenuMapper;
import com.it.mapper.LikeRelationMapper;
import com.it.mapper.MenuTypeRelationMapper;
import com.it.mapper.UserMapper;
import com.it.req.LikeRelationReq;
import com.it.res.FoodMenuDetailVO;
import com.it.res.FoodMenuVO;
import com.it.res.MenuMainVO;
import com.it.service.FoodMenuService;
import com.it.service.FoodTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 
 * @date 2024/5/14 14:08
 */
@Service
public class FoodMenuServiceImpl extends ServiceImpl<FoodMenuMapper, FoodMenu> implements FoodMenuService {

    @Resource
    private FoodTypeService foodTypeService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private FoodMenuConvert foodMenuConvert;

    @Resource
    private LikeRelationMapper likeRelationMapper;

    @Resource
    private MenuTypeRelationMapper menuTypeRelationMapper;

    @Override
    public List<MenuMainVO> search(Integer type) {
        List<Integer> types = type == null ? foodTypeService.list().stream().map(FoodType::getId).map(Long::intValue).collect(Collectors.toList())
                : Arrays.asList(type);

        List<MenuMainVO> res = new ArrayList<>();
        for(Integer searchType : types){
            FoodType foodType = foodTypeService.getById(searchType);
            MenuMainVO vo = new MenuMainVO();
            vo.setName(foodType.getName());
            List<MenuTypeRelation> foodTypeId = menuTypeRelationMapper.selectList(new QueryWrapper<MenuTypeRelation>().eq("food_type_id", searchType));
            if(CollUtil.isEmpty(foodTypeId)){
                continue;
            }
            List<FoodMenu> foodMenus = this.listByIds(foodTypeId.stream().map(MenuTypeRelation::getFoodMenuId).collect(Collectors.toList()));
            List<FoodMenuVO> foodMenuVOList = new ArrayList<>();
            for(FoodMenu foodMenu : foodMenus){
                FoodMenuVO foodMenuVO = new FoodMenuVO();
                foodMenuVO.setId(foodMenu.getId());
                foodMenuVO.setName(foodMenu.getName());
                foodMenuVO.setImg("http://127.0.0.1:8080/static/"+foodMenu.getPic());
                foodMenuVOList.add(foodMenuVO);
            }
            vo.setSubArr(foodMenuVOList);
            res.add(vo);
        }
        return res;
    }

    @Override
    public List<MenuMainVO> searchByName(String name) {
        List<MenuMainVO> search = search(null);
        if(StrUtil.isEmpty(name)){
            return search;
        }
        List<MenuMainVO> res = new ArrayList<>();
        for(MenuMainVO menuMainVO : search){
            List<FoodMenuVO> subArr = menuMainVO.getSubArr();
            List<FoodMenuVO> subArrNew = subArr.stream().filter(item -> item.getName().contains(name)).collect(Collectors.toList());
            if(CollUtil.isEmpty(subArrNew)){
                continue;
            }
            menuMainVO.setSubArr(subArrNew);
            res.add(menuMainVO);
        }
        if(CollUtil.isEmpty(res)){
            return null;
        }
        return res;
    }

    @Override
    public FoodMenuDetailVO detail(Long id) {
        FoodMenu byId = this.getById(id);
        FoodMenuDetailVO foodMenuDetailVO = foodMenuConvert.convertVo(byId);
        if(StrUtil.isNotBlank(foodMenuDetailVO.getPic())){
            foodMenuDetailVO.setPic("http://localhost:8080/static/"+foodMenuDetailVO.getPic());
        }
        if(StrUtil.isNotBlank(byId.getSteps())){
            List<String> steps = Arrays.asList(byId.getSteps().split("\r\n"));
            foodMenuDetailVO.setStepList(steps);
        }
        // 确定是否喜欢
        Long userId = UserContext.getUserId();
        LambdaQueryWrapper<LikeRelation> eq = new LambdaQueryWrapper<LikeRelation>().eq(LikeRelation::getFoodMenuId, id).eq(LikeRelation::getUserId, userId);
        LikeRelation likeRelation = likeRelationMapper.selectOne(eq);
        if(!Objects.isNull(likeRelation)){
            foodMenuDetailVO.setLike(1);
        }else{
            foodMenuDetailVO.setLike(0);
        }
        return foodMenuDetailVO;
    }

    @Override
    public Boolean likeRelation(LikeRelationReq likeRelationReq) {
        Long userId = UserContext.getUserId();
        Long foodMenuId = likeRelationReq.getFoodMenuId();

        Long like = likeRelationReq.getLike();
        if(like == 0){
            return likeRelationMapper.delete(new LambdaQueryWrapper<LikeRelation>().eq(LikeRelation::getFoodMenuId, foodMenuId).eq(LikeRelation::getUserId, userId)) >= 0;
        }
        LikeRelation likeRelation = new LikeRelation();
        likeRelation.setFoodMenuId(foodMenuId);
        likeRelation.setUserId(userId);
        return likeRelationMapper.insert(likeRelation) > 0;
    }

    @Override
    public List<MenuMainVO> getStoreMenus() {
        Long userId = UserContext.getUserId();
        List<LikeRelation> likeRelations = likeRelationMapper.selectList(new LambdaQueryWrapper<LikeRelation>().eq(LikeRelation::getUserId, userId));
        if (CollUtil.isEmpty(likeRelations)) {
            return null;
        }
        List<Long> foodMenusIds = likeRelations.stream().map(LikeRelation::getFoodMenuId).distinct().collect(Collectors.toList());
        List<MenuMainVO> res = new ArrayList<>();

        MenuMainVO vo = new MenuMainVO();
        vo.setName("收藏");

        List<FoodMenu> foodMenus = this.listByIds(foodMenusIds);
        List<FoodMenuVO> foodMenuVOList = new ArrayList<>();
        for (FoodMenu foodMenu : foodMenus) {
            FoodMenuVO foodMenuVO = new FoodMenuVO();
            foodMenuVO.setId(foodMenu.getId());
            foodMenuVO.setName(foodMenu.getName());
            foodMenuVO.setImg("http://127.0.0.1:8080/static/" + foodMenu.getPic());
            foodMenuVOList.add(foodMenuVO);
        }
        vo.setSubArr(foodMenuVOList);
        res.add(vo);
        return res;
    }

    @Override
    public MenuMainVO getStoreMenus(Long userId) {
        if(userId <= 0){
            userId = UserContext.getUserId();
        }
        MenuMainVO res = new MenuMainVO();


        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
        res.setName(user.getUsername());
        List<LikeRelation> likeRelations = likeRelationMapper.selectList(new LambdaQueryWrapper<LikeRelation>().eq(LikeRelation::getUserId, userId));
        if (CollUtil.isEmpty(likeRelations)) {
            return res;
        }
        List<Long> foodMenusIds = likeRelations.stream().map(LikeRelation::getFoodMenuId).distinct().collect(Collectors.toList());
        List<FoodMenu> foodMenus = this.listByIds(foodMenusIds);
        List<FoodMenuVO> foodMenuVOList = new ArrayList<>();
        for (FoodMenu foodMenu : foodMenus) {
            FoodMenuVO foodMenuVO = new FoodMenuVO();
            foodMenuVO.setId(foodMenu.getId());
            foodMenuVO.setName(foodMenu.getName());
            foodMenuVO.setImg("http://127.0.0.1:8080/static/" + foodMenu.getPic());
            foodMenuVOList.add(foodMenuVO);
        }
        res.setSubArr(foodMenuVOList);
        return res;
    }

    @Override
    public FoodMenuDetailVO recommend(Long foodType) {
        Long type = likeRelationMapper.selectMaxLiked(foodType);
        if(type == null){
            type = menuTypeRelationMapper.randomRecommend(foodType);
        }
        if(type == null){
            return null;
        }
        return detail(type);
    }

    @Override
    public List<FoodMenuVO> getBanner() {
        List<FoodMenu> list = this.list(new QueryWrapper<FoodMenu>().orderByAsc("rand()").last("limit 3"));
        if(CollUtil.isEmpty(list)){
            return null;
        }
        list.forEach(item->{
            item.setPic("http://localhost:8080/static/"+item.getPic());
        });
        return foodMenuConvert.convertoVoList(list);
    }


}
