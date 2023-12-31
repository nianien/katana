package com.katana.jooq.condition;

import com.cudrania.core.arrays.ArrayUtils;
import com.cudrania.core.functions.Fluent;
import com.cudrania.core.functions.Param;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static com.cudrania.core.functions.Params.notNull;

/**
 * 继承自{@link Fluent}类, 用于{@link Condition}对象的流式组合
 *
 * @author scorpio
 * @version 1.0.0
 */
public class FluentCondition extends Fluent<Condition> {
    /**
     * 连接符
     */
    private BinaryOperator<Condition> op;

    /**
     * 指定连接操作
     *
     * @param target
     * @param op     连接符
     */
    protected FluentCondition(Condition target, BinaryOperator<Condition> op) {
        super(target);
        this.op = op;
    }

    /**
     * 使用AND构建Condition对象
     *
     * @return
     */
    public static FluentCondition and() {
        return and(DSL.noCondition());
    }

    /**
     * 使用AND构建Condition对象
     *
     * @param initial
     * @return
     */
    public static FluentCondition and(Condition initial) {
        return new FluentCondition(initial, (c1, c2) -> c1.and(c2));
    }

    /**
     * 使用OR构建Condition对象
     *
     * @return
     */
    public static FluentCondition or() {
        return or(DSL.noCondition());
    }


    /**
     * 使用OR构建Condition对象
     *
     * @param initial 初始condition
     * @return
     */
    public static FluentCondition or(Condition initial) {
        return new FluentCondition(initial, (c1, c2) -> c1.or(c2));
    }


    /**
     * 追加判断字段相等
     *
     * @param param 条件参数
     * @param field 表字段
     * @return
     */
    public <P> FluentCondition with(P param, Field field) {
        if (param instanceof Param) {
            return when((Param) param, field);
        }
        return when(notNull(param), field);
    }

    /**
     * 追加参数匹配
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      函数第二个参数类型
     * @return
     */
    public <P> FluentCondition with(P param,
                                    Function<P, Condition> function) {
        if (param instanceof Param) {
            return when((Param) param, function);
        }
        return when(notNull(param), function);
    }


    /**
     * 追加字段匹配
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      函数第二个参数类型
     * @return
     */
    public <P, F> FluentCondition with(P param, Field<F> field,
                                       BiFunction<Field<F>, P, Condition> function) {
        if (param instanceof Param) {
            return when((Param) param, field, function);
        }
        return when(notNull(param), field, function);
    }

    /**
     * 如果{@link Param#get()}返回结果有值,追加EQUAL或IN条件
     *
     * @param param 条件参数
     * @param field 表字段
     * @return
     */
    public <P> FluentCondition when(Param<P> param, Field field) {
        return this.when(param, field,
                (f, p) -> {
                    if (p instanceof Collection) {
                        return f.in((Collection) p);
                    }
                    if (p != null && p.getClass().isArray()) {
                        return f.in(ArrayUtils.toObjectArray(p));
                    }
                    return f.eq(p);
                });
    }


    /**
     * 如参数{@link Param#get()}返回结果有值,则追加匹配条件
     *
     * @param param    条件参数
     * @param function 表字段连接条件
     * @param <P>      函数第二个参数类型
     * @return
     */
    public <P> FluentCondition when(Param<P> param, Function<P, Condition> function) {
        return (FluentCondition) this.applyIf((c, p) -> op.apply(c, function.apply(p)), param);
    }

    /**
     * 如参数{@link Param#get()}返回结果有值,则追加匹配条件
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      函数第二个参数类型
     * @return
     */
    public <P, F> FluentCondition when(Param<P> param, Field<F> field,
                                       BiFunction<Field<F>, P, Condition> function) {
        return (FluentCondition) this.applyIf((c, p) -> op.apply(c, function.apply(field, p)), param);
    }


}
