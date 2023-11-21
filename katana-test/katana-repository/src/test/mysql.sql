CREATE TABLE `ad_crm_account`
(
    `id`                          bigint(20) unsigned NOT NULL COMMENT '自增id',
    `account_type`                tinyint(4) DEFAULT '0' COMMENT '0 unknown, 1 cpc, 2 cpm,\n3 game, 4 fanstop, 5 push',
    `frozen_status`               tinyint(4) DEFAULT '1' COMMENT '冻结状态。默认1，0：unknow 1:不冻结 2冻结',
    `frozen_reason`               varchar(400) DEFAULT NULL COMMENT '冻结理由',
    `account_detail_type`         tinyint(3) DEFAULT NULL COMMENT '1.自助；2.渠道；3.品牌；4.内部非游戏；5.游戏联运;',
    `coop_status`                 tinyint(3) DEFAULT NULL COMMENT '合作状态: 0:unknow, 1:已创建待审核、2：已提交审核认证中，3：合作中、4：不合作，5：冻结',
    `owner`                       varchar(127) DEFAULT NULL COMMENT '销售责任人',
    `product_name_old`            varchar(200) DEFAULT NULL COMMENT '旧产品名称',
    `product_name`                varchar(200) DEFAULT NULL COMMENT '产品名称',
    `account_level`               varchar(15)  DEFAULT NULL COMMENT '客户等级S,A,B,C,D',
    `marketing_content_type`      tinyint(4) DEFAULT '0' COMMENT '推广内容类型 0-未填写，1-推广内容链接，2-安装包链接',
    `marketing_content`           text COMMENT '推广内容链接',
    `quality_control_status`      tinyint(4) DEFAULT '0' COMMENT '品控审核状态，0-初始状态，1-待审核，2-品控审核通过，3-品控审核不通过',
    `quality_control_detail`      text COMMENT '品控审核描述',
    `collaborator`                varchar(256) DEFAULT '' COMMENT '改版后只能填一个，字符串形式，非json,原表中也只有一个',
    `create_time`                 bigint(20) unsigned NOT NULL COMMENT '广告主准确的创建时间',
    `update_time`                 bigint(20) unsigned NOT NULL COMMENT 'crm维护字段的更新时间',
    `fans_count`                  bigint(20) DEFAULT '0' COMMENT '创建账户时的粉丝量',
    `hard_quality_control_status` tinyint(4) DEFAULT NULL COMMENT '硬广品控状态',
    PRIMARY KEY (`id`)
) COMMENT 'CRM维护的广告主字段，一期字段全部来源于迁移ad_dsp_account字段'