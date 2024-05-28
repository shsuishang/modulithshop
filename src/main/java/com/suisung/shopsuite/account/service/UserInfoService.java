package com.suisung.shopsuite.account.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.model.output.UserInfoOutput;
import com.suisung.shopsuite.account.model.req.UserInfoListReq;
import com.suisung.shopsuite.core.web.service.IBaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户详细信息表 服务类
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
public interface UserInfoService extends IBaseService<UserInfo, UserInfoListReq> {

    Page<UserInfo> getList(QueryWrapper<UserInfo> queryWrapper, Integer page, Integer size);

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    UserInfoOutput getUserData(Integer userId);

    /**
     * 修改用户
     *
     * @param userInfo
     * @return
     */
    boolean editUser(UserInfo userInfo);

    /**
     * 修改密码
     *
     * @param userId
     * @param userPassword
     * @return
     */
    boolean passWordEdit(Integer userId, String userPassword);

    /**
     * 批量设置标签
     *
     * @param userIds
     * @param tagIds
     * @return
     */
    boolean addTags(String userIds, String tagIds);

    /**
     * 删除用户账号
     *
     * @param userId
     * @return
     */
    boolean removeUser(Integer userId);

    /**
     * 导出模版
     *
     * @param response
     */
    void exportTemp(HttpServletResponse response);

    /**
     * 导出指定用户详细信息
     *
     * @param response
     * @param userIds
     */
    void exportFile(HttpServletResponse response, List<Integer> userIds);

    /**
     * 导入用户信息
     *
     * @param file
     */
    void importTemp(MultipartFile file) throws Exception;

    /**
     * 批量发放优惠券
     *
     * @param userIds
     * @param activityId
     */
    void addVouchers(List<Integer> userIds, Integer activityId);
}
