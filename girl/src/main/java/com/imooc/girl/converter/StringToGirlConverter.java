package com.imooc.girl.converter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.imooc.girl.pojo.Girl;

/**
 * 自定义字符串转Girl转换器
 *
 */
public class StringToGirlConverter implements Converter<String, Girl> {

	@Override
	public Girl convert(String source) {
		if(StringUtils.isEmpty(source)) {
			return null;
		}
		//不包含-分隔符
		if(source.indexOf(",")==-1) {
			return null;
		}
		String [] arr = source.split(",");
		if(arr.length!=4) {
			return null;
		}
		Girl girl = new Girl();
		girl.setCupSize(arr[0]);
		girl.setAge(Integer.parseInt(arr[1]));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			girl.setBirthday(sdf.parse(arr[2]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BigDecimal money = new BigDecimal(arr[3]);
		girl.setMoney(money);
		return girl;
	}

}
