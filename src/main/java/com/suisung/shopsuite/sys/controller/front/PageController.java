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
package com.suisung.shopsuite.sys.controller.front;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suisung.shopsuite.account.service.UserInfoService;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.core.web.CommonRes;
import com.suisung.shopsuite.core.web.controller.BaseController;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import com.suisung.shopsuite.marketing.service.ActivityBaseService;
import com.suisung.shopsuite.sys.model.entity.PageBase;
import com.suisung.shopsuite.sys.model.entity.PageModule;
import com.suisung.shopsuite.sys.model.entity.PagePcNav;
import com.suisung.shopsuite.sys.model.req.PageBaseListReq;
import com.suisung.shopsuite.sys.model.req.PageDetailReq;
import com.suisung.shopsuite.sys.model.res.PageBaseRes;
import com.suisung.shopsuite.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 页面表 前端控制器
 * </p>
 *
 * @author Xinze
 * @since 2021-07-03
 */
@Api(tags = "页面表")
@RestController
@RequestMapping("/front/sys/page")
public class PageController extends BaseController {
    @Autowired
    private PageBaseService pageBaseService;

    @Autowired
    private PageModuleService pageModuleService;

    @Autowired
    private ActivityBaseService activityBaseService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PageCategoryNavService pageCategoryNavService;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private PagePcNavService pagePcNavService;


    @ApiOperation(value = "页面表-分页列表查询", notes = "页面表-分页列表查询")
    @RequestMapping(value = "/getMobileIndexNav", method = RequestMethod.GET)
    public CommonRes<BaseListRes<PageBase>> getMobileIndexNav(PageBaseListReq pageBaseListReq) {
        IPage<PageBase> pageList = pageBaseService.lists(pageBaseListReq);

        return success(pageList);
    }

    @ApiOperation(value = "读取移动页面", notes = "读取移动页面")
    @RequestMapping(value = "/getMobilePage", method = RequestMethod.GET)
    public CommonRes<?> getMobilePage(PageDetailReq req) {
        ContextUser user = ContextUtil.getLoginUser();

        if (CheckUtil.isNotEmpty(req.getPageId())) {

        } else if (CheckUtil.isNotEmpty(req.getPageIndex())) {
            //根据类型读取pageId
            QueryWrapper<PageBase> baseQueryWrapper = new QueryWrapper<>();


            //baseQueryWrapper.eq("subsite_id", subsite_id).eq("store_id", store_id).eq("app_id", app_id).eq("page_type", 3);
            baseQueryWrapper.eq("page_type", 3);

            switch (req.getPageIndex()) {
                case "page_index":
                    baseQueryWrapper.eq("page_index", 1);
                    break;
                case "page_sns":
                    baseQueryWrapper.eq("page_sns", 1);
                    break;
                case "page_article":
                    baseQueryWrapper.eq("page_article", 1);
                    break;
                case "page_point":
                    baseQueryWrapper.eq("page_point", 1);
                    break;
                case "page_upgrade":
                    baseQueryWrapper.eq("page_upgrade", 1);
                    break;
                case "page_zerobuy":
                    baseQueryWrapper.eq("page_zerobuy", 1);
                    break;
                case "page_higharea":
                    baseQueryWrapper.eq("page_higharea", 1);
                    break;
                case "page_taday":
                    baseQueryWrapper.eq("page_taday", 1);
                    break;
                case "page_everyday":
                    baseQueryWrapper.eq("page_everyday", 1);
                    break;
                case "page_secondkill":
                    baseQueryWrapper.eq("page_secondkill", 1);
                    break;
                case "page_secondday":
                    baseQueryWrapper.eq("page_secondday", 1);
                    break;
                case "page_rura":
                    baseQueryWrapper.eq("page_rura", 1);
                    break;
                case "page_likeyou":
                    baseQueryWrapper.eq("page_likeyou", 1);
                    break;
                case "page_exchange":
                    baseQueryWrapper.eq("page_exchange", 1);
                    break;
                case "page_new":
                    baseQueryWrapper.eq("page_new", 1);
                    break;
                case "page_newperson":
                    baseQueryWrapper.eq("page_newperson", 1);
                    break;
            }

            List<Serializable> pageIds = pageBaseService.findKey(baseQueryWrapper);

            if (CollUtil.isNotEmpty(pageIds)) {
                req.setPageId((Long) pageIds.get(0));
            }

        } else if (CheckUtil.isNotEmpty(req.getCategoryId())) {
            //根据分类读取pageId
        } else {
            return fail(__("请求数据有误！"));
        }
        PageBaseRes pageBaseRes = pageBaseService.detail(req.getPageId());

        return success(pageBaseRes);
    }

    @ApiOperation(value = "读取PC页面", notes = "读取PC页面")
    @RequestMapping(value = "/getPcPage", method = RequestMethod.GET)
    public CommonRes<?> getPcPage(PageDetailReq req) {
        ContextUser user = ContextUtil.getLoginUser();

        if (CheckUtil.isNotEmpty(req.getPageId())) {

        } else if (CheckUtil.isNotEmpty(req.getPageIndex())) {
            //根据类型读取pageId
            QueryWrapper<PageBase> baseQueryWrapper = new QueryWrapper<>();


            //baseQueryWrapper.eq("subsite_id", subsite_id).eq("store_id", store_id).eq("app_id", app_id).eq("page_type", 3);
            baseQueryWrapper.eq("page_type", 2);

            switch (req.getPageIndex()) {
                case "page_index":
                    baseQueryWrapper.eq("page_index", 1);
                    break;
                case "page_sns":
                    baseQueryWrapper.eq("page_sns", 1);
                    break;
                case "page_article":
                    baseQueryWrapper.eq("page_article", 1);
                    break;
                case "page_point":
                    baseQueryWrapper.eq("page_point", 1);
                    break;
                case "page_upgrade":
                    baseQueryWrapper.eq("page_upgrade", 1);
                    break;
                case "page_zerobuy":
                    baseQueryWrapper.eq("page_zerobuy", 1);
                    break;
                case "page_higharea":
                    baseQueryWrapper.eq("page_higharea", 1);
                    break;
                case "page_taday":
                    baseQueryWrapper.eq("page_taday", 1);
                    break;
                case "page_everyday":
                    baseQueryWrapper.eq("page_everyday", 1);
                    break;
                case "page_secondkill":
                    baseQueryWrapper.eq("page_secondkill", 1);
                    break;
                case "page_secondday":
                    baseQueryWrapper.eq("page_secondday", 1);
                    break;
                case "page_rura":
                    baseQueryWrapper.eq("page_rura", 1);
                    break;
                case "page_likeyou":
                    baseQueryWrapper.eq("page_likeyou", 1);
                    break;
                case "page_exchange":
                    baseQueryWrapper.eq("page_exchange", 1);
                    break;
                case "page_new":
                    baseQueryWrapper.eq("page_new", 1);
                    break;
                case "page_newperson":
                    baseQueryWrapper.eq("page_newperson", 1);
                    break;
            }

            List<Serializable> pageIds = pageBaseService.findKey(baseQueryWrapper);

            if (CollUtil.isNotEmpty(pageIds)) {
                req.setPageId((Long) pageIds.get(0));
            }

            //

        } else if (CheckUtil.isNotEmpty(req.getCategoryId())) {
            //根据分类读取pageId
        } else {
            return fail(__("请求数据有误！"));
        }

        Map data = new HashMap();

        if (req.getPageId() > 0) {
            QueryWrapper<PageModule> qw = new QueryWrapper<>();
            qw.eq("page_id", req.getPageId()).eq("pm_enable", 1);
            qw.orderByAsc("pm_order");

            List<PageModule> page_data = pageModuleService.find(qw);

            data.put("floor", pageModuleService.fixPcPageModuleData(page_data));

        } else {
            //throw new BusinessException("页面不存在");
            data.put("floor", new ArrayList<>());
        }

        /*
        if (req.getPageIndex().equals("page_index")) {
            //首页弹窗 新人优惠券
            ActivityBaseListReq activityBaseListReq = new ActivityBaseListReq();
            activityBaseListReq.setActivityState(StateCode.ACTIVITY_STATE_NORMAL);
            activityBaseListReq.setActivityTypeId(StateCode.ACTIVITY_TYPE_POP);
            IPage<ActivityBaseRes> activityBaseResIPage = activityBaseService.getList(activityBaseListReq);

            if (activityBaseResIPage != null && CollectionUtil.isNotEmpty(activityBaseResIPage.getRecords())) {
                List<ActivityBaseRes> activityList = activityBaseResIPage.getRecords();

                //未登录
                if (user == null) {
                    pageBaseRes.setPopUps(dealWithPopUp(activityList, null));
                } else {
                    //已登录
                    UserInfo userInfo = userInfoService.get(user.getUserId());
                    pageBaseRes.setPopUps(dealWithPopUp(activityList, userInfo));
                }
            }
        }

         */

        return success(data);
    }

    /**
     * 获得首尾部导航
     */
    @ApiOperation(value = "PC头尾数据", notes = "PC头尾数据")
    @RequestMapping(value = "/pcLayout", method = RequestMethod.GET)
    public CommonRes<?> pcLayout() {
        Map pcLayout = pageCategoryNavService.getPcLayout();

        //底部站内帮助
        String keys = "page_pc_help";
        String pagePcHelp = configBaseService.getConfig(keys);
        if (StrUtil.isNotEmpty(pagePcHelp)) {
            pcLayout.put("footer_article", JSONUtil.parse(pagePcHelp));
        }

        //尾部导航
        QueryWrapper<PagePcNav> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("nav_position", 2).eq("nav_enable", 1);
        queryWrapper.eq("nav_enable", 1);
        List<PagePcNav> pagePcNavs = pagePcNavService.find(queryWrapper);

        pcLayout.put("page_pc_nav", pagePcNavs);


        ContextUser user = ContextUtil.getLoginUser();

        if (user != null) {
            pcLayout.put("user_nickname", "Hi," + user.getUserNickname() + "!");
            pcLayout.put("user_avatar", userInfoService.get(user.getUserId()).getUserAvatar());
        }

        return success(pcLayout);
    }
}

