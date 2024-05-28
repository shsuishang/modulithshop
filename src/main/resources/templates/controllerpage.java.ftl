package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.suisung.mall.common.api.CommonRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

<#if restControllerStyle!>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import java.util.Arrays;

/**
* <p>
    * ${table.comment} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Api(tags = "${table.comment}")
<#if restControllerStyle!>
    @RestController
<#else>
    @Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
        public class ${table.controllerName}Ui {
    </#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    /**
    * 分页列表查询
    *
    * @param ${entity?uncap_first}
    * @param pageNum
    * @param pageSize
    * @return
    */
    @ApiOperation(value="${table.comment}-分页列表查询", notes="${table.comment}-分页列表查询")
    @GetMapping(value = "/lists")
    public CommonRes queryPageLists(@RequestParam(name="queryWrapper", required=false)${entity} ${entity?uncap_first},
    @RequestParam(name="pageNum", defaultValue="1") Integer pageNum,
    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {

    QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByAsc("id");
    Page<${entity}> page = new Page<>(pageNum, pageSize);
    IPage<${entity}> pageList = ${entity?uncap_first}Service.page(page, queryWrapper);
    return CommonRes.success(pageList);
    }

    /**
    * 通过id查询
    *
    * @param id
    * @return
    */
    @ApiOperation(value="${table.comment}-通过id查询", notes="${table.comment}-通过id查询")
    @GetMapping(value = "/queryById")
    public CommonRes queryById(@RequestParam(name="id",required=true) String id) {

    ${entity} ${entity?uncap_first} = ${entity?uncap_first}Service.getById(id);
    return CommonRes.success(${entity?uncap_first});
    }

    /**
    * 添加数据
    *
    * @param ${entity?uncap_first}
    * @return
    */
    @ApiOperation(value="${table.comment}-添加", notes="${table.comment}-添加")
    @PostMapping(value = "/add")
    public CommonRes add(@RequestBody ${entity} ${entity?uncap_first}) {

    ${entity?uncap_first}Service.save(${entity?uncap_first});
    return CommonRes.success("添加成功！");
    }

    /**
    * 编辑更新
    *
    * @param ${entity?uncap_first}
    * @return
    */
    @ApiOperation(value="${table.comment}-编辑", notes="${table.comment}-编辑")
    @PutMapping(value = "/edit")
    public CommonRes edit(@RequestBody ${entity} ${entity?uncap_first}) {

    ${entity?uncap_first}Service.updateById(${entity?uncap_first});
    return CommonRes.success("编辑成功!");
    }

    /**
    * 通过id删除
    *
    * @param id
    * @return
    */
    @ApiOperation(value="${table.comment}-通过id删除", notes="${table.comment}-通过id删除")
    @DeleteMapping(value = "/delete")
    public CommonRes delete(@RequestParam(name="id",required=true) String id) {

    ${entity?uncap_first}Service.removeById(id);
    return CommonRes.success("删除成功!");
    }

    /**
    * 批量删除
    *
    * @param ids
    * @return
    */
    @ApiOperation(value="${table.comment}-批量删除", notes="${table.comment}-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public CommonRes deleteBatch(@RequestParam(name="ids",required=true) String ids) {

    this.${entity?uncap_first}Service.removeByIds(Arrays.asList(ids.split(",")));
    return CommonRes.success("批量删除成功！");
    }
</#if>
}

