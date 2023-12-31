/**
 * wdk.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.katana.jooq.test;


import com.katana.jooq.condition.Match;
import com.katana.jooq.condition.Operator;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 异常货品查询
 *
 * @author skyfalling
 * @date 2021/01/21
 */
@Data
public class GoodsQuery {


    /**
     * 数据id
     */
    private Long id;

    /**
     * 业务标识
     */
    private Integer bizType;

    /**
     * 操作人
     */
    private String modifier;

    /**
     * 环境标识
     */
    @Match(Operator.NONE)
    private String env;

    /**
     * 任务单号
     */
    private String orderCode;

    /**
     * 运单号
     */
    private String mailNo;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 提报时间开始
     */
    @Match(name = "out_submit_time", value = Operator.GE)
    private Date submitTimeBegin;

    /**
     * 提报时间结束
     */
    @Match(name = "out_submit_time", value = Operator.LE)
    private Date submitTimeEnd;

    /**
     * 行业id
     */
    private Long industryId;

    /**
     * 行业名称
     */
    private String industryName;

    /**
     * 发货仓code
     */
    private String srcStoreCode;

    /**
     * 发货仓name
     */
    private String srcStoreName;


    /**
     * 价格
     */
    private BigDecimal price;


    /**
     * 过期时间
     */
    @Match(Operator.BETWEEN)
    private Date[] expireDate;

    /**
     * 创建时间
     */
    @Match(Operator.BETWEEN)
    private Date createDate;


}