package com.liuli.hash;

import com.alibaba.fastjson.JSON;
import com.liuli.hash.entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void getProduct() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/7449160388077954952")
               // 设置请求类型
                .accept(MediaType.APPLICATION_JSON)

                // 设置响应类型
                .contentType(MediaType.APPLICATION_JSON)

                // 设置字符集
                .characterEncoding("utf-8")

                // 设置其他参数
            .param("name","zzzzz")
        )

                // 打印结果到控制台
                .andDo(MockMvcResultHandlers.print())

                //判断请求状态码
                .andExpect(MockMvcResultMatchers.status().isOk())

                // 判断响应结果的id值
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("7449160388077954952"))

                // 返回mvcResult
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        System.err.println( response.getContentAsString(StandardCharsets.UTF_8));
    }


    @Test
    public void addProduct() throws Exception {
        Product product = Product.builder().name("中兴330").price(new BigDecimal(1800)).detail("中兴手机").build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/products")
                // 设置请求类型
                .accept(MediaType.APPLICATION_JSON)

                // 设置响应类型
                .contentType(MediaType.APPLICATION_JSON)

                // 设置请求体
             .content(JSON.toJSONString(product))

                // 设置字符集
                .characterEncoding("utf-8")
        )

                // 打印结果到控制台
                .andDo(MockMvcResultHandlers.print())

                //判断请求状态码
                .andExpect(MockMvcResultMatchers.status().isOk())

                // 返回mvcResult
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        System.err.println( response.getContentAsString(StandardCharsets.UTF_8));
    }
}
