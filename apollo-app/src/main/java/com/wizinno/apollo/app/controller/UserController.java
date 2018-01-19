package com.wizinno.apollo.app.controller;

import com.google.gson.Gson;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import com.wizinno.apollo.app.config.Constant;
import com.wizinno.apollo.app.domain.DeviceMapper;
import com.wizinno.apollo.app.domain.TmpTableMapper;
import com.wizinno.apollo.app.domain.UserMapper;
import com.wizinno.apollo.app.domain.model.Device;
import com.wizinno.apollo.app.domain.model.TmpTable;
import com.wizinno.apollo.app.domain.model.User;
import com.wizinno.apollo.app.dto.UserDeviceDto;
import com.wizinno.apollo.app.utils.JwtUtil;
import com.wizinno.apollo.common.controller.BaseController;
import com.wizinno.apollo.common.data.ResponseVO;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
import com.wizinno.apollo.common.sms.TemplateSms;
import com.wizinno.apollo.common.utils.PhoneUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/9/2.
 */

@RestController
@RequestMapping("/api/app/user")
public class UserController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private TmpTableMapper tmpTableMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HttpServletRequest request;

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private Gson gson = new Gson();

    @RequestMapping(value = "/authCode/{phone}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO sendCode(@PathVariable(value = "phone") String phone) throws CustomException {


        if (!PhoneUtil.phoneNumber(phone)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        try {
            String code = TemplateSms.sendSms(phone);
            TmpTable tmpTable1 = tmpTableMapper.selectByPrimaryKey(phone);
            if (tmpTable1 == null){
                TmpTable tmpTable = new TmpTable();
                tmpTable.setCode(code);
                tmpTable.setPhone(phone);
                tmpTable.setTimeStamp(new Date());
                tmpTable.setType(1);
                tmpTableMapper.insert(tmpTable);
            }else {
                TmpTable tmpTable = new TmpTable();
                tmpTable.setCode(code);
                tmpTable.setPhone(phone);
                tmpTable.setTimeStamp(new Date());
                tmpTable.setType(1);
                tmpTableMapper.updateByPrimaryKey(tmpTable);
            }

            if (code != null){
                log.info("发送验证码给" + phone + "成功");
            }else {
                log.info("发送验证码给" + phone + "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseVO();

    }

    @RequestMapping(value = "/verification/{phone}/{code}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO verificationCode(@PathVariable(value = "phone") String phone,
                                       @PathVariable(value = "code") String code) throws CustomException {

        log.info("=========verificationCode========");
        log.info("phone:"+phone);
        log.info("code:"+code);
        log.info("=========End========");

        if (!PhoneUtil.phoneNumber(phone)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (phone != null) {
            if (code == null) {
                throw new CustomException(CustomExceptionCode.ErrorParam);
            }
            TmpTable tmpTable = tmpTableMapper.selectByPrimaryKey(phone);

            if (tmpTable.getCode().equals(code)){
                log.info(phone+"验证码验证成功");
            }else {
                log.info(phone+"验证码验证失败");
                throw new CustomException(CustomExceptionCode.CaptchaError);
            }


        }

        return new ResponseVO();
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO resetPassword(@RequestParam(value = "phone") String phone,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam(value = "confirmPassword") String confirmPassword) throws CustomException {
        if (!PhoneUtil.phoneNumber(phone)) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (!password.equals(confirmPassword)) {
            throw new CustomException(CustomExceptionCode.PasswordMismatch);
        }
        User user = userMapper.getUserByMobileNum(phone);
        if (user != null) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userMapper.updateByPrimaryKey(user);
        } else {
            throw new CustomException(CustomExceptionCode.UserNotExist);
        }

        return new ResponseVO();

    }


    /*
    * 注册
    * */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO register(@RequestBody User user) throws CustomException {

        log.info("=========register========");
        log.info(gson.toJson(user));
        log.info("=========End========");

        if (!PhoneUtil.phoneNumber(user.getMobileNum())) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }

        if (user.getUserId() == null) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (user.getState() == null) {
            user.setState(1);
        }
        if (user.getuSex() == null) {
            user.setuSex(0);
        }
        User user1 = userMapper.getUserByUname(user.getuName());
        if (user1 != null) {
            throw new CustomException(CustomExceptionCode.UsernameExist);
        }
        User user2 = userMapper.getUserByMobileNum(user.getMobileNum());
        if (user2 != null) {
            throw new CustomException(CustomExceptionCode.PhoneExist);
        }
        Date date = new Date();
        user.setuRegDate(date);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

        return new ResponseVO();

    }

    /*
    * 查询用户
    *
    * */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getUser() {
        List<User> users = userMapper.selectAll();
        return new ResponseVO(users);
    }


    /*
    *  用户登录
    * */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO loginUser(@RequestParam("mobileNumAndUname") String mobileNumAndUname, @RequestParam("password") String password,
                                @RequestBody Device device) throws CustomException {

        log.info("=========loginUser========");
        log.info("mobileNumAndUname:"+mobileNumAndUname);
        log.info("password:"+password);
        log.info("==Device==");
        log.info(gson.toJson(device));
        log.info("=========End========");

        String token = null;
        Date date = new Date();

        User user = userMapper.getUserByMobileNumOrUname(mobileNumAndUname);

        UserDeviceDto userDeviceDto = new UserDeviceDto();


        if (user != null) {
            String subject = jwtUtil.generalSubject(user);
            try {
                token = jwtUtil.createJWT(Constant.JWT_ID, subject, Constant.JWT_TTL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new CustomException(CustomExceptionCode.UserNotExist);
        }

        boolean matches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new CustomException(CustomExceptionCode.WrongPassword);
        }


        if (device == null) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        } else {
            Device device1 = deviceMapper.getDeviceByDevImei(device.getDevImei());
            if (device1 == null) {
                device.setdLogintime(date);
                device.setuName(user.getuName());
                device.setdMobilenum(user.getMobileNum());
                if (device.getState() == null) {
                    device.setState(1);
                }
                deviceMapper.insert(device);
                userDeviceDto.setdState(device.getState());
                BeanUtils.copyProperties(device, userDeviceDto);
            } else {
                if (!device1.getAndroidApiVer().equals(device.getAndroidApiVer())){
                    device1.setAndroidApiVer(device.getAndroidApiVer());
                    deviceMapper.updateByPrimaryKey(device1);
                }
                userDeviceDto.setdState(device1.getState());
                BeanUtils.copyProperties(device1, userDeviceDto);
            }

        }


        BeanUtils.copyProperties(user, userDeviceDto);
        userDeviceDto.setuState(user.getState());
        userDeviceDto.setToken(token);

        return new ResponseVO(userDeviceDto);
    }
}
