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
package com.suisung.shopsuite.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统参数设置表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_config_base")
@ApiModel(value = "ConfigBase对象", description = "系统参数设置表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConfigBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("配置编码")
    @TableId(value = "config_key", type = IdType.AUTO)
    private String configKey;

    @ApiModelProperty("配置标题")
    @TableField("config_title")
    private String configTitle;

    @ApiModelProperty("配置类型(ENUM):readonly-只读文本;number-数字;text-单行文本;textarea-多行文本;array-数组;password-密码;radio-单选框;checkbox-复选框;select-下拉框;icon-字体图标;date-日期;datetime-时间;image-单张图片;images-多张图片;file-单个文件;files-多个文件;ueditor-富文本编辑器;area-地区选择")
    @TableField("config_datatype")
    private String configDatatype;

    @ApiModelProperty("配置项")
    @TableField("config_options")
    private String configOptions;

    @ApiModelProperty("配置值")
    @TableField("config_value")
    private String configValue;

    @ApiModelProperty("所属分类")
    @TableField("config_type_id")
    private Integer configTypeId;

    @ApiModelProperty("配置注释")
    @TableField("config_note")
    private String configNote;

    @ApiModelProperty("配置排序:从小到大")
    @TableField("config_sort")
    private Integer configSort;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    @TableField("config_enable")
    private Boolean configEnable;

    @ApiModelProperty("系统内置(BOOL):1-是; 0-否")
    @TableField("config_buildin")
    private Boolean configBuildin;
}
