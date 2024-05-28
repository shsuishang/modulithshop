// +----------------------------------------------------------------------
// | ShopSuite商城系统 [ 赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | 版权所有 随商信息技术（上海）有限公司
// +----------------------------------------------------------------------
// | 未获商业授权前，不得将本软件用于商业用途。禁止整体或任何部分基础上以发展任何派生版本、
// | 修改版本或第三方版本用于重新分发。
// +----------------------------------------------------------------------
// | 官方网站: https://www.shopsuite.cn  https://www.modulithshop.cn
// +----------------------------------------------------------------------
// | 版权和免责声明:
// | 本公司对该软件产品拥有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等）
// | 均受到相关法律法规的保护，任何个人、组织和单位不得在未经本团队书面授权的情况下对所授权
// | 软件框架产品本身申请相关的知识产权，禁止用于任何违法、侵害他人合法权益等恶意的行为，禁
// | 止用于任何违反我国法律法规的一切项目研发，任何个人、组织和单位用于项目研发而产生的任何
// | 意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、
// | 附带或衍生的损失等)，本团队不承担任何法律责任，本软件框架只能用于公司和个人内部的
// | 法律所允许的合法合规的软件产品研发，详细见https://www.modulithshop.cn/policy
// +----------------------------------------------------------------------
package com.suisung.shopsuite.${package.ModuleName}.service;

import ${package.Entity}.${entity};
import ${package.Other}.req.${entity}ListReq;
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import com.suisung.shopsuite.core.web.service.IBaseService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
</#list>
/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
*/
public interface ${entity}Service extends IBaseService<${entity}, ${entity}ListReq> {
<#if false>
    /**
    * 插入${table.comment}数据
    *
    * @param ${entity?uncap_first} ${table.comment}
    * @return boolean
    */
    boolean add(${entity} ${entity?uncap_first});

    /**
    * 更新 ${table.comment} 数据
    *
    * @param ${entity?uncap_first} ${table.comment}
    * @return boolean
    */
    boolean edit(${entity} ${entity?uncap_first});

    /**
    * 根据${keyPropertyName}删除
    *
    * @param ${keyPropertyName}
    * @return boolean
    */
    boolean remove(Serializable ${keyPropertyName});

    /**
    * 根据${keyPropertyName}集合删除 ${table.comment} 数据
    *
    * @param ${keyPropertyName}s
    * @return boolean
    */
    boolean remove(Collection<? extends Serializable> ${keyPropertyName}s);

    /**
    * 查询${table.comment}数据
    *
    * @return ${entity}
    */
    ${entity} get(Serializable ${keyPropertyName});

    /**
    * 根据主键读取${table.comment}列表
    *
    * @param ${keyPropertyName}s
    * @return List<${entity}> ${table.comment}类表
    */
    List<${entity}> gets(Collection<? extends Serializable> ${keyPropertyName}s);

    /**
    * 根据条件查询${table.comment}分页信息
    *
    * @param wrapper
    * @param pageNum
    * @param pageSize
    * @return Page<${entity}>
    */
    Page<${entity}> lists(QueryWrapper<${entity}> wrapper, Integer pageNum, Integer pageSize);
</#if>
}
