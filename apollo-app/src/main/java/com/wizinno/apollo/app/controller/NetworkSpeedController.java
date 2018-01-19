package com.wizinno.apollo.app.controller;

import com.google.gson.Gson;
import com.wizinno.apollo.app.domain.NetworkSpeedMapper;
import com.wizinno.apollo.app.domain.UserMapper;
import com.wizinno.apollo.app.domain.model.NetworkSpeed;
import com.wizinno.apollo.app.domain.model.User;
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
 * Created by HP on 2017/9/21.
 */
@RestController
@RequestMapping("/api/app/network")
public class NetworkSpeedController extends BaseController {

    @Autowired
    private NetworkSpeedMapper networkSpeedMapper;

    @Autowired
    private UserMapper userMapper;

    Logger log = LoggerFactory.getLogger(NetTestLogController.class);

    private Gson gson = new Gson();

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createNetworkSpeed(@RequestBody List<NetworkSpeed> networkSpeeds) throws CustomException {

/*        log.info("=========createNetworkSpeed========");
        log.info(gson.toJson(networkSpeeds));
        log.info("=========End========");*/

        if (networkSpeeds.size() < 0) {
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        for (NetworkSpeed networkSpeed : networkSpeeds) {
            User user = userMapper.selectByPrimaryKey(networkSpeed.getUserId());
            if (user == null) {
                throw new CustomException(CustomExceptionCode.UserNotExist);
            }
            networkSpeedMapper.insert(networkSpeed);
        }
        return new ResponseVO();

    }
}
