(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["member-fans-endorsement"],{"0441":function(t,e,i){"use strict";var n=i("67cf"),o=i.n(n);o.a},"30f1":function(t,e,i){"use strict";i.r(e);var n=i("31e2"),o=i.n(n);for(var a in n)["default"].indexOf(a)<0&&function(t){i.d(e,t,(function(){return n[t]}))}(a);e["default"]=o.a},"31e2":function(t,e,i){"use strict";i("6a54");var n=i("f5bd").default;Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0,i("5c47"),i("a1c1"),i("5ef2");var o=n(i("9b1b")),a=n(i("2634")),s=n(i("2fdc")),r=n(i("8ab3")),u=n(i("b8a6")),c=i("8f59"),d={data:function(){return{options:{},invite_qrcode:"",isShowBtn:!1,ProductInfo:null,isShowMag:!1,ProductUrl:"",buyname:"",PromotionRule:"",htmlString:"",RuleEnabled:!0,flag:!0,CashData:{},shareData:{},PageQRCodeInfo:{Path:"",IsShare:!1,IsShareBox:!1,IsJT:!1},productPoster:"",show:!1,poster:{css:{width:"750rpx",height:"1334rpx",background:"#fff"},views:[{type:"image",src:"背景图片",css:{left:"0rpx",top:"0rpx",width:"750rpx",height:"1334rpx",position:"fixed"}},{type:"image",src:"用户头像",css:{left:"600rpx",top:"40rpx",width:"120rpx",height:"120rpx",position:"fixed",borderRadius:"20rpx"}},{type:"text",text:"用户昵称",css:{position:"fixed",right:"40rpx",top:"180rpx",fontSize:"20rpx",color:"#fff"}},{type:"image",src:"二维码图片",css:{left:"210rpx",top:"590rpx",width:"330rpx",height:"330rpx",position:"fixed"}},{type:"text",text:"长按保存或扫码查看",css:{position:"fixed",left:"285rpx",top:"940rpx",fontSize:"20rpx",color:"#fff"}}]}}},components:{shareBoxMp:r.default,shareBoxApp:u.default},watch:{plantformInfo:function(t,e){console.info(e),console.info(t),this.plantformInfo=t,this.poster.views[0].src=t.plantform_poster_bg},userInfo:function(t,e){this.userInfo=t,this.poster.views[1].src=t.user_avatar,this.poster.views[2].text=t.user_nickname}},computed:(0,c.mapState)(["Config","StateCode","notice","plantformInfo","shopInfo","userInfo","hasLogin"]),onLoad:function(t){var e=this;return(0,s.default)((0,a.default)().mark((function i(){var n;return(0,a.default)().wrap((function(i){while(1)switch(i.prev=i.next){case 0:n=e,uni.setNavigationBarTitle({title:e.__("我要代言")}),e.setData({options:t}),n.$.isNull(t.type)?e.setData({ProductUrl:"/pagesub/product/detail",buyname:n.__("去购买")}):e.setData({ProductUrl:"/pagesub/product/detail",buyname:n.__("去预约")}),n.$.isNull(e.userInfo)?e.forceuserInfo((function(e){e&&(n.setData({isShowBtn:!n.$.isNull(t.uid)}),n.showButton(),n.getPageInfo(n.$.isNull(t.uid)?n.userInfo.user_id:t.uid))})):(e.setData({isShowBtn:!n.$.isNull(t.uid)}),e.showButton(),e.getPageInfo(n.$.isNull(t.uid)?e.userInfo.user_id:t.uid),n.$.isNull(t.pid)?n.$.setNavigationBarTitle({title:n.__("我要代言")}):(n.$.setNavigationBarTitle({title:n.__("我要推广")}),e.setData({ProductInfo:t}),e.getPlantformInfo((function(t){n.setData({flag:!0,RuleEnabled:t.promotion}),n.htmlString=t.promotion.replace(/\\/g,"").replace(/<img/g,'<img style="display:none;"')})))),e.poster.views[0].src=e.plantformInfo.plantform_poster_bg,e.poster.views[1].src=e.userInfo.user_avatar,e.poster.views[2].text=e.userInfo.user_nickname;case 8:case"end":return i.stop()}}),i)})))()},onBackPress:function(){if(this.$refs.shareBoxApp.showBoxView)return this.$refs.shareBoxApp.cancel(),!0},onUnload:function(){this.$refs.shareBoxApp.showBoxView&&this.$refs.shareBoxApp.cancel()},onNavigationBarButtonTap:function(t){0===t.index?this.onShareBox():uni.showToast({title:"你点了收藏按钮",icon:"none"})},onShareAppMessage:function(){var t,e;return this.setData({isShowMag:!1}),this.$.isNull(this.ProductInfo)?(t="/member/fans/endorsement?uid="+this.userInfo.user_id,e=this.userInfo.user_nickname+this.__("偷偷的告诉你，粉丝也能赚钱哦~")):(t="/member/fans/endorsement?pid="+this.ProductInfo.pid+"&pname="+this.ProductInfo.pname+"&pprice="+this.ProductInfo.pprice+"&ppic="+this.ProductInfo.ppic+"&uid="+this.userInfo.user_id+"&type="+this.ProductInfo.type,e=this.__("我发现这个商品很好，非常适合你哦！")),{title:e,desc:e,path:t}},methods:(0,o.default)((0,o.default)({},(0,c.mapMutations)(["login","logout","getPlantformInfo","forceuserInfo","getuserInfo"])),{},{createPoster:function(t){this.productPoster=t,this.productPoster&&(uni.hideLoading(),this.$refs.shareBoxMp.path=this.productPoster)},showButton:function(){this.userInfo.user_id;this.setData({CashData:{isShowDistributionButton:!0}})},share:function(){this.getPageInfo(this.userInfo.user_id),this.setData({isShowBtn:!1})},getPageInfo:function(t){var e=this,i={user_id:t,path:"pages/index/index?uid="+t,width:430},n=e.$.sprintf("%s/h5/pages/index/index?uid=%d",e.Config.SiteUrl,t);i["path"]=n,i["poster_type"]=1,e.poster.views[3].type="qrcode",e.poster.views[3].text=n,e.$.request({url:this.Config.URL.fx.poster,data:i,success:function(t,i,n,o){if(200==i){var a=t.qrcode;a=-1!=t.qrcode.indexOf("?")?t.qrcode+"&rand="+Math.random():t.qrcode+"?rand="+Math.random(),e.setData({invite_qrcode:a}),e.poster.views[3].src=a}else e.setData({flag:!1,ispage:!1})}})},closeMsk:function(){this.setData({isShowMag:!1})},onShareBox:function(t){var e,i,n=this.$.sprintf("%s/h5/pages/index/index?uid=%d",this.Config.SiteUrl,this.userInfo.user_id);this.$.isNull(this.ProductInfo)?("/member/fans/endorsement?uid="+this.userInfo.user_id,e=this.userInfo.user_nickname+this.__("偷偷的告诉你，粉丝也能赚钱哦~"),i=this.plantformInfo.site_mobile_logo):("/member/fans/endorsement?pid="+this.ProductInfo.pid+"&pname="+this.ProductInfo.pname+"&pprice="+this.ProductInfo.pprice+"&ppic="+this.ProductInfo.ppic+"&uid="+this.userInfo.user_id+"&type="+this.ProductInfo.type,e=this.__("我发现这个商品很好，非常适合你哦！"),i=this.ProductInfo.ppic),this.setData({shareData:{shareTitle:e,shareText:e,href:n,image:i,price:0}}),this.$refs.shareBoxMp.show()},cancelShare:function(t){},showCodeImg:function(t){},shareQRCode:function(t){""==this.productPoster&&(uni.showLoading({title:"海报生成中..",mask:!0}),this.$refs.painter.render(this.poster))},saveImg:function(t){},previewImg:function(){this.$.previewImage({current:this.invite_qrcode,urls:[this.invite_qrcode]})}})};e.default=d},"35b4":function(t,e,i){var n=i("c86c");e=n(!1),e.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */[data-v-fc8ba5c0]:export{theme_bg:#e93323}uni-page-body[data-v-fc8ba5c0]{background-color:#fff}body.?%PAGE?%[data-v-fc8ba5c0]{background-color:#fff}.u-loadmore[data-v-fc8ba5c0]{margin:%?60?% auto 0 auto}.u-loadmore-tips[data-v-fc8ba5c0]{background-color:#fff!important}.m-endorsementBox[data-v-fc8ba5c0]{width:100%;overflow:hidden;height:%?240?%;position:relative;text-align:center}.m-endorsementBox-bg[data-v-fc8ba5c0]{height:%?940?%;width:%?940?%;background-color:#1e1c1d;-webkit-border-radius:%?940?%/%?320?%;border-radius:%?940?%/%?320?%;margin:0 auto;margin-top:%?-700?%;margin-left:%?-100?%;position:relative}.m-userInfo-bg[data-v-fc8ba5c0]{bottom:%?20?%;width:%?400?%}.m-userInfo[data-v-fc8ba5c0]{z-index:2;position:relative;display:inline-block;position:relative;top:%?-180?%}.m-userInfo .m-photo[data-v-fc8ba5c0]{width:%?124?%;height:%?124?%;float:left}.m-photo uni-image[data-v-fc8ba5c0]{width:100%;height:100%;border-radius:100%}.m-text[data-v-fc8ba5c0]{float:left;text-align:left;line-height:%?40?%;margin-top:%?22?%;margin-left:%?20?%;width:%?360?%}.m-text[data-v-fc8ba5c0]{font-size:%?24?%;color:#eebe8c}.m-text uni-label[data-v-fc8ba5c0]{font-size:%?32?%;font-weight:700}.m-text uni-text[data-v-fc8ba5c0]{display:block}.m-product-info[data-v-fc8ba5c0]::before{content:"";border:none}.u-btn[data-v-fc8ba5c0]{margin-top:%?80?%}.u-tip-text[data-v-fc8ba5c0]{display:block;font-size:%?28?%;text-align:center;color:#e93323}.m-product-price .u-btn[data-v-fc8ba5c0]{margin:0;float:right;width:%?160?%;height:%?60?%;font-size:%?24?%;margin-right:%?20?%;margin-top:%?-30?%}.QRCodeBOx[data-v-fc8ba5c0]{width:%?400?%;height:%?400?%;display:block;margin:%?40?% auto}.u-tap-btn[data-v-fc8ba5c0]{position:fixed;right:%?20?%;bottom:%?150?%}.u-go-home[data-v-fc8ba5c0]{border-radius:100%;width:%?80?%;height:%?80?%;border:1px solid #eee;font-size:%?20?%;text-align:center;background-color:#fff;box-shadow:0 %?4?% %?8?% rgba(0,0,0,.35);z-index:2;opacity:.8;line-height:%?80?%;margin-bottom:%?20?%}.u-go-home .iconfont[data-v-fc8ba5c0]{font-size:%?40?%}.m-text-box[data-v-fc8ba5c0]{font-size:%?24?%;box-sizing:border-box;padding:%?20?%;color:#888;line-height:%?40?%}.m-text-box uni-label[data-v-fc8ba5c0]{padding-right:%?10?%}.m-text-box1[data-v-fc8ba5c0]{box-sizing:border-box;padding:%?20?% %?36?%;color:#888;line-height:%?40?%}.m-text-box1 uni-label[data-v-fc8ba5c0]{padding-right:%?10?%;font-size:%?24?%}.m-msk[data-v-fc8ba5c0]{position:fixed;width:100%;height:100%;background-color:rgba(0,0,0,.5);top:0;left:0;z-index:10}.m-msk uni-image[data-v-fc8ba5c0]{position:fixed;right:%?30?%;top:0;width:%?300?%}',""]),t.exports=e},"40ed":function(t,e,i){"use strict";i.d(e,"b",(function(){return o})),i.d(e,"c",(function(){return a})),i.d(e,"a",(function(){return n}));var n={lPainter:i("dce2").default},o=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("v-uni-view",[t.CashData.isShowDistributionButton||t.isShowBtn?i("v-uni-view",[i("v-uni-view",{staticClass:"m-endorsementBox"},[i("v-uni-view",{staticClass:"m-endorsementBox-bg"}),i("v-uni-view",{staticClass:"m-userInfo"},[i("v-uni-view",{staticClass:"m-photo"},[i("v-uni-image",{attrs:{"lazy-load":!0,src:t.userInfo.user_avatar}})],1),i("v-uni-view",{staticClass:"m-text"},[i("v-uni-label",[t._v(t._s(t.sprintf(t.__("我是%s"),t.userInfo.user_nickname)))]),i("v-uni-text",[t._v(t._s(t.sprintf(t.__("我为%s代言"),t.plantformInfo.site_name||"-")))])],1)],1)],1),null!=t.ProductInfo?i("v-uni-view",{staticClass:"u-toptip"},[i("v-uni-view",{staticClass:"u-loadmore u-loadmore-line"},[i("v-uni-text",{staticClass:"u-loadmore-tips"},[t._v(t._s(t.__("为您推荐")))])],1)],1):t._e(),null!=t.ProductInfo?i("v-uni-view",{staticClass:"m-product-list"},[i("v-uni-navigator",{staticClass:"m-product-item",attrs:{"open-type":"redirect",url:t.ProductUrl+"?pid="+t.ProductInfo.pid+"&uid="+t.userInfo.user_id}},[i("v-uni-view",{staticClass:"m-product-img"},[i("v-uni-image",{attrs:{"lazy-load":!0,src:t.ProductInfo.ppic,mode:"aspectFill"}})],1),i("v-uni-view",{staticClass:"m-product-info"},[i("v-uni-view",{staticClass:"m-product-name"},[i("v-uni-label",[t._v(t._s(t.ProductInfo.pname))])],1),i("v-uni-view",{staticClass:"m-product-price"},[i("v-uni-label",[t._v(t._s(t.__("￥")))]),t._v(t._s(t.ProductInfo.pprice)),i("v-uni-button",{staticClass:"u-btn u-btn-default"},[t._v(t._s(t.buyname))])],1)],1)],1)],1):t._e(),i("v-uni-view",{on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.previewImg.apply(void 0,arguments)}}},[null==t.ProductInfo?i("v-uni-image",{staticClass:"QRCodeBOx",attrs:{"lazy-load":!0,src:t.invite_qrcode,mode:"widthFix"}}):t._e()],1),t.RuleEnabled&&0==t.flag?i("v-uni-view",{staticClass:"m-text-box"},[i("v-uni-view",{on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.showTip.apply(void 0,arguments)}}},[i("v-uni-label",{staticClass:"iconfont icon-14052218"}),t._v(t._s(t.__("推广规则")))],1),i("v-uni-view",[i("v-uni-view",[t._v(t._s(t.sprintf(t.__("当你的粉丝在店铺内支付订单后，订单中的所有商品，都有相应的收益比例：一级粉丝 %s%% ，二级粉丝 %s%% 。将支付订单金额和收益比例相乘后，累计计算出你此单的总收益。* 注： - 不计算收益的内容：运费、优惠券、关税，以及收益比例为0的商品；"),t.plantformInfo.plantform_fx_cps_rate_1,t.plantformInfo.plantform_fx_cps_rate_2))+"\n          "+t._s(t.__(" - 如果发生了退货退款，需要按照实际退货数量对收益进行相应的扣除；- 按照国家法律规定，当你的月累计收入达到相关税金起征点后，商家会为你代缴税款，你的收益将是税后收入；")))])],1)],1):i("v-uni-view",{staticClass:"m-text-box"},[i("v-uni-view",{on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.showTip.apply(void 0,arguments)}}},[i("v-uni-label",{staticClass:"iconfont icon-14052218"}),t._v(t._s(t.__("推广规则")))],1),i("v-uni-view",[i("v-uni-view",[t._v(t._s(t.__("成功邀请一位好友，可获积分奖励："))),i("v-uni-label",{staticStyle:{color:"red","padding-left":"10rpx"}},[t._v(t._s(t.plantformInfo.plantform_fx_gift_point))]),t._v(t._s(t.__("枚"))+",\n          "+t._s(t.__("当好友产生消费时，再获得相应佣金奖励")))],1)],1)],1),t.RuleEnabled&&t.flag?i("v-uni-view",{staticClass:"m-text-box1"},[i("v-uni-rich-text",{attrs:{nodes:t.htmlString}})],1):t._e(),null==t.ProductInfo&&0==t.isShowBtn&&t.CashData.isShowDistributionButton?i("v-uni-button",{staticClass:"u-btn u-btn-default",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.onShareBox.apply(void 0,arguments)}}},[t._v(t._s(t.__("分享赚钱")))]):t._e(),i("v-uni-view",{staticClass:"u-tap-btn"},[i("v-uni-navigator",{staticClass:"u-go-home",attrs:{url:"/pages/index/index","open-type":"switchTab"}},[i("v-uni-view",{staticClass:"iconfont icon-shouyeshouye"})],1)],1),t.isShowMag?i("v-uni-view",{staticClass:"m-msk",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.closeMsk.apply(void 0,arguments)}}},[i("v-uni-image",{attrs:{"lazy-load":!0,mode:"widthFix",src:t.plantformInfo.site_mobile_logo+"share_tips.png"}})],1):t._e()],1):t._e(),0==t.CashData.isShowDistributionButton&&0==t.isShowBtn?i("v-uni-navigator",{staticClass:"m-nullpage",attrs:{url:"/pages/index/index","open-type":"switchTab"}},[i("v-uni-view",{staticClass:"m-nullpage-middle"},[i("v-uni-view",{staticClass:"m-null-tip"},[i("v-uni-text",[t._v(t._s(t.__("对不起，您现在还没有分销权限")))]),i("v-uni-text",[t._v(t._s(t.__("多多购物有助于获取权限哦。")))])],1)],1)],1):t._e(),i("share-box-app",{ref:"shareBoxApp",attrs:{shareDataDefault:t.shareData}}),i("share-box-mp",{ref:"shareBoxMp",attrs:{shareDataDefault:t.shareData},on:{cancelShare:function(e){arguments[0]=e=t.$handleEvent(e),t.cancelShare.apply(void 0,arguments)},showCodeImg:function(e){arguments[0]=e=t.$handleEvent(e),t.showCodeImg.apply(void 0,arguments)},shareQRCode:function(e){arguments[0]=e=t.$handleEvent(e),t.shareQRCode.apply(void 0,arguments)},saveImg:function(e){arguments[0]=e=t.$handleEvent(e),t.saveImg.apply(void 0,arguments)}}}),i("l-painter",{ref:"painter",attrs:{useCORS:!0,isCanvasToTempFilePath:!0,"path-type":"url","custom-style":"position: fixed; left: 200%"},on:{success:function(e){arguments[0]=e=t.$handleEvent(e),t.createPoster(e)}}})],1)},a=[]},"67cf":function(t,e,i){var n=i("35b4");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var o=i("967d").default;o("24d71936",n,!0,{sourceMap:!1,shadowMode:!1})},db29:function(t,e,i){"use strict";i.r(e);var n=i("40ed"),o=i("30f1");for(var a in o)["default"].indexOf(a)<0&&function(t){i.d(e,t,(function(){return o[t]}))}(a);i("0441");var s=i("828b"),r=Object(s["a"])(o["default"],n["b"],n["c"],!1,null,"fc8ba5c0",null,!1,n["a"],void 0);e["default"]=r.exports}}]);