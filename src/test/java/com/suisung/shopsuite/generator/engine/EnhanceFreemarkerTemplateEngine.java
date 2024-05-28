package com.suisung.shopsuite.generator.engine;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器支持自定义[DTO\VO等]模版
 */
public final class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    /**
     * 渲染对象 MAP 信息
     *
     * @param config    配置信息
     * @param tableInfo 表信息对象
     * @return ignore
     */
    @org.jetbrains.annotations.NotNull
    public Map<String, Object> getObjectMap(@org.jetbrains.annotations.NotNull ConfigBuilder config, @org.jetbrains.annotations.NotNull TableInfo tableInfo) {
        StrategyConfig strategyConfig = config.getStrategyConfig();
        Map<String, Object> controllerData = strategyConfig.controller().renderData(tableInfo);
        Map<String, Object> objectMap = new HashMap<>(controllerData);
        Map<String, Object> mapperData = strategyConfig.mapper().renderData(tableInfo);
        objectMap.putAll(mapperData);
        Map<String, Object> serviceData = strategyConfig.service().renderData(tableInfo);
        objectMap.putAll(serviceData);
        Map<String, Object> entityData = strategyConfig.entity().renderData(tableInfo);
        objectMap.putAll(entityData);
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageConfig().getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger", globalConfig.isSwagger());
        objectMap.put("date", globalConfig.getCommentDate());
        // 启用 schema 处理逻辑
        String schemaName = "";
        if (strategyConfig.isEnableSchema()) {
            // 存在 schemaName 设置拼接 . 组合表名
            schemaName = config.getDataSourceConfig().getSchemaName();
            if (StringUtils.isNotBlank(schemaName)) {
                schemaName += ".";
                tableInfo.setConvert(true);
            }
        }
        objectMap.put("schemaName", schemaName);
        objectMap.put("table", tableInfo);
        objectMap.put("entity", tableInfo.getEntityName());

        //Logic Name
        String[] s = StrUtil.splitToArray(tableInfo.getName(), "_");

        if (s[2].equals("base")) {
            //objectMap.put("serviceModule", s[1]);
            objectMap.put("serviceModule", StrUtil.lowerFirst(tableInfo.getEntityName()));
        } else {
            objectMap.put("serviceModule", StrUtil.lowerFirst(tableInfo.getEntityName()));
        }

        return objectMap;
    }

    @Override
    protected void outputEntity(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = getPathInfo(OutputFile.entity);
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
            getTemplateFilePath(template -> template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())).ifPresent((entity) -> {
                String entityFile = String.format((entityPath + File.separator + "%s" + suffixJavaOrKt()), entityName);
                outputFile(new File(entityFile), objectMap, entity, true);
            });
        }
    }

    @Override
    protected void outputCustomFile(@NotNull Map<String, String> customFile, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String tableName = entityName.replace("Entity", "");

        String otherPath = this.getPathInfo(OutputFile.other);
        customFile.forEach((key, value) -> {
            String dir = "";
            String prefix = "";
            if (key.equals("ListInput.java") || key.equals("AddInput.java") || key.equals("EditInput.java")) {
                dir = "input";
            } else if (key.equals("ListOutput.java") || key.equals("AddOutput.java") || key.equals("EditOutput.java")) {
                dir = "output";
            } else if (key.equals("ListReq.java") || key.equals("AddReq.java") || key.equals("EditReq.java")) {
                dir = "req";
            } else if (key.equals("ListRes.java") || key.equals("AddRes.java") || key.equals("EditRes.java")) {
                dir = "res";
            } else if (key.equals("Service.java")) {
                dir = "../service/";
                prefix = "";
            } else if (key.equals("ServiceImpl.java")) {
                dir = "../service/impl/";
            }
            String fileName = "";
            if (key.equals("Service.java") || key.equals("ServiceImpl.java")) {
                String[] s = StrUtil.splitToArray(tableInfo.getName(), "_");
                String logicModuleName = s[1];

                if (s[2].equals("base")) {
                    //多生成一次汇总Service模板
                    logicModuleName = StrUtil.upperFirst(logicModuleName);
                    //fileName = String.format(otherPath + File.separator + dir.toLowerCase() + File.separator + prefix + logicModuleName + "%s", key);
                    //this.outputFile(new File(fileName), objectMap, value);
                } else {
                }

                logicModuleName = tableName;
                fileName = String.format(otherPath + File.separator + dir.toLowerCase() + File.separator + prefix + logicModuleName + "%s", key);
            } else {
                fileName = String.format(otherPath + File.separator + dir.toLowerCase() + File.separator + prefix + tableName + "%s", key);
            }

            boolean fileOverrid = false;

            if (key.equals("ListReq-nnnn.java") || key.equals("AddReq.java")) {
                fileOverrid = true;
                //EditReq.java  AddReq.java ListReq.java
            }

            this.outputFile(new File(fileName), objectMap, value, fileOverrid);
        });
    }
}
