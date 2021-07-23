package com.my.facade.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.my.facade.dao.ArticleDao;
import com.my.facade.dao.UserDao;
import com.my.facade.dao.mapper.UserMapper;
import com.my.facade.dao.domain.Article;
import com.my.facade.dao.domain.User;
import com.my.facade.service.UserService;
import com.my.common.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("UserService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List getUserList(String name){

        // ex 获取用户列表
        List<User> userList = userDao.getUserList();

        // ex 获取单个用户
        userDao.getUserById(1l);
        System.out.println(userList.toString());

        ArrayList<Integer> idlist = new ArrayList<>();
        idlist.add(1);
        List<User>  userList1  = userMapper.selectBatchIds(idlist);

        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", name);
        List<User> userList2 = userMapper.selectByMap(columnMap);

        userMapper.abc(1L);

        // 更新操作
        // 这是 条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 1);
        // 这是需要更新的值
        User user = new User();
        user.setAge(55);
        userMapper.update(user, updateWrapper);
//        Example ex = new Example(User.class);
//        ex.createCriteria()
//                .andIsNotNull("id")
//                .andEqualTo("name", "Sandy");
//        userMapper.updateByExampleSelective(user, ex);

        // PreparedStatement   Statement
        return userList;
    }

//    @Async("asyncServiceExecutor")
    @Async
    @Override
    public void doASync(){
        System.out.println("开始=======");
        for (int i = 0 ; i < 10 ; i++) {
            String name = "name+" + i;
            Article article = new Article();
            article.setName(name);
            articleDao.insertArticle(article);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Thread.sleep(2 * 1000);
                System.out.println(sdf.format(new Date()) + "--循环执行第" + (i+1) + "次");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public Map<String, Object> login(User user){
        //phone是除id外的唯一标志 需要进行检查
//        User selectUser = userDao.selectUserByPhone(user.get());
//        if (selectUser == null) {
//            //注册用户
//            int count = userDao.insertUser(user);
//            if (count < 1) throw new ApiException("注册异常");
//        }
        User selectUser  = userDao.getUserById(user.getId());
        //将userId存入token中
        String token = JWTUtils.createToken(selectUser.getId().toString());
        Map<String,Object> map = new HashMap<>();
        map.put("user",selectUser);
        map.put("token",token);
        return map;
    }

    public User getUserByToken(String token){

        String userId = JWTUtils.validateToken(token);

        User selectUser  = userDao.getUserById(Long.valueOf(userId));
        return selectUser;
    }
}
