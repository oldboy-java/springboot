package com.liuli.actuator.indicator;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 这里通过模拟访问系统首页URL来判断系统是否健康
 *
 */
@Component
public class CustomHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		Health health = null;
		boolean accessSystemIndexSuccess = false;
		if (accessSystemIndexSuccess = accessSystemIndexSuccess()) {
			health = Health.up().withDetail("accessSystemIndexSuccess", accessSystemIndexSuccess)
					.withDetail("msg","系统首页访问正常").build();
		}else {
			health = Health.down().withDetail("accessSystemIndexSuccess", accessSystemIndexSuccess)
					.withDetail("msg","系统首页访问失败").build();
		}
		return health;
	}

	
	
	public boolean accessSystemIndexSuccess() {
		// 这里通过http访问系统接口
		try {
			//TimeUnit.SECONDS.sleep(30);
			int c = 3 / 0;   //默认访问失败
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
