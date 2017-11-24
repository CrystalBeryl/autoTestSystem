package com.beryl.controller;

import com.alibaba.fastjson.JSON;
import com.beryl.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by qjnup on 2016/10/30.
 */

@RestController
@RequestMapping("/autoTest/file")
public class FileController {

    private String filePath;
    private List<String> filePathList = new ArrayList<String>();
    private String fileSize;

    @Autowired
    private Environment env;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FormService formService;


    @RequestMapping("/test")
    public Object test(@ModelAttribute("msg") String msg) {

        Map<String, Object> map = new HashMap();

        try {
            throw new Exception("测试异常" + msg);
        } catch (Exception e) {
            map.put("msg",msg);
            return map;
        }

    }



    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    //btnFile对应页面的name属性
    @ResponseBody
    public Object fileUpLoad( @RequestBody MultipartFile[] btnFile, HttpServletRequest request,@PathVariable("id")String id) throws Exception {
        Map<String, Object> map = new HashMap();
        Map<String, Object> dataMap =  new HashMap();
        System.out.println("表单编号："+id);
        int formId = Integer.parseInt(id);
    //    filePath = env.getProperty("sys.file.savePath");
    //    fileSize = env.getProperty("spring.http.multipart.max-request-size");

/*        try {
            // if (btnFile[0].getSize() > 3000000) {//文件长度
            //@RequestBody MultipartFile[] btnFile,

            throw new Exception("文件上传过大");
            //}
        }catch (Exception e){
            map.put("msg", msg);
            map.put("success", false);
            return map;

        }*/

        try {
            //文件类型:btnFile[0].getContentType()
            //文件名称:btnFile[0].getName()
            InputStream is = btnFile[0].getInputStream();
            //String fileName = request.getParameter("fileName");
            String guid = request.getParameter("guid");
            byte[] b = new byte[(int) btnFile[0].getSize()];
            int read = 0;
            int i = 0;
            while ((read = is.read()) != -1) {
                b[i] = (byte) read;
                i++;
            }
            is.close();
            String fileHeader = env.getProperty("sys.file.savePath");

            filePath = fileHeader + guid + "." + btnFile[0].getOriginalFilename();
           // filePathList.add(filePath);
            OutputStream os = new FileOutputStream(new File(filePath));//文件原名,如a.txt
            os.write(b);
            os.flush();
            os.close();
            if(formId!=0){
                String attachment = formService.selectAttachment(formId);
                if(attachment==null || attachment.equals("")){
                    attachment = filePath;
                }else {
                    attachment = attachment+","+filePath;
                }
                formService.updateAttachment(attachment,formId);
            }
            dataMap.put("msg","文件上传成功");
            dataMap.put("filePath",filePath);
            map.put("data", dataMap);
            map.put("success", true);
            //将文件路径存放到redis下
/*            ListOperations<String, String> lop = redisTemplate.opsForList();
            RedisSerializer<String> redisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(redisSerializer);
            redisTemplate.setValueSerializer(redisSerializer);
            lop.leftPush("filePathList",filePath);*/
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("msg","文件上传失败");
            map.put("data", dataMap);
            map.put("success", false);
            return map;
        }

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = "application/json")
    public Object fileDelete(HttpServletRequest request,@PathVariable("id")String id){
        Map<String, Object> map = new HashMap();
        Map<String, Object> dataMap =  new HashMap();


        try{
            String guid = request.getParameter("guid");
            String fileName = request.getParameter("fileName");
            String fileHeader = env.getProperty("sys.file.savePath");

            filePath = fileHeader +guid+"."+fileName;
            File file = new File(filePath);
            boolean isDeleted = file.delete();
            if(!isDeleted){
                dataMap.put("msg","文件删除失败");
                map.put("data", dataMap);
                map.put("success", false);
                return map;
            }
            int formId = Integer.parseInt(id);
            if(formId!=0){
               String attachment = formService.selectAttachment(formId);
               String[] attachArray = attachment.split(",");
                List<String> list = new ArrayList<String>(Arrays.asList(attachArray));

                for (int i = 0; i <list.size() ; i++) {
                    if(filePath.equals(list.get(i))){
                        list.remove(i);
                    }
                }
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i <list.size() ; i++) {
                    buffer.append(list.get(i));
                }
                System.out.println("附件："+buffer.toString());
                formService.updateAttachment(buffer.toString(),formId);

            }
            dataMap.put("msg","文件删除成功");
            dataMap.put("filePath",filePath);
            map.put("data", dataMap);
            map.put("success", true);
            return map;
        }catch (Exception e) {
            dataMap.put("msg","文件删除失败");
            map.put("data", dataMap);
            map.put("success", false);
            return map;
        }
    }

/*    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception e, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        if(e instanceof MaxUploadSizeExceededException){
            Long maxSize = ((MaxUploadSizeExceededException) e).getMaxUploadSize();
            map.put("success", false);
            map.put("msg","上传文件超过最大限制"+ maxSize/1024 + "kb");
        }else
            {
                map.put("success", false);
                map.put("msg","文件上传失败");
        }
        return JSON.toJSON(map);
    }*/


}
