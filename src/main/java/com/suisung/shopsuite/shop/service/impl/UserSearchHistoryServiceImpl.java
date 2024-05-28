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
package com.suisung.shopsuite.shop.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.shop.model.entity.UserSearchHistory;
import com.suisung.shopsuite.shop.model.req.UserSearchHistoryListReq;
import com.suisung.shopsuite.shop.model.res.SearchInfoRes;
import com.suisung.shopsuite.shop.model.vo.SearchWordVo;
import com.suisung.shopsuite.shop.repository.UserSearchHistoryRepository;
import com.suisung.shopsuite.shop.service.UserSearchHistoryService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户搜索历史记录表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-08-04
 */
@Service
public class UserSearchHistoryServiceImpl extends BaseServiceImpl<UserSearchHistoryRepository, UserSearchHistory, UserSearchHistoryListReq> implements UserSearchHistoryService {

    @Autowired
    private ConfigBaseService configBaseService;

    /**
     * 返回搜索关键词
     *
     * @return
     */
    @Override
    public SearchInfoRes getSearchInfo() {
        SearchInfoRes infoRes = new SearchInfoRes();

        String suggestSearchWords = configBaseService.getConfig("suggest_search_words");
        String searchHotWords = configBaseService.getConfig("search_hot_words");

        if (StrUtil.isNotBlank(suggestSearchWords)) {
            List<String> wordsArray = Convert.toList(String.class, suggestSearchWords);
            String wordStr = wordsArray.get(RandomUtil.randomInt(0, wordsArray.size()));
            SearchWordVo searchWordVo = new SearchWordVo(wordStr, wordStr);
            infoRes.setSuggestSearchWords(searchWordVo);
            infoRes.setSearchHotWords(Convert.toList(String.class, searchHotWords));
        }

        ContextUser user = ContextUtil.getLoginUser();
        Integer userId = ObjectUtil.isNotNull(user) ? user.getUserId() : null;

        if (userId != null) {
            // 查询用户搜索记录
            QueryWrapper<UserSearchHistory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId).orderByDesc("search_time");
            List<UserSearchHistory> searchHistories = find(queryWrapper);

            if (CollUtil.isNotEmpty(searchHistories)) {
                List<String> keyWords = searchHistories.stream().map(UserSearchHistory::getSearchKeyword).collect(Collectors.toList());
                infoRes.setSearchHistoryWords(keyWords);
            } else {
                infoRes.setSearchHistoryWords(new ArrayList<>());
            }
        }
        return infoRes;
    }

}
