package com.xxl.job.executor;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class XxlJobExecutorApplication {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (SpringApplicationContextHolder.getContext() == null) return;
                SpringApplicationContextHolder.getSpringBeanForClass(XxlJobExecutor.class).safeDestroy();
                Thread.currentThread().interrupt();
            }
        }));
        new SpringApplicationBuilder(XxlJobExecutorApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .web(false)
                .run(args);

    }

}