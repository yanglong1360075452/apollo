package com.wizinno.apollo.app.controller;

import com.google.gson.Gson;
import com.wizinno.apollo.app.domain.AppInstallInfoMapper;
import com.wizinno.apollo.app.domain.model.AppInstallInfo;
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
 * Created by HP on 2017/9/15.
 */

@RestController
@RequestMapping("/api/app/appInstall")
public class AppInstallInfoController extends BaseController {

    @Autowired
    private AppInstallInfoMapper appInstallInfoMapper;

    Logger log = LoggerFactory.getLogger(AppInstallInfoController.class);

    private Gson gson = new Gson();


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createAppInstall(@RequestBody List<AppInstallInfo> appInstallInfos) throws CustomException {
 /*       log.info("=========createAppInstall========");
        log.info(gson.toJson(appInstallInfos));
        log.info("=========End========");*/

        if (appInstallInfos.size() < 0) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }

        for (AppInstallInfo appInstallInfo : appInstallInfos) {
            appInstallInfoMapper.insert(appInstallInfo);
        }
        return new ResponseVO();
    }
}
