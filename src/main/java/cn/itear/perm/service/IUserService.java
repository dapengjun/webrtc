package cn.itear.perm.service;

import java.util.List;
import java.util.Map;

import cn.itear.common.service.IBaseService;
import cn.itear.perm.po.UserPo;

public interface IUserService extends IBaseService {

    // 添加用户
    public void insert(UserPo user);

    public void update(UserPo user);

    public void delete(Integer userId);

    public UserPo selectOne(Integer userId);

    @SuppressWarnings("rawtypes")
    public List<UserPo> selectList(Map map);
    
    @SuppressWarnings("rawtypes")
    public int selectCnt(Map map);

    public UserPo userLogin(UserPo user);
}
