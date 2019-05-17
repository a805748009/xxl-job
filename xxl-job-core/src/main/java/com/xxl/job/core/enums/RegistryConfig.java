package com.xxl.job.core.enums;

/**
 * Created by xuxueli on 17/5/10.
 */
public class RegistryConfig {

    public static final int BEAT_TIMEOUT = 3;
    public static final int DEAD_TIMEOUT = BEAT_TIMEOUT *2;

    public enum RegistType{ EXECUTOR, ADMIN }

}
