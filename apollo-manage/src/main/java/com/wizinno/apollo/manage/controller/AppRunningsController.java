package com.wizinno.apollo.manage.controller;

import com.wizinno.apollo.app.controller.NetTestLogController;
import com.wizinno.apollo.app.domain.AppRunningInfoMapper;
import com.wizinno.apollo.app.dto.NetTestAndAppRunDto;
import com.wizinno.apollo.common.condition.AppRunningsCondition;
import com.wizinno.apollo.common.data.*;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
import com.wizinno.apollo.common.utils.ExportExcel;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by HP on 2017/9/10.
 */

@RestController
@RequestMapping("/api/manage/appRunnings")
public class AppRunningsController {

    @Autowired
    private AppRunningInfoMapper appRunningInfoMapper;
    Logger log = LoggerFactory.getLogger(AppRunningsController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getAppRunnings(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "length",defaultValue = "10") Integer length,
                               @RequestParam(value = "mobileNum",required = false)String mobileNum,
                               @RequestParam(value = "uName",required = false)String uName,
                               @RequestParam(value = "operator",required = false)String operator)throws CustomException{

        if (page <= 0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        AppRunningsCondition con = new AppRunningsCondition();
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
       Long startTime = System.currentTimeMillis();
        List<NetTestAndAppRunDto> dtos=appRunningInfoMapper.getAppRunnings(con);
        System.out.println("查询消耗时间:"+(System.currentTimeMillis()-startTime)/1000+"秒");
        for (NetTestAndAppRunDto dto:dtos){
            if (dto.getTestEvent() != null){
                dto.setTestEventDesc(TestEvent.getNameByCode(dto.getTestEvent()));
            }
            if (dto.getIsFrontApp() == -1){
                dto.setAppNameFront("锁屏");
            }
            if (dto.getIsFrontApp() == 0){
                dto.setAppNameFront("无障碍权限未开启");
            }
            if (dto.getIsFrontApp() == 3){
                dto.setAppNameFront("7.0版本限制");
            }
         /*   dto.setAppNames(appRunningInfoMapper.getAppRunningsByTestIdAndFrontApp(dto.getTestId()));*/
        }

        Long startTime2 = System.currentTimeMillis();
        Long total= appRunningInfoMapper.getAppRunningsTotal(con);
        System.out.println("计数消耗时间:"+(System.currentTimeMillis()-startTime2)/1000+"秒");


        return new PageDataVO(page,length,total,dtos);
    }


    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public ResponseEntity getUsersExport(@RequestParam(value = "mobileNum",required = false) String mobileNum,
                                         @RequestParam(value = "uName",required = false)String uName,
                                         @RequestParam(value = "operator",required = false)String operator ) throws Exception {

        String title = "app运行统计";
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd ");

        AppRunningsCondition con = new AppRunningsCondition();
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

        List<NetTestAndAppRunDto> dtos=appRunningInfoMapper.getAppRunnings(con);
        for (NetTestAndAppRunDto dto:dtos){
            if (dto.getTestEvent() != null){
                dto.setTestEventDesc(TestEvent.getNameByCode(dto.getTestEvent()));
            }
            if (dto.getIsFrontApp() == -1){
                dto.setAppNameFront("锁屏");
            }
            if (dto.getIsFrontApp() == 0){
                dto.setAppNameFront("无障碍权限未开启");
            }
            if (dto.getIsFrontApp() == 3){
                dto.setAppNameFront("7.0版本限制");
            }
     /*       dto.setAppNames(appRunningInfoMapper.getAppRunningsByTestIdAndFrontApp(dto.getTestId()));*/
        }


        String[] rowsName = new String[]{"序号","test_id", "时间", "事件", "用户姓名", "手机号码",
                "设备IMEI", "运行中APP", "前台APP"};

        title= title+"-"+"("+formatter2.format(new Date())+")";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        NetTestAndAppRunDto dto = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");

        for (int i = 0; i < dtos.size(); i++) {
            dto = dtos.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getTestId();
            objs[2] = formatter.format(dto.getCheckTime());
            objs[3] = dto.getTestEventDesc();
            objs[4] = dto.getUserName();
            objs[5] = dto.getMobileNum();
            objs[6] = dto.getDeviceImei();
            objs[7] = dto.getAppRunTotal();
            objs[8] = dto.getAppNameFront();
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
