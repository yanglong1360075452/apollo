package com.wizinno.apollo.manage.controller;

import com.wizinno.apollo.app.domain.DeviceMapper;
import com.wizinno.apollo.app.domain.model.Device;
import com.wizinno.apollo.app.domain.model.User;
import com.wizinno.apollo.common.condition.DeviceCondition;
import com.wizinno.apollo.common.condition.UserCondition;
import com.wizinno.apollo.common.data.PageDataVO;
import com.wizinno.apollo.common.data.ResponseVO;
import com.wizinno.apollo.common.data.SexEnum;
import com.wizinno.apollo.common.data.StatusEnum;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
import com.wizinno.apollo.common.utils.AgeByBirthUtil;
import com.wizinno.apollo.common.utils.ExportExcel;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by HP on 2017/9/8.
 */
@RestController
@RequestMapping("/api/manage/devices")
public class DevicesController {

    @Autowired
    private DeviceMapper deviceMapper;

    @RequestMapping(method = RequestMethod.GET)
    private ResponseVO getDevices(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                  @RequestParam(value = "length",defaultValue = "10") Integer length,
                                  @RequestParam(value = "mobileNum",required = false)String mobileNum,
                                  @RequestParam(value = "uName",required = false)String uName,
                                  @RequestParam(value = "operator",required = false)String devBrand)throws CustomException{

        if (page <= 0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        DeviceCondition con = new DeviceCondition();
        con.setSize(length);
        con.setStart((page-1) * length);
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
        }

        if (devBrand != null && !devBrand.trim().equals("")){
            con.setDevBrand(devBrand.trim());
        }

        List<Device> devices=deviceMapper.getDevices(con);
        for (Device device:devices){
            device.setStateDesc(StatusEnum.getNameByCode(device.getState()));
        }
        Long total=deviceMapper.getDevicesTotal(con);

        return new PageDataVO(page,length,total,devices);
    }

    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public ResponseEntity getUsersExport(@RequestParam(value = "mobileNum",required = false) String mobileNum,
                                         @RequestParam(value = "uName",required = false)String uName,
                                         @RequestParam(value = "operator",required = false)String devBrand
    ) throws Exception {

        String title = "设备信息";
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd ");

        DeviceCondition con = new DeviceCondition();
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
            title=title+"-"+mobileNum.trim();
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
            title=title+"-"+uName.trim();
        }

        if (devBrand != null && !devBrand.trim().equals("")){
            con.setDevBrand(devBrand.trim());
        }



        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);

        List<Device> data = deviceMapper.getDevices(con);
        for (Device device:data){
            device.setStateDesc(StatusEnum.getNameByCode(device.getState()));
        }

        String[] rowsName = new String[]{"序号", "手机品牌", "手机型号", "IMEI", "Android版本",
                "厂商制定Android版本", "关联手机号", "用户名", "绑定时间"};

        title= title+"-"+"("+formatter2.format(new Date())+")";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        Device dto = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");

        for (int i = 0; i < data.size(); i++) {
            dto = data.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getDevBrand();
            objs[2] = dto.getDevModel();
            objs[3] = dto.getDevImei();
            objs[4] = dto.getAndroidApiVer();
            objs[5] = dto.getFactoryOsVer();
            objs[6] = dto.getdMobilenum();
            objs[7] = dto.getuName();
            objs[8] = dto.getdLogintime();
            /*objs[9] = dto.getStateDesc();*/

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
