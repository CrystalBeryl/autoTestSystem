/*
package com.beryl.controller;

import com.beryl.model.EmailAuthenticator;
import com.beryl.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

*/
/**
 * Created by qjnup on 2016/12/13.
 *//*


@RestController
@RequestMapping("/autoTest/email")
public class SendEmailsController {

    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    public EmailRepository repository;
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public Object sendEmail(@RequestBody Map param, HttpSession httpSession) {

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        String emailTo = (String)param.get("emailTo");
        String emailForm = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
        System.out.println("邮件发送来自于："+emailForm);
        String subject = (String)param.get("subject");
        String content = (String)param.get("content");
        Properties properties = new Properties();
        //设置SMTP邮箱发送服务器
//      properties.put("mail.smtp.host", "smtp.qq.com");
//      properties.put("mail.smtp.host", "smtp.163.com");
       

//      properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");

        //授权发送的邮箱
        String id = (String)redisTemplate.opsForHash().get("authenticators","username:"+emailForm);

        System.out.println("邮件验证的id："+id);
        EmailAuthenticator authenticator = repository.findOne(id);
*/
/*
        Authenticator authenticator = (Authenticator)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAuth:authenticator");
*//*

*/
/*
        Authenticator authenticator = (Authenticator)redisTemplate.opsForHash().entries("spring:session:sessions:"+httpSession.getId()).get("sessionAuth:authenticator");
*//*

        System.out.println("发送邮件Authenticator:"+authenticator);
        System.out.println("发送邮件姓名："+authenticator.getUsername());
        System.out.println("发送邮件密码："+authenticator.getPassword());
*/
/*
        EmailAuthenticator authenticator2 = new EmailAuthenticator(authenticator.getUsername(),authenticator.getPassword());
*//*

        Authenticator authenticator2 = new EmailAuthenticator("emial","password");
        Session session = Session.getInstance(properties, authenticator2);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailForm));
//            msg.setFrom(new InternetAddress("emailPath"));
//            msg.setRecipients(Message.RecipientType.TO,"emailPath");
            msg.setRecipients(Message.RecipientType.TO, emailTo);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(content);
            Transport.send(msg);
            dataMap.put("msg","邮件发送成功");
            map.put("data",dataMap);
            map.put("success",true);
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
            dataMap.put("msg","邮件发送成功");
            map.put("data",dataMap);
            map.put("success",true);
        }
            return map;

        }

    }
*/
