package com.wizinno.apollo.common.utils;

import com.wizinno.apollo.common.config.Config;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/8/15.
 */
public class FileUtil {

    static String path = Config.nfsMntRw;
   /* static String path = Config.nfsMntRw;*/

    public static String fileUpload(MultipartFile file) throws IOException {


       if (file != null) {// 判断上传的文件是否为空
            String realName=null;
            String realPath=null;

            String type = null;// 文件类型
            String fileName = file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:" + fileName);
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {
                // 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
//                    String realPath = request.getSession().getServletContext().getRealPath("/");
                    /*        String strDirPath = request.getSession().getServletContext().getRealPath("/");
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        StringBuffer requestURL = request.getRequestURL();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/");

        String realPath1 = request.getRealPath("../");
        String contextPath1 = request.getContextPath();*/
                    // 自定义的文件名称
                    // 设置存放图片文件的路径

                    realName=System.currentTimeMillis()+fileName;
                    File f = new File(path + realName);

                    //realPath = path +realName;
                    //获取真实路径
                    realPath=f.getAbsolutePath();
                    File file1 = new File(realPath);
                    //是否存在，不存在则创建
                    if(!file1.exists()){
                        if (file1.getParentFile().mkdir()){
                            System.out.print("创建文件夹成功");
                        }
                    }
                    System.out.println("存放图片文件的路径:" + path);
                    // 转存文件到指定的路径
                    file.transferTo(new File(realPath));
                    System.out.println("文件成功上传到指定目录下");
                } else {
                    System.out.println("不是我们想要的文件类型,请按要求重新上传");
                    return null;
                }
                return realName;
            } else {
                System.out.println("文件类型为空");
                return null;
            }
        } else {
            System.out.println("没有找到相对应的文件");
            return null;
        }


    }

    public static File getPhoto(String photoName) throws IOException {
        File file = new File(path + photoName);
        String absolutePath = file.getAbsolutePath();

        File file1 = new File(absolutePath);
        Runtime.getRuntime().exec("chmod 777 -R " + file1);

        return file1;

    }
}
