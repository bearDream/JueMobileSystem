package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.FileUploadMapper;
import com.beardream.model.FileUpload;
import com.beardream.model.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Created by beardream on 2017/5/16.
 * 文件上传控制器
 */
@RestController
@RequestMapping("/api/mobile/upload")
@CrossOrigin
@Api(value = "文件上传",description = "提供RESTful风格API的文件上传功能")
public class UploadController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    public static final String ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;


    @Value("${env}")
    private String env;

    @Value("${dev_savedir}")
    private String dev_savedir;

    @Value("${pro_savedir}")
    private String pro_savedir;

    @Value("${upload_url}")
    private String upload_url;

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    public UploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /*
        多文件上传
     */
    @PostMapping(value = "/multiUpload")
    public Result FileUploads(MultipartFile[] file, HttpServletRequest request){
        // 上传目录
        String uploadDir, url;
        StringBuilder file_url = new StringBuilder();
        if (env.equals("dev")){
            // 开发环境下的存储路径
            uploadDir = dev_savedir;
        }else {
            // 生产环境下的存储路径
            uploadDir = pro_savedir;
        }
        try {
            // 如果目录不存在则创建
            File dir = new File(uploadDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            // 遍历数组执行executeupload上传方法
            for (int i = 0; i < file.length; i++) {
                if (file[i] != null){
                    file_url.append(executeUpload(uploadDir, file[i]));
                    file_url.append("|");
                }
            }
            // 删除最后一个字符 “|”
            url = file_url.substring(0,file_url.length()-1);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"文件上传失败,原因："+e.getMessage());
        }
        return ResultUtil.success(url);
    }


    /*
        单个文件上传
        type: business/dish/user
        存储的路径：
        JueImage-->businessImage-->20170521-->对应的文件
        JueImage-->dishImage-->20170521-->对应的文件
        JueImage-->userImage-->20170521-->对应的文件
        JueImage-->articleImage-->20170521-->对应的文件
     */
    @PostMapping(value = "/singleUpload")
    public Result FileUpload(@RequestParam(required = false) String type, MultipartFile file, HttpServletRequest request) {
        // 上传目录
        System.out.println(type);
        if (!TextUtil.isEmpty(type)){
            return ResultUtil.error(-1,"请携带type参数又进行上传");
        }
        String uploadDir, url;
        System.out.println(env);
        if (env.equals("dev")){
            // 开发环境下的存储路径
            uploadDir = dev_savedir;
        }else {
            // 生产环境下的存储路径
            uploadDir = pro_savedir;
        }
        try {
            log.info("uploadDir={}", uploadDir);
            uploadDir = uploadDir + type + "Image" + File.separator + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separator;
            // 目录不存在则自动创建
            File dir = new File(uploadDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            String file_url = executeUpload(uploadDir, file);
            url = upload_url + "?url=" + file_url;// 将图片路径返回
            System.out.println("存储的url: " + url);
            // 将相关的文件信息写入数据库
            FileUpload files = new FileUpload();
            files.setType(type);
            files.setUrl(url);
            files.setPath(dir.getPath() + File.separator + file_url);
            files.setFileName(file_url);
            files.setSize(file.getSize()+"");
            files.setAddTime(new Date());
            fileUploadMapper.insert(files);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"文件上传失败,原因："+e.getMessage());
        }
        return ResultUtil.success(url);
    }

    /*
        公共上传方法，无论单文件还是多文件上传均可如此
     */
    public String executeUpload(String uploadDir, MultipartFile file) throws IOException {

        // 文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 上传的文件名字  以uuid来命名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + suffix;
        // 服务器端保存的文件对象
        File serverFile = new File(uploadDir+filename);
        // 保存
        file.transferTo(serverFile);

        return serverFile.getName();
    }
}
