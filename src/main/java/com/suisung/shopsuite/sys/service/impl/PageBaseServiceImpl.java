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
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.cms.model.entity.ArticleBase;
import com.suisung.shopsuite.cms.model.entity.ArticleCategory;
import com.suisung.shopsuite.cms.repository.ArticleBaseRepository;
import com.suisung.shopsuite.cms.repository.ArticleCategoryRepository;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.utils.JSONUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.marketing.model.entity.ActivityBase;
import com.suisung.shopsuite.marketing.model.entity.ActivityItem;
import com.suisung.shopsuite.marketing.model.vo.PageDataVo;
import com.suisung.shopsuite.marketing.repository.ActivityBaseRepository;
import com.suisung.shopsuite.marketing.repository.ActivityItemRepository;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import com.suisung.shopsuite.pt.model.entity.*;
import com.suisung.shopsuite.pt.model.input.ProductItemInput;
import com.suisung.shopsuite.pt.model.output.ItemOutput;
import com.suisung.shopsuite.pt.model.res.ItemListRes;
import com.suisung.shopsuite.pt.repository.*;
import com.suisung.shopsuite.pt.service.ProductIndexService;
import com.suisung.shopsuite.sys.model.entity.PageBase;
import com.suisung.shopsuite.sys.model.req.PageBaseListReq;
import com.suisung.shopsuite.sys.model.req.PageDataReq;
import com.suisung.shopsuite.sys.model.res.PageBaseRes;
import com.suisung.shopsuite.sys.model.vo.ImConfigVo;
import com.suisung.shopsuite.sys.model.vo.PageDataItemVo;
import com.suisung.shopsuite.sys.model.vo.PageMenuVo;
import com.suisung.shopsuite.sys.model.vo.PageMobileVo;
import com.suisung.shopsuite.sys.repository.PageBaseRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.PageBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 页面表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-07-03
 */
@Service
public class PageBaseServiceImpl extends BaseServiceImpl<PageBaseRepository, PageBase, PageBaseListReq> implements PageBaseService {

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ArticleBaseRepository articleBaseRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private PageBaseRepository pageBaseRepository;

    @Autowired
    private ActivityBaseRepository activityBaseRepository;

    @Autowired
    private ActivityItemRepository activityItemRepository;

    @Autowired
    private ProductIndexRepository productIndexRepository;


    @Autowired
    private ProductIndexService productIndexService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductBaseRepository productBaseRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ActivityBaseService activityBaseService;

    @Override
    public IPage<PageDataItemVo> getDataInfo(PageDataReq pageDataReq) {
        IPage<PageDataItemVo> baseListRes = new Page<>();
        List<PageDataItemVo> pageDataItemVoList = new ArrayList<>();

        Integer type = pageDataReq.getType();

        if (CheckUtil.isEmpty(type)) {
            throw new BusinessException(__("请求参数不能为空"));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        switch (type) {
            case 1:
            case 104:
                //商品
                ProductItemInput input = new ProductItemInput();
                input.setProductName(pageDataReq.getName());

                if (StrUtil.isNotEmpty(pageDataReq.getName())) {
                    input.setKeywords(pageDataReq.getName());
                }

                input.setPage(pageDataReq.getPage());
                input.setSize(pageDataReq.getSize());

                ItemListRes productItemRes = productIndexService.listItem(input);
                baseListRes.setTotal(productItemRes.getRecords());
                baseListRes.setSize(productItemRes.getSize());
                baseListRes.setCurrent(productItemRes.getPage());
                baseListRes.setPages(productItemRes.getTotal());

                BeanUtils.copyProperties(productItemRes, baseListRes);
                List<ItemOutput> productItemList = productItemRes.getItems();

                if (CollectionUtil.isNotEmpty(productItemList)) {
                    for (ItemOutput productIndex : productItemList) {
                        PageDataItemVo pageDataItemVo = new PageDataItemVo();

                        pageDataItemVo.setProductTips(productIndex.getProductTips());

                        pageDataItemVo.setId(productIndex.getItemId());
                        pageDataItemVo.setName(productIndex.getProductName() + productIndex.getItemName());
                        pageDataItemVo.setMarketPice(productIndex.getItemMarketPrice().intValue());
                        pageDataItemVo.setItemSalePrice(productIndex.getItemUnitPrice().intValue());
                        pageDataItemVo.setPath(productIndex.getProductImage());

                        pageDataItemVoList.add(pageDataItemVo);
                    }

                    baseListRes.setRecords(pageDataItemVoList);
                }
                break;
            case 2:
                //店铺分类
                QueryWrapper<ProductCategory> productCategoryQueryWrapper = new QueryWrapper<>();

                if (StrUtil.isNotEmpty(pageDataReq.getName())) {
                    productCategoryQueryWrapper.like("category_name", pageDataReq.getName());
                }
                IPage<ProductCategory> productCategoryPage = productCategoryRepository.lists(productCategoryQueryWrapper, pageDataReq.getPage(), pageDataReq.getSize());
                BeanUtils.copyProperties(productCategoryPage, baseListRes);

                if (CollectionUtil.isNotEmpty(productCategoryPage.getRecords())) {
                    productCategoryPage.getRecords().forEach(item -> {
                        PageDataItemVo pageDataItemVo = new PageDataItemVo();
                        pageDataItemVo.setId(item.getCategoryId().longValue());
                        pageDataItemVo.setPath(item.getCategoryImage());
                        pageDataItemVo.setName(item.getCategoryName());
                        pageDataItemVoList.add(pageDataItemVo);
                    });
                    baseListRes.setRecords(pageDataItemVoList);
                }
                break;
            case 3:
                //APP
                String strJsonData = "[{\"id\":872,\"name\":\"<section  ><section style=\\\"margin:5px 0;box-sizing: border-box;\\\"><section  style=\\\"width:96%;clear:both;overflow:hidden;margin:0 auto;background-color:#8787B5;\\\"><section style=\\\"width:55%;float:left;overflow:hidden;\\\"><img   style=\\\"max-width:100%;float:left;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/0d119208-64c3-4a75-988f-745c7b0241fe1.jpg\\\"></section><section style=\\\"display:inline-block;width:45%;float:right;\\\"><section style=\\\"margin-right:0.3em;margin-top:30px;padding:0.3em 0.5em;color:#FFFFFF;font-size:0.9em;font-family:inherit;font-weight:inherit;text-align:center;text-decoration:inherit;\\\"><p>燕飞蝉寒秋叶黄，</p><p>雀叫枣红荷叶清。</p><p>碧云蓝天气色浓，</p><p>转身回头又风景。</p></section></section></section><section style=\\\"width: 0px; height: 0px; clear: both;\\\"></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null,\"RowNumber\":1,\"Total\":190},{\"RowNumber\":2,\"Total\":190,\"id\":871,\"name\":\"<section  ><section style=\\\"margin:5px 0;box-sizing: border-box;\\\"><section style=\\\"margin:0;padding:0;box-sizing:border-box;padding-bottom:40px;\\\"><section style=\\\"text-align:center;color:inherit;\\\"><section style=\\\"color:inherit;width:320px;display:inline-block;\\\"><img   style=\\\"color:inherit;width:100%;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/be76aed9-a53a-455e-ae21-8464a2951d702.jpg\\\"></section></section><section style=\\\"text-align:center;color:inherit;margin-top:-110px;\\\"><section style=\\\"display:inline-block;color:inherit;\\\"><section  style=\\\"height:0px;border-style:solid;border-width:0px 0 70px 90px;border-color:transparent transparent #292929;color:inherit;float:left;margin-left:20px;\\\"></section><section  style=\\\"float:left;height:70px;background-color:#292929;width:190px;color:#ffffff;font-size:18px;text-align:center;padding-top:6px;\\\">离时髦忽远又忽近？</section></section></section><section style=\\\"margin-top:-40px;text-align:center;margin-bottom:12px;color:inherit;\\\"><section style=\\\"width:320px;display:inline-block;padding-left:20px;color:inherit;\\\"><section style=\\\"color:#F7F9F6;border-color:#80B135;text-align:left;margin-left:40px;\\\"><span style=\\\"border-color:#FDFDFD;color:inherit;font-size:18px;\\\">“文青风”不是你想穿就能穿！</span></section></section></section></section><section style=\\\"width: 0px; height: 0px; clear: both;\\\"></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":3,\"Total\":190,\"id\":870,\"name\":\"<section  ><section style=\\\"border: 20px solid #f96e57;-webkit-border-image: url(http://files.qiluzhaoshang.com//fck007/2017042015/c74022df-eb0c-481c-a5d5-7c8d2eda39a132.png) 20 20;-moz-border-image: url(http://files.qiluzhaoshang.com//fck007/2017042015/c74022df-eb0c-481c-a5d5-7c8d2eda39a132.png) 20 20;-ms-border-image: url(http://files.qiluzhaoshang.com//fck007/2017042015/c74022df-eb0c-481c-a5d5-7c8d2eda39a132.png) 20 20;padding: 0;margin: 0;\\\"><p style=\\\"text-align:center;white-space:normal\\\"><img   style=\\\"width:100%;margin:0;height:auto!important\\\" height=\\\"auto\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/aba52f28-0c27-44dd-bc6c-6dbeeea869cd3.jpg\\\"></p></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":4,\"Total\":190,\"id\":869,\"name\":\"<section  ><section style=\\\"box-sizing:border-box;margin:5px;\\\"><img  style=\\\"width:200px;float:left;margin-right:10px;margin-bottom:5px\\\"  src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/8f61b9f8-3e2b-45e6-9dc0-f1b39822d93a4.jpg\\\"><section style=\\\"font-size:14px;font-family:inherit;line-height:30px;text-decoration:inherit\\\"><section>人没安全感，总会不确定。试着去接受一个爱你的人，也只有在与之相处的过程里你才会体会到，这到底是怎样的爱。过度的怕和试探忐忑的矫情，也许会让你看的更清透，但也同样会让你错过感情最美的时候，情感的递增需要一个过程，而它的开始是心动。当有一天你懂了，那时你失去的也许是你的全世界</section></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":5,\"Total\":190,\"id\":868,\"name\":\"<section  ><section style=\\\"background:#fff;text-align:center;border-style:none;clear:both;overflow:hidden;margin:5px 0;\\\"><span  style=\\\"padding: 5px; margin-left: 6px; border: 1px solid rgb(95, 170, 255); float: right;\\\"><img   style=\\\"display:block;width:200px;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/7838ecdd-96a4-4012-b9f0-00b5445de3bd5.jpg\\\"></span><section style=\\\"line-height:1.5;text-align:left;font-size:14px\\\"><p style=\\\"display:inline\\\">智慧本身就是好的。有一天我们都会死去，追求智慧的道路还会有人在走着。死掉以后的事我看不到。但在我活着的时候，想到这件事，心里就很高兴。</p></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":6,\"Total\":190,\"id\":867,\"name\":\"<section  ><section style=\\\"background:#fff;text-align:center;border-style:none;clear:both;overflow:hidden;margin:5px 0;\\\"><span  style=\\\"margin-right: 6px; padding: 5px; border: 1px solid rgb(95, 170, 255); float: left;\\\"><img   style=\\\"display:block;width:200px\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/67863ba5-d73b-48b2-85cd-7634ac11502b6.jpg\\\"></span><section style=\\\"line-height:30px;text-align:left;font-size:14px\\\"><p style=\\\"display:inline\\\">&nbsp;智慧本身就是好的。有一天我们都会死去，追求智慧的道路还会有人在走着。死掉以后的事我看不到。但在我活着的时候，想到这件事，心里就很高兴。</p></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":7,\"Total\":190,\"id\":866,\"name\":\"<section  ><section style=\\\"margin-top:.5em;margin-bottom:.5em;box-sizing:border-box\\\"><section style=\\\"overflow:hidden\\\"><section  style=\\\"width: 10em; height: 2em; line-height: 2em; margin-top: 1em; margin-bottom: -4em; margin-right: -3em; -webkit-transform: rotate(45deg); font-size: 1.5em; font-family: inherit; font-weight: inherit; text-align: center; text-decoration: inherit; color: rgb(255, 255, 255); border-color: rgb(255, 255, 255); box-sizing: border-box; float: right; background-color: rgb(95, 170, 255);\\\"><section style=\\\"box-sizing:border-box\\\">咖啡物语</section></section><img  style=\\\"box-sizing:border-box;width:100%\\\"  src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/1ec3b5ed-9640-475e-bd31-72ddb95b8f3c7.jpg\\\"></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":8,\"Total\":190,\"id\":865,\"name\":\"<section  ><section style=\\\"background:#fff;text-align:center;overflow:hidden;margin: 10px auto;display: -webkit-box;display: -ms-flexbox;display: -webkit-flex;display: flex;-webkit-flex-wrap: nowarp;-ms-flex-wrap: nowarp;flex-wrap: nowarp;\\\"><span style=\\\"display: block;padding:3px;border:solid 1px #bfbfbf;margin-right:10px;-webkit-box-flex: 1;-webkit-flex: auto;-ms-flex: auto;flex: auto;\\\"><img   style=\\\"display:block;width: 100%;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/b6707719-3a67-4b52-b2ee-5662d6e802ea9.jpg\\\"></span><span style=\\\"padding:3px;display: block;border:solid 1px #bfbfbf;-webkit-box-flex: 1;-webkit-flex: auto;-ms-flex: auto;flex: auto;\\\"><img   style=\\\"display:block;width: 100%;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/054a991d-6b84-41c3-8aa8-4f4a8fedc81d8.jpg\\\"></span></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":9,\"Total\":190,\"id\":864,\"name\":\"<section  ><section style=\\\"margin-top: 0.5em; margin-bottom: 0.5em;box-sizing: border-box;\\\"><section style=\\\"margin: 3px; box-sizing: border-box; padding: 0px;\\\"><p style=\\\"text-align: center; box-sizing: border-box; color: inherit;\\\"><img   style=\\\"box-sizing: border-box; margin: 0px; padding: 0px; width: 100%; color: inherit;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/4458d6f2-64b2-4a3a-abe1-7ba9894a4d9c10.jpg\\\"></p><section style=\\\"padding: 2px 0px; box-sizing: border-box; margin: 0px; color: inherit;\\\"><section style=\\\"float: left; margin-right: 20px; margin-left: 5px; box-sizing: border-box; padding: 0px; color: inherit;\\\"><span style=\\\"box-sizing: border-box; color: rgb(216, 40, 33); font-size: 30px; margin: 0px; padding: 0px; border-color: rgb(216, 40, 33);\\\"><em  style=\\\"box-sizing: border-box; padding: 0px; margin: 0px; border-color: rgb(216, 40, 33); color: inherit;\\\">1</em></span><span style=\\\"box-sizing: border-box; font-size: 14px; margin: 0px; padding: 0px; color: inherit;\\\"><em style=\\\"box-sizing: border-box; padding: 0px; margin: 0px;color:rgb(153,153,153)\\\">/6</em></span></section><section style=\\\"padding: 5px 0px; box-sizing: border-box; margin-top: 5px; color: inherit;\\\"><p style=\\\"clear: none; font-size: 12px; line-height: 17px; box-sizing: border-box; padding: 0px; margin: 0px; color: inherit;\\\"><span style=\\\"box-sizing:border-box; color:rgb(165, 165, 165); margin:0px; padding:0px\\\">我要你知道,在这个世界上总有一个人是等着你的,不管在什么时候,不管在什么地方,反正你知道,总有这么个人。—— 张爱玲</span></p></section></section></section><section style=\\\"display: block; width: 0; height: 0; clear: both;\\\"></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":10,\"Total\":190,\"id\":863,\"name\":\"<section  ><section style=\\\"margin: 5px 0;box-sizing: border-box;\\\"><section style=\\\"border: 0px rgb(145, 168, 252);box-sizing: border-box;width: 100%;clear: both;padding: 0px 0.5em 0.5em 0px;text-align: center;\\\"><img   style=\\\"border-radius: 50%;box-sizing: border-box;vertical-align: baseline;width: 222px;height: 222px !important;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/59683df0-1688-4877-b3b4-a2624ffa78cd11.jpg\\\"><section style=\\\"max-width: 100%;margin: -3.2em 0 0 0;box-sizing: border-box;\\\"><section  style=\\\"display: inline-block;height: 45px;vertical-align: top;border-right-width: 21px;border-right-style: solid;border-right-color: rgb(95, 170, 255);box-sizing: border-box !important;border-top-width: 21px !important;border-top-style: solid !important;border-top-color: transparent !important;border-bottom-width: 21px !important;border-bottom-style: solid !important;border-bottom-color: transparent !important;\\\"></section><section  style=\\\"height: 45px;width: 192px;display: inline-block;color: rgb(255, 255, 255);font-size: 16px;font-weight: bold;padding: 4px 10px;line-height: 36px;vertical-align: middle;border-color: rgb(245, 248, 254);box-sizing: border-box !important;background-color: rgb(95, 170, 255);\\\"><span style=\\\"border-color: rgb(145, 168, 252);box-sizing: border-box;font-size: 16px;\\\">我是樱桃小丸子</span></section><section  style=\\\"display: inline-block;height: 45px;vertical-align: top;border-left-width: 22px;border-left-style: solid;border-left-color: rgb(95, 170, 255);box-sizing: border-box !important;border-top-width: 22px !important;border-top-style: solid !important;border-top-color: transparent !important;border-bottom-width: 22px !important;border-bottom-style: solid !important;border-bottom-color: transparent !important;\\\"></section></section></section><section style=\\\"display: block; width: 0; height: 0; clear: both;\\\"></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":11,\"Total\":190,\"id\":862,\"name\":\"<section  ><section style=\\\"border:none;border-style:none;margin: 5px 0;text-align:center;\\\"><span  style=\\\"width: 0px; height: 0px; border-style: solid; border-width: 1.5em 1em 1em; border-color: rgb(58, 188, 255) transparent transparent; display: inline-block;\\\"></span><span  style=\\\"width: 0px; height: 0px; border-style: solid; border-width: 1.3em 0.8em 0.8em; border-color: rgb(58, 188, 255) transparent transparent; margin: 0px auto; display: block;\\\"></span><span  style=\\\"padding: 0.5em; border: 1px solid rgb(58, 188, 255); border-radius: 50%; display: inline-block;\\\"><img   style=\\\"width: 14em;height: 14em; border-radius: 50%; display: block;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/fa44985f-9f16-4221-a32b-ae4ffcc3d59712.jpg\\\"></span><span style=\\\"padding: 0.5em 0;font-size: 1.25em;display: block;\\\"><section style=\\\"padding: 0 0.5em;display: inline-block;\\\"><p style=\\\"margin: 0\\\">面朝大海&nbsp;|&nbsp;春暖花开</p></section></span></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":12,\"Total\":190,\"id\":861,\"name\":\"<section  ><section style=\\\"margin: 5px 0;text-align:center;\\\"><section  style=\\\"width: 0px; height: 0px; margin: 0px auto; border-style: solid; border-width: 1.2em 1.2em 1.8em; border-color: transparent transparent rgb(58, 188, 255); display: block;\\\"><section style=\\\"width: 0;height: 0;border: solid 0.5em transparent;border-bottom: solid 1em #ffffff;margin-left: -0.5em;display: block;\\\"></section></section><section  style=\\\"width: 10em; margin: -0.6em auto 0px; border-top-style: solid; border-top-width: 1px; border-color: rgb(58, 188, 255); display: block; color: rgb(58, 188, 255);\\\"></section><section style=\\\"display: block;margin-top: 1.5em;\\\"><img   style=\\\"width: 8.5em;margin-right: 1em;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/cccb4867-d8ee-4ed4-99a5-50dbb573182014.jpg\\\"><img   style=\\\"width:8.5em\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/fcd696ed-5a10-41ab-a27a-58bbcbf4b8ca13.jpg\\\"></section><section  style=\\\"border-top-style: solid; border-top-width: 1px; border-color: rgb(58, 188, 255); width: 5em; color: rgb(58, 188, 255); margin-top: 0.5em; font-size: 2em; height: 0.5em; line-height: 0.5em; display: inline-block;\\\">....</section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":13,\"Total\":190,\"id\":860,\"name\":\"<section  ><section style=\\\"border:none;border-style:none;margin: 1em auto;text-align:center;\\\"><section  style=\\\"width: 16em;border-top: 2px solid rgb(58, 188, 255);display: inline-block;\\\"><section style=\\\"height: 1.4em;line-height: 1.4em;margin-top: -0.9em;display: block;\\\"><section  style=\\\"font-size: 1.25em; color: rgb(58, 188, 255); min-width: 6em; display: inline-block;\\\"><section style=\\\"font-size:14px;margin-right:-1px;display:inline-block;\\\">●</section><p style=\\\"margin: 0;display:inline-block;padding:0 10px;background-color: #ffffff;\\\">动静之间</p><section style=\\\"font-size: 14px;margin-left:-2px;display:inline-block;\\\">●</section></section></section></section><section style=\\\"margin: 1em auto;\\\"><img   style=\\\"display: inline-block;vertical-align: top;width: 20em;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/2f4ee319-bb38-4de7-b6ed-215b3652899316.jpg\\\"></section><section style=\\\"\\\"><img   style=\\\"width: 20em;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/74c3e9b2-f770-4c55-8954-a973e7a3f16515.png\\\"></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":14,\"Total\":190,\"id\":859,\"name\":\"<section  ><section style=\\\"border:none;border-style:none;margin: 1em auto 1em;text-align:center;width: 20em;color: #000000;\\\"><span style=\\\"display:block;\\\"><img   style=\\\"width: 20em;vertical-align: middle;float: left;display:inline-block;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/4432d79c-ca72-4e6b-8d2a-2f9ed4d4fa7417.jpg\\\"><span style=\\\"background-color: #ffffff;opacity: 0.5;width: 8.5em;height: 8.5em;line-height: 8.5em;overflow: hidden;border-radius: 50%;vertical-align: middle;margin-top: -13em;display: inline-block;\\\"><section style=\\\"font-weight: bold;margin-right: 1em;display: inline-block;vertical-align: middle;font-size: 1.25em;\\\"><p style=\\\"margin: 0;\\\">慢</p></section><section style=\\\"width: 1em;margin: 0.5em auto;display: inline-block;vertical-align: middle;font-size: 1em;\\\"><p style=\\\"margin: 0;line-height:1.1;\\\">是一种生活态度</p></section></span></span></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":15,\"Total\":190,\"id\":858,\"name\":\"<section  ><section style=\\\"width: 100%;clear: both;overflow: hidden;margin: 10px auto;\\\"><section style=\\\"width: 50%; float: left;\\\"><img style=\\\"width: 100%  !important;height: 201px !important;\\\"   src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/b2521319-647d-4641-a4e5-881e7f2b53b518.jpg\\\"></section><section style=\\\"display: inline-block; width: 50%;height: 201px; float: right;background: #68744e;\\\"><section style=\\\"margin-right: 0.3em;margin-top: 30px; padding: 0.3em 0.5em; color: rgb(255, 255, 255); font-size: 0.9em; font-family: inherit; font-weight: inherit; text-align: center; text-decoration: inherit;\\\"><p>孤独与否</p><p>岁月依旧</p><p>红了樱桃</p><p>绿了芭蕉</p></section></section><p></p></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":16,\"Total\":190,\"id\":857,\"name\":\"<section  ><section style=\\\"border:none;margin:.5em 0;box-sizing:border-box;padding:0;font-family:微软雅黑;font-size:14px\\\"><section  style=\\\"border-radius: 0.8em; width: 100%; border: 2px solid rgb(95, 170, 255); box-sizing: border-box;\\\"><section  style=\\\"border-radius: 0.8em; width: 100%; text-align: center; display: table; padding: 10px; box-sizing: border-box; background-color: rgb(95, 170, 255);\\\"><section style=\\\"display:table-cell;vertical-align:middle;min-height:4em;width:100%;height:100%;padding:10px;line-height:1.2;border:2px dotted #fff;font-family:inherit;font-weight:inherit;text-decoration:inherit;color:#fff;box-sizing:border-box;background-color:transparent\\\"><section style=\\\"width:7em;height:7em;border:5px solid #ffcf2d;border-radius:100%;margin:20px auto;box-sizing:border-box\\\"><img  style=\\\"box-sizing:border-box;width:100%;height:100%;border-radius:100%;background-image:url(http://files.qiluzhaoshang.com//fck007/2017042015/fbc90ab8-7e07-4088-9415-880e425a670b19.png);background-size:cover;background-position:50% 50%;background-repeat:no-repeat\\\" ></section><section style=\\\"box-sizing:border-box\\\"><section style=\\\"box-sizing:border-box\\\">为保证效果，强烈建议更换图片为正方形</section></section></section></section></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":17,\"Total\":190,\"id\":856,\"name\":\"<section  ><section style=\\\"border:0 none;padding:0;box-sizing:border-box;margin:0;font-family:微软雅黑\\\"><section style=\\\"margin:.5em 0 1em;padding:0;box-sizing:border-box;min-width:0;color:#3e3e3e;font-size:15px;word-wrap:break-word!important\\\"><section style=\\\"text-align:right;box-sizing:border-box;padding:0;margin:0;color:inherit;font-size:14px\\\"><section style=\\\"margin-right:15px;padding:0;box-sizing:border-box;display:inline-block;vertical-align:top;height:6em;width:6em;border-top-left-radius:50%;border-top-right-radius:50%;border-bottom-right-radius:50%;border-bottom-left-radius:50%;border:5px solid rgba(0,0,0,.2);font-family:inherit;font-weight:inherit;text-decoration:inherit;font-size:1.6em;color:inherit;word-wrap:break-word!important\\\"><img   style=\\\"border-radius:50%;box-sizing:border-box;color:inherit;display:inline-block\\\" width=\\\"100%\\\" height=\\\"100%\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/649b0ef3-b833-4e9f-9f78-8729a0936db320.jpg\\\"></section></section><section  style=\\\"margin: -8.5em 0px 1em; padding: 10px 50% 10px 15px; border: 0px solid rgb(73, 158, 243); font-size: 14px; font-weight: inherit; text-decoration: inherit; color: rgb(255, 255, 255); box-sizing: border-box; overflow: hidden; min-height: 105px; background-color: rgb(95, 170, 255);\\\"><p style=\\\"color:inherit;white-space:normal;line-height:2em\\\"><span style=\\\"color:#fff\\\">输入标题</span></p><p style=\\\"color:inherit;white-space:normal;line-height:2em\\\"><span style=\\\"color:#fff\\\">输入内容正文</span></p><p style=\\\"color:inherit;white-space:normal;line-height:2em\\\"><span style=\\\"color:#fff\\\">为保证效果，强烈建议更换图片为正方形。</span></p></section></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":18,\"Total\":190,\"id\":855,\"name\":\"<section  ><section style=\\\"font-size:14px;font-family:'Microsoft YaHei';margin: 5px auto;white-space: normal;\\\"><section style=\\\"margin:20px auto;padding:0;width:80%;text-align:center\\\"><section style=\\\"margin:0;padding:0;border:1px solid #a9a9a9;text-align:center;box-shadow:0 0 8px #787878\\\"><section style=\\\"margin-top:-5px;margin-left:-5px;margin-bottom:4px;border:1px solid #a9a9a9;padding:0;background-color:#fff;box-shadow:0 0 8px #c6c6c6\\\"><section style=\\\"margin-top:-5px;margin-left:-5px;margin-bottom:4px;border:1px solid #a9a9a9;padding:10px;background-color:#fefefe;box-shadow:#c6c6c6 0 0 8px\\\"><section style=\\\"clear:both;overflow:hidden;border:0;margin:0;padding:0;display:inline-block;width:100%\\\"><section style=\\\"width:100%;margin:0;padding:0;border-color:#757576;color:inherit\\\"><img   style=\\\"width:100%;display:block;padding:0;margin:0\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/c37004c7-d925-42d0-afdf-40f18bdb64ce21.jpg\\\"></section></section></section></section></section></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":19,\"Total\":190,\"id\":854,\"name\":\"<section  ><section style=\\\"font-size:14px;font-family:'Microsoft YaHei';margin: 5px auto;white-space: normal;\\\"><section style=\\\"margin:20px 0;padding:0\\\"><section style=\\\"margin:0;padding:0;box-sizing:border-box;text-align:center\\\"><section style=\\\"margin:0;padding:0;box-sizing:border-box;display:inline-block\\\"><section style=\\\"margin:0;padding:0;width:1.8em;height:1.8em;border-radius:50%;box-shadow:0 2px 3px #999;border:1px solid #ccc;display:table-cell;vertical-align:middle\\\"><section style=\\\"margin:0 auto;padding:0;width:1.4em;height:1.4em;border-radius:50%;background-color:#ccc\\\"></section></section></section><section style=\\\"margin:-10px auto 0;padding:0;width:4em;height:4em;border-right:1px solid #ccc;border-top:1px solid #ccc;box-sizing:border-box;transform:rotate(-45deg) translateZ(0);-moz-transform:rotate(-45deg) translateZ(0);-ms-transform:rotate(-45deg) translateZ(0);-o-transform:rotate(-45deg) translateZ(0);-webkit-transform:rotate(-45deg) translateZ(0)\\\"></section></section><section style=\\\"margin-top:-2em;padding:15px;box-sizing:border-box;border:1px solid #ccc;border-radius:10px\\\"><img   style=\\\"width:100%;display:block\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/d2ea9da3-a853-41d2-886d-8ce7e65fe94d22.jpg\\\"></section></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null},{\"RowNumber\":20,\"Total\":190,\"id\":853,\"name\":\"<section  ><section style=\\\"margin: 5px 0;box-sizing: border-box;display: table;width: 100%;\\\"><section style=\\\"line-height: 0; box-sizing: border-box; color: inherit;\\\"><img   style=\\\"border: 0px; box-sizing: border-box; display: inline-block; width: 100%; max-width: 100%; height: auto !important; color: inherit;\\\" src=\\\"http://files.qiluzhaoshang.com//fck007/2017042015/1e48f04b-3030-4e12-998e-bf85cf394a7723.png\\\"></section><section style=\\\"width: 30%; display: inline-block; float: left; text-align: right; margin: 15px 0px 0px; padding: 0px; box-sizing: border-box; color: inherit;\\\"><section style=\\\"float: right; text-align: center; margin-top: -15px; box-sizing: border-box; color: inherit;\\\"><section style=\\\"width: 1px; height: 1.2em; margin-left: 13px; background-color: rgb(102, 102, 102); box-sizing: border-box; color: inherit;\\\"></section><section style=\\\"width: 2em; height: 2em; border: 1px solid rgb(102, 102, 102); border-top-left-radius: 50%; border-top-right-radius: 50%; border-bottom-right-radius: 50%; border-bottom-left-radius: 50%; line-height: 2em; font-size: 1em; font-weight: inherit; text-decoration: inherit; box-sizing: border-box; color: inherit;\\\"><section style=\\\"box-sizing: border-box; color: inherit;\\\">巴</section></section><section style=\\\"width: 2em; height: 2em; border: 1px solid rgb(102, 102, 102); margin-top: 2px; border-top-left-radius: 50%; border-top-right-radius: 50%; border-bottom-right-radius: 50%; border-bottom-left-radius: 50%; line-height: 2em; font-size: 1em; font-weight: inherit; text-decoration: inherit; box-sizing: border-box; color: inherit;\\\"><section style=\\\"box-sizing: border-box; color: inherit;\\\">瑶</section></section></section></section><section  style=\\\"width: 65%; float: left; margin-top: 20px; line-height: 1.5em; padding-left: 20px; font-size: 1em; font-weight: inherit; text-decoration: inherit; color: rgb(58, 188, 255); box-sizing: border-box;\\\"><section style=\\\"box-sizing: border-box; border-color: rgb(58, 188, 255); color: inherit;\\\"><section style=\\\"box-sizing: border-box; font-size: 175%; margin: 5px 0px; border-color: rgb(58, 188, 255); color: inherit;\\\">海上人家</section><section style=\\\"box-sizing: border-box; font-size: 16px; border-color: rgb(58, 188, 255); color: inherit;\\\">巴瑶族，唯一的海上民族</section></section></section></section></section>\",\"path\":null,\"ItemSalePrice\":0,\"AppUrl\":null,\"ProductForm\":0,\"ProductTips\":null}]";
                List<PageDataItemVo> pageDataItemVos = JSONUtil.parseArray(strJsonData, PageDataItemVo.class);

                if (CollectionUtil.isNotEmpty(pageDataItemVos)) {
                    baseListRes.setRecords(pageDataItemVos);
                }
                break;
            case 4:
                //快捷入口
                String strJson = "[\n" +
                        "  {\n" +
                        "    \"id\": 23,\n" +
                        "    \"name\": \"扫码点餐\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon23.png\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"AppUrl\": \"/chain/chain/scan\",\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null,\n" +
                        "    \"RowNumber\": 1,\n" +
                        "    \"Total\": 19\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 2,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 22,\n" +
                        "    \"name\": \"好友砍价\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon22.png\",\n" +
                        "    \"AppUrl\": \"/activity/cutprice/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 5,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 17,\n" +
                        "    \"name\": \"餐饮外卖\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon17.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/index/store-list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 3,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 16,\n" +
                        "    \"name\": \"新闻资讯\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon16.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/article/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 4,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 15,\n" +
                        "    \"name\": \"优惠买单\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon15.png\",\n" +
                        "    \"AppUrl\": \"/chain/chain/favorable\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 6,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 14,\n" +
                        "    \"name\": \"服务预约\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon14.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/product/list?kind_id=1202\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 7,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 13,\n" +
                        "    \"name\": \"拼团活动\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon13.png\",\n" +
                        "    \"AppUrl\": \"/activity/fightgroup/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 8,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 12,\n" +
                        "    \"name\": \"粉丝榜\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon12.png\",\n" +
                        "    \"AppUrl\": \"/member/fans/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 9,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 11,\n" +
                        "    \"name\": \"砸金蛋\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon11.png\",\n" +
                        "    \"AppUrl\": \"/activity/smashgoldeneggs/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 10,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 10,\n" +
                        "    \"name\": \"幸运抽奖\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon10.png\",\n" +
                        "    \"AppUrl\": \"/member/smashgoldeneggs/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 11,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 9,\n" +
                        "    \"name\": \"领券中心\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon9.png\",\n" +
                        "    \"AppUrl\": \"/activity/coupon/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 12,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 8,\n" +
                        "    \"name\": \"附近门店\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon8.png\",\n" +
                        "    \"AppUrl\": \"/chain/chain/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 14,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 7,\n" +
                        "    \"name\": \"物流查询\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon7.png\",\n" +
                        "    \"AppUrl\": \"/member/order/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 13,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 6,\n" +
                        "    \"name\": \"活动中心\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon6.png\",\n" +
                        "    \"AppUrl\": \"/activity/market/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 15,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 5,\n" +
                        "    \"name\": \"我的粉丝\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon5.png\",\n" +
                        "    \"AppUrl\": \"/member/fans/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 16,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 4,\n" +
                        "    \"name\": \"分享赚钱\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon4.png\",\n" +
                        "    \"AppUrl\": \"/member/fans/endorsement\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 17,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 3,\n" +
                        "    \"name\": \"我的收藏\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon3.png\",\n" +
                        "    \"AppUrl\": \"/member/member/favorites\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 18,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 2,\n" +
                        "    \"name\": \"我的拼团\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon2.png\",\n" +
                        "    \"AppUrl\": \"/activity/fightgroup/order\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 19,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"我的金库\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon1.png\",\n" +
                        "    \"AppUrl\": \"/member/cash/predeposit\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 30,\n" +
                        "    \"name\": \"店铺街\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon30.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/index/store-list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 31,\n" +
                        "    \"name\": \"抢购活动\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon31.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/product/list?tag_id=1404&cname=抢购活动\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 32,\n" +
                        "    \"name\": \"众宝区\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon32.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/product/list?sp_from=1&sp_to=1000000&cname=众宝区\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 33,\n" +
                        "    \"name\": \"积分区\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon33.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/product/list?points_from=1&points_to=1000000&cname=积分区\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 34,\n" +
                        "    \"name\": \"积分商城\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon34.png\",\n" +
                        "    \"AppUrl\": \"/integral/integral/integral\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 35,\n" +
                        "    \"name\": \"跨境商品\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon35.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/product/list?tag_id=1405&cname=跨境商品\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 36,\n" +
                        "    \"name\": \"限时折扣\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon36.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/product/list?activity_type_id=1103&cname=限时折扣\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 37,\n" +
                        "    \"name\": \"满减\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon37.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/product/list?activity_type_id=1107&cname=满减\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 38,\n" +
                        "    \"name\": \"平台秒杀\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon38.png\",\n" +
                        "    \"AppUrl\": \"/activity/plantform/secondlist?cname=限时秒杀\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 39,\n" +
                        "    \"name\": \"直播\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon39.png\",\n" +
                        "    \"AppUrl\": \"/pagesub/uLive/index\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"RowNumber\": 20,\n" +
                        "    \"Total\": 19,\n" +
                        "    \"id\": 40,\n" +
                        "    \"name\": \"组合套餐\",\n" +
                        "    \"path\": \"https://static.shopsuite.cn/xcxfile/appicon/icon39.png\",\n" +
                        "    \"AppUrl\": \"/activity/giftbag/list\",\n" +
                        "    \"ItemSalePrice\": 0,\n" +
                        "    \"ProductForm\": 0,\n" +
                        "    \"ProductTips\": null\n" +
                        "  }\n" +
                        "]";
                List<PageDataItemVo> dataItemVos = JSONUtil.parseArray(strJson, PageDataItemVo.class);

                List<Long> idList = new ArrayList<>();
                idList.add(1L);
                idList.add(2L);
                idList.add(6L);
                idList.add(7L);
                idList.add(8L);
                idList.add(10L);
                idList.add(11L);
                idList.add(12L);
//                idList.add(13L);
                idList.add(14L);
                idList.add(15L);
                idList.add(17L);
//                idList.add(22L);
                idList.add(23L);
                //idList.add(33L);
                //idList.add(34L);
                idList.add(35L);
                idList.add(37L);
                idList.add(38L);
                idList.add(30L);
                idList.add(32L);
                idList.add(31L);


                Iterator<PageDataItemVo> iterator = dataItemVos.iterator();
                while (iterator.hasNext()) {
                    if (idList.contains(iterator.next().getId())) {
                        // 删除元素
                        iterator.remove();
                    }
                }

                if (CollectionUtil.isNotEmpty(dataItemVos)) {
                    baseListRes.setRecords(dataItemVos);
                }
                break;
            case 5:
                QueryWrapper<ArticleCategory> articleCategoryQueryWrapper = new QueryWrapper<>();

                if (CheckUtil.isNotEmpty(pageDataReq.getName())) {
                    articleCategoryQueryWrapper.like("category_name", pageDataReq.getName());
                }

                IPage<ArticleCategory> articleCategoryPage = articleCategoryRepository.lists(articleCategoryQueryWrapper, pageDataReq.getPage(), pageDataReq.getSize());
                BeanUtils.copyProperties(articleCategoryPage, baseListRes);

                if (CollectionUtil.isNotEmpty(articleCategoryPage.getRecords())) {
                    articleCategoryPage.getRecords().forEach(item -> {
                        PageDataItemVo pageDataItemVo = new PageDataItemVo();
                        pageDataItemVo.setId(item.getCategoryId().longValue());
                        pageDataItemVo.setPath(item.getCategoryImageUrl());
                        pageDataItemVo.setName(item.getCategoryName());
                        pageDataItemVoList.add(pageDataItemVo);
                    });
                    baseListRes.setRecords(pageDataItemVoList);
                }
                break;
            case 6:
                //资讯
                QueryWrapper<ArticleBase> articleBaseQueryWrapper = new QueryWrapper<>();

                if (StrUtil.isNotEmpty(pageDataReq.getName())) {
                    articleBaseQueryWrapper.like("article_title", pageDataReq.getName());
                }

                IPage<ArticleBase> articleBasePage = articleBaseRepository.lists(articleBaseQueryWrapper, pageDataReq.getPage(), pageDataReq.getSize());
                BeanUtils.copyProperties(articleBasePage, baseListRes);

                if (CollectionUtil.isNotEmpty(articleBasePage.getRecords())) {
                    articleBasePage.getRecords().forEach(item -> {
                        PageDataItemVo pageDataItemVo = new PageDataItemVo();
                        pageDataItemVo.setId(item.getArticleId().longValue());
                        pageDataItemVo.setPath(item.getArticleImage());
                        pageDataItemVo.setName(item.getArticleTitle());
                        pageDataItemVoList.add(pageDataItemVo);
                    });
                    baseListRes.setRecords(pageDataItemVoList);
                }
                break;
            case 8:
                //自定义页面
                QueryWrapper<PageBase> pageBaseQueryWrapper = new QueryWrapper<>();

                if (StrUtil.isNotEmpty(pageDataReq.getName())) {
                    pageBaseQueryWrapper.like("page_name", pageDataReq.getName());
                }

                IPage<PageBase> pageBasePage = pageBaseRepository.lists(pageBaseQueryWrapper, pageDataReq.getPage(), pageDataReq.getSize());
                BeanUtils.copyProperties(pageBasePage, baseListRes);

                if (CollectionUtil.isNotEmpty(pageBasePage.getRecords())) {
                    pageBasePage.getRecords().forEach(item -> {
                        PageDataItemVo pageDataItemVo = new PageDataItemVo();
                        pageDataItemVo.setId(item.getPageId());
                        pageDataItemVo.setPath(item.getPageShareImage());
                        pageDataItemVo.setName(item.getPageName());
                        pageDataItemVoList.add(pageDataItemVo);
                    });
                    baseListRes.setRecords(pageDataItemVoList);
                }
                break;
            case 10:
                //社区板块
                break;
            case 11:
                break;
            case 14:
                //秒杀
                QueryWrapper<ActivityItem> activityItemQueryWrapper = new QueryWrapper<>();
                activityItemQueryWrapper.in("activity_item_state", StateCode.ACTIVITY_STATE_NORMAL, StateCode.ACTIVITY_STATE_WAITING)
                        .eq("activity_type_id", StateCode.ACTIVITY_TYPE_LIMITED_DISCOUNT);

                if (StrUtil.isNotEmpty(pageDataReq.getName())) {
                    QueryWrapper<ProductIndex> productIndexQueryWrapper = new QueryWrapper<>();
                    productIndexQueryWrapper.like("product_name", pageDataReq.getName());
                    List<Serializable> productIds = productIndexRepository.findKey(productIndexQueryWrapper);

                    if (CollectionUtil.isNotEmpty(productIds)) {
                        activityItemQueryWrapper.in("product_id", productIds);
                    }
                }

                IPage<ActivityItem> activityItemPage = activityItemRepository.lists(activityItemQueryWrapper, pageDataReq.getPage(), pageDataReq.getSize());
                BeanUtils.copyProperties(activityItemPage, baseListRes);
                List<ActivityItem> activityItemList = activityItemPage.getRecords();
                if (CollectionUtil.isNotEmpty(activityItemList)) {
                    List<Long> productIds = activityItemList.stream().map(ActivityItem::getProductId).collect(Collectors.toList());
                    //产品名称、商品卖点
                    Map<Long, ProductBase> productBaseMap = getProductBaseMap(productIds);
                    //产品图片
                    Map<Long, ProductImage> productImageMap = getProductImageMap(productIds);
                    //商品SKU表 副标题、市场价
                    Map<Long, ProductItem> productItemMap = getProductItemMap(productIds);
                    for (ActivityItem activityItem : activityItemList) {
                        PageDataItemVo pageDataItemVo = new PageDataItemVo();
                        //图片
                        ProductImage productImage = productImageMap.get(activityItem.getProductId());

                        if (productImage != null) {
                            pageDataItemVo.setPath(productImage.getItemImageDefault());
                        }

                        //标题 卖点 市场价
                        ProductBase productBase = productBaseMap.get(activityItem.getProductId());
                        ProductItem productItem = productItemMap.get(activityItem.getProductId());

                        if (productBase != null) {
                            pageDataItemVo.setProductTips(productBase.getProductTips());

                            if (productItem != null) {
                                pageDataItemVo.setName(productBase.getProductName() + productItem.getItemName());
                                pageDataItemVo.setMarketPice(productItem.getItemMarketPrice().intValue());
                                pageDataItemVo.setId(productItem.getItemId());
                            }
                        }

                        pageDataItemVo.setItemSalePrice(activityItem.getActivityItemPrice().intValue());
                        //开始时间
                        pageDataItemVo.setStartTime(activityItem.getActivityItemStarttime().toString());
                        Instant start = Instant.ofEpochMilli(activityItem.getActivityItemStarttime());
                        LocalDateTime startTime = LocalDateTime.ofInstant(start, ZoneId.systemDefault());
                        pageDataItemVo.setStartTimeStr(startTime.format(formatter));
                        //结束时间
                        pageDataItemVo.setEndTime(activityItem.getActivityItemEndtime().toString());
                        Instant end = Instant.ofEpochMilli(activityItem.getActivityItemEndtime());
                        LocalDateTime endTime = LocalDateTime.ofInstant(end, ZoneId.systemDefault());
                        pageDataItemVo.setStartTimeStr(endTime.format(formatter));
                        pageDataItemVoList.add(pageDataItemVo);
                    }
                    baseListRes.setRecords(pageDataItemVoList);
                }
                break;
            case 17:
                //代金券
                QueryWrapper<ActivityBase> activityQueryWrapper = new QueryWrapper<>();

                if (StrUtil.isNotEmpty(pageDataReq.getName())) {
                    activityQueryWrapper.like("activity_name", pageDataReq.getName());
                }

                activityQueryWrapper.eq("activity_state", StateCode.ACTIVITY_STATE_NORMAL)
                        .eq("activity_type_id", StateCode.ACTIVITY_TYPE_VOUCHER)
                        .orderByDesc("activity_id");
                IPage<ActivityBase> activityPage = activityBaseRepository.lists(activityQueryWrapper, pageDataReq.getPage(), pageDataReq.getSize());

                if (CollectionUtil.isNotEmpty(activityPage.getRecords())) {
                    activityPage.getRecords().forEach(item -> {

                        if (StrUtil.isNotEmpty(item.getActivityRule())) {
                            PageDataVo pageDataVo = JSONUtil.parseObject(item.getActivityRule(), PageDataVo.class);

                            if (pageDataVo != null) {
                                PageDataItemVo pageDataItemVo = new PageDataItemVo();
                                pageDataItemVo.setId(item.getActivityId().longValue());
                                pageDataItemVo.setName(item.getActivityName());
                                pageDataItemVo.setPath(pageDataVo.getVoucherImage());
                                pageDataItemVo.setItemSalePrice(pageDataVo.getVoucherPrice());
                                pageDataItemVo.setProductTips("");
                                pageDataItemVo.setStartTime("/Date(-62135596800000)/");
                                pageDataItemVo.setStartTimeStr(pageDataVo.getActivityStarttime());
                                pageDataItemVo.setEndTime("/Date(-62135596800000)/");
                                pageDataItemVo.setEndTimeStr(pageDataVo.getActivityEndtime());
                                Integer productSaleNum = Convert.toInt(pageDataVo.getProductSaleNum(), 0);
                                pageDataItemVo.setOrderCount(NumberUtil.max(RandomUtil.randomInt(8, 100), productSaleNum));
                                pageDataItemVo.setMarketPice(pageDataVo.getVoucherPrice());
                                pageDataItemVo.setUserLimit(pageDataVo.getVoucherPreQuantity());
                                pageDataItemVoList.add(pageDataItemVo);
                            }
                        }
                    });
                    baseListRes.setRecords(pageDataItemVoList);
                }
                break;
            default:
                break;
        }

        return baseListRes;
    }

    @Override
    public PageBaseRes detail(Long pageId) {
        PageBaseRes pageBaseRes = new PageBaseRes();
        PageBase pageBase = repository.get(pageId);

        if (pageBase != null) {
            BeanUtils.copyProperties(pageBase, pageBaseRes);
        }

        fixData(pageBaseRes);

        return pageBaseRes;
    }

    private void fixData(PageBaseRes pageBaseRes) {
        boolean imEnable = configBaseService.getConfig("im_enable", false);
        Integer imUserId = configBaseService.getConfig("site_im", 10001);

        ContextUser user = ContextUtil.getLoginUser();

        ImConfigVo imConfigVo = new ImConfigVo();
        pageBaseRes.setIm(imConfigVo);

        imConfigVo.setPuid(0);

        if (ObjectUtil.isNotNull(user)) {
            String service_user_id = configBaseService.getConfig("service_user_id");
            imConfigVo.setPuid(CheckUtil.getPlantformUid(service_user_id, user.getUserId().toString()));
        }

        imConfigVo.setImEnable(imEnable);
        imConfigVo.setImUserId(imUserId);

        String pageCode = pageBaseRes.getPageCode();

        JSONArray pageCodeRows = new JSONArray();
        if (StrUtil.isNotBlank(pageCode) && !"[]".equals(pageCode)) {
            pageCodeRows = cn.hutool.json.JSONUtil.parseArray(pageCode);
        }

        List<Long> item_id_row = new ArrayList<>();

        for (Object pageCodeRow : pageCodeRows) {
            Integer eltmType = Convert.toInt(((JSONObject) pageCodeRow).get("eltmType"));
            if (ObjectUtil.equal(eltmType, 4)) {
                JSONObject eltm4 = (JSONObject) ((JSONObject) pageCodeRow).get("eltm4");
                if (eltm4 == null) continue;
                JSONArray data = (JSONArray) eltm4.get("data");
                if (data == null) continue;

                for (Object datum : data) {
                    Long did = Convert.toLong(((JSONObject) datum).get("did"));

                    if (did != null) item_id_row.add(did);
                }
            } else if (ObjectUtil.equal(eltmType, 16)) {
                //营销组件 selectType = 14 秒杀      12：团购
                JSONObject eltm16 = (JSONObject) ((JSONObject) pageCodeRow).get("eltm16");
                if (eltm16 == null) continue;

                JSONArray data = (JSONArray) eltm16.get("data");
                if (data == null) continue;

                for (Object datum : data) {
                    Long did = Convert.toLong(((JSONObject) datum).get("did"));
                    if (did != null) item_id_row.add(did);
                }
            }
        }

        if (CollUtil.isNotEmpty(item_id_row)) {
            // 修改汇率 getCurrencyRate
            //BigDecimal currency_exchange_rate = shopBaseCurrencyService.getCurrencyRate();
            BigDecimal currency_exchange_rate = BigDecimal.ONE;

            ProductItemInput productItemInput = new ProductItemInput();
            productItemInput.setItemId(item_id_row);
            productItemInput.setProductStateId(StateCode.PRODUCT_STATE_NORMAL);
            List<ItemOutput> itemOutputList = productIndexService.listItem(productItemInput).getItems();

            //过滤上架的产品
            List<Long> item_idss = new ArrayList<>();

            if (CollUtil.isNotEmpty(itemOutputList)) {
                item_idss = itemOutputList.stream().map(e -> e.getItemId()).collect(Collectors.toList());
            }

            for (Object page_code_row : pageCodeRows) {

                Integer eltmType = Convert.toInt(((JSONObject) page_code_row).get("eltmType"));
                if (ObjectUtil.equal(eltmType, 4)) {
                    JSONObject eltm4 = (JSONObject) ((JSONObject) page_code_row).get("eltm4");
                    if (eltm4 == null) continue;

                    JSONArray data = (JSONArray) eltm4.get("data");
                    List<PageDataItemVo> as = cn.hutool.json.JSONUtil.toList(data, PageDataItemVo.class);
                    List<Long> finalItem_idss = item_idss;
                    as.removeIf(e -> !finalItem_idss.contains(e.getDid()));

                    String s2 = cn.hutool.json.JSONUtil.toJsonStr(as);
                    data = cn.hutool.json.JSONUtil.parseArray(s2);

                    eltm4.set("data", data);
                    ((JSONObject) page_code_row).set("eltm4", eltm4);


                    if (data == null) continue;

                    for (Object item : data) {
                        Long did = Convert.toLong(((JSONObject) item).get("did"));
                        Optional<ItemOutput> product_item_opl = itemOutputList.stream().filter(s -> ObjectUtil.equal(s.getItemId(), did)).findFirst();
                        if (product_item_opl.isPresent()) {
                            ItemOutput product_item_row = product_item_opl.get();
                            BigDecimal itemSalePrice = Convert.toBigDecimal(((JSONObject) item).get("ItemSalePrice"));
                            BigDecimal item_unit_price = product_item_row.getItemUnitPrice();
                            BigDecimal item_unit_points = product_item_row.getItemUnitPoints();
                            BigDecimal item_unit_sp = BigDecimal.ZERO;
                            Integer int_item_unit_price = item_unit_price.compareTo(BigDecimal.ZERO) > 0 ? NumberUtil.mul(item_unit_price, currency_exchange_rate).intValue() : 0;
                            BigDecimal round_ItemSalePrice = NumberUtil.round(NumberUtil.mul(itemSalePrice, currency_exchange_rate), 2);
                            Integer int_item_unit_points = item_unit_points.compareTo(BigDecimal.ZERO) > 0 ? NumberUtil.mul(item_unit_points, currency_exchange_rate).intValue() : 0;
                            Integer int_item_unit_sp = item_unit_sp.compareTo(BigDecimal.ZERO) > 0 ? NumberUtil.mul(item_unit_sp, currency_exchange_rate).intValue() : 0;

                            ((JSONObject) item).set("item_unit_price", int_item_unit_price);
                            ((JSONObject) item).set("ItemSalePrice", round_ItemSalePrice);
                            ((JSONObject) item).set("item_unit_points", int_item_unit_points);
                            ((JSONObject) item).set("item_unit_sp", int_item_unit_sp);
                        }
                    }
                } else if (ObjectUtil.equal(eltmType, 13)) {
                    //营销组件 selectType = 14 秒杀      12：团购

//                    ShopPageUserForm shopPageUserForm = shopPageUserFormService.get(9);
//                    String question_data = shopPageUserForm.getQuestion_data();
//                    JSONArray jsonArray = cn.hutool.json.JSONUtil.parseArray(question_data);
//                    System.out.println(jsonArray);
                    JSONObject eltm13 = (JSONObject) ((JSONObject) page_code_row).get("eltm13");
                    if (eltm13 == null) continue;

                    JSONArray jsonArray = (JSONArray) eltm13.get("data");
                    for (Object o : jsonArray) {
                        JSONObject jsonObject = cn.hutool.json.JSONUtil.parseObj(o);
                        String type = Convert.toStr(jsonObject.get("type"));
                        if (type.equals(1)) {
                            String placeholderText = Convert.toStr(jsonObject.get("placeholderText"));

                        }
                    }
                    if (jsonArray == null) continue;
//                    eltm13.set("data",jsonArray);
//                    ((JSONObject) page_code_row).set("eltm13",eltm13);

                } else if (ObjectUtil.equal(eltmType, 104)) {
                    JSONObject eltm104 = (JSONObject) ((JSONObject) page_code_row).get("eltm104");
                    if (eltm104 == null) continue;

                    JSONArray data = (JSONArray) eltm104.get("data");

                    List<Long> item_id_104 = new ArrayList<>();

                    for (Object datum : data) {
                        JSONObject jsonObject = cn.hutool.json.JSONUtil.parseObj(datum);
                        List ids = Convert.toList(jsonObject.get("ids"));

                        item_id_104.addAll(ids);
                    }

                    List<Long> item_ids = productItemRepository.find(new QueryWrapper<ProductItem>().in("item_id", item_id_104).eq("item_enable", 1001)).stream().map(ProductItem::getItemId).collect(Collectors.toList());

                    JSONArray data1 = new JSONArray();
                    for (Object datum : data) {
                        JSONObject jsonObject = cn.hutool.json.JSONUtil.parseObj(datum);
                        List ids = Convert.toList(jsonObject.get("ids"));
                        List id_s = new ArrayList();
                        for (Object id : ids) {
                            for (Long item_id : item_ids) {
                                if (Convert.toLong(id).equals(item_id)) {
                                    id_s.add(Convert.toInt(id));
                                }
                            }
                        }

                        jsonObject.set("ids", id_s.toString());
                        data1.add(jsonObject);
                    }

                    eltm104.set("data", data1);
                    ((JSONObject) page_code_row).set("eltm104", eltm104);

                    if (data == null) continue;

                    for (Object item : data) {
                        Long did = Convert.toLong(((JSONObject) item).get("did"));
                    }
                }
            }
        }

        // todo 系统启用自动翻译功能
        pageBaseRes.setPageLoaded(CollUtil.isNotEmpty(pageCodeRows));

        pageBaseRes.setPageCode(cn.hutool.json.JSONUtil.toJsonStr(pageCodeRows));
    }

    public Map getAllCenterMenu() {
        Map menu = new HashMap();
        List list = new ArrayList();

        //读取类型
        menu.put("type", 2);

        menu.put("list", list);

        Boolean plantform_fx_enable = configBaseService.ifPlantformFx();


/*
        PageMenuVo menu2 = new PageMenuVo();
        list.add(menu2);
        menu2.setId(2);
        menu2.setName(__("分销佣金"));
        menu2.setIsShow(plantform_fx_enable);
        menu2.setCat(2);
        menu2.setColor("#44afa4");
        menu2.setIcon("zc zc-xiaojinku");
        menu2.setFeatureKey("MemCashAcct");
        menu2.setUrl("/member/fans/profitlist");

        PageMenuVo menu3 = new PageMenuVo();
        list.add(menu3);
        menu3.setId(3);
        menu3.setName(__("我的预约"));
        menu3.setIsShow(true);
        menu3.setCat(2);
        menu3.setColor("#44afa4");
        menu3.setIcon("zc zc-shijian");
        menu3.setFeatureKey("isVirtual");
        menu3.setUrl("/member/order/list?kind_id=1202");


        PageMenuVo menu31 = new PageMenuVo();
        list.add(menu31);
        menu31.setId(39);
        menu31.setName(__("我的卡包"));
        menu31.setIsShow(true);
        menu31.setCat(2);
        menu31.setColor("#44afa4");
        menu31.setIcon("zc zc-wodekabao");
        menu31.setFeatureKey("PayCardType");
        menu31.setUrl("/member/card/user_list?kind_id=1203");

        PageMenuVo menu32 = new PageMenuVo();
        list.add(menu32);
        menu32.setId(40);
        menu32.setName(__("礼包兑换"));
        menu32.setIsShow(true);
        menu32.setCat(1);
        menu32.setColor("#44afa4");
        menu32.setIcon("zc zc-libaoduihuan");
        menu32.setFeatureKey("GiftPackType");
        menu32.setUrl("/activity/gift/exchange");
*/

        PageMenuVo menu4 = new PageMenuVo();
        list.add(menu4);
        menu4.setId(36);
        menu4.setName(__("售后服务"));
        menu4.setIsShow(true);
        menu4.setCat(1);
        menu4.setColor("#44afa4");
        menu4.setIcon("zc zc-shouhoufuwu");
        menu4.setFeatureKey("service");
        menu4.setUrl("/member/member/returnlist");

        /*
        PageMenuVo menu6 = new PageMenuVo();
        list.add(menu6);
        menu6.setId(45);
        menu6.setName(__("账户余额"));
        menu6.setIsShow(true);
        menu6.setCat(1);
        menu6.setColor("#DB384C");
        menu6.setIcon("zc zc-zhanghuyue");
        menu6.setFeatureKey("UserMoneyKey");
        menu6.setUrl("/member/cash/predeposit");

        PageMenuVo menu7 = new PageMenuVo();
        list.add(menu7);
        menu7.setId(5);
        menu7.setName(__("优惠券"));
        menu7.setIsShow(true);
        menu7.setCat(1);
        menu7.setColor("#56ABE4");
        menu7.setIcon("zc zc-wodeyouhuiquan");
        menu7.setFeatureKey("Coupon");
        menu7.setUrl("/member/member/coupon");
*/

        PageMenuVo menu44 = new PageMenuVo();
        list.add(menu44);
        menu44.setId(44);
        menu44.setName(__("签到"));
        menu44.setIsShow(true);
        menu44.setCat(1);
        menu44.setColor("#5cdbd3");
        menu44.setIcon("zc zc-wodeqiandao");
        menu44.setFeatureKey("MemSign");
        menu44.setUrl("/member/member/sign");

        PageMenuVo menu8 = new PageMenuVo();
        list.add(menu8);
        menu8.setId(6);
        menu8.setName(__("会员中心"));
        menu8.setIsShow(true);
        menu8.setCat(1);
        menu8.setColor("#ffc333");
        menu8.setIcon("zc zc-huiyuanzhongxin");
        menu8.setFeatureKey("MemGrade");
        menu8.setUrl("/member/member/task");
/*

        PageMenuVo menu9 = new PageMenuVo();
        list.add(menu9);
        menu9.setId(7);
        menu9.setName(__("店铺收藏"));
        menu9.setIsShow(true);
        menu9.setCat(1);
        menu9.setColor("#7672B8");
        menu9.setIcon("zc zc-dianpushoucang");
        menu9.setFeatureKey("FavProd");
        menu9.setUrl("/member/member/favorites-store");
*/

        PageMenuVo menu10 = new PageMenuVo();
        list.add(menu10);
        menu10.setId(107);
        menu10.setName(__("商品收藏"));
        menu10.setIsShow(true);
        menu10.setCat(1);
        menu10.setColor("#56ABE4");
        menu10.setIcon("zc zc-wodeshoucang");
        menu10.setFeatureKey("FavProd");
        menu10.setUrl("/member/member/favorites");

        PageMenuVo menu11 = new PageMenuVo();
        list.add(menu11);
        menu11.setId(108);
        menu11.setName(__("我的足迹"));
        menu11.setIsShow(true);
        menu11.setCat(1);
        menu11.setColor("#56ABE4");
        menu11.setIcon("zc zc-wodezuji");
        menu11.setFeatureKey("FavProd");
        menu11.setUrl("/member/member/browse");

        PageMenuVo menu12 = new PageMenuVo();
        list.add(menu12);
        menu12.setId(8);
        menu12.setName(__("收货地址"));
        menu12.setIsShow(true);
        menu12.setCat(1);
        menu12.setColor("#1BC2A6");
        menu12.setIcon("zc zc-wodedizhi");
        menu12.setFeatureKey("UserAddress");
        menu12.setUrl("/member/address/list");

        PageMenuVo menu120 = new PageMenuVo();
        list.add(menu120);
        menu120.setId(120);
        menu120.setName(__("开票信息"));
        menu120.setIsShow(true);
        menu120.setCat(1);
        menu120.setColor("#ff85c0");
        menu120.setIcon("zc zc-kaipiaoxinxi");
        menu120.setFeatureKey("UserInvoice");
        menu120.setUrl("/member/invoice/list");

        PageMenuVo menu121 = new PageMenuVo();
        list.add(menu121);
        menu121.setId(120);
        menu121.setName(__("我的发票"));
        menu121.setIsShow(true);
        menu121.setCat(1);
        menu121.setColor("#ffc069");
        menu121.setIcon("zc zc-wodefapiao");
        menu121.setFeatureKey("OrderInvoice");
        menu121.setUrl("/member/invoice/order");
/*

        PageMenuVo menu13 = new PageMenuVo();
        list.add(menu13);
        menu13.setId(10);
        menu13.setName(__("我的小店"));
        boolean plugin_distributionWeStore = configBaseService.getConfig("plantform_fx_westore_enable", false);
        menu13.setIsShow(plantform_fx_enable && plugin_distributionWeStore);
        menu13.setCat(2);
        menu13.setColor("#327eac");
        menu13.setIcon("zc zc-wodexiaodian");
        menu13.setFeatureKey("WeStore");
        menu13.setUrl("/pagesub/westore/index");
*/

/*

        PageMenuVo menu15 = new PageMenuVo();
        list.add(menu15);
        menu15.setId(33);
        menu15.setName(__("我的消息"));
        menu15.setIsShow(true);
        menu15.setCat(1);
        menu15.setColor("#b5dbaf");
        menu15.setIcon("zc zc-wodexiaoxi");
        menu15.setFeatureKey("Message");
        menu15.setUrl("/member/member/message");

        PageMenuVo menu16 = new PageMenuVo();
        list.add(menu16);
        menu16.setId(31);
        menu16.setName(__("用户设置"));
        menu16.setIsShow(true);
        menu16.setCat(1);
        menu16.setColor("#7673db");
        menu16.setIcon("zc zc-yonghushezhi");
        menu16.setFeatureKey("Options");
        menu16.setUrl("/member/member/options");
*/

        PageMenuVo menu17 = new PageMenuVo();
        list.add(menu17);
        menu17.setId(32);
        menu17.setName(__("帮助"));
        menu17.setIsShow(true);
        menu17.setCat(1);
        menu17.setColor("#ac8dd5");
        menu17.setIcon("zc zc-bangzhuzhongxin");
        menu17.setFeatureKey("Help");
        menu17.setUrl("/pagesub/article/list");
/*
        PageMenuVo menu18 = new PageMenuVo();
        list.add(menu18);
        menu18.setId(33);
        menu18.setName(__("关于"));
        menu18.setIsShow(false);
        menu18.setCat(1);
        menu18.setColor("#b5dbaf");
        menu18.setIcon("zc zc-guanyu");
        menu18.setFeatureKey("AbtUs");
        menu18.setUrl("/pagesub/index/about");

        PageMenuVo menu19 = new PageMenuVo();
        list.add(menu19);
        menu19.setId(10);
        menu19.setName(__("用户反馈"));
        menu19.setIsShow(true);
        menu19.setCat(1);
        menu19.setColor("#DB384C");
        menu19.setIcon("zc zc-yonghufankui");
        menu19.setFeatureKey("");
        menu19.setUrl("/member/member/feedback");
*/
/*

        PageMenuVo menu20 = new PageMenuVo();
        list.add(menu20);
        menu20.setId(34);
        menu20.setName(__("商家中心"));
        menu20.setIsShow(true);
        menu20.setCat(2);
        menu20.setColor("#db384c");
        menu20.setIcon("zc zc-shangjiazhongxin");
        menu20.setFeatureKey("Seller");
        menu20.setUrl("/seller/index/index");
*/

        PageMenuVo menu21 = new PageMenuVo();
        list.add(menu21);
        menu21.setId(11);
        menu21.setName(__("清除缓存"));
        menu21.setIsShow(true);
        menu21.setCat(1);
        menu21.setColor("#80b7f2");
        menu21.setIcon("zc zc-qingchuhuancun");
        menu21.setFeatureKey("CleanCacheKey");
        menu21.setUrl("");


        Integer source_type = Convert.toInt(getParameter("source_type"));

        Integer liveModeAliyun = configBaseService.getConfig("live_mode_aliyun", 0);

        if (ObjectUtil.isNotNull(source_type) && StateCode.SOURCE_TYPE_H5 != source_type && liveModeAliyun.equals(1)) {
            PageMenuVo menu22 = new PageMenuVo();
            list.add(menu22);
            menu22.setId(33);
            menu22.setName(__("我的直播"));
            menu22.setIsShow(true);
            menu22.setCat(1);
            menu22.setColor("#ac8dd5");
            menu22.setIcon("zc zc-wodezhibo");
            menu22.setFeatureKey("Live");
            menu22.setUrl("/pagesub/livepush/add");
        }

        Boolean plugin_paotui = configBaseService.getConfig("Plugin_Paotui", false);
        if (plugin_paotui) {
            PageMenuVo menu23 = new PageMenuVo();
            list.add(menu23);
            menu23.setId(109);
            menu23.setName(__("骑手大厅"));
            menu23.setIsShow(false);
            menu23.setCat(2);
            menu23.setColor("#56ABE4");
            menu23.setIcon("zc zc-qishoudating");
            menu23.setFeatureKey("FavProd");
            menu23.setUrl("/paotui/index/index");
        }

        Boolean make_lang_package_enable = configBaseService.getConfig("make_lang_package_enable", false);
        if (make_lang_package_enable) {
            PageMenuVo menu23 = new PageMenuVo();
            list.add(menu23);
            menu23.setId(35);
            menu23.setName(__("翻译制作"));
            menu23.setIsShow(true);
            menu23.setCat(2);
            menu23.setColor("#ac8dd5");
            menu23.setIcon("zc zc-duoyuyan");
            menu23.setFeatureKey("ReloadLang");
            menu23.setUrl("");
        }

        // 直播
        Integer liveModeXcx = configBaseService.getConfig("live_mode_xcx", 0);
        if (liveModeXcx.equals(1)) {
            PageMenuVo menu25 = new PageMenuVo();
            list.add(menu25);
            menu25.setId(109);
            menu25.setName(__("申请主播"));
            menu25.setIsShow(true);
            menu25.setCat(2);
            menu25.setColor("#56ABE4");
            menu25.setIcon("zc zc-shenqingzhubo");
            menu25.setFeatureKey("FavProd");
            menu25.setUrl("/xcxlive/anchor/apply");

            PageMenuVo menu26 = new PageMenuVo();
            list.add(menu26);
            menu26.setId(109);
            menu26.setName(__("创建房间"));
            menu26.setIsShow(true);
            menu26.setCat(2);
            menu26.setColor("#56ABE4");
            menu26.setIcon("zc zc-chuanjianfangjian");
            menu26.setFeatureKey("FavProd");
            menu26.setUrl("/xcxlive/room/add");

            PageMenuVo menu27 = new PageMenuVo();
            list.add(menu27);
            menu27.setId(109);
            menu27.setName(__("房间列表"));
            menu27.setIsShow(true);
            menu27.setCat(2);
            menu27.setColor("#56ABE4");
            menu27.setIcon("zc zc-zhiboliebiao");
            menu27.setFeatureKey("FavProd");
            menu27.setUrl("/xcxlive/room/list");
        }

        return menu;
    }

    @Override
    public Map getUserCenterMenu() {
        Map menus = new HashMap();

        String appMemberCenter = configBaseService.getConfig("app_member_center");

        if (CheckUtil.isNotEmpty(appMemberCenter)) {
            //appMemberCenter
            PageMobileVo jsonObject = JSONUtil.parseObject(appMemberCenter, PageMobileVo.class);

            String pageCode = jsonObject.getPageCode();
            menus = JSONUtil.parseObject(pageCode, Map.class);

            //翻译标签
            List<Map> pageMenuVoList = (List<Map>) menus.get("list");
            for (Map it: pageMenuVoList) {
                it.put("name", __(it.get("name").toString()));
            }
        } else {
            menus = getAllCenterMenu();
        }

        Object type = ObjectUtil.defaultIfNull(menus.get("type"), 2);
        menus.put("type", Convert.toInt(type));

        return menus;
    }

    private Map<Long, ProductItem> getProductItemMap(List<Long> productIds) {
        Map<Long, ProductItem> productItemMap = new HashMap<>();
        QueryWrapper<ProductItem> productItemQueryWrapper = new QueryWrapper<>();
        productItemQueryWrapper.in("product_id", productIds);
        List<ProductItem> productItems = productItemRepository.find(productItemQueryWrapper);

        if (CollectionUtil.isNotEmpty(productItems)) {
            productItemMap = productItems.stream().collect(Collectors.toMap(ProductItem::getProductId, ProductItem -> ProductItem, (k1, k2) -> k1));
        }

        return productItemMap;
    }

    private Map<Long, ProductImage> getProductImageMap(List<Long> productIds) {
        Map<Long, ProductImage> productImageMap = new HashMap<>();
        QueryWrapper<ProductImage> productImageQueryWrapper = new QueryWrapper<>();
        productImageQueryWrapper.in("product_id", productIds);
        List<ProductImage> productImages = productImageRepository.find(productImageQueryWrapper);

        if (CollectionUtil.isNotEmpty(productImages)) {
            productImageMap = productImages.stream().collect(Collectors.toMap(ProductImage::getProductId, ProductImage -> ProductImage, (k1, k2) -> k1));
        }

        return productImageMap;
    }

    private Map<Long, ProductBase> getProductBaseMap(List<Long> productIds) {
        List<ProductBase> productBaseList = productBaseRepository.gets(productIds);
        Map<Long, ProductBase> productBaseMap = new HashMap<>();

        if (CollectionUtil.isNotEmpty(productBaseList)) {
            productBaseMap = productBaseList.stream().collect(Collectors.toMap(ProductBase::getProductId, ProductBase -> ProductBase, (k1, k2) -> k1));
        }

        return productBaseMap;
    }

}
