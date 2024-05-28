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
package com.suisung.shopsuite.cms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.cms.model.entity.ArticleTag;
import com.suisung.shopsuite.cms.model.req.ArticleTagListReq;
import com.suisung.shopsuite.cms.repository.ArticleTagRepository;
import com.suisung.shopsuite.cms.service.ArticleTagService;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 文章标签表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-08
 */
@Service
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleTagRepository, ArticleTag, ArticleTagListReq> implements ArticleTagService {


    @Override
    public IPage<ArticleTag> getLists(ArticleTagListReq articleTagListReq) {
        return lists(articleTagListReq);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Integer tagId) {
        //判断标签内容数量 该标签是否有文章引用
        ArticleTag byId = get(tagId);

        if (byId != null && byId.getTagCount() > 0) {
            throw new BusinessException(__("该标签有文章引用"));
        }

        return remove(tagId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeBatch(List<Integer> toList) {
        List<ArticleTag> articleTags = gets(toList);

        if (!CollectionUtils.isEmpty(articleTags)) {
            for (ArticleTag byId : articleTags) {

                if (byId.getTagCount() > 0) {
                    throw new BusinessException(__("该标签有文章引用"));
                }

            }

        }

        return remove(toList);
    }
}
