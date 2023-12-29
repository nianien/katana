package org.jooq.impl;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;

/**
 * 接口{@link ExecuteListener}的默认实现
 *
 * @author : liyifei
 * @created : 2023/10/23, 星期一
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public interface DefaultExecuteListener extends ExecuteListener {
    @Override
    default void start(ExecuteContext ctx) {
    }


    @Override
    default void renderStart(ExecuteContext ctx) {
    }


    @Override
    default void renderEnd(ExecuteContext ctx) {
    }


    @Override
    default void prepareStart(ExecuteContext ctx) {
    }


    @Override
    default void prepareEnd(ExecuteContext ctx) {
    }


    @Override
    default void bindStart(ExecuteContext ctx) {
    }


    @Override
    default void bindEnd(ExecuteContext ctx) {
    }


    @Override
    default void executeStart(ExecuteContext ctx) {
    }


    @Override
    default void executeEnd(ExecuteContext ctx) {
    }


    @Override
    default void outStart(ExecuteContext ctx) {
    }


    @Override
    default void outEnd(ExecuteContext ctx) {
    }


    @Override
    default void fetchStart(ExecuteContext ctx) {
    }


    @Override
    default void resultStart(ExecuteContext ctx) {
    }


    @Override
    default void recordStart(ExecuteContext ctx) {
    }


    @Override
    default void recordEnd(ExecuteContext ctx) {
    }


    @Override
    default void resultEnd(ExecuteContext ctx) {
    }


    @Override
    default void fetchEnd(ExecuteContext ctx) {
    }


    @Override
    default void end(ExecuteContext ctx) {
    }


    @Override
    default void exception(ExecuteContext ctx) {
    }


    @Override
    default void warning(ExecuteContext ctx) {
    }


}
