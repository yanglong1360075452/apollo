package com.wizinno.apollo.security;


import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wizinno.apollo.app.domain.model.User;
import com.wizinno.apollo.app.utils.JwtUtil;
import com.wizinno.apollo.common.data.ResponseVO;
import com.wizinno.apollo.common.data.TokenInfo;
import com.wizinno.apollo.common.data.UserInfo;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by LiuMei on 2017-07-26.
 */
public class JWTSecurityFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(JWTSecurityFilter.class);
    private Gson gson = new Gson();

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        String requestURI = httpRequest.getServletPath();
        ResponseVO responseVO;
        User user = null;
        if (requestURI.indexOf("/api/app/user/register") == -1 && requestURI.indexOf("/api/app/user/login") == -1
                && requestURI.indexOf("/api/app/netTestLog/") == -1  && requestURI.indexOf("/api/manage/users/") == -1
                && requestURI.indexOf("/api/app/user/authCode/") == -1  && requestURI.indexOf("/api/app/user/verification/") == -1
                && requestURI.indexOf("/api/app/user/resetPassword") == -1) {
            String auth = httpRequest.getHeader("Authorization");


       /*     Base64 base64 = new Base64();
            String s = new String(base64.decode(auth), "UTF-8");
            String sss = s.substring(0, s.indexOf(";"));
            String ssss = sss.substring(sss.indexOf("}") + 1, sss.length());
            TokenInfo tokenInfo = gson.fromJson(ssss, TokenInfo.class);
            String id = tokenInfo.getSub().getId();*/



            if (auth == null) {
                responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                response(response, responseVO);
            } else {
                String result=auth.substring(auth.indexOf(".")+1,auth.lastIndexOf("."));

                Base64 base64 = new Base64();
                String s = new String(base64.decode(result), "UTF-8");

                TokenInfo tokenInfo = gson.fromJson(s, TokenInfo.class);
                String sub = tokenInfo.getSub();

                UserInfo userInfo = gson.fromJson(sub, UserInfo.class);
                if (userInfo.getId() != null){
                    request.setAttribute("userId",userInfo.getId());
                }else {
                    responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                    response(response, responseVO);
                }


  /*              try {


                    Claims claims = jwtUtil.parseJWT(auth);
                    if (claims != null) {
                        String subject = claims.getSubject();
                        Gson gson = new Gson();
                        user = gson.fromJson(subject, User.class);
                        request.setAttribute("userId", user.getUserId());
                    }
                } catch (Exception e) {
                    responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                    response(response, responseVO);
                }*/
            }
        }
        chain.doFilter(request, response);
    }

    private void response(ServletResponse response, ResponseVO responseVO) {
        try {
            response.getOutputStream().write(gson.toJson(responseVO).toString().getBytes("UTF-8"));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
    }
}
