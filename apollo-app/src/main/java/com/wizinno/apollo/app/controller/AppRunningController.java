package com.wizinno.apollo.app.controller;

import com.google.gson.Gson;
import com.wizinno.apollo.app.domain.AppRunningInfoMapper;
import com.wizinno.apollo.app.domain.NetTestLogMapper;
import com.wizinno.apollo.app.domain.model.AppRunningInfo;
import com.wizinno.apollo.app.domain.model.NetTestLog;
import com.wizinno.apollo.common.controller.BaseController;
import com.wizinno.apollo.common.data.ResponseVO;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by HP on 2017/9/3.
 */

@RestController
@RequestMapping("/api/app/appRun")
public class AppRunningController extends BaseController {

    @Autowired
    private AppRunningInfoMapper appRunningInfoMapper;

    @Autowired
    private NetTestLogMapper netTestLogMapper;

    Logger log = LoggerFactory.getLogger(AppRunningController.class);

    private Gson gson = new Gson();

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createAppRunning(@RequestBody List<AppRunningInfo> appRunningInfos) throws CustomException {

     /*   log.info("=========createAppRunning========");
        log.info(gson.toJson(appRunningInfos));
        log.info("=========End========");*/

        if (appRunningInfos.size() < 0) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        for (AppRunningInfo appRunningInfo : appRunningInfos) {
            if (appRunningInfo.getTestId() != null) {
                NetTestLog netTestLog = netTestLogMapper.selectByPrimaryKey(appRunningInfo.getTestId());
                if (netTestLog == null) {
                    throw new CustomException(CustomExceptionCode.NetTestLogNotExist);
                }
                try {
                    appRunningInfoMapper.insert(appRunningInfo);
                } catch (Exception e) {
                    throw new CustomException(CustomExceptionCode.DateRepeat);

                }


            } else {
                throw new CustomException(CustomExceptionCode.ErrorParam);
            }
        }

        return new ResponseVO();

    }
}
