# JOOQ for H2

## 1. H2语法

### 1.2 Comment on table
```h2
CREATE TABLE `user_tag`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `userid`      bigint      NOT NULL COMMENT '用户id',
    `tag`         varchar(64) NOT NULL DEFAULT '0' COMMENT '账户标签',
    `create_time` timestamp   NOT NULL DEFAULT '2010-01-01 00:00:00' COMMENT '创建时间',
    `modify_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `env`         varchar(8)  NOT NULL DEFAULT '' COMMENT '环境标',
    PRIMARY KEY (`id`)
);COMMENT ON TABLE `user_tag` IS '用户标签表';
```

### 1.2 Quotes names

------------------------------------------
<font color=red>***Quotes names in H2 are case sensitive***</font>, as required by the SQL specification. That means:

CREATE TABLE "testquote" (dummy INT, "quotedDummy" INT);

```
SELECT * FROM "testquote";  --work
SELECT * FROM testquote;  --not work
SELECT * FROM "TestQuote";  --not work
```
------------------------------------------

<font color=red bold="">***Unquotes names are not case sensitive in H2***</font>. They are normally converted to
uppercase (as in Oracle and other databases).

That means the statements

```
CREATE TABLE test (dummy INT);
SELECT * FROM test;
are the same as

CREATE TABLE "TEST" ("DUMMY" INT);
SELECT * FROM "TEST";
```

------------------------------------------

In that H2 behaves in the same way as Oracle.
This is a bit different on how other databases like MySQL and PostgreSQL deal with identifier names.
H2 has a compatibility feature: ***If you append ;DATABASE_TO_UPPER=FALSE to the database URL,
unquotes identifiers are not converted to uppercase, that means they are case sensitive as well***.
But you need append this when creating the database, and each time you use it
(if you append the setting for existing databases, the identifiers of existing objects are already converted to
uppercase).


### 1.3  Bit wise operation for H2

```
   a&b; --not work
   BITAND(a,b) -- work
   see also: BITOR,BITNOT
```

### 1.4  insert newline for H2

```
   CONCAT_WS(char(10), '威信', 'VX','薇芯')
   '威信'||char(10)||'VX'||char(10)||'薇芯'
   STRINGDeCODE('威信\nVX\n薇芯')
```

注: char(10)=='\n'

## 2. JOOQ for H2

### 2.1 DDL is <font color=red>unquoted</font> with ```\"```

DSL Context set as follows:

####

    new Settings().withRenderNameStyle(RenderNameStyle.AS_IS)

### 2.2 DDL is <font color=red>quoted</font> with ```\"```

DSL Context set as follows:

```
new Settings().withRenderNameStyle(RenderNameStyle.QUOTED);
```

or

```
new Settings().withRenderNameStyle(RenderNameStyle.AS_IS)   
   
AND 

jdbc-url: "jdbc:h2:<url>;DATABASE_TO_UPPER=FALSE";
```
