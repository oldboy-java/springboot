package com.glodon.shorturl.utils;

import org.springframework.util.DigestUtils;

public class ShortUrlGenerator {

    /**
     * ① 将长网址用md5算法生成32位签名串，分为4段,，每段8个字符；
     * <p>
     * ② 对这4段循环处理，取每段的8个字符, 将他看成16进制字符串与0x3fffffff(30位1)的位与操作，超过30位的忽略处理；
     * <p>
     * ③ 将每段得到的这30位又分成6段，每5位的数字作为字母表的索引取得特定字符，依次进行获得6位字符串；
     * <p>
     * ④ 这样一个md5字符串可以获得4个6位串，取里面的任意一个就可作为这个长url的短url地址。
     *
     * @param url
     * @param salt
     * @return
     */
    public static String[] shortUrl(String url, String salt) {
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = DigestUtils.md5DigestAsHex((salt + url).getBytes());
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        //得到 4组短链接字符串
        for (int i = 0; i < 4; i++) {
            // 取出8位字符串， md5是32位，被切割成4组，每组8个字符
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 先转换成16进制，然后用 0x3FFFFFFF 进行位与运算，目的是格式化截取前30位
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);

            String outChars = "";
            //循环获得每组6位的字符串
            for (int j = 0; j < 6; j++) {
                //0x0000003D 其10进制是61,61代表chars数组长度62，其坐标是0到61
                // 0x0000003D &lHexLong进行位运算，就是格式化为6位，即61内的数字，保证了index绝对是61以内的值
                long index = 0x0000003D & lHexLong;

                // 把取得的字符相加
                outChars += chars[(int) index];

                // 每次循环按位右移 5 位，因为30位的二进制，分6次循环，即每次右移5位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }
}
