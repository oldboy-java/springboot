package com.imooc.girl.controller;

import com.imooc.girl.common.Result;
import com.imooc.girl.common.ResultUtils;
import com.imooc.girl.pojo.Girl;
import com.imooc.girl.service.GirlService;
import com.imooc.girl.service.impl.GirlService2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Api("用户接口")
public class GirlController {

    private static final Logger logger = LoggerFactory.getLogger(GirlController.class);

    @Autowired
    private GirlService girlService;

    //由于AOP动态代理设置为JDK动态代理后，JDK动态代理是基于接口生成的对象，只能赋值于接口类型的变量
    // 赋值给实现类会出现错误：The bean 'girlServiceImpl' could not be injected as a 'com.imooc.girl.service.impl.GirlServiceImpl' because it is a JDK dynamic proxy that implements:
//	@Autowired
//	private GirlServiceImpl girlService3;


    @Autowired
    private GirlService2 girlService2;

    /**
     * 查询女生列表
     *
     * @return
     */
    @GetMapping(value = "girls")
    public Result<Object> girlList() {
        return ResultUtils.success(girlService.girlList());
    }

    /**
     * 查询女生列表
     *
     * @return
     */
    @GetMapping(value = "girls2")
    public Result<Object> girlList2() {
        return ResultUtils.success(girlService2.girlList());
    }


    /**
     * 添加女生
     *
     * @return
     */
    // @Valid:对girl对象进行验证，如果有错误，则结果保存再BindingResult
    @PostMapping(value = "girls")
    @GetMapping(value = "girls")
    @ApiOperation(value = "新增用户")
    public Result<Object> addGirl(@ApiParam(name = "girl", value = "用户信息", required = true) @Valid Girl girl, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            logger.info(bindingResult.getFieldError().getDefaultMessage());
            return ResultUtils.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtils.success(girlService.addGirl(girl));
    }

    /***
     * 更新女生
     *
     * @param age
     * @param cupSize
     * @param id
     * @return
     */
    @PutMapping(value = "girls/{id}")
    public Result<Object> updateGirl(@RequestParam("age") Integer age, @RequestParam("cupSize") String cupSize,
                                     @PathVariable("id") Integer id) {
        Girl girl = new Girl();
        girl.setId(id);
        girl.setAge(age);
        girl.setCupSize(cupSize);
        return ResultUtils.success(girlService.updateGirl(girl));
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "girls/{id}")
    @ApiOperation(value = "根据用户ID查询用户")
    public Result<Object> findGirl(@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable("id") Integer id) {
        return ResultUtils.success(girlService.findGirl(id));
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping(value = "girls/{id}")
    public Result<Object> deleteGirl(@PathVariable("id") Integer id) {
        girlService.deleteGirl(id);
        return ResultUtils.success(null);
    }

    /**
     * 根据年龄查询女生列表
     *
     * @return
     */
    @GetMapping(value = "girls/age/{age}")
    public Result<Object> girlListByAge(@PathVariable("age") Integer age) {
        return ResultUtils.success(girlService.girlListByAge(age));
    }

    /***
     * 查询女生年龄
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "girls/getAge/{id}")
    public Result<Object> getGirlAge(@PathVariable("id") Integer id) throws Exception {
        return ResultUtils.success(girlService.getGirlAge(id));
    }

}
