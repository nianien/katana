package org.jooq.impl;

import org.jooq.Field;

/**
 * @author : liyifei
 * @created : 2023/10/25, 星期三
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class FieldsRow extends AbstractRow {
    public FieldsRow(Field... fields) {
        super(new FieldsImpl(fields));
    }

}
