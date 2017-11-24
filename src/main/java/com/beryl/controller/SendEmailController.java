package com.beryl.controller;

import com.beryl.model.EmailAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/*
*
 * Created by qjnup on 2016/11/28.

*/


@RestController
@RequestMapping("/autoTest/email")
public class SendEmailController {
/*

@Autowired
public EmailRepository repository;
*/

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisTemplate redisTemplate;

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public Object sendEmail(@RequestBody Map param) {

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();


        String emailTo = (String)param.get("emailTo");
/*
        String emailForm = (String)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAttr:username");
*/
        String subject = (String)param.get("subject");
        String content = (String)param.get("content");
        String emailForm = "emailPath";

        /*
        String subject = "test";
        String content = "test";*/


        Properties properties = new Properties();
//设置SMTP邮箱发送服务器
//      properties.put("mail.smtp.host", "smtp.qq.com");
//      properties.put("mail.smtp.host", "smtp.163.com");

//      properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");

//授权发送的邮箱
/*
        String id = stringRedisTemplate.opsForValue().get(emailForm);

        Authenticator authenticator = (Authenticator)redisTemplate.opsForHash().get("spring:session:sessions:"+httpSession.getId(),"sessionAuth:authenticator");
*/

        Authenticator authenticator = new EmailAuthenticator("email","password");
        System.out.println("发送邮件Authenticator:"+authenticator);
        Session session = Session.getInstance(properties, authenticator);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailForm));
//            msg.setFrom(new InternetAddress("emailPath"));
//            msg.setRecipients(Message.RecipientType.TO,"emailPath");
            msg.setRecipients(Message.RecipientType.TO, emailTo);
            msg.setSubject(subject,"UTF-8");
            msg.setSentDate(new Date());
            Multipart mainPart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent(content.trim(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            msg.setContent(mainPart);
            /*msg.setText(content);*/
            Transport.send(msg);
            dataMap.put("msg","邮件发送成功");
            map.put("data",dataMap);
            map.put("success",true);
        } catch (MessagingException mex){
            System.out.println("send failed, exception: " + mex);
            dataMap.put("msg","邮件发送失败");
            map.put("data",dataMap);
            map.put("success",true);
        }
             return map;
    }
}




