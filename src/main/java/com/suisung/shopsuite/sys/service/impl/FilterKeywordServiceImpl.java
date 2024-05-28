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
package com.suisung.shopsuite.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.RedisService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.sys.model.entity.FilterKeyword;
import com.suisung.shopsuite.sys.model.req.FilterKeywordListReq;
import com.suisung.shopsuite.sys.repository.FilterKeywordRepository;
import com.suisung.shopsuite.sys.service.FilterKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.consts.ConstantSns.KEYWORD_REGEX_REDIS_KEY;
import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 敏感词过滤-启用api 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-08-02
 */
@Service
public class FilterKeywordServiceImpl extends BaseServiceImpl<FilterKeywordRepository, FilterKeyword, FilterKeywordListReq> implements FilterKeywordService {

    @Autowired
    private RedisService redisService;

    /**
     * 判断字符中是否包含敏感词
     *
     * @param original
     * @return
     */
    @Override
    public boolean hasKeyword(String original) {
        if (StrUtil.isNotEmpty(original)) {
            // 先读缓存中的正则，没有就查表再拼
            String regex = (String) redisService.get(KEYWORD_REGEX_REDIS_KEY);
            if (StrUtil.isEmpty(regex)) {
                // 缓存没有，查表之后手动拼
                QueryWrapper<FilterKeyword> keywordQueryWrapper = new QueryWrapper<>();
                keywordQueryWrapper.eq("filter_enable", 1);
                List<FilterKeyword> keywordList = find(keywordQueryWrapper);
                // 没有配置任何敏感词
                if (CollUtil.isEmpty(keywordList)) {
                    return true;
                }
                // 拼正则表达式
                List<String> banned = keywordList.stream()
                        .map(FilterKeyword::getFilterFind)
                        .collect(Collectors.toList());
                regex = ".*(" + String.join("|", banned) + ").*";
                // 表达式置入缓存
                redisService.set(KEYWORD_REGEX_REDIS_KEY, regex);
            }
            // 判断源串是否含有敏感词
            Pattern pattern = Pattern.compile(regex);
            if (!pattern.matcher(original).matches()) {
                throw new BusinessException(__("评论中包含非法词汇！"));
            }
        }
        return true;
    }
}
