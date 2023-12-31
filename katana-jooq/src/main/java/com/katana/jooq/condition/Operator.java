package com.katana.jooq.condition;

/**
 * 枚举器,Sql语法中的匹配运算符
 *
 * @author skyfalling
 */
public enum Operator {
    /**
     * "="
     */
    EQ("? = ?"),
    /**
     * "!="
     */
    NE("? != ?"),
    /**
     * "&gt;"
     */
    GT("? > ?"),
    /**
     * "&lt;"
     */
    LT("? < ?"),
    /**
     * "&gt;="
     */
    GE("? >= ?"),
    /**
     * "&lt;="
     */
    LE("? <= ?"),
    /**
     * "like"
     */
    LIKE("? like %?%"),
    /**
     * "not like"
     */
    NOT_LIKE("? not like %?%"),
    /**
     * "in"
     */
    IN("? in ?"),
    /**
     * "not in"
     */
    NOT_IN("? not in ?"),
    /**
     * "between and"
     */
    BETWEEN("? between ? and ?"),
    /**
     * "not between and"
     */
    NOT_BETWEEN("? not between ? and ?"),
    /**
     * "no operation"
     */
    NONE("");

    /**
     * 构造方法,私有访问权限
     *
     * @param value
     */
    Operator(String value) {
        this.value = value;
    }

    /**
     * 枚举实例对应的值
     */
    private String value;

    /**
     * 操作符对应的Sql模板
     *
     * @return
     */
    @Override
    public String toString() {
        return this.value;
    }

    /**
     * 根据变量赋值生成SQL
     *
     * @param name
     * @return
     */
    public String toSQL(String name) {
        return this.value.replaceFirst("\\?", name);
    }

}
