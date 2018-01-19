package com.wizinno.apollo.manage.controller;

import com.wizinno.apollo.app.domain.NetworkSpeedMapper;
import com.wizinno.apollo.app.dto.UserAndNetworksDto;
import com.wizinno.apollo.common.condition.NetworksSpeedCondition;
import com.wizinno.apollo.common.data.PageDataVO;
import com.wizinno.apollo.common.data.ResponseVO;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
import com.wizinno.apollo.common.utils.ExportExcel;
import com.wizinno.apollo.common.utils.ExportExcel2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by HP on 2017/9/21.
 */
@RestController
@RequestMapping("/api/manage/networks")
public class NetworksSpeedController {

    @Autowired
    private NetworkSpeedMapper networkSpeedMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getNetworksSpeeds(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                        @RequestParam(value = "length",defaultValue = "10") Integer length,
                                        @RequestParam(value = "startTime",required = false)Long startTime,
                                        @RequestParam(value = "endTime",required = false)Long endTime,
                                        @RequestParam(value = "mobileNum",required = false)String mobileNum,
                                        @RequestParam(value = "uName",required = false)String uName,
                                        @RequestParam(value = "operator",required = false)String operator,
                                        @RequestParam(value = "uploadMaximum",required = false)Double uploadMaximum,
                                        @RequestParam(value = "downMaximum",required = false)Double downMaximum)throws CustomException{

        if (page <= 0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        NetworksSpeedCondition con = new NetworksSpeedCondition();
        con.setSize(length);
        con.setStart((page-1) * length);
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
        }
        if (operator != null && !operator.trim().equals("")){
            con.setOperator(operator.trim());
        }
        if (uploadMaximum != null ){
            con.setUploadMaximum(uploadMaximum);
        }

        if (downMaximum != null ){
            con.setDownMaximum(downMaximum);
        }


        if (startTime != null){
            if (startTime != 0){
                con.setStartTime(new Date(startTime));
            }

        }
        System.out.print(uploadMaximum);
        if (endTime != null){
            if (endTime != 0){
                Calendar   calendar   =   new GregorianCalendar();
                calendar.setTime(new Date(endTime));
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动//这个时间就是日期往后推一天的结果
                con.setEndTime(calendar.getTime());
            }

        }


        List<UserAndNetworksDto> dtos=networkSpeedMapper.getNetworksSpeeds(con);
       Long total= networkSpeedMapper.getNetworksSpeedsTotal(con);

        return new PageDataVO(page,length,total,dtos);

    }


    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public ResponseEntity getNetworksExport(@RequestParam(value = "startTime",required = false)Long startTime,
                                            @RequestParam(value = "endTime",required = false)Long endTime,
                                            @RequestParam(value = "mobileNum",required = false)String mobileNum,
                                            @RequestParam(value = "uName",required = false)String uName,
                                            @RequestParam(value = "operator",required = false)String operator,
                                            @RequestParam(value = "uploadMaximum",required = false)Double uploadMaximum,
                                            @RequestParam(value = "downMaximum",required = false)Double downMaximum) throws Exception {

        String title = "网络速度";
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");

        NetworksSpeedCondition con = new NetworksSpeedCondition();
        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
            title=title+"-"+mobileNum.trim();
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
            title=title+"-"+uName.trim();
        }
        if (operator != null && !operator.trim().equals("")){
            con.setOperator(operator.trim());
        }
        if (startTime != null){
            if (startTime != 0){
                con.setStartTime(new Date(startTime));
                title=title+"-"+formatter1.format(new Date(startTime));
            }

        }
        System.out.print(uploadMaximum);
        if (endTime != null){
            if (endTime != 0){
                Calendar   calendar   =   new GregorianCalendar();
                calendar.setTime(new Date(endTime));
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动//这个时间就是日期往后推一天的结果
                con.setEndTime(calendar.getTime());
                title=title+"-"+formatter1.format(calendar.getTime());
            }

        }
        if (uploadMaximum != null ){
            con.setUploadMaximum(uploadMaximum);
            title=title+"-"+uploadMaximum;
        }

        if (downMaximum != null ){
            con.setDownMaximum(downMaximum);
            title=title+"-"+downMaximum;
        }

        List<UserAndNetworksDto> dtos=networkSpeedMapper.getNetworksSpeeds(con);
        title= title+"-"+"("+formatter1.format(new Date())+")";

        String[] rowsName = new String[]{"序号","user_id", "时间", "用户姓名", "手机号码",
                "网络类型","SSID","运营商","上传最大值",
                "上传最小值", "上传平均值","下载最大值", "下载最小值", "下载平均值","前台APP"};


        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        UserAndNetworksDto dto = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < dtos.size(); i++) {
            dto = dtos.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getUserId();
            objs[2] = formatter.format(dto.getTimeStamp());
            objs[3] = dto.getuName();
            objs[4] = dto.getMobileNum();
            if (dto.getNetType() == null){
                objs[5] = null;
            }else {
                objs[5] = dto.getNetType();
            }
            if (dto.getSsid() == null){
                objs[6] = null;
            }else {
                objs[6] = dto.getSsid();
            }

            if (dto.getOperator() == null){
                objs[7] = null;
            }else {
                objs[7] = dto.getOperator();
            }

            objs[8] = dto.getUploadMaximum();
            objs[9] = dto.getUploadMinimum();
            objs[10] = dto.getUploadAvg();
            objs[11] = dto.getDownMaximum();
            objs[12] = dto.getDownMinimum();
            objs[13] = dto.getDownAvg();
            objs[14] = dto.getAppPackages();

            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMdd").format(new Date());
        String fileName = title + ".xls";


        File file = ex.export(UUID.randomUUID().toString() + ".xls");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8")+".xls");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);


        return responseEntity;

    }
}
