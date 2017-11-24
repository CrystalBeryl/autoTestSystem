package com.beryl.controller;

import com.alibaba.fastjson.JSON;
import com.beryl.model.EmailAuthenticator;
import com.beryl.model.User;
import com.beryl.service.UserService;
import com.beryl.util.Consts;
import com.beryl.util.PwDigest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.deploy.util.SearchPath.findOne;

/**
 * Created by qjnup on 2016/9/28.
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisTemplate redisTemplate;


/*
    AddressToMapConverter toMapConverter = new AddressToMapConverter();
*/

/*    @Autowired
    public HashOperations<String,byte[],byte[]> hashOperations;*/

/*
     HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper(Authenticator.class) ;
*/



    Logger logger = Logger.getLogger(UserController.class);
    String sessionId = null;

    @RequestMapping("/")
    public ModelAndView route(){
        return new ModelAndView("redirect:login");
    }

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request,HttpSession httpSession){
        String username = (String)httpSession.getAttribute("username");
        /*System.out.println("httpsession得到的sessionId:"+httpSession.getId());
        System.out.println("登录用户名:"+username);*/
        sessionId=request.getRequestedSessionId();
        if(sessionId==null || username==null){
            return new ModelAndView("login");
        }else {
            return new ModelAndView("redirect:autoTest/index");
        }

    }

    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public String  login(@RequestBody User user, HttpSession httpSession){
        Map<String, Object> map = new HashMap<>();
        String username = user.getUsername().trim();
        String password = user.getPassword().trim();
        if(username == null || username.equals("")|| password==null || password.equals("")){
            map.put("msg","用户名或者密码不能为空");
            map.put("success",false);
        }else {
            int flag = userService.login(username,password);
            if(flag == Consts.LOGIN_SUCCESS){
                map.put("msg","用户登陆成功");
                map.put("success",true);
                httpSession.setAttribute("username",username);

                String id = httpSession.getId();
                redisTemplate.opsForHash().put("spring:session:sessions:"+id,"sessionAttr:username",username);
                redisTemplate.opsForHash().put("spring:session:sessions:"+id,"maxInactiveInterval",3600);

                String s =(String)redisTemplate.opsForHash().get("spring:session:sessions:"+id,"sessionAttr:username");

                System.out.println("登录成功之后用户名："+s);
                /*sessionId =  request.getSession().getId();
                stringRedisTemplate.opsForValue().set("sessionId",sessionId);*/

            }else if(flag == Consts.LOGIN_USER_NOT_EXISTS){
                map.put("msg","用户名不存在");
                map.put("success", false);
            }else if(flag == Consts.LOGIN_USER_PW_ERROE){
                map.put("msg","用户名或者密码错误");
                map.put("success",false);
            }
        }
        return JSON.toJSONString(map);
    }


   /* @RequestMapping("say/{username}")
    public User say(@PathVariable("username") String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("111");
        return user;
    }*/

   //注册用户
   @RequestMapping(value = "/reg", method = RequestMethod.POST)
   public String regUser(@RequestBody User user){
       String username = user.getUsername().trim();
       String password = user.getPassword().trim();
       Map<String, Object> map = new HashMap<>();
       if(username == null || username.equals("")|| password==null || password.equals("")){
           map.put("msg","用户名或者密码不能为空");
           map.put("success",false);
           return JSON.toJSONString(map);
       }
       String str = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";
       Pattern regex = Pattern.compile(str);
       Matcher matcher = regex.matcher(username);
       if (!matcher.matches()){
           map.put("msg","请输入正确的邮箱格式");
           map.put("success", false);
           logger.info("reg: 邮箱格式不正确");
           return JSON.toJSONString(map);
       }else if(password.length() > 20){
           map.put("msg","请输入小于20位的密码");
           map.put("success", false);
           logger.info("reg:密码长度大于20");
           return JSON.toJSONString(map);
       }else{
           Date createTime = new Date();
           /*EmailAuthenticator authenticator = new EmailAuthenticator(username, password);
           repository.save(authenticator);
           System.out.println("注册的时候EmailAuthenticator:"+authenticator.toString());
           *//*HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
           Map<String, Object> hashMap = new HashMap<>();
           hashMap.put("sessionAuth:authenticator",authenticator);
           hash.putAll("spring:session:sessions:"+httpSession.getId(),hashMap);*//*
           redisTemplate.opsForHash().put("authenticators","username:"+username,authenticator.getId());
           System.out.println("注册的时候EmailAuthenticatorId:"+authenticator.getId());*/

/*
           System.out.println("注册的时候从redis中取出EmailAuthenticator："+repository.findOne(authenticator.getId()));
*/

/*           Map<String,Object> authMap = new HashMap<>();
           authMap.put("username",username);
           authMap.put("authenticator",authenticator);*/

           /*repository.save(authenticator);
           stringRedisTemplate.opsForValue().set(username,authenticator.getId());*/
        /*      Map<byte[], byte[]> mappedHash = mapper.toHash(authenticator);
           hashOperations.putAll(username, mappedHash);*/

           PwDigest pwDigest = new PwDigest();
           //加密
           password = pwDigest.digest(password);
           //判断用户名是否已经存在
           if(userService.regUser(username,password,createTime)){

               map.put("msg","注册成功");
               map.put("success", true);
               logger.info("reg: 注册成功");
           }
           else {
               map.put("msg","用户名已存在");
               map.put("success", false);
               logger.info("reg: 用户名已存在");
           }
           return JSON.toJSONString(map);
       }
   }

   @RequestMapping("/getAllUsers")
   public Object getAllUsers(){
       Map<String,Object> map = new HashMap<>();
       ArrayList<String> userList = userService.getAllUsers();
       Map<String,Object> userMap =  new HashMap<>();
       userMap.put("userList",userList);
       map.put("success", true);
       map.put("data", userMap);
       return map;
   }


/*    public void writeHash(String key, Authenticator anthenticator) {

        Map<byte[], byte[]> mappedHash = mapper.toHash(anthenticator);
        hashOperations.putAll(key, mappedHash);
    }

    public Authenticator loadHash(String key) {

        Map<byte[], byte[]> loadedHash = hashOperations.entries("key");
        return (Authenticator) mapper.fromHash(loadedHash);
    }*/

    @RequestMapping("/queryRoleId")
    public Object queryRoleId(HttpSession httpSession){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        int roleId = userService.queryRoleId(username);
        dataMap.put("roleId",roleId);
        dataMap.put("msg","查询用户角色成功");

        map.put("data",dataMap);
        map.put("success",true);

        return map;

    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Object logout(HttpServletRequest request, HttpSession httpSession)throws Exception{
        String username = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        redisTemplate.opsForHash().delete("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username",username);
        request.getSession().invalidate();
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("msg","注销成功");
        map.put("data",dataMap);
        map.put("success",true);

        return map;
    }

    @RequestMapping("/queryDevelopNames")
    public Object queryDevelopNames(){

        List<String> developNameList = userService.developerNames();

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("msg","查询开发人员数据成功");
        dataMap.put("developNameList",developNameList);
        map.put("data",dataMap);
        map.put("success",true);

        return map;
    }

}


