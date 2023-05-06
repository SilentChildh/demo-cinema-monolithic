package com.huanghehua.www.orm.util;


/**
 * sql语句构建器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/22
 */
public class SqlBuilder {
    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    /**
     * 插入表
     *
     * @param table 表格
     * @return {@link SqlBuilder}
     */
    public SqlBuilder insertInto(String table) {
        stringBuilder
                .append(INSERT)
                .append(WHITE_SPACE)
                .append(INTO)
                .append(WHITE_SPACE)
                .append(table)
                .append(WHITE_SPACE);
        return this;
    }

    /**
     * 插入多字段
     *
     * @param fields 字段
     * @return {@link SqlBuilder}
     */
    public SqlBuilder insertField(String... fields) {
        insertBracketAndFillElements(fields);
        stringBuilder.append(WHITE_SPACE);
        return this;
    }

    /**
     * 插入多字段值
     *
     * @param values 值
     * @return {@link SqlBuilder}
     */
    public SqlBuilder insertValues(String... values) {
        stringBuilder.append(VALUES).append(WHITE_SPACE);
        insertBracketAndFillElements(values);
        stringBuilder.append(WHITE_SPACE);
        return this;
    }

    public SqlBuilder delete() {
        stringBuilder.append(DELETE).append(WHITE_SPACE);
        return this;
    }

    /**
     * 更新单表
     *
     * @param table 表格
     * @return {@link SqlBuilder}
     */
    public SqlBuilder update(String table) {
        stringBuilder
                .append(UPDATE)
                .append(WHITE_SPACE)
                .append(table)
                .append(WHITE_SPACE);
        return this;
    }

    /**
     * 可以查询多字段
     *
     * @param fields 字段
     * @return {@link SqlBuilder}
     */
    public SqlBuilder select(String... fields) {
        stringBuilder.append(SELECT).append(WHITE_SPACE);
        nonBracketButFillElements(fields);
        stringBuilder.append(WHITE_SPACE);
        return this;
    }

    /**
     * 可以查询多表
     *
     * @param tables 表
     * @return {@link SqlBuilder}
     */
    public SqlBuilder from(String... tables) {
        stringBuilder.append(FROM).append(WHITE_SPACE);
        nonBracketButFillElements(tables);
        stringBuilder.append(WHITE_SPACE);
        return this;
    }

    /**
     * 可以更新多字段
     *
     * @param fieldsAndValues 字段和值
     * @return {@link SqlBuilder}
     */
    public SqlBuilder set(String... fieldsAndValues) {
        stringBuilder.append(SET).append(WHITE_SPACE);
        nonBracketButFillElements(fieldsAndValues);
        stringBuilder.append(WHITE_SPACE);

        return this;
    }

    /**
     * 用于在查询字句中注入变量，变量之间用括号括起来。例如@{code (variable)}.<br/>
     *
     * @param variable 变量
     * @return {@link SqlBuilder}
     */
    public SqlBuilder injectVariable(String variable) {
        stringBuilder.append(LEFT_PARENTHESIS)
                .append(variable)
                .append(RIGHT_PARENTHESIS)
                .append(WHITE_SPACE);
        return this;
   }


    public SqlBuilder where() {
        stringBuilder.append(WHERE).append(WHITE_SPACE);
        return this;
    }

    public SqlBuilder like() {
        stringBuilder.append(LIKE).append(WHITE_SPACE);
        return this;
    }



    public SqlBuilder and() {
        stringBuilder.append(AND).append(WHITE_SPACE);
        return this;
    }
    public SqlBuilder or(){
        stringBuilder.append(OR).append(WHITE_SPACE);
        return this;
    }
    public SqlBuilder between(){
        stringBuilder.append(BETWEEN).append(WHITE_SPACE);
        return this;
    }
    public SqlBuilder not(){
        stringBuilder.append(NOT).append(WHITE_SPACE);
        return this;
    }
    public SqlBuilder in() {
        stringBuilder.append(IN).append(WHITE_SPACE);
        return this;
    }

    public SqlBuilder is() {
        stringBuilder.append(IS).append(WHITE_SPACE);
        return this;
    }

    public SqlBuilder nullValue() {
        stringBuilder.append(NULL).append(WHITE_SPACE);
        return this;
    }


    /**
     * 用于将各元素利用逗号分隔，并用括号括起来。<br/>
     *
     * @param elements 元素
     */
    private void insertBracketAndFillElements (String... elements) {
        stringBuilder.append(LEFT_PARENTHESIS)
                .append(String.join(COMMA, elements))
                .append(RIGHT_PARENTHESIS);
    }

    /**
     * 用于将各元素利用逗号分隔，但不用括号括起来。<br/>
     *
     * @param elements 元素
     */
    private void nonBracketButFillElements (String... elements) {
        stringBuilder.append(String.join(COMMA, elements));
    }


    private static final String WHITE_SPACE = " ";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";
    private static final String COMMA = ",";


    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String SELECT = "select";


    private static final String INTO = "into";
    private static final String VALUES = "values";
    private static final String FROM = "from";
    private static final String SET = "set";


    private static final String WHERE = "where";
    private static final String LIKE = "like";

    private static final String BETWEEN = "between";
    private static final String AND = "and";
    private static final String OR = "or";
    private static final String NOT = "not";
    private static final String IN = "in";

    private static final String IS = "is";
    private static final String NULL = "null";

    private static final String GROUP_BY = "group by";
    private static final String HAVING = "having";
    private static final String ORDER_BY = "order by";
    private static final String LIMIT = "limit";

}
