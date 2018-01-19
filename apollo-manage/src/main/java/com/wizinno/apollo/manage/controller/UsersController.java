package com.wizinno.apollo.manage.controller;

import com.wizinno.apollo.app.domain.UserMapper;
import com.wizinno.apollo.app.domain.model.NetTestLog;
import com.wizinno.apollo.app.domain.model.User;
import com.wizinno.apollo.common.condition.NetTestCondition;
import com.wizinno.apollo.common.condition.UserCondition;
import com.wizinno.apollo.common.data.*;
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
@RequestMapping("/api/manage/users")
public class UsersController {

    @Autowired
    private UserMapper userMapper;
    /*
    *
    * 获取用户列表
    * */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getUsers(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "length",defaultValue = "10") Integer length,
                               @RequestParam(value = "mobileNum",required = false)String mobileNum,
                               @RequestParam(value = "uName",required = false)String uName,
                               @RequestParam(value = "operator",required = false)String operator)throws CustomException{

        if (page <0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
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

        List<User> users=userMapper.getUsers(con);
                userInfo(users);
       Long total= userMapper.getUsersTotal(con);


     return new PageDataVO(page,length,total,users);
    }




    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public ResponseEntity getUsersExport(@RequestParam(value = "mobileNum",required = false) String mobileNum,
                                         @RequestParam(value = "uName",required = false)String uName,
                                         @RequestParam(value = "operator",required = false)String operator ) throws Exception {

        String title = "用户信息";
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");

        UserCondition con = new UserCondition();
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


        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);

        List<User> data = userMapper.getUsers(con);
        userInfo(data);


        String[] rowsName = new String[]{"序号", "手机号", "运营商", "网络制式", "姓名",
                "性别", "出生年月", "年龄", "注册时间"};

        title= title+"-"+"("+formatter2.format(new Date())+")";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        User dto = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");

        for (int i = 0; i < data.size(); i++) {
            dto = data.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getMobileNum();
            objs[2] = dto.getOperator();
            objs[3] = dto.getNetSystem();
            objs[4] = dto.getuName();
            objs[5] = dto.getuSexDesc();
            if (dto.getuBirthdate() == null){
                objs[6] = null;
            }else {
                objs[6] = formatter1.format(dto.getuBirthdate());
            }

            objs[7] = dto.getAge();
            objs[8] = formatter.format( dto.getuRegDate());
        /*    objs[9] = dto.getStateDesc();*/

            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMdd").format(new Date());
        String fileName = title+ ".xls";


        File file = ex.export(UUID.randomUUID().toString() + ".xls");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8")+".xls");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);


        return responseEntity;

    }


    /*
*
* 获取异常用户列表用户列表
* */
    @RequestMapping(value = "/exceptions",method = RequestMethod.GET)
    public ResponseVO getExceptionsUsers(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "length",defaultValue = "10") Integer length,
                               @RequestParam(value = "mobileNum",required = false)String mobileNum,
                               @RequestParam(value = "uName",required = false)String uName)throws CustomException{

        if (page <0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
        con.setSize(length);
        con.setStart((page-1) * length);
        con.setExceptionsTime(ExceptionsUserTime.NinetyMinutes.toString());
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
        }

        List<User> users=userMapper.getExceptionsUsers(con);
            userInfo(users);
        Long total= userMapper.getExceptionsUsersTotal(con);

        return new PageDataVO(page,length,total,users);
    }


    /*
*
* 获取无权限列表用户列表
* */
    @RequestMapping(value = "/nopermission",method = RequestMethod.GET)
    public ResponseVO getNopermissionUsers(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                         @RequestParam(value = "length",defaultValue = "10") Integer length,
                                         @RequestParam(value = "mobileNum",required = false)String mobileNum,
                                         @RequestParam(value = "uName",required = false)String uName)throws CustomException{

        if (page <0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
        con.setSize(length);
        con.setStart((page-1) * length);
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
        }
        List<User> users1=userMapper.getExceptionsUsersAll();

        List<User> users=userMapper.getNopermissionUsers(con);
        List<User> lists = new ArrayList();
        for (User u:users){
            for (User u1:users1){
                if (u.getMobileNum().equals(u1.getMobileNum())){
                    lists.add(u);
                }
            }
        }
        users.removeAll(lists);

        userInfo(users);
       /* Long total= userMapper.getNopermissionUsersTotal(con);*/
        Long size = Long.valueOf(users.size());

        return new PageDataVO(page,length,size,users);
    }



    /*
*
* 获取异常用户列表用户列表导出
* */
    @RequestMapping(value = "/exceptions/export",method = RequestMethod.GET)
    public ResponseEntity getExceptionsUsersExport(@RequestParam(value = "mobileNum",required = false) String mobileNum,
                                         @RequestParam(value = "uName",required = false)String uName,
                                         @RequestParam(value = "operator",required = false)String operator ) throws Exception {

        String title = "异常用户信息";
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");

        UserCondition con = new UserCondition();

        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);

        con.setExceptionsTime(ExceptionsUserTime.NinetyMinutes.toString());
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
            title=title+"-"+mobileNum.trim();
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
            title=title+"-"+uName.trim();
        }

        List<User> data=userMapper.getExceptionsUsers(con);
        userInfo(data);
        Long total= userMapper.getExceptionsUsersTotal(con);


        String[] rowsName = new String[]{"序号", "手机号", "运营商", "网络制式", "姓名",
                "性别", "出生年月", "年龄", "注册时间","网络质量最后测试时间","异常"};

        title= title+"-"+"("+formatter2.format(new Date())+")";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        User dto = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");

        for (int i = 0; i < data.size(); i++) {
            dto = data.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getMobileNum();
            objs[2] = dto.getOperator();
            objs[3] = dto.getNetSystem();
            objs[4] = dto.getuName();
            objs[5] = dto.getuSexDesc();
            if (dto.getuBirthdate() == null){
                objs[6] = null;
            }else {
                objs[6] = formatter1.format(dto.getuBirthdate());
            }

            objs[7] = dto.getAge();
            objs[8] = formatter.format( dto.getuRegDate());
            objs[9] = formatter.format( dto.getTestTime());
            objs[10] = dto.getAppName();

            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMdd").format(new Date());
        String fileName = "异常用户信息" + format + ".xls";


        File file = ex.export(UUID.randomUUID().toString() + ".xls");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8")+".xls");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);


        return responseEntity;

    }


    /*
*
* 获取非活跃用户列表用户列表
* */
    @RequestMapping(value = "/disabled",method = RequestMethod.GET)
    public ResponseVO getDisabledUsers(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                         @RequestParam(value = "length",defaultValue = "10") Integer length,
                                         @RequestParam(value = "mobileNum",required = false)String mobileNum,
                                         @RequestParam(value = "uName",required = false)String uName)throws CustomException{

        if (page <0 || length < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
        con.setSize(length);
        con.setStart((page-1) * length);
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
        }

        List<User> users=userMapper.getDisabledUsers(con);
        userInfo(users);
        Long total= userMapper.getDisabledUsersTotal(con);

        return new PageDataVO(page,length,total,users);
    }



    /*
*
* 获取非活跃用户列表用户列表导出
* */
    @RequestMapping(value = "/disabled/export",method = RequestMethod.GET)
    public ResponseEntity getDisabledUsersExport(@RequestParam(value = "mobileNum",required = false) String mobileNum,
                                                   @RequestParam(value = "uName",required = false)String uName,
                                                   @RequestParam(value = "operator",required = false)String operator ) throws Exception {

        String title = "非活跃用户信息";
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd ");

        UserCondition con = new UserCondition();

        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);

        con.setExceptionsTime(ExceptionsUserTime.NinetyMinutes.toString());
        if (mobileNum != null && !mobileNum.trim().equals("")){
            con.setMobileNum(mobileNum.trim());
            title=title+"-"+mobileNum.trim();
        }

        if (uName != null && !uName.trim().equals("")){
            con.setuName(uName.trim());
            title=title+"-"+uName.trim();
        }

        List<User> data=userMapper.getDisabledUsers(con);
        userInfo(data);
        Long total= userMapper.getDisabledUsersTotal(con);

        title= title+"-"+"("+formatter2.format(new Date())+")";
        String[] rowsName = new String[]{"序号", "手机号", "运营商", "网络制式", "姓名",
                "性别", "出生年月", "年龄", "注册时间","网络质量最后测试时间"};

        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        User dto = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd ");

        for (int i = 0; i < data.size(); i++) {
            dto = data.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getMobileNum();
            objs[2] = dto.getOperator();
            objs[3] = dto.getNetSystem();
            objs[4] = dto.getuName();
            objs[5] = dto.getuSexDesc();
            if (dto.getuBirthdate() == null){
                objs[6] = null;
            }else {
                objs[6] = formatter1.format(dto.getuBirthdate());
            }

            objs[7] = dto.getAge();
            objs[8] = formatter.format( dto.getuRegDate());
            objs[9] = formatter.format( dto.getTestTime());

            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMdd").format(new Date());
        String fileName = "非活跃用户信息" + format + ".xls";


        File file = ex.export(UUID.randomUUID().toString() + ".xls");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8")+".xls");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity responseEntity = new ResponseEntity(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);


        return responseEntity;

    }




    public void userInfo(List<User> users){

        if (users.size() > 0){
            for (User user:users){
                if (user.getuBirthdate() != null){
                    user.setAge(AgeByBirthUtil.getAge(user.getuBirthdate()));
                }
                if (user.getuSex() != null){
                    user.setuSexDesc(SexEnum.getNameByCode(user.getState()));
                }
                if (user.getState() != null){
                    user.setStateDesc(StatusEnum.getNameByCode(user.getState()));
                }

            }
        }


    }
}
