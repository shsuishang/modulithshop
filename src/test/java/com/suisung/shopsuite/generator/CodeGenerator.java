package com.suisung.shopsuite.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.repository.IBaseRepository;
import com.suisung.shopsuite.core.web.repository.impl.BaseRepositoryImpl;
import com.suisung.shopsuite.generator.engine.EnhanceFreemarkerTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * MyBatisPlus代码生成器
 * Created by Xinze on 2020/8/20.
 */
public class CodeGenerator {

    // 自定义项目名称
    private static final String MOD_NAME = ""; //mall-mbg

    /**
     * 生成的实体类忽略表前缀: 不需要则置空
     */
    String ENTITY_IGNORE_PREFIX = "";

    /**
     * 表名
     */
    String[] TABLES = {
            "edu_user_paper_info"
    };


    // 输出位置
    private static final String OUTPUT_LOCATION = System.getProperty("user.dir");

    // 输出目录
    private static final String OUTPUT_DIR = "/src/main/java";
    // 作者名称
    private static final String AUTHOR = "Xinze";

    // 是否在xml中添加二级缓存配置
    private static final boolean ENABLE_CACHE = false;

    // 数据库连接配置
    private static final String DB_URL = "jdbc:mysql://47.100.28.2:3306/dev_shop_suite?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_USERNAME = "store";
    private static final String DB_PASSWORD = "storefhwui!#2sf13f";

    // 包名
    private static final String PACKAGE_NAME = "com.suisung.shopsuite";

    // 需要生成的表
    private static final String[] TABLE_NAMES = new String[]{
            "admin_menu_base"
    };
    // 需要去除的表前缀
    private static final String[] TABLE_PREFIX = new String[]{
            "admin_",
            "sys_",
            "pay_",
            "account_",
            "shop_",
            "cms_",
            "order_",
            "store_",
    };
    // 不需要作为查询参数的字段
    private static final String[] PARAM_EXCLUDE_FIELDS = new String[]{
            "id",
            "create_time",
            "update_time",
            "create_by",
            "update_by",
            "deleted",
            "version",
            "extra"
    };

    // 查询参数使用String的类型
    private static final String[] PARAM_TO_STRING_TYPE = new String[]{
            "Date",
            "LocalDate",
            "LocalTime",
            "LocalDateTime"
    };
    // 查询参数使用EQ的类型
    private static final String[] PARAM_EQ_TYPE = new String[]{
            "Integer",
            "Boolean",
            "BigDecimal"
    };
    // 是否添加权限注解
    private static final boolean AUTH_ANNOTATION = true;
    // 是否添加日志注解
    private static final boolean LOG_ANNOTATION = true;
    // controller的mapping前缀
    private static final String CONTROLLER_MAPPING_PREFIX = "/api";
    // 模板所在位置
    private static final String TEMPLATES_DIR = "/src/test/java/com/suisung/shopsuite/generator/templates";

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        //String moduleName = scanner("模块名");
        //String[] tableNames = scanner("表名，多个英文逗号分割").split(",");
        //String[] tableNames = "account_user_base".split(",");

        // 模块名
        String moduleName = "test";
        String likeTablePrefix = moduleName + "_";
        //moduleName = scanner(" 模块名 ");


        //String[] tableNames = TABLE_NAMES;
        String[] tableNames = scanner(" table name ，dot split ").split(",");


        String[] s = cn.hutool.core.util.StrUtil.splitToArray(tableNames[0], "_");
        moduleName = s[0];
        String finalModuleName = moduleName;

        // 代码生成器
        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                .globalConfig(builder -> {
                    builder.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() // 禁止打开输出目录
                            .author(AUTHOR)// 设置作者
                            //.enableKotlin()
                            .dateType(DateType.ONLY_DATE)//TIME_PACK
                            .commentDate("yyyy-MM-dd")
                            .outputDir(projectPath + "/" + MOD_NAME + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(PACKAGE_NAME) // 设置父包名
                            .moduleName(finalModuleName) // 设置父包模块名
                            .entity("model.entity")
                            .service("repository")
                            .serviceImpl("repository.impl")
                            .mapper("dao")
                            .xml("mapper.xml")
                            .controller("controller.manage")
                            .other("model")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/" + MOD_NAME + "/src/main/resources/mapper/" + finalModuleName));
                })
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                    // DTO
                    //customFile.put("AddInput.java", "/templates/entityAddInput.java.ftl");
                    //customFile.put("AddOutput.java", "/templates/entityAddOutput.java.ftl");

                    //customFile.put("EditInput.java", "/templates/entityEditInput.java.ftl");
                    //customFile.put("EditOutput.java", "/templates/entityEditOutput.java.ftl");

                    //customFile.put("ListInput.java", "/templates/entityListInput.java.ftl");
                    //customFile.put("ListOutput.java", "/templates/entityListOutput.java.ftl");


                    customFile.put("AddReq.java", "/templates/entityAddReq.java.ftl");
                    //customFile.put("AddRes.java", "/templates/entityAddRes.java.ftl");

                    customFile.put("EditReq.java", "/templates/entityEditReq.java.ftl");
                    //customFile.put("EditRes.java", "/templates/entityEditRes.java.ftl");

                    customFile.put("ListReq.java", "/templates/entityListReq.java.ftl");
                    //customFile.put("ListRes.java", "/templates/entityListRes.java.ftl");


                    customFile.put("Service.java", "/templates/serviceLogic.java.ftl");
                    customFile.put("ServiceImpl.java", "/templates/serviceLogicImpl.java.ftl");

                    consumer.customFile(customFile);
                })
                .strategyConfig(builder -> {
                    builder
                            .addInclude(tableNames)
                            //.likeTable(new LikeTable(likeTablePrefix)) // 设置需要生成的表名
                            .addTablePrefix(finalModuleName + "_") // 设置过滤表前缀
                            .enableCapitalMode()
                            .enableSkipView()
                            //.disableSqlFilter()

                            .entityBuilder()
                            //.superClass(BaseEntity.class)
                            //.superClass("sss")
                            //.disableSerialVersionUID()
                            .enableChainModel()
                            .enableLombok()
                            //.enableRemoveIsPrefix()
                            .enableTableFieldAnnotation()
                            //.enableActiveRecord()
                            .versionColumnName("version")
                            .versionPropertyName("version")
                            .logicDeleteColumnName("deleted")
                            .logicDeletePropertyName("deleteFlag")
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .addSuperEntityColumns(PARAM_EXCLUDE_FIELDS)
                            .addIgnoreColumns("age")
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                            .idType(IdType.AUTO)
                            .formatFileName("%s")

                            .controllerBuilder()
                            .superClass(BaseController.class)
                            .enableHyphenStyle()
                            .enableRestStyle()
                            .formatFileName("%sController")

                            .serviceBuilder()
                            //.superServiceClass(PACKAGE_NAME + ".common.repository.IBaseService")
                            .superServiceClass(IBaseRepository.class)
                            //.superServiceImplClass(PACKAGE_NAME + ".common.repository.impl.BaseServiceImpl")
                            .superServiceImplClass(BaseRepositoryImpl.class)
                            .formatServiceFileName("%sRepository")
                            .formatServiceImplFileName("%sRepositoryImpl")


                            .mapperBuilder()
                            //.superClass(BaseMapper.class)
                            .enableMapperAnnotation()
                            //.enableBaseResultMap()
                            //.enableBaseColumnList()
                            //.cache(MyMapperCache.class)
                            .formatMapperFileName("%sDao")
                            .formatXmlFileName("%sXml").build()
                    ;

                })
                .templateEngine(new EnhanceFreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


    /**
     * 读取控制台内容信息
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (cn.hutool.core.util.StrUtil.isNotEmpty(next)) {
                return next;
            }
        }
        throw new com.baomidou.mybatisplus.core.exceptions.MybatisPlusException("请输入正确的" + tip + "！");
    }

}