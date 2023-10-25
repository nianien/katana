## 1. H2数据库配置

### 1.1 数据源

SpringBoot配置：

```yaml
spring:
  jooq:
    sql-dialect: h2
  datasource:
    url: "jdbc:h2:mem:test"
    username: sa
    password: sa
```

## 1.2. 初始化

SpringBoot配置：

```yaml
spring:
  sql:
    init:
      platform: h2
      mode: embedded #embedded指定只有嵌入式才会初始化
```

```
schema-h2.sql 用于初始化表结构
data-h2.sql 用于初始化数据
```

## 1.3. 控制台

SpringBoot配置：

```yaml
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: false
```

访问地址：

```
127.0.0.1:8081/h2-console
```

## 2. JOOQ代码生成

<font color=red>***基于H2的sql文件生成代码，不依赖数据库链接***</font>

### 配置[jooqConfig.xml](../jooqConfig.xml)

```xml

<generator>
    <database>
        <properties>
            ...
            <property>
                <key>scripts</key>
                <!--指定SQL目录-->
                <value>**/h2/*.sql</value>
            </property>
            ...
        </properties>
    </database>
    <target>
        <!--指定生成代码的存放目录和包名-->
        <packageName>com.katana.demo.entity</packageName>
        <directory>src/main/java</directory>
    </target>
</generator>
```

### 配置maven

```xml

<plugin>
    <groupId>org.jooq</groupId>
    <artifactId>jooq-codegen-maven</artifactId>
    <version>${jooq.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <phase>package</phase>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.jooq</groupId>
            <artifactId>jooq-meta-extensions</artifactId>
            <version>${jooq.version}</version>
        </dependency>
    </dependencies>
    <configuration>
        <!--jooqConfig.xml相对当前模块的路径-->
        <configurationFile>../../jooqConfig.xml</configurationFile>
    </configuration>
</plugin>
```

maven命令：

```shell
mvn clean package
```

## 3. 日志配置

SpringBoot配置：
<font color=red>***控制台日志格式可以指定颜色***</font>

```yaml
logging:
  pattern:
    console: "${CONSOLE_LOG_PATTERN:-%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}@%line){cyan} %clr(:) %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"
  level:
    root: info
    org.springframework.web: trace
```
