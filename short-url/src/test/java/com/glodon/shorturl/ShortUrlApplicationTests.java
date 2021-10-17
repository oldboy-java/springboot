package com.glodon.shorturl;

import com.glodon.shorturl.utils.ShortUrlGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootTest
class ShortUrlApplicationTests {

    @Test
    void generateUrl() {
        System.err.println(Arrays.stream(ShortUrlGenerator.shortUrl("https://www.cnblogs.com/zhuyeshen/p/11424713.html","")).collect(Collectors.joining(",")));
    }


}
