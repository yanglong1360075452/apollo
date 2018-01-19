package com.wizinno.apollo.manage.controller;

import com.wizinno.apollo.app.domain.AppInstallInfoMapper;
import com.wizinno.apollo.app.domain.model.AppInstallInfo;
import com.wizinno.apollo.app.dto.AppInstallAndDeviceDto;
import com.wizinno.apollo.app.dto.NetTestAndAppRunDto;
import com.wizinno.apollo.common.condition.AppInstallsCondition;
import com.wizinno.apollo.common.condition.AppRunningsCondition;
import com.wizinno.apollo.common.data.PageDataVO;
import com.wizinno.apollo.common.data.ResponseVO;
import com.wizinno.apollo.common.data.TestEvent;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
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
 * Created by HP on 2017/9/15.
 */

@RestController
@RequestMapping("/api/manage/appInstalls")
public class AppInstallInfosController {

    @Autowired
    private AppInstallInfoMapper appInstallInfoMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getAppInstalls(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                     @RequestParam(value = "length",defaultValue = "10") Integer length,
                                     @RequestParam(value = "mobileNum",required = false)String mobileNum,
                                     @RequestParam(value = "uName",required = false)String uName,
                                     @RequestParam(value = "operator",required = false)String operator)throws CustomException{

        if (page <= 0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        AppInstallsCondition con = new AppInstallsCondition();
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

        List<AppInstallAndDeviceDto> appInstallInfos=appInstallInfoMapper.getAppInstalls(con);
       Long total= appInstallInfoMapper.getAppInstallsTotal(con);
        return new PageDataVO(page,length,total,appInstallInfos);
    }



    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public ResponseEntity getAppInstallsExport(@RequestParam(value = "mobileNum",required = false) String mobileNum,
                                         @RequestParam(value = "uName",required = false)String uName,
                                         @RequestParam(value = "operator",required = false)String operator ) throws Exception {

        String title = "app安装统计";
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd ");

        AppInstallsCondition con = new AppInstallsCondition();
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

        List<AppInstallAndDeviceDto> dtos=appInstallInfoMapper.getAppInstalls(con);

        String[] rowsName = new String[]{"序号", "时间", "用户姓名", "手机号码", "设备IMEI",
                "已安装APP"};

        title= title+"-"+"("+formatter2.format(new Date())+")";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        AppInstallAndDeviceDto dto = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");

        for (int i = 0; i < dtos.size(); i++) {
            dto = dtos.get(i);
            objs = new Object[rowsName.length];
            objs[1] = formatter.format(dto.getCheckTime());
            objs[2] = dto.getuName();
            objs[3] = dto.getMobileNum();
            objs[4] = dto.getDevImei();
            objs[5] = dto.getAppInstallTotal();
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
