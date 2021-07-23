package com.my.facade.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.my.common.api.CommonResult;
import com.my.facade.common.MyResponse;
import com.my.facade.dao.ArticleDao;
import com.my.facade.dao.domain.Article;
import com.my.facade.dao.domain.User;
import com.my.facade.service.UserService;
import com.my.facade.request.ReRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;


@RestController
@RequestMapping("/home")
@Api(tags = "home", description = "home desc")
class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping(value = "me")
    @ApiOperation(value = "me-测试")
    public CommonResult toMe(){

        return CommonResult.success("");

    } ;

    @GetMapping(value = "mq")
    @ApiOperation(value = "mq-测试")
    public MyResponse sendMessage(){

        String msg = request.getParameter("msg");

        System.out.println(rabbitTemplate.getConnectionFactory().getHost());

        String queueName = "q-1";
        // direct
        rabbitTemplate.convertAndSend(queueName, msg);

        int num = Integer.parseInt(request.getParameter("num"));

        for (int i = 0; i < num; i++) {
            String context = "hello " + new Date();
            System.out.println("Sender : " + context);
            this.rabbitTemplate.convertAndSend("hello", context);
        }

        //参数1：交换机 参数2：路由key/queue队列名称 参数3：消息内容
        String name = "s11";
        String exchangeName="fanout_to_exchange";
        String routingkey = "";
        rabbitTemplate.convertAndSend(exchangeName,routingkey,"s11");

        // topic
        String context = "hi, i am message 1";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("exchange", "topic.message", context);
        this.rabbitTemplate.convertAndSend("exchange1", "topic.message", context);

        String context1 = "hi, i am messages 2";
        System.out.println("Sender : " + context1);
        this.rabbitTemplate.convertAndSend("exchange1", "topic.messages", context);
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages11", context);

        ArrayList<Object> ulist = new ArrayList<>();
        ulist.add(null);
        ulist.add(msg);

        return  MyResponse.returnSuccess(ulist);

    }

    @GetMapping(value = "redis")
    @ApiOperation(value = "测试redis")
    public MyResponse useRedis(HttpSession session){

        // 测试异步
        userService.doASync();

        String name = request.getParameter("name");
        stringRedisTemplate.opsForValue().set("aaa", name);
        String aa = stringRedisTemplate.opsForValue().get("aaa");

        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>("张三" + i, 1D + i);
            tuples.add(tuple);
        }

        Set<String> set1 =  new HashSet<>();
        set1.add("set1");
        set1.add("set2");
        set1.add("set3");
        redisTemplate.opsForSet().add("user", set1);
        Set<String>  setRes = redisTemplate.opsForSet().members("user");

        redisTemplate.opsForSet();
        redisTemplate.opsForHash().put("hname","name", "zs");
        redisTemplate.opsForHash().put("hname","age", "11");
        redisTemplate.opsForHash().put("hname","id", "16");

        // process


        Map<String, String >  hashRes = redisTemplate.opsForHash().entries("hname");

        redisTemplate.opsForZSet().add("rankName", tuples);

        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        session.setAttribute("uname", name);

        ArrayList<Object> ulist = new ArrayList<>();
        ulist.add(null);
        ulist.add(aa);
        ulist.add(session.getId());
        ulist.add(setRes);
        ulist.add(hashRes);
        return  MyResponse.returnSuccess(ulist);

    }


    @GetMapping(value = "conn")
    @ApiOperation(value = "数据库链接")
    public MyResponse conn(){

        try {
            Connection connection = dataSource.getConnection();
            System.out.println(connection);
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // 获取传参 赋值转化
        String name = request.getParameter("name");
        Integer id = Integer.valueOf(request.getParameter("id"));
        Article article = new Article();
        article.setName(name);
        articleDao.insertArticle(article);
        // ex 获取用户列表
        List<User> userList = userService.getUserList(name);

        ArrayList<Object> ulist = new ArrayList<>();
        ulist.add(userList);
        return  MyResponse.returnSuccess(ulist);
    }

    // 首页设置
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ApiOperation(value = "获取首页")
    @ResponseBody
    public List getIndex(){

        String x_code = request.getHeader("x-code");
        String name = request.getHeader("x-code");

        Integer id = Integer.parseInt(request.getParameter("id"));
        String rname = request.getParameter("rname");

        List<String> list=new ArrayList<String>();
        list.add(x_code);
        list.add(rname);
        list.set(0,"dededede");

        System.out.println(list);

        for (String str : list){
            System.out.println(str);
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");

        System.out.println("k" + map.get("2"));

        for (String key : map.keySet()) {
            System.out.println("key= "+ key + " and value= " + map.get(key));
        }


        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(dNow);
        System.out.println("当前时间为: " + ft.format(dNow));

        return list;

    }

    // re test
    @RequestMapping(value = "/re", method = RequestMethod.POST)
    @ApiOperation(value = "re test")
    @ResponseBody
    public MyResponse getRe(@RequestBody @Valid ReRequest reRequest){
        log.info("收到请求request:{}", JSON.toJSONString(reRequest));
        JSONArray array = new JSONArray();

        String name = reRequest.getName();
        if (name.isEmpty()){
            System.out.println("name is null " +  name);
            name = "122abd";
        };

        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");

        List<String> list = new ArrayList();

        list.add("a");
        list.add("a");
        list.add("c");

        String ltostr = JSON.toJSONString(list);
        log.info("转化记录:{}", ltostr);

        try {
            System.out.println(list.get(4));

            log.info("转化记录:{}", map.toString());
            String str = map.get("4");
            System.out.println("str : " + str);

        } catch (Exception e) {
            log.error("error", e);
        }



//        Runtime.getRuntime().exec("open ");
        return  MyResponse.returnSuccess(map);
    }




}
