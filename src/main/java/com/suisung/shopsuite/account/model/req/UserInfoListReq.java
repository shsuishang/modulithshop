package com.suisung.shopsuite.account.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.annotation.QueryField;
import com.suisung.shopsuite.core.annotation.QueryType;
import com.suisung.shopsuite.core.consts.Constants;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 用户详细信息表
 * </p>
 *
 * @author Xinze
 * @since 2022-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "用户详细信息表分页查询")
public class UserInfoListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("用户账号")
    @QueryField(type = QueryType.LIKE)
    private String userAccount;

    @ApiModelProperty("用户昵称")
    @QueryField(type = QueryType.LIKE)
    private String userNickname;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("状态(ENUM):0-锁定;1-已激活;2-未激活;")
    private Integer userState;

    @ApiModelProperty("性别(ENUM):0-保密;1-男;  2-女;")
    private Integer userGender;

    @ApiModelProperty("生日(DATE)")
    private LocalDate userBirthday;

    @ApiModelProperty("国家编码")
    private String userIntl;

    @ApiModelProperty("手机号码(mobile)")
    private String userMobile;

    @ApiModelProperty("用户邮箱(email)")
    private String userEmail;

    @ApiModelProperty("用户等级")
    private Integer userLevelId;

    @ApiModelProperty("用户标签")
    @QueryField(type = QueryType.FIND_IN_SET_STR)
    private String tagIds;

    @ApiModelProperty("认证状态(ENUM):0-未认证;1-待审核;2-认证通过;3-认证失败")
    @QueryField(type = QueryType.EQ)
    private Integer userIsAuthentication;

    public UserInfoListReq() {
        setSidx("user_id");
        setSort(Constants.ORDER_BY_DESC);
    }
}
