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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.common.consts.ConstantConfig;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.MapUtil;
import com.suisung.shopsuite.core.web.service.CloundService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pt.model.entity.ProductBrand;
import com.suisung.shopsuite.pt.model.input.ProductItemInput;
import com.suisung.shopsuite.pt.model.output.ItemOutput;
import com.suisung.shopsuite.pt.model.res.ItemListRes;
import com.suisung.shopsuite.pt.repository.ProductBrandRepository;
import com.suisung.shopsuite.pt.service.ProductIndexService;
import com.suisung.shopsuite.sys.model.entity.ConfigBase;
import com.suisung.shopsuite.sys.model.entity.PageModule;
import com.suisung.shopsuite.sys.model.req.PageModuleListReq;
import com.suisung.shopsuite.sys.model.vo.PageModuleVo;
import com.suisung.shopsuite.sys.repository.PageModuleRepository;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.PageModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 页面模块表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Service
public class PageModuleServiceImpl extends BaseServiceImpl<PageModuleRepository, PageModule, PageModuleListReq> implements PageModuleService {
    @Autowired
    private CloundService cloundService;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Autowired
    private ProductIndexService productIndexService;

    @Override
    public Map getModuleTpl() {
        try {
            ConfigBase configBaseUserId = configBaseService.get("service_user_id");
            ConfigBase configBaseAppKey = configBaseService.get("service_app_key");

            return cloundService.getModuleTpl(Convert.toInt(configBaseUserId.getConfigValue()), configBaseAppKey.getConfigValue());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public IPage<PageModuleVo> getLists(PageModuleListReq pageModuleListReq) {
        QueryWrapper<PageModule> pageModuleQueryWrapper = new QueryWrapper<>();
        pageModuleQueryWrapper.eq("page_id", pageModuleListReq.getPageId());
        IPage<PageModule> modulePage = lists(pageModuleQueryWrapper, 1, ConstantConfig.MAX_LIST_NUM);
        IPage<PageModuleVo> pageModuleVo = new Page<>();
        BeanUtils.copyProperties(modulePage, pageModuleVo);

        List<PageModuleVo> pageModuleVos = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(modulePage.getRecords())) {
            for (PageModule pageModule : modulePage.getRecords()) {
                PageModuleVo moduleVo = new PageModuleVo();
                BeanUtils.copyProperties(pageModule, moduleVo);
                JSON parse = JSONUtil.parse(pageModule.getPmJson());
                moduleVo.setPmJson(parse);
                pageModuleVos.add(moduleVo);
            }
            pageModuleVo.setRecords(pageModuleVos);
        }

        return pageModuleVo;
    }


    @Override
    public List<Map> fixPcPageModuleData(List<PageModule> page_data) {

        List<Map> data = new ArrayList<>();
        if (CollUtil.isNotEmpty(page_data)) {
            for (PageModule module_row : page_data) {
                Map moduleDefault = Convert.toMap(String.class, Object.class, module_row);

                Map<String, Object> module = MapUtil.keyToUnderline(moduleDefault);


                data.add(module);
                // todo 系统启用自动翻译功能
                String module_id = module_row.getModuleId();
                JSONObject pm_json = null;
                try {
                    pm_json = JSONUtil.parseObj(module_row.getPmJson());
                } catch (Exception e) {
                    // json 解析错误可以忽略（脏数据）
                }

                if (pm_json == null) continue;
                module.put("pm_json", pm_json);

                if (Arrays.asList("1001", "1004", "1005", "1006", "3002").contains(module_id)) {
                    // 读取商品
                    JSONArray tabs = (JSONArray) pm_json.get("tabs");
                    if (CollUtil.isNotEmpty(tabs)) {
                        List<Long> item_ids = new ArrayList<>();
                        for (Object tab : tabs) {
                            JSONArray items = (JSONArray) ((JSONObject) tab).get("items");
                            if (CollUtil.isNotEmpty(items)) {
                                item_ids = items.stream().map(s -> Convert.toLong(((JSONObject) s).get("item_id"))).distinct().collect(Collectors.toList());
                            }
                        }

                        ProductItemInput input = new ProductItemInput();
                        input.setItemId(item_ids);
                        ItemListRes itemListRes = productIndexService.listItem(input);
                        List<ItemOutput> item_rows = itemListRes.getItems();

                        for (Object tab : tabs) {
                            JSONArray items = ObjectUtil.defaultIfNull((JSONArray) ((JSONObject) tab).get("items"), new JSONArray());
                            for (Object item : items) {

                                Long item_id = Convert.toLong(((JSONObject) item).get("item_id"));
                                if (CollUtil.isNotEmpty(item_rows)) {
                                    Optional<ItemOutput> item_row_opl = item_rows.stream().filter(s -> ObjectUtil.equal(item_id, s.getItemId())).findFirst();
                                    ItemOutput item_row = item_row_opl.orElseGet(ItemOutput::new);

                                    ((JSONObject) item).put("item_unit_price", item_row.getItemUnitPrice());
                                    ((JSONObject) item).put("item_market_price", item_row.getItemMarketPrice());
                                }

                                ((JSONObject) item).put("activity_type_id", 0);
                                ((JSONObject) item).put("activity_type_name", 0);
                            }

                        }
                    }
                } else if (StrUtil.equals(module_id, "1104")) {
                    // 读取推荐品牌
                    QueryWrapper<ProductBrand> brandQueryWrapper = new QueryWrapper<>();
                    brandQueryWrapper.eq("brand_recommend", 1)
                            .eq("brand_enable", 1);
                    Page<ProductBrand> brandPage = productBrandRepository.lists(brandQueryWrapper, 1, 30);
                    List<ProductBrand> productBrands = brandPage.getRecords();
                    pm_json.put("brand_rows", productBrands);
                }
            }
        }

        return data;
    }
}
