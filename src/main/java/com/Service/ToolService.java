package com.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ToolService {

    //存储文件，转为URL返回
    //第一个参数为文件，第二个参数为文件类型，只可以为："community"，"user"其一，表示该文件属于贴吧头像还是用户头像
    public String FileToURL(MultipartFile file,String FileType){
        //设置imgName。
        String imgName = System.currentTimeMillis() + file.getOriginalFilename();
        //根据FileType选择存储在何处。
        String fileDirPath="";
        if("community".equals(FileType))
            fileDirPath = "src/main/resources/static/img/CommunityImg";
        else if ("user".equals(FileType))
            fileDirPath= "src/main/resources/static/img/UserImg";
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
            //返回图片地址
            return newFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //删除文件，返回删除结果。
    public boolean deleteFile(String fileURL){
        File file = new File(fileURL);
        if (file.exists() && file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }

}
