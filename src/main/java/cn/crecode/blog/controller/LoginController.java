package cn.crecode.blog.controller;

import cn.crecode.blog.service.TokenService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class LoginController {

    private final static Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private TokenService tokenService;

    /**
     * PC 登录模块
     * @param userInfo 前端传递的用户信息
     * @param userAgent 登录客户端
     * @return {code: int,data: Object}
     */

    @PostMapping(value = "/admin/login/pc")
    public ResponseEntity<Map<String, Object>> loginPC(@RequestBody Map<String, String> userInfo,
                                                       @RequestHeader(value="User-Agent") String userAgent){
        String userName = userInfo.get("userName");
        String password = userInfo.get("password");
//        String userType = userInfo.get("userType");
        String userType = "admin";

        HashMap<String, Object> result = new HashMap<>();
        String token = tokenService.loginCheck(userName, password,userAgent, userType);
        if(token == null) {
            if(log.isTraceEnabled()) {
                log.trace("PC端登录 token令牌为空");
            }
            result.put("status", 0);
            result.put("msg","wrong username or password!");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            String userName2 = tokenService.getUserFromToken(token).getUsername();
            if(log.isTraceEnabled()) {
                log.trace("PC端登录 用户名为"+ userName2 + "的用户成功登录");
            }
            JSONObject data = new JSONObject();
            data.put("userName",userName2);
            data.put("userType",userType);
            data.put("token",token);
            result.put("status",1);
            result.put("data",data);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }


    /**
     * PC端注销登录
     * @param request
     * @return
     */

    @DeleteMapping(value = "/admin/logout/pc")
    public Map<String, Object> logout(HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        String token = null;
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            if(tokenService.getUserFromToken(token) != null) {
                String userName = tokenService.getUserFromToken(token).getUsername();
                if(log.isTraceEnabled()) {
                    log.trace("will delete token : " + token);
                    log.trace("注销用户: " + userName);
                }
                result.put("status",1);
                result.put("data",userName);
                tokenService.logout(token);
                return result;
            } else {
                result.put("status",1);
                result.put("data","此用户登录信息已失效!");
                return result;
            }
        } else{
            result.put("status",0);
            result.put("msg","无此用户登录信息!");
            return result;
        }
    }

//    @PostMapping(value = "/token_check")
//    public Map<String, String> tokenCheck(@RequestBody Map<String, String> userInfo) {
//        Map<String,String> result = new HashMap<>();
//
//        String userName = userInfo.get("userName");
//        String token = userInfo.get("token");
//
//        if(userName != null && token != null) {
//            if(log.isTraceEnabled()) {
//                log.trace("检查用户的token: " + userInfo);
//            }
//            if(tokenService.tokenCheck(userName,token)) {
//                result.put("result","success");
//                return result;
//            }
//            result.put("result", "fail");
//            return result;
//        }
//        result.put("result", "fail");
//        return result;
//    }


}
