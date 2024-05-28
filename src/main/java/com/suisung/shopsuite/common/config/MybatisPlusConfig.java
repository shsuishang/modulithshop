package com.suisung.shopsuite.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.suisung.shopsuite.account.model.entity.UserBase;
import com.suisung.shopsuite.common.mybatisplus.SqlLogInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

/**
 * MybatisPlus配置
 *
 * @author Xinze
 * @since 2018-02-22 11:29:28
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    @Bean
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

    @Bean
    public MybatisPlusInterceptor optimisticLockerInnerInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 多租户插件配置
        TenantLineHandler tenantLineHandler = new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return getLoginUserTenantId();
            }

            @Override
            public String getTenantIdColumn() {
                return "subsite_id";
            }

            @Override
            public boolean ignoreTable(String tableName) {
                return true || Arrays.asList(
                        "account_user_base",
                        "sys_tenant",
                        "sys_dictionary",
                        "sys_dictionary_data"
                ).contains(tableName);
            }
        };
        TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor(tenantLineHandler);
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);

        // 分页插件配置
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        return interceptor;
    }

    /**
     * 获取当前登录用户的租户id
     *
     * @return Integer
     */
    public Expression getLoginUserTenantId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object object = authentication.getPrincipal();
                if (object instanceof UserBase) {
                    return new LongValue(((UserBase) object).getSiteId());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new NullValue();
    }

}
