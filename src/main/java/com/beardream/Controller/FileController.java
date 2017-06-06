package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.FileUploadMapper;
import com.beardream.model.FileUpload;
import com.beardream.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by laxzh on 2017/5/6.
 * 返回对应文件的路径
 */
@RestController
@RequestMapping("/api/file")
@Api(value = "文件下载服务",description = "提供RESTful风格API的获取文件服务")
public class FileController {

    @Autowired
    private FileUploadMapper mFileUploadMapper;
    /*
        Put更新数据的请求只能是参数形式，不能写在body中
     */
    @ApiOperation("获取文件")
    @GetMapping
    public Result get(@RequestParam String url, HttpServletResponse response) {

        if (!TextUtil.isEmpty(url)){
            return ResultUtil.error(-1,"url不能为空");
        }
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(url);
        // 查找文件是否存在
        FileUpload fileUploadexist = mFileUploadMapper.findBySelective(fileUpload);
        if (fileUploadexist == null){
            return ResultUtil.error(-1,"文件不存在");
        }
        // 将文件以流的形式写到浏览器中
        String suffix = fileUploadexist.getFileName().substring(fileUploadexist.getFileName().lastIndexOf(".") + 1);
        if (suffix.equals("png"))
            response.setContentType("image/png");
        if (suffix.equals("jpeg"))
            response.setContentType("image/jpeg");

        File file = new File(fileUploadexist.getPath());
        if (!file.exists()){
            return ResultUtil.error(-1,"文件不存在");
        }

        try {
            InputStream in = new FileInputStream(fileUploadexist.getPath());
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                os.write(b);
            }
            in.close();
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.success(url);
    }


}