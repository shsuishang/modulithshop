(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chain-chain-favorabledetail"],{2798:function(t,e,a){"use strict";a.r(e);var i=a("4b4f"),n=a.n(i);for(var o in i)["default"].indexOf(o)<0&&function(t){a.d(e,t,(function(){return i[t]}))}(o);e["default"]=n.a},"4b4f":function(t,e,a){"use strict";a("7a82");var i=a("4ea4").default;Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var n=i(a("5530")),o=a("26cb"),s={data:function(){return{info:{},deduction:0,showRPK:!1,ActivityGroupId:0,maxRPK:0,order_id:"",isshow1:!1,Currency:"￥"}},components:{},computed:(0,o.mapState)(["Config","StateCode","notice","plantformInfo","shopInfo","userInfo","hasLogin"]),onLoad:function(t){uni.setNavigationBarTitle({title:this.__("优惠买单")});this.setData({order_id:t.order_id||""}),this.load()},onUnload:function(){},methods:(0,n.default)((0,n.default)({},(0,o.mapMutations)(["login","logout","getPlantformInfo","forceUserInfo","getUserInfo"])),{},{load:function(){var t=this;t.$.request({type:"post",url:this.Config.URL.pay.consume_trade_detail,data:{order_id:t.order_id},dataType:"json",success:function(e,a,i,n){t.setData({info:e,Currency:e.currency_symbol_left}),t.setData({isshow1:!0}),t.inputVal()}})},shareQRCode:function(t){var e=this,a={store_id:app.globalData.VendorInfo.Id,sponsorId:app.globalData.UserInfo.Id,imageUrl:"https://static.shopsuite.cn/xcxfile/appicon/shareImg.png",path:"pages/redpacket/redpacket?g="+this.data.ActivityGroupId+"&n="+this.data.maxRPK+"&uid="+app.globalData.UserInfo.Id,luckyOrder:this.data.maxRPK};$.xsr($.makeUrl(orderapi.ShareLuckyRedPacket,a),(function(t){e.setData({PageQRCodeInfo:{Path:t.Info,IsShare:!0,IsShareBox:!1,IsJT:!0}})}))},shareBox:function(){this.setData({PageQRCodeInfo:{Path:"",IsShare:!0,IsShareBox:!0,IsJT:!1}})},cancelShare:function(){this.setData({PageQRCodeInfo:{Path:"",IsShare:!1,IsShareBox:!1,IsJT:!1}})},saveImg:function(){var t=this;$.loading(),wx.downloadFile({url:this.data.PageQRCodeInfo.Path,success:function(e){$.hideloading(),wx.saveImageToPhotosAlbum({filePath:e.tempFilePath,success:function(){t.setData({PageQRCodeInfo:{Path:"",IsShare:!1,IsShareBox:!1,IsJT:!1}}),$.alert("保存图片成功！"),$.xsr1($.makeUrl(orderapi.ShareCount,{sponsorId:app.globalData.UserInfo.Id,audienceType:3,audienceId:0,ContentType:22,contentId:t.data.ActivityGroupId}))},fail:function(t){$.hideloading(),console.log("保存图片失败：",t)}})},fail:function(t){$.hideloading()}})},showCodeImg:function(){wx.previewImage({current:this.data.PageQRCodeInfo.Path,urls:[this.data.PageQRCodeInfo.Path]})},IsShowRPK:function(){var t=this;t.data.showRPK?setTimeout((function(){t.setData({showRPK:!1,type:1})}),250):t.setData({showRPK:!0,type:1})}})};e.default=s},"6bd1":function(t,e,a){"use strict";var i=a("bbcf"),n=a.n(i);n.a},"725c":function(t,e,a){var i=a("24fb");e=i(!1),e.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */[data-v-4fee706c]:export{theme_bg:#e93323}.paysuccess[data-v-4fee706c]{height:%?160?%;margin-left:%?30?%;display:flex;align-items:center;font-size:%?36?%;color:#000}.content[data-v-4fee706c]{height:%?400?%;background:#fff;padding:0 %?30?%}.store[data-v-4fee706c]{font-size:%?28?%;color:#000;height:%?80?%;border-bottom:1px solid #d9d9d9;line-height:%?90?%}.details[data-v-4fee706c]{display:flex;font-size:%?24?%;color:#000}.details-left[data-v-4fee706c]{width:35%}.details-left uni-view[data-v-4fee706c]{margin-top:%?40?%}.details-right[data-v-4fee706c]{width:65%}.details-right uni-view[data-v-4fee706c]{margin-top:%?40?%}.m-footer-btn[data-v-4fee706c]{width:100%;position:fixed;bottom:0;left:0;background-color:#fff;text-align:right;border-top:%?1?% solid #d5d5d5;box-sizing:border-box;padding:%?20?%}.u-link-btn[data-v-4fee706c]{margin:0 %?10?%;vertical-align:middle;display:inline-block;line-height:%?40?%}.move-area[data-v-4fee706c]{position:absolute;min-height:100%;width:100%}.move-img[data-v-4fee706c]{height:%?126?%;width:%?100?%;z-index:5;margin-top:70%;margin-left:%?650?%}.move-img uni-image[data-v-4fee706c]{height:%?126?%;width:%?100?%}.shareRPK[data-v-4fee706c]{position:fixed;width:100%;height:100%;background-color:rgba(0,0,0,.5);top:0;left:0;z-index:5}.shareRPKBox[data-v-4fee706c]{width:%?640?%;height:%?698?%;background-color:red;position:absolute;top:40%;left:50%;margin-left:%?-320?%;margin-top:%?-349?%}.sendRPK[data-v-4fee706c]{width:%?222?%;height:%?70?%;position:absolute;left:50%;margin-left:%?-100?%;top:50%;margin-top:%?140?%}.closeRPK[data-v-4fee706c]{width:%?50?%;height:%?50?%;position:absolute;left:50%;margin-left:%?-22?%;bottom:%?25?%}.shareCodeImg[data-v-4fee706c]{height:%?800?%}.u-back2 uni-image[data-v-4fee706c]{border-radius:100%;width:%?77?%;height:%?77?%;border:1px solid #eee;font-size:%?20?%;text-align:center;background-color:#fff;box-shadow:0 %?4?% %?8?% rgba(0,0,0,.35);z-index:999;opacity:.8;line-height:%?77?%;margin-bottom:%?20?%}',""]),t.exports=e},"923b":function(t,e,a){"use strict";a.d(e,"b",(function(){return i})),a.d(e,"c",(function(){return n})),a.d(e,"a",(function(){}));var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-uni-view",{staticClass:"page"},[a("v-uni-movable-area",{staticClass:"move-area"},[t.info.IsRedPacketIcon?a("v-uni-movable-view",{staticClass:"move-img",attrs:{inertia:!0,direction:"all"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.IsShowRPK.apply(void 0,arguments)}}},[a("v-uni-image",{attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/rpk_min.png"}})],1):t._e(),a("v-uni-view",{staticStyle:{background:"#f8f8f8"}},[a("v-uni-view",{staticClass:"paysuccess"},[a("v-uni-label",[a("v-uni-image",{staticStyle:{width:"40rpx",height:"40rpx","margin-top":"8rpx"},attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/images/true.png"}})],1),a("v-uni-text",{staticStyle:{"margin-left":"10rpx"}},[t._v(t._s(t.info.trade_is_paid==t.StateCode.ORDER_PAID_STATE_YES?t.__("已支付"):t.__("待付款")))])],1),a("v-uni-view",{staticClass:"content"},[a("v-uni-view",{staticClass:"store"},[t._v(t._s(t.info.store_name))]),a("v-uni-view",{staticClass:"details"},[a("v-uni-view",{staticClass:"details-left"},[a("v-uni-view",{staticClass:"charge"},[a("v-uni-text",[t._v(t._s(t.__("消费金额"))+"："),a("v-uni-text",{staticStyle:{color:"red"}},[t._v(t._s(t.Currency)+t._s(t.info.order_payment_amount))])],1)],1),a("v-uni-view",{staticClass:"pay"},[a("v-uni-text",[t._v(t._s(t.__("实际支付"))+"："),a("v-uni-text",{staticStyle:{color:"red"}},[t._v(t._s(t.Currency)+t._s(t.info.trade_amount))])],1)],1),a("v-uni-view",{staticClass:"monetary"},[a("v-uni-text",[t._v(t._s(t.__("优惠金额"))+"："),a("v-uni-text",{staticStyle:{color:"red"}},[t._v(t._s(t.Currency)+t._s(t.info.trade_discount))])],1)],1)],1),a("v-uni-view",{staticClass:"details-right"},[a("v-uni-view",{staticClass:"num"},[a("v-uni-text",[t._v(t._s(t.__("订单编号"))+"："+t._s(t.info.order_id))])],1),a("v-uni-view",{staticClass:"time"},[a("v-uni-text",[t._v(t._s(t.__("消费时间"))+"："+t._s(t.info.trade_create_time))])],1),a("v-uni-view",{staticClass:"monetary"},[a("v-uni-text",[t._v(t._s(t.__("余额抵扣"))+"："),a("v-uni-text",{staticStyle:{color:"red"}},[t._v(t._s(t.Currency)+t._s(t.info.trade_payment_money))])],1)],1)],1)],1)],1)],1)],1),t.showRPK&&t.info.IsRedPacketIcon&&1==t.type?a("v-uni-view",{staticClass:"shareRPK ",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.IsShowRPK.apply(void 0,arguments)}}},[a("v-uni-view",{class:"animated bounceIn",staticStyle:{background:"url(https://static.shopsuite.cn/xcxfile/appicon/rpk_box.png) no-repeat","background-size":"cover"},attrs:{catchtap:!0}},[a("v-uni-view",{staticClass:"sendRPK",attrs:{catchtap:"shareBox"}}),a("v-uni-view",{staticClass:"closeRPK",attrs:{catchtap:"IsShowRPK"}})],1)],1):t._e(),a("v-uni-view",{staticClass:"u-top-default"},[a("v-uni-navigator",{staticClass:"u-back2",attrs:{url:"/pages/index/index","open-type":"switchTab"}},[a("v-uni-image",{attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/img/gohome.png"}})],1)],1)],1)},n=[]},bbcf:function(t,e,a){var i=a("725c");i.__esModule&&(i=i.default),"string"===typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);var n=a("4f06").default;n("27a89669",i,!0,{sourceMap:!1,shadowMode:!1})},da09:function(t,e,a){"use strict";a.r(e);var i=a("923b"),n=a("2798");for(var o in n)["default"].indexOf(o)<0&&function(t){a.d(e,t,(function(){return n[t]}))}(o);a("6bd1");var s=a("f0c5"),r=Object(s["a"])(n["default"],i["b"],i["c"],!1,null,"4fee706c",null,!1,i["a"],void 0);e["default"]=r.exports}}]);