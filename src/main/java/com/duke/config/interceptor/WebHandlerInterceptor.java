package com.duke.config.interceptor;

import com.duke.component.UserCacheComponent;
import com.duke.constant.SysConstant;
import com.duke.entity.redis.LoginUser;
import com.duke.task.AsyncTaskService;
import com.duke.util.CheckUtil;
import com.duke.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Title 请求拦截器
 * @ClassName WebHandlerInterceptor
 * @Author duke
 * @Date 2018/9/13
 */
public class WebHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebHandlerInterceptor.class);

    private static final String LOGIN_URL_APP = "/app/login";
    private static final String LOGIN_URL_WEB = "/web/login";

    private static final String JSON_401 = "{\"code\":401, \"message\":\"需要登录\"}";

    private static final String JSON_404 =  "{\"code\":404, \"message\":\"无效的请求\"}";
    @Autowired
    UserCacheComponent userCacheComponent;
    @Autowired
    AsyncTaskService asyncTaskService;
    private static boolean returnJson(HttpServletResponse response, String json)  throws IOException{
        try (PrintWriter writer = response.getWriter()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            writer.append(json);
        } catch (Exception ex) {
            ExceptionUtils.record("拦截器返回Json", ex);
            ex.printStackTrace();
            response.sendError(500);
        }
        return false;
       /* PrintWriter out;
        try{
            ApiResult apiResult = ApiResult.returnAuthorization();
            out = response.getWriter();
            out.append(JSON.toJSONString(apiResult));
            return false;
        } catch (Exception e){

        }*/

    }
    @Value("${custom.validation.state}")
    boolean validationState;

    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws  IOException {
        /**
         * vue 跨域处理
         */
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");


        String url = request.getRequestURI();
        System.out.println(url);
        if (LOGIN_URL_APP.equalsIgnoreCase(url) || LOGIN_URL_WEB.equalsIgnoreCase(url)) {
            return true;
        }
        if(url.contains(SysConstant.APP)){
            //APP 请求
        }else if(url.contains(SysConstant.WEB)) {
            //WEB 请求
        }else {
            // 错误的请求
            return returnJson(response, JSON_404);
            //throw new FunctionException("无效请求");
        }
        boolean isOPTIONS = false;
        // post请求之前会发送一次OPTIONS请求 直接放过
        if ("OPTIONS".equals(request.getMethod())) {
            isOPTIONS = true;
        }
        if(validationState && !isOPTIONS){
            // 获取登录用户
            LoginUser loginUser = userCacheComponent.getCacheUser();
            if(!CheckUtil.objectIsNull(loginUser)){
                // 表示已经登录
                return true;
            }
        }else {
            // 如果是OPTIONS 请求直接放过
            return true;
        }
        return  returnJson(response, JSON_401);
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //请求响应拦截器
        logger.info("-------------------------请求响应拦截器-------------------------");
    }
    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //页面渲染拦截器
        logger.info("-------------------------页面渲染拦截器-------------------------");
    }
}
