package com.wizinno.apollo.app.controller;

import com.google.gson.Gson;
import com.wizinno.apollo.app.domain.NetTestLogMapper;
import com.wizinno.apollo.app.domain.model.NetTestLog;
import com.wizinno.apollo.common.config.Config;
import com.wizinno.apollo.common.controller.BaseController;
import com.wizinno.apollo.common.data.ResponseVO;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
import com.wizinno.apollo.common.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by HP on 2017/9/3.
 */

@RestController
@RequestMapping("/api/app/netTestLog")
public class NetTestLogController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private NetTestLogMapper netTestLogMapper;

    String path = Config.nfsMntRw;
    Logger log = LoggerFactory.getLogger(NetTestLogController.class);

    private Gson gson = new Gson();

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createNetTestLog(@RequestBody NetTestLog netTestLog) throws CustomException {

/*        log.info("=========createNetTestLog========");
        log.info(gson.toJson(netTestLog));
        log.info("=========End========");*/

        if (netTestLog.getTestId() == null) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (netTestLog.getNeighborStationInfo().length() > 200){
            netTestLog.setNeighborStationInfo(netTestLog.getNeighborStationInfo().substring(0,200));
        }

        try {
            netTestLogMapper.insert(netTestLog);
        } catch (Exception e) {
            throw new CustomException(CustomExceptionCode.DateRepeat);

        }

        return new ResponseVO(netTestLog);
    }

    @RequestMapping(value = "/describe", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO addSpitslot(@RequestParam("testId") String testId,
                                  @RequestParam("describe") String describe) throws CustomException {

        log.info("=========addSpitslot========");
        log.info("testId:"+testId);
        log.info("describe:"+describe);
        log.info("=========End========");

        NetTestLog netTestLog = netTestLogMapper.selectByPrimaryKey(testId);
        if (netTestLog == null) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        netTestLog.setDescribe(describe);
        netTestLogMapper.updateByPrimaryKey(netTestLog);


        return new ResponseVO();
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.POST)
    public void fileUpload(@PathVariable("testId") String testId) throws CustomException,IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String realPath = null;
        String realName = testId + ".jpg";

        NetTestLog netTestLog = netTestLogMapper.selectByPrimaryKey(testId);

        if (netTestLog == null) {
            pw.println("netTestLog not exist");
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }

        //利用request对象返回客户端来的输入流
        try (ServletInputStream sis = request.getInputStream()) {

            File f = new File(path + realName);

            //realPath = path +realName;
            //获取真实路径
            realPath = f.getAbsolutePath();
            File file1 = new File(realPath);
            Runtime.getRuntime().exec("chmod 777 -R " + file1);
            file1.setWritable(true, false);
            if (!file1.exists()) {
                if (file1.getParentFile().mkdir()) {
                    System.out.print("创建文件夹成功");
                }
            }

            log.debug("file path:" + realPath);

            OutputStream os = new FileOutputStream(file1);
            BufferedOutputStream bos = new BufferedOutputStream(os);


            byte[] buf = new byte[1024];

            int length = 0;
            length = sis.readLine(buf, 0, buf.length);//使用sis的读取数据的方法
                try {
                    while (length != -1) {
                    bos.write(buf, 0, length);
                    length = sis.read(buf);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    pw.print("error write read");
                    log.debug("Stream error write read"+testId);
                }


            try {
                bos.close();
            }catch (Exception e){
                e.printStackTrace();
                log.debug("bos close+++++++++++++++++++++++"+testId);
            }
            try {
                os.close();
            }catch (Exception e){
                e.printStackTrace();
                log.debug("os close++++++++++++++++"+testId);
            }
            try {
                sis.close();
            }catch (Exception e){
                e.printStackTrace();
                log.debug("sis close++++++++++++++++++"+testId);
            }

            if (file1.length() > 0){
                netTestLog.setScreenShap(realName);
                netTestLogMapper.updateByPrimaryKey(netTestLog);
                //回复给客户端一个信息
                pw.println("success");
            }else {
                if (file1.delete()){
                    pw.print("file delete success");
                    if(netTestLog.getScreenShap() != null){
                        netTestLog.setScreenShap(null);
                        netTestLogMapper.updateByPrimaryKey(netTestLog);
                    }

                }

            }

        }
    }


/*    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void fileUploadTest() throws CustomException,IOException, ServletException {
        String path1 = "D:\\testimg\\";

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String realPath = null;
        String realName = "test" + RandomUtil.randomFor6()+ ".jpg";

 *//*       NetTestLog netTestLog = netTestLogMapper.selectByPrimaryKey(testId);
        log.info("=========photostart========");
        log.info("netTestLog"+testId);
        log.info("=========photoEnd========");
        if (netTestLog == null) {
            pw.println("netTestLog not exist");
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
*//*
        //利用request对象返回客户端来的输入流
        try (ServletInputStream sis = request.getInputStream()) {

            File f = new File(path1 + realName);

            //realPath = path +realName;
            //获取真实路径
            realPath = f.getAbsolutePath();
            File file1 = new File(realPath);
           *//* Runtime.getRuntime().exec("chmod 777 -R " + file1);*//*
            file1.setWritable(true, false);
            if (!file1.exists()) {
                if (file1.getParentFile().mkdir()) {
                    System.out.print("创建文件夹成功");
                }
            }

            log.debug("file path:" + realPath);

            OutputStream os = new FileOutputStream(file1);
            BufferedOutputStream bos = new BufferedOutputStream(os);


            byte[] buf = new byte[1024];

            int length = 0;
            length = sis.readLine(buf, 0, buf.length);//使用sis的读取数据的方法
            try {
                while (length != -1) {
                    bos.write(buf, 0, length);
                    length = sis.read(buf);
                }
            }catch (IOException e){
                e.printStackTrace();
                pw.print("error write read");
            }


*//*            if (realName != null) {
                netTestLog.setScreenShap(realName);
            }
            netTestLogMapper.updateByPrimaryKey(netTestLog);

            log.info("=========successStart========");
            log.info("netTestLog"+testId);
            log.info("=========successEnd========");*//*

            try {
                bos.close();
            }catch (Exception e){
                e.printStackTrace();
                log.debug("bos close+++++++++++++++++++++++");
            }
            try {
                os.close();
            }catch (Exception e){
                e.printStackTrace();
                log.debug("os close++++++++++++++++");
            }
            try {
                sis.close();
            }catch (Exception e){
                e.printStackTrace();
                log.debug("sis close++++++++++++++++++");
            }



            if (file1.length() > 0){
                //回复给客户端一个信息
                pw.println("success");
            }else {
                pw.print("error");
                if (file1.delete()){
                    pw.print("file delete success");
                }

            }

        }
    }*/


}
