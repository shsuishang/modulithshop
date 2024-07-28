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
package com.suisung.shopsuite.pt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suisung.shopsuite.account.model.entity.UserInfo;
import com.suisung.shopsuite.account.repository.UserInfoRepository;
import com.suisung.shopsuite.common.api.ResultCode;
import com.suisung.shopsuite.common.api.StateCode;
import com.suisung.shopsuite.common.exception.BusinessException;
import com.suisung.shopsuite.common.utils.CheckUtil;
import com.suisung.shopsuite.common.utils.CommonUtil;
import com.suisung.shopsuite.common.utils.ContextUtil;
import com.suisung.shopsuite.common.web.ContextUser;
import com.suisung.shopsuite.common.web.service.MessageService;
import com.suisung.shopsuite.core.web.service.impl.BaseServiceImpl;
import com.suisung.shopsuite.pt.model.entity.*;
import com.suisung.shopsuite.pt.model.req.ProductCommentListReq;
import com.suisung.shopsuite.pt.model.res.ProductCommentRes;
import com.suisung.shopsuite.pt.repository.*;
import com.suisung.shopsuite.pt.service.ProductCommentService;
import com.suisung.shopsuite.shop.service.StoreBaseService;
import com.suisung.shopsuite.sys.service.ConfigBaseService;
import com.suisung.shopsuite.sys.service.FilterKeywordService;
import com.suisung.shopsuite.trade.model.entity.OrderBase;
import com.suisung.shopsuite.trade.model.entity.OrderComment;
import com.suisung.shopsuite.trade.model.entity.OrderInfo;
import com.suisung.shopsuite.trade.model.entity.OrderItem;
import com.suisung.shopsuite.trade.model.input.OrderCommentInput;
import com.suisung.shopsuite.trade.model.req.OrderCommentItemReq;
import com.suisung.shopsuite.trade.model.req.OrderCommentReq;
import com.suisung.shopsuite.trade.model.res.OrderCommentRes;
import com.suisung.shopsuite.trade.model.vo.EvaluationVo;
import com.suisung.shopsuite.trade.model.vo.OrderCommentVo;
import com.suisung.shopsuite.trade.model.vo.OrderItemVo;
import com.suisung.shopsuite.trade.repository.OrderBaseRepository;
import com.suisung.shopsuite.trade.repository.OrderCommentRepository;
import com.suisung.shopsuite.trade.repository.OrderInfoRepository;
import com.suisung.shopsuite.trade.repository.OrderItemRepository;
import com.suisung.shopsuite.trade.service.OrderBaseService;
import com.suisung.shopsuite.trade.service.OrderCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.suisung.shopsuite.common.api.StateCode.ORDER_ITEM_EVALUATION_YES;
import static com.suisung.shopsuite.common.utils.I18nUtil.__;

/**
 * <p>
 * 商品评价表 服务实现类
 * </p>
 *
 * @author Xinze
 * @since 2021-05-06
 */
@Service
public class ProductCommentServiceImpl extends BaseServiceImpl<ProductCommentRepository, ProductComment, ProductCommentListReq> implements ProductCommentService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private OrderCommentRepository orderCommentRepository;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private OrderBaseRepository orderBaseRepository;

    @Autowired
    private FilterKeywordService filterKeywordService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductBaseRepository productBaseRepository;

    @Autowired
    private ConfigBaseService configBaseService;

    @Autowired
    private ProductIndexRepository productIndexRepository;

    @Autowired
    private OrderCommentService orderCommentService;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductCommentReplyRepository productCommentReplyRepository;

    @Autowired
    private ProductCommentHelpfulRepository productCommentHelpfulRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private MessageService messageService;

    @Override
    @Transactional
    public boolean editState(Long commentId, Boolean commentEnable) {
        ProductComment productComment = get(commentId);

        if (productComment == null) {
            throw new BusinessException(__("该商品评价信息不存在！"));
        }
        productComment.setCommentEnable(commentEnable);

        if (!edit(productComment)) {
            throw new BusinessException(__("修改评价信息的状态失败！"));
        }
        ProductIndex productIndex = productIndexRepository.get(productComment.getProductId());

        if (productIndex == null) {
            throw new BusinessException(__("该产品索引信息不存在！"));
        }
        Integer productEvaluationNum = productIndex.getProductEvaluationNum();

        if (commentEnable) {
            productIndex.setProductEvaluationNum(productEvaluationNum + 1);
        } else {
            productIndex.setProductEvaluationNum(productEvaluationNum > 0 ? productEvaluationNum - 1 : 0);
        }

        if (!productIndexRepository.edit(productIndex)) {
            throw new BusinessException(__("修改产品索引信息失败！"));
        }

        return true;
    }

    /**
     * 查询订单评价
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderCommentRes storeEvaluationWithContent(String orderId) {
        OrderCommentRes commentRes;

        Integer userId = ContextUtil.getLoginUserId();
        OrderInfo orderInfo = orderInfoRepository.get(orderId);
        if (CheckUtil.checkDataRights(userId, orderInfo, OrderInfo::getUserId)) {
            List<Integer> orderItemEvaluationStatus = Arrays.asList(StateCode.ORDER_ITEM_EVALUATION_NO, ORDER_ITEM_EVALUATION_YES);
            commentRes = orderBaseService.getEvaluationItem(new EvaluationVo(userId, orderItemEvaluationStatus, orderId));

            QueryWrapper<ProductComment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("order_id", orderId).eq("user_id", userId);
            List<ProductComment> commentList = find(commentQueryWrapper);

            List<OrderItemVo> orderItems = commentRes.getItems();
            for (OrderItemVo orderItemVo : orderItems) {
                Long itemId = orderItemVo.getItemId();
                for (ProductComment productComment : commentList) {
                    if (productComment.getItemId().equals(itemId)) {
                        BeanUtils.copyProperties(productComment, orderItemVo);
                        // 评论图片
                        String commentImage = productComment.getCommentImage();
                        if (StrUtil.isNotEmpty(commentImage)) {
                            orderItemVo.setCommentImage(Convert.toList(String.class, commentImage));
                        }

                        //评论回复
                        QueryWrapper<ProductCommentReply> replyQueryWrapper = new QueryWrapper<>();
                        replyQueryWrapper.eq("comment_id", productComment.getCommentId());
                        replyQueryWrapper.eq("user_id_to", userId);
                        replyQueryWrapper.eq("comment_reply_enable", true);
                        replyQueryWrapper.orderByDesc("comment_reply_id");
                        List<ProductCommentReply> productCommentReplies = productCommentReplyRepository.find(replyQueryWrapper);

                        if (CollectionUtil.isNotEmpty(productCommentReplies)) {
                            orderItemVo.setProductCommentReplyList(productCommentReplies);
                        }
                    }
                }
            }

            // 查询订单评价信息
            OrderComment orderComment = orderCommentRepository.get(orderId);
            if (orderComment != null) {
                OrderCommentVo orderCommentVo = new OrderCommentVo();
//                OrderItemVo orderItemVo = commentRes.getItems().get(0);
                BeanUtils.copyProperties(orderComment, orderCommentVo);
//                BeanUtils.copyProperties(orderComment, orderItemVo);
                String commentImage = orderCommentVo.getCommentImage();
                if (StrUtil.isNotEmpty(commentImage)) {
                    orderCommentVo.setCommentImages(Convert.toList(String.class, commentImage));
                }
                commentRes.setOrderEvaluation(orderCommentVo);
            } else {
                commentRes.setOrderEvaluation(new ArrayList<>());
            }

            // 店铺信息
//            Integer storeId = orderInfo.getStoreId();
//            List<StoreInfoVo> infoVoList = storeBaseService.getStore(Collections.singletonList(storeId));
//            if (CollUtil.isNotEmpty(infoVoList)) {
//                commentRes.setStoreInfo(infoVoList.get(0));
//            }
        } else {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        return commentRes;
    }

    /**
     * 添加订单评论
     *
     * @param commentReq
     */
    @Override
    @Transactional
    public void addOrderComment(OrderCommentReq commentReq) {
        ContextUser user = ContextUtil.getLoginUser();
        assert user != null;

        OrderComment orderComment = orderCommentRepository.get(commentReq.getOrderId());
        if (orderComment != null) {
            throw new BusinessException(__("该订单已评论！"));
        }

        String orderId = commentReq.getOrderId();
        OrderBase orderBase = orderBaseRepository.get(orderId);
        OrderInfo orderInfo = orderInfoRepository.get(orderId);
        JSONArray itemArray = JSONUtil.parseArray(commentReq.getItem());
        List<OrderCommentItemReq> orderCommentReqItem = JSONUtil.toList(itemArray, OrderCommentItemReq.class);
        for (OrderCommentItemReq commentItemReq : orderCommentReqItem) {
            ProductComment comment = new ProductComment();
            BeanUtils.copyProperties(commentItemReq, comment);
            Boolean commentIsAnonymous = commentItemReq.getCommentIsAnonymous();
            if (commentIsAnonymous.equals(Boolean.TRUE)) {
                comment.setUserName(__("匿名用户") + new Date().getTime());
                comment.setCommentIsAnonymous(Boolean.TRUE);
            } else {
                comment.setUserName(user.getUserNickname());
                comment.setCommentIsAnonymous(Boolean.FALSE);
            }

            // 提交时候，判断没有违禁词才可以通行
            String commentContent = commentItemReq.getCommentContent();
            if (!filterKeywordService.hasKeyword(commentContent)) {
                throw new BusinessException(__("评论中包含非法词汇！"));
            }
            comment.setUserId(user.getUserId());
            comment.setCommentEnable(Boolean.TRUE);
            comment.setStoreId(orderBase.getStoreId());
            comment.setStoreName(orderBase.getStoreName());
            comment.setChainId(orderInfo.getChainId());
            comment.setCommentScores(commentItemReq.getCommentScores());

            List<String> commentImage = commentItemReq.getCommentImage();
            if (CollUtil.isNotEmpty(commentImage)) {
                comment.setCommentImage(commentImage.toString());
            }

            // 商品评论信息
            addProductComment(comment, commentItemReq.getOrderItemId());

            OrderCommentInput commentInput = new OrderCommentInput(orderId, orderBase, commentItemReq, commentImage, commentReq.getStoreDesccredit(), commentReq.getStoreServicecredit(), commentReq.getStoreDeliverycredit());
            orderCommentService.addOrderComment(commentInput);
        }

    }

    /**
     * 添加商品评论
     *
     * @param comment
     * @param orderItemId
     */
    private void addProductComment(ProductComment comment, Integer orderItemId) {
        String orderId = comment.getOrderId();

        List<OrderItem> orderItems;
        if (orderItemId != null) {
            orderItems = orderItemRepository.gets(orderItemId);
        } else {
            QueryWrapper<OrderItem> itemQueryWrapper = new QueryWrapper<>();
            itemQueryWrapper.eq("order_id", orderId).eq("item_id", comment.getItemId());
            orderItems = orderItemRepository.find(itemQueryWrapper);
        }

        if (CollUtil.isEmpty(orderItems)) {
            throw new BusinessException(__("保存评论失败！"));
        }

        OrderItem orderItem = orderItems.get(0);
        Long productId = orderItem.getProductId();
        comment.setProductId(productId);
        comment.setItemId(orderItem.getItemId());
        comment.setItemName(orderItem.getItemName());

        ProductBase productBase = productBaseRepository.get(productId);
        if (productBase == null) {
            throw new BusinessException(__("商品已不存在，不可评论！"));
        }

        Boolean orderItemEvaluationStatus = orderItem.getOrderItemEvaluationStatus();
        if (orderItemEvaluationStatus.equals(Boolean.FALSE)) {
            // 获得积分 经验
            BigDecimal pointsEvaluateGood = configBaseService.getConfig("points_evaluate_good", BigDecimal.ZERO);
            BigDecimal expEvaluateGood = configBaseService.getConfig("exp_evaluate_good", BigDecimal.ZERO);

            // 添加
            comment.setCommentPoints(pointsEvaluateGood);

            //分站
            OrderInfo orderInfo = orderInfoRepository.get(orderId);

            if (orderInfo != null) {
                comment.setSubsiteId(orderInfo.getSubsiteId());
            }

            if (!add(comment)) {
                throw new BusinessException(__("保存评论失败！"));
            }

            QueryWrapper<OrderItem> itemQueryWrapper = new QueryWrapper<>();
            List<Long> orderItemIds = orderItems.stream().map(OrderItem::getOrderItemId).distinct().collect(Collectors.toList());
            itemQueryWrapper.in("order_item_id", orderItemIds);

            OrderItem shopOrderItem = new OrderItem();
            shopOrderItem.setOrderItemEvaluationStatus(Boolean.TRUE);

            if (!orderItemRepository.edit(shopOrderItem, itemQueryWrapper)) {
                throw new BusinessException(__("修改评论状态失败！"));
            }

            ProductIndex shopProductIndex = productIndexRepository.get(productId);
            shopProductIndex.setProductEvaluationNum(shopProductIndex.getProductEvaluationNum() + 1);
            if (!productIndexRepository.edit(shopProductIndex)) {
                throw new BusinessException(__("产品评论更新失败！"));
            }

            // 触发评论完成时间
            // 差评提醒
            if (comment.getCommentScores() == 1) {
                String messageId = "bad-review-reminder-notification";
                Map<String, Object> args = new HashMap<>();
                args.put("order_id", orderId);
                Integer adminUserId = configBaseService.getConfig("message_notice_user_id", 10001);
                messageService.sendNoticeMsg(adminUserId, messageId, args);
            }
        } else {
            throw new BusinessException(__("该订单商品已进行评价！"));
        }
    }

    @Override
    public ProductCommentRes getComment(ProductCommentListReq req) {

        ProductCommentRes commentRes = new ProductCommentRes();

        // 根据itemId查找ProductId
        Long productId = null;
        Long itemId = req.getItemId();
        Long commentId = req.getCommentId();
        Integer chainId = req.getChainId();

        if (CheckUtil.isNotEmpty(itemId)) {
            ProductItem productItem = productItemRepository.get(itemId);
            productId = productItem.getProductId();
        }

        ContextUser user = ContextUtil.getLoginUser();
        Integer userId = user != null ? user.getUserId() : null;

        if (CheckUtil.isNotEmpty(productId) || CheckUtil.isNotEmpty(commentId) || CheckUtil.isNotEmpty(chainId)) {

            QueryWrapper<ProductComment> queryWrapper = new QueryWrapper<>();
            // 如果有评论编号
            if (CheckUtil.isNotEmpty(commentId)) {
                ProductComment productComment = get(commentId);
                commentRes.setItems(Collections.singletonList(productComment));
            }
            // 如果有spu编号
            if (CheckUtil.isNotEmpty(productId)) {
                queryWrapper.eq("product_id", productId);
                queryWrapper.eq("comment_enable", 1);
            }
            // 如果有门店编号
            if (CheckUtil.isNotEmpty(chainId)) {
                queryWrapper.eq("chain_id", chainId);
            }

            if (CheckUtil.isNotEmpty(req.getOnlyHasPic())) {
                queryWrapper.ne("comment_image", "");
            }

            // 根据打分情况获取评论总数
            QueryWrapper<ProductComment> goodNumWapper = queryWrapper.clone();
            goodNumWapper.gt("comment_scores", 3);
            long goodNum = count(goodNumWapper);
            commentRes.setGood(goodNum);
            QueryWrapper<ProductComment> satisfiedNumWapper = queryWrapper.clone();
            satisfiedNumWapper.eq("comment_scores", 3);
            long satisfiedNum = count(satisfiedNumWapper);
            commentRes.setSatisfied(satisfiedNum);
            QueryWrapper<ProductComment> badNumWapper = queryWrapper.clone();
            badNumWapper.lt("comment_scores", 3);
            long badNum = count(badNumWapper);
            commentRes.setBad(badNum);

            Integer commentType = req.getCommentType();
            if (CheckUtil.isNotEmpty(commentType)) {
                if (commentType == 1) {
                    queryWrapper.gt("comment_scores", 3);
                } else if (commentType == 2) {
                    queryWrapper.eq("comment_scores", 3);
                } else if (commentType == 3) {
                    queryWrapper.lt("comment_scores", 3);
                } else if (commentType == 4) {
                    queryWrapper.ne("comment_scores", "");
                } else if (commentType == 5) {
                    //有评论
                }
            }
            queryWrapper.eq("comment_enable", true);
            queryWrapper.orderByDesc("comment_time");
            Page<ProductComment> commentPage = lists(queryWrapper, req.getPage(), req.getSize());
            commentRes.setTotal(Convert.toInt(commentPage.getPages()));
            commentRes.setSize(Convert.toInt(commentPage.getSize()));
            commentRes.setPage(Convert.toInt(commentPage.getCurrent()));
            commentRes.setRecords(Convert.toInt(commentPage.getTotal()));

            List<ProductComment> commentList = commentPage.getRecords();

            commentRes.setItems(commentList);

            List<Long> commentIds = commentList.stream().map(ProductComment::getCommentId).distinct().collect(Collectors.toList());
            // 评论回复
            List<ProductCommentReply> replyList = new ArrayList<>();
            if (CollUtil.isNotEmpty(commentIds)) {
                QueryWrapper<ProductCommentReply> replyQueryWrapper = new QueryWrapper<>();
                replyQueryWrapper.in("comment_id", commentIds);
                replyQueryWrapper.eq("comment_reply_enable", 1);
                replyList = productCommentReplyRepository.find(replyQueryWrapper);
            }

            List<ProductCommentHelpful> helpfulList = new ArrayList<>();
            if (CollUtil.isNotEmpty(commentIds)) {
                QueryWrapper<ProductCommentHelpful> helpfulQueryWrapper = new QueryWrapper<>();
                helpfulQueryWrapper.in("comment_id", commentIds);
                helpfulQueryWrapper.eq("user_id", userId);
                helpfulList = productCommentHelpfulRepository.find(helpfulQueryWrapper);
            }

            List<Integer> userIds = commentList.stream().map(ProductComment::getUserId).distinct().collect(Collectors.toList());
            List<UserInfo> userInfos = userInfoRepository.gets(userIds);

            for (ProductComment comment : commentList) {
                Iterator<ProductCommentReply> replyIterator = replyList.iterator();
                Long itemCommentId = comment.getCommentId();
                ArrayList<ProductCommentReply> commentReplies = new ArrayList<>();

                while (replyIterator.hasNext()) {
                    ProductCommentReply commentReply = replyIterator.next();
                    Long reply_comment_id = commentReply.getCommentId();
                    if (ObjectUtil.equal(itemCommentId, reply_comment_id)) {
                        commentReplies.add(commentReply);
                    }
                }
                comment.setCommentReplyList(commentReplies);

                Optional<ProductCommentHelpful> helpfulOpl = helpfulList.stream().filter(s -> ObjectUtil.equal(itemCommentId, s.getCommentId())).findFirst();
                comment.setHelpful(helpfulOpl.isPresent() ? 1 : 0);

                String strCommentImage = Optional.ofNullable(comment.getCommentImage()).orElse("");
                comment.setCommentImages(Convert.toList(String.class, strCommentImage));

                // 设置用户头像
                for (UserInfo userInfo : userInfos) {
                    if (comment.getUserId().equals(userInfo.getUserId())) {
                        comment.setUserAvatar(userInfo.getUserAvatar());
                    }
                }
            }

        } else {
            throw new BusinessException(__("缺少产品ID或商品编号！"));
        }

        return commentRes;
    }

    @Override
    public IPage<ProductComment> getList(ProductCommentListReq productCommentListReq) {
        IPage<ProductComment> productCommentPage = lists(productCommentListReq);

        if (productCommentPage != null && CollectionUtil.isNotEmpty(productCommentPage.getRecords())) {
            List<ProductComment> commentList = productCommentPage.getRecords();
            List<Long> productIds = CommonUtil.column(commentList, ProductComment::getProductId);

            List<Long> commentIds = CommonUtil.column(commentList, ProductComment::getCommentId);
            QueryWrapper<ProductCommentReply> replyQueryWrapper = new QueryWrapper<>();
            replyQueryWrapper.in("comment_id", commentIds);
            List<ProductCommentReply> productCommentReplies = productCommentReplyRepository.find(replyQueryWrapper);
            Map<Long, Integer> commentIdCountMap = new HashMap<>();

            if (CollectionUtil.isNotEmpty(productCommentReplies)) {

                for (ProductCommentReply reply : productCommentReplies) {
                    Long commentId = reply.getCommentId();
                    if (commentIdCountMap.containsKey(commentId)) {
                        commentIdCountMap.put(commentId, commentIdCountMap.get(commentId) + 1);
                    } else {
                        commentIdCountMap.put(commentId, 1);
                    }
                }
            }

            List<ProductBase> productBases = productBaseRepository.gets(productIds);
            Map<Long, String> productNameMap = new HashMap<>();

            if (CollectionUtil.isNotEmpty(productBases)) {
                productNameMap = productBases.stream().collect(Collectors.toMap(ProductBase::getProductId, ProductBase::getProductName, (k1, k2) -> k1));
            }
            for (ProductComment productComment : commentList) {
                String commentImage = productComment.getCommentImage();

                if (StrUtil.isNotEmpty(commentImage)) {
                    productComment.setCommentImages(Convert.toList(String.class, commentImage));
                }
                //产品名称
                if (CollUtil.isNotEmpty(productNameMap)) {
                    productComment.setProductName(productNameMap.get(productComment.getProductId()));
                }
                //评价回复数量
                if (!commentIdCountMap.isEmpty()) {
                    productComment.setCommentReplyNum(Convert.toInt(commentIdCountMap.get(productComment.getCommentId()), 0));
                }
            }
        }

        return productCommentPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeComment(Long commentId) {
        if (!remove(commentId)) {
            throw new BusinessException(__("该商品评价删除失败"));
        }
        QueryWrapper<ProductCommentReply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.eq("comment_id", commentId);
        List<Serializable> replyIds = productCommentReplyRepository.findKey(replyQueryWrapper);

        if (CollectionUtil.isNotEmpty(replyIds)) {
            if (!productCommentReplyRepository.remove(replyIds)) {
                throw new BusinessException(__("该商品评价回复删除失败"));
            }
        }

        return true;
    }

}
