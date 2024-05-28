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
package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.suisung.shopsuite.core.web.model.req.BaseListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 计划任务表
 * </p>
 *
 * @author Xinze
 * @since 2021-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "计划任务表分页查询")
public class CrontabBaseListReq extends BaseListReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务编号")
    private Integer crontabId;

    @ApiModelProperty("任务名称")
    private String crontabName;

    @ApiModelProperty("任务脚本")
    private String crontabFile;

    @ApiModelProperty("上次执行时间")
    private Integer crontabLastExeTime;

    @ApiModelProperty("下次执行时间")
    private Integer crontabNextExeTime;

    @ApiModelProperty("分钟(LIST):*-每分; 0-0;1-1; 2-2; 3-3; 4-4; 5-5; 6-6; 7-7; 8-8; 9-9; 10-10; 11-11; 12-12; 13-13; 14-14; 15-15; 16-16; 17-17; 18-18; 19-19; 20-20; 21-21; 22-22; 23-23; 24-24; 25-25; 26-26; 27-27; 28-28; 29-29; 30-30; 31-31; 32-32; 33-33; 34-34; 35-35; 36-36; 37-37; 38-38; 39-39; 40-40; 41-41; 42-42; 43-43; 44-44; 45-45; 46-46; 47-47; 48-48; 49-49; 50-50; 51-51; 52-52; 53-53; 54-54; 55-55; 56-56; 57-57; 58-58; 59-59")
    private String crontabMinute;

    @ApiModelProperty("小时(LIST):*-任意; 0-0;1-1; 2-2; 3-3; 4-4; 5-5; 6-6; 7-7; 8-8; 9-9; 10-10; 11-11; 12-12; 13-13; 14-14; 15-15; 16-16; 17-17; 18-18; 19-19; 20-20; 21-21; 22-22; 23-23")
    private String crontabHour;

    @ApiModelProperty("每天(LIST):*-任意;1-1; 2-2; 3-3; 4-4; 5-5; 6-6; 7-7; 8-8; 9-9; 10-10; 11-11; 12-12; 13-13; 14-14; 15-15; 16-16; 17-17; 18-18; 19-19; 20-20; 21-21; 22-22; 23-23; 24-24; 25-25; 26-26; 27-27; 28-28; 29-29; 30-30; 31-31")
    private String crontabDay;

    @ApiModelProperty("每月(LIST):*-任意;1-1月; 2-2月; 3-3月; 4-4月; 5-5月; 6-6月; 7-7月; 8-8月; 9-9月; 10-10月; 11-11月; 12-12月")
    private String crontabMonth;

    @ApiModelProperty("每周(LIST):*-每周;0-周日; 1-周一;2-周二;3-周三;4-周四;5-周五;6-周六")
    private String crontabWeek;

    @ApiModelProperty("是否启用(BOOL):0-禁用; 1-启用")
    private Boolean crontabEnable;

    @ApiModelProperty("是否内置(BOOL):0-否; 1-是")
    private Boolean crontabBuildin;

    @ApiModelProperty("任务备注")
    private String crontabRemark;


}
