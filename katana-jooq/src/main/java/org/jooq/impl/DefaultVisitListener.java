package org.jooq.impl;

import org.jooq.VisitContext;
import org.jooq.VisitListener;

/**
 * 接口{@link VisitListener}的默认实现
 *
 * @author : liyifei
 * @created : 2023/12/29, 星期五
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public interface DefaultVisitListener extends VisitListener {

    @Override
    default void clauseStart(VisitContext context) {
    }


    @Override
    default void clauseEnd(VisitContext context) {
    }


    @Override
    default void visitStart(VisitContext context) {
    }


    @Override
    default void visitEnd(VisitContext context) {
    }

}
