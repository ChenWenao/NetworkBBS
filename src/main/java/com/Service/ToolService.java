package com.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ToolService {


    public String FileToURL(MultipartFile file,String FileType){

        //设置imgName。
        String imgName = System.currentTimeMillis() + file.getOriginalFilename();
        //根据FileType选择存储在何处。
        String fileDirPath="";
        if("community".equals(FileType))
            fileDirPath = "static/img/CommunityImg";
        else if ("user".equals(FileType))
            fileDirPath= "static/img/UserImg";
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        try {
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + imgName);
            //输出文件路径。
            //System.out.println(newFile.getAbsolutePath());
            // 上传图片到 -》 “绝对路径”
            file.transferTo(newFile);
            //System.out.println("上传成功！");
            //设置课程图片Logo。
            return newFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return "";
        }
    }

}
