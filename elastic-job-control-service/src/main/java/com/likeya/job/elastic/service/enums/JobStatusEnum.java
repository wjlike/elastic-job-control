package com.likeya.job.elastic.service.enums;

import lombok.Getter;

/**
 * 未注册 <-->  启用 <--> 暂停 <--> 关闭
 *
 *             |
 *             v
 *            关闭
 */
@Getter
public enum JobStatusEnum {
    UNREGISTERED,
    ENABLED,
    DISABLED,
    CLOSED;
}
