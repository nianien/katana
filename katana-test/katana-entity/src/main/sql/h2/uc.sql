-- noinspection SqlNoDataSourceInspectionForFile
-- (.*)(<.*>)(.*),  ==> $1$3 COMMENT'$2',
CREATE SCHEMA `uc`;
SET SCHEMA `uc`;
CREATE TABLE `user_info`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '账户ID',
    `name`        varchar(16) NOT NULL DEFAULT '' COMMENT '账户名称',
    `phone`       varchar(20) NOT NULL DEFAULT '' COMMENT '电话',
    `email`       varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',
    `contact`     varchar(20) NOT NULL DEFAULT '' COMMENT '联系人',
    `create_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `tenant_code` varchar(16) NOT NULL DEFAULT '' COMMENT '租户编码',
    `env`         varchar(8)  NOT NULL DEFAULT '' COMMENT '环境',
    PRIMARY KEY (`id`)
)/* ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户信息表'*/;
COMMENT ON TABLE `user_info` IS '用户信息表';

CREATE TABLE `user_audit`
(
    `userid`        bigint      NOT NULL COMMENT '用户ID',
    `name`          varchar(64) NOT NULL COMMENT '用户名',
    `audit_state`   int         NOT NULL DEFAULT '5' COMMENT '审核状态，0表示审核通过，1表示审核中，2表示审核拒绝，5待提交至审核(搁置)',
    `auditor_id`    bigint      NOT NULL DEFAULT '-1' COMMENT '审核员id',
    `reason_code`   varchar(256)         DEFAULT NULL COMMENT '拒绝理由',
    `refuse_reason` varchar(1024)        DEFAULT NULL COMMENT '拒绝原因',
    `env`           varchar(8)  NOT NULL DEFAULT '' COMMENT '环境标',
    PRIMARY KEY (`userid`)
)/*ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户审核表'*/;
COMMENT ON TABLE `user_audit` IS '用户审核表';

CREATE TABLE `user_tag`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `userid`      bigint      NOT NULL COMMENT '用户id',
    `tag`         varchar(64) NOT NULL DEFAULT '0' COMMENT '账户标签',
    `create_time` timestamp   NOT NULL DEFAULT '2010-01-01 00:00:00' COMMENT '创建时间',
    `modify_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `env`         varchar(8)  NOT NULL DEFAULT '' COMMENT '环境标',
    PRIMARY KEY (`id`)
)/* ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户标签表'*/;
COMMENT ON TABLE `user_tag` IS '用户标签表';

