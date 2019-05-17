package com.xxl.job.executor;

import com.xxl.job.core.down.ShutDownHandleInterface;
import com.xxl.job.core.down.ShutDownHandler;
import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class XxlJobExecutorApplication {

    public static void main(String[] args) {
        ShutDownHandler shutDownHandler = new ShutDownHandler();
        shutDownHandler.registerSignal(new ShutDownHandleInterface() {
            @Override
            public void run() {
                if (SpringApplicationContextHolder.getContext() == null) return;
                SpringApplicationContextHolder.getSpringBeanForClass(XxlJobExecutor.class).safeDestroy();
            }
        });
        new SpringApplicationBuilder(XxlJobExecutorApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .web(false)
                .run(args);
        while (true) {

        }
    }

}