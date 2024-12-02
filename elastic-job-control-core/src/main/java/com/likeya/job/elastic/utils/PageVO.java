package com.likeya.job.elastic.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 * @param <T>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageVO<T> extends Page<T> {

    /**
     * 请求条件
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Valid
    private T entity;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Map<String, Object> filters;

    private String prop;
    @Getter
    private String order;
    private static final List<String> ascKeyList = new ArrayList<>() {{
        add("ascending");
        add("ascend");
        add("asc");
    }};

    public void setProp(String prop) {
        this.prop = prop;
        handleElementOrder();
    }

    public void setOrder(String order) {
        this.order = order;
        handleElementOrder();
    }

    private void handleElementOrder() {
        if (StrUtil.isNotEmpty(prop) && StrUtil.isNotEmpty(order) && this.orders().isEmpty()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(prop);
            orderItem.setAsc(ascKeyList.contains(order));
            this.orders().add(orderItem);
        }
    }

    public PageVO() {

    }

    public PageVO(T entity, long size) {
        this.entity = entity;
        this.setSize(size);
    }
}
