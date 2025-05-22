package com.quanxiaoha.xiaohashu.auth.controller;

import com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog;
import com.quanxiaoha.framework.common.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 犬小哈
 * @url: www.quanxiaoha.com
 * @date: 2024/5/4 12:53
 * @description: TODO
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    public Response<String> test() {
        return Response.success("Hello, 犬小哈教程");
    }

    @GetMapping("/test1")
    @ApiOperationLog(description = "测试接口")
    public Response<String> test1() {
        return Response.success("Hello, 犬小哈专栏");
    }
}
