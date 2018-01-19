package com.wizinno.apollo.manage.controller;

import com.wizinno.apollo.app.domain.NetTestLogMapper;
import com.wizinno.apollo.app.domain.NetworkSpeedMapper;
import com.wizinno.apollo.app.domain.model.NetTestLog;
import com.wizinno.apollo.app.domain.model.NetworkSpeed;
import com.wizinno.apollo.common.condition.NetTestCondition;
import com.wizinno.apollo.common.controller.BaseController;
import com.wizinno.apollo.common.data.ErrorType;
import com.wizinno.apollo.common.data.PageDataVO;
import com.wizinno.apollo.common.data.ResponseVO;
import com.wizinno.apollo.common.data.TestEvent;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;



import com.wizinno.apollo.common.utils.ExportExcel;
import com.wizinno.apollo.common.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by HP on 2017/9/4.
 */

@RestController
@RequestMapping("/api/manage/netTestLog")
public class NetTestLogsController extends BaseController {

    @Autowired
    private NetTestLogMapper netTestLogMapper;

    @RequestMapping(method = RequestMethod.GET)
    public  ResponseVO getNetTestLogs(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "length",defaultValue = "10") Integer length,
                                      @RequestParam(value = "filter",required = false) String filter,
                                      @RequestParam(value = "uName",required = false) String uName,
                                      @RequestParam(value = "testEvent",required = false) Integer testEvent,
                                      @RequestParam(value = "startTime",required = false)Long startTime,
                                      @RequestParam(value = "endTime",required = false)Long endTime)throws CustomException{
        if (page <0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        NetTestCondition con = new NetTestCondition();
        con.setSize(length);
        con.setStart((page-1) * length);
        con.setTestEvent(testEvent);

        if (uName != null && !"".equals(uName.trim())){
            con.setuName(uName.trim());
        }

        if (filter != null && !"".equals(filter.trim())){
            con.setFilter(filter.trim());
        }

        if (startTime != null){
            if (startTime != 0){
                con.setStartTime(new Date(startTime));
            }

        }
        if (endTime != null){
            if (endTime != 0){
                Calendar   calendar   =   new GregorianCalendar();
                calendar.setTime(new Date(endTime));
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动//这个时间就是日期往后推一天的结果
                con.setEndTime(calendar.getTime());
            }

        }


        List<NetTestLog> netTestLogs = netTestLogMapper.getNetTestLogs(con);
        for (NetTestLog netTestLog:netTestLogs){
            if (netTestLog.getTestEvent() != null){
                netTestLog.setTestEventDesc(TestEvent.getNameByCode(netTestLog.getTestEvent()));
            }
            if (netTestLog.getErrType() != null){
                netTestLog.setErrTypeDesc(ErrorType.getNameByCode(netTestLog.getErrType()));
            }

        }

       Long total= netTestLogMapper.getNetTestLogsTotal(con);
        return new PageDataVO(page,length,total,netTestLogs);
    }



    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public ResponseEntity getNetTestLogsExport(@RequestParam(value = "filter",required = false) String filter,
                                               @RequestParam(value = "uName",required = false) String uName,
                                               @RequestParam(value = "testEvent",required = false) Integer testEvent,
                                         @RequestParam(value = "startTime",required = false)Long startTime,
                                         @RequestParam(value = "endTime",required = false)Long endTime) throws Exception {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
        String title = "网络质量测试";
        NetTestCondition con = new NetTestCondition();
        if (testEvent != null){
            con.setTestEvent(testEvent);
            title=title+"-"+TestEvent.getNameByCode(testEvent);
        }


        if (filter != null && !"".equals(filter.trim())){
            con.setFilter(filter.trim());
            title=title+"-"+filter.trim();
        }

        if (startTime != null){
            if (startTime != 0){
                con.setStartTime(new Date(startTime));
                title=title+"-"+formatter1.format(new Date(startTime));
            }

        }
        if (endTime != null){
            if (endTime != 0){
                Calendar   calendar   =   new GregorianCalendar();
                calendar.setTime(new Date(endTime));
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动//这个时间就是日期往后推一天的结果
                con.setEndTime(calendar.getTime());
                title=title+"-"+formatter1.format(calendar.getTime());
            }

        }
        if (uName != null && !"".equals(uName.trim())){
            con.setuName(uName.trim());
            title=title+"-"+uName.trim();
        }


        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);

        List<NetTestLog> data = netTestLogMapper.getNetTestLogs(con);
        title= title+"-"+"("+formatter1.format(new Date())+")";

        String[] rowsName = new String[]{"序号","test_id", "时间", "事件", "用户姓名", "手机号码",
                "设备IMEI", "运营商", "网络类型","SSID", "IP地址", "地理位置"
                , "移动国家代码MCC","移动网络号码MNC","位置区域码LAC", "基站编号CID","" +
                "基站信号强度BSSS","上传速度","下载速度","丢包率", "平均延时","抖动(+/- ms)","错误类型","用户截屏","文字反馈","前台APP"};


        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        NetTestLog dto = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < data.size(); i++) {
            dto = data.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getTestId();
            objs[2] = formatter.format(dto.getTestTime());
            objs[3] = TestEvent.getNameByCode(dto.getTestEvent());
            objs[4] = dto.getUserName();
            objs[5] = dto.getMobileNum();
            objs[6] = dto.getDeviceImei();
            objs[7] = dto.getOperator();
            objs[8] = dto.getNetType();
            objs[9] = dto.getSsid();
            objs[10] = dto.getIpAddr();
            objs[11] = dto.getGps();
            objs[12] = dto.getMcc();
            objs[13] = dto.getMnc();
            objs[14] = dto.getLac();
            objs[15] = dto.getCid();
            objs[16] = dto.getBsss();
            objs[17] = dto.getTransSpeedUp();
            objs[18] = dto.getTransSpeedDown();
            objs[19] = dto.getPakageLossProb();
            objs[20] = dto.getAveTransDelay();
            objs[21] = dto.getTransJitterP()+"/"+dto.getTransJitterN();
            objs[22] = ErrorType.getNameByCode(dto.getErrType());
            objs[23] = dto.getScreenShap();
            objs[24] = dto.getDescribe();
            objs[25] = dto.getAppName();

            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMdd").format(new Date());
        String fileName = title + ".xls";


        File file = ex.export(UUID.randomUUID().toString() + ".xls");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-disposition","attachment;filename="+URLEncoder.encode(fileName,"UTF-8")+".xls");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);


        return responseEntity;

    }


    @RequestMapping(value = "/photo/{testId}",method = RequestMethod.GET)
    public ResponseEntity getPhoto(@PathVariable("testId") String testId) throws IOException {
        String photoName=testId+".jpg";

        File  file = FileUtil.getPhoto(photoName);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("fileName",photoName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);
        return responseEntity;
    }



}
