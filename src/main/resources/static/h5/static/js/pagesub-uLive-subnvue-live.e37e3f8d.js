(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pagesub-uLive-subnvue-live"],{"0350":function(t,i,e){"use strict";e.d(i,"b",(function(){return a})),e.d(i,"c",(function(){return n})),e.d(i,"a",(function(){}));var a=function(){var t=this,i=t.$createElement,e=t._self._c||i;return e("div",{staticClass:"nlv__container"},[e("v-uni-swiper",{staticClass:"nlv-swiper",staticStyle:{height:"100%"},attrs:{current:t.tabIndex,duration:300,"indicator-dots":!1,vertical:!1},on:{change:function(i){arguments[0]=i=t.$handleEvent(i),t.changeTab.apply(void 0,arguments)}}},[e("v-uni-swiper-item",[e("v-uni-view",{staticClass:"nlv_main"},[e("v-uni-swiper",{staticClass:"nlv-swiper",attrs:{"indicator-dots":!1,vertical:!0,current:t.videoIndex},on:{change:function(i){arguments[0]=i=t.$handleEvent(i),t.handleSlider.apply(void 0,arguments)}}},t._l(t.vlist,(function(i,a){return e("v-uni-swiper-item",{key:a},[e("v-uni-view",{staticClass:"nlv-playerbox"},[e("v-uni-video",{ref:"myVideo"+a,refInFor:!0,staticClass:"player-video",style:{height:t.winHeight,width:t.winWidth},attrs:{id:"myVideo"+a,src:i.online?i.src:i.room_video,autoplay:a==t.videoIndex&&0==t.tabIndex,controls:!1,loop:!0,"show-center-play-btn":!1,objectFit:"fill"}}),e("v-uni-view",{staticClass:"nlv_topbar",style:{height:t.headerBarH,"padding-top":t.statusBarH}},[e("v-uni-view",{staticClass:"topbar-info",staticStyle:{width:"90%"}},[e("v-uni-view",{staticClass:"avator-box"},[e("v-uni-image",{staticClass:"ta-avator",attrs:{src:i.user_avatar,mode:"aspectFill"}}),e("v-uni-view",{staticClass:"tabox"},[e("v-uni-text",{staticClass:"ta-name"},[t._v(t._s(i.user_nickname))])],1),e("v-uni-text",{staticClass:"ta-gz",class:i.is_follow?"ta-gz-on":"",on:{click:function(i){arguments[0]=i=t.$handleEvent(i),t.handleFollow(a)}}},[t._v(t._s(i.is_follow?t.__("已关注"):t.__("关注")))])],1),t._e(),e("v-uni-text",{staticClass:"member-num"},[t._v(t._s(i.friend_num))]),e("v-uni-text",{staticClass:"member-num"},[t._v(t._s(t.__("观众："))+t._s(i.live_click_num))])],1),e("v-uni-view",{staticClass:"topbar-right"},[e("v-uni-text",{staticClass:"topbar_ico iconfont",style:{fontFamily:"zc"},on:{click:function(i){arguments[0]=i=t.$handleEvent(i),t.GoBack.apply(void 0,arguments)}}},[t._v("")])],1)],1),e("v-uni-view",{staticClass:"nlv-rankbox",style:{top:t.headerBarH}},[e("v-uni-view",{staticClass:"nlv-rkls"},[e("v-uni-text",{staticClass:"rkitem",on:{click:function(i){arguments[0]=i=t.$handleEvent(i),t.tapTap.apply(void 0,arguments)}}},[t._v(t._s(t.__("更多直播")))])],1),e("v-uni-text",{staticClass:"nlv-uid"},[t._v(t._s(t.__("U直播"))+":"+t._s(i.user_id))])],1),e("v-uni-view",{staticClass:"nlv-footToolbar"},[t._e(),e("v-uni-scroll-view",{staticClass:"nlv-rollMsgPanel",attrs:{"scroll-y":!0,"show-scrollbar":"false"}},[t._l(i.rollmsg,(function(i,a){return[e("v-uni-view",{key:a+"_0",staticClass:"nlv-msglist"},[e("v-uni-view",{staticClass:"msg_bg"},[e("v-uni-text",{staticClass:"msg_name"},[t._v(t._s(i.uname))]),e("v-uni-text",{staticClass:"msg_text"},[t._v(t._s(i.content))])],1)],1)]}))],2),e("v-uni-view",{staticClass:"nlv-infobox"},[e("v-uni-view",{staticClass:"nlv_reply",on:{click:function(i){arguments[0]=i=t.$handleEvent(i),t.handleRollMsg(a)}}},[e("v-uni-text",{staticClass:"nlv_reply_text"},[t._v(t._s(t.__("说点什么...")))])],1),e("v-uni-view",{staticClass:"nlv_btntool"},[t._e(),i.cart.length?e("v-uni-view",{staticClass:"btn-toolitem",on:{click:function(i){arguments[0]=i=t.$handleEvent(i),t.handleLiveCart(a)}}},[e("v-uni-text",{staticClass:"iconfont i-btntool",staticStyle:{color:"#ff4e0e","font-size":"50rpx"}},[t._v("")])],1):t._e(),t._e(),t._e()],1)],1)],1)],1)],1)})),1)],1)],1),e("v-uni-swiper-item",[e("v-uni-scroll-view",{staticStyle:{"z-index":"9999",height:"100%"},attrs:{"scroll-y":!0},on:{scrolltolower:function(i){arguments[0]=i=t.$handleEvent(i),t.scrolltolower.apply(void 0,arguments)}}},[e("v-uni-view",{staticClass:"row-space-around"},t._l(t.liveList,(function(i,a){return e("v-uni-view",{key:a,staticClass:"bg-gray-help margin-sm padding-lr-lg padding-tb-sm radius-xs",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.goLive(i.room_id,i.room_state)}}},[i.room_poster?e("v-uni-image",{staticStyle:{width:"344rpx"},attrs:{"lazy-load":!0,src:i.room_poster}}):t._e(),i.room_state?e("v-uni-view",{staticClass:"lv-status flexbox flex_alignc"},[e("v-uni-text",{staticClass:"dot"}),e("v-uni-text",{staticStyle:{color:"#fff","font-size":"18rpx"}},[t._v(t._s(t.__("直播中")))])],1):e("v-uni-view",{staticClass:"lv-status flexbox flex_alignc"},[e("v-uni-text",{staticClass:"dot dot-gray"}),e("v-uni-text",{staticStyle:{color:"#fff","font-size":"24rpx"}},[t._v(t._s(t.__("离线中")))])],1),e("v-uni-view",{staticClass:"item-info"},[e("v-uni-image",{staticClass:"info-avatar",attrs:{src:i.user_avatar,mode:"aspectFill"}})],1),e("v-uni-view",{staticClass:"live-room-name"},[e("v-uni-text",{staticClass:"room-name"},[t._v(t._s(i.room_name||""))])],1)],1)})),1)],1)],1)],1),e("live-cart",{ref:"liveCart",attrs:{vlist:t.vlist},on:{confirm:function(i){arguments[0]=i=t.$handleEvent(i),t.confirm.apply(void 0,arguments)}}}),e("roll-msg",{ref:"rollMsg",attrs:{vlist:t.vlist,anchor:t.anchor},on:{confirm:function(i){arguments[0]=i=t.$handleEvent(i),t.confirm.apply(void 0,arguments)}}}),e("live-gift",{ref:"liveGift"})],1)},n=[]},"1b27":function(t,i,e){"use strict";e("6a54");var a=e("f5bd").default;Object.defineProperty(i,"__esModule",{value:!0}),i.default=void 0,e("aa9c"),e("c223"),e("e966"),e("bf0f");var n=a(e("9b1b")),o=a(e("2634")),s=a(e("2fdc")),r=a(e("b8a6")),l=e("8f59"),d=a(e("e093")),v=a(e("9600")),c=a(e("7b27")),u=(e("669e"),{data:function(){return{statusBarH:"",headerBarH:"",headerBarH_1:"",winHeight:"",winWidth:"",videoIndex:0,room_id:0,vlist:[],anchor:{},clickNum:0,tabIndex:0,page_index:1,ispage_index:!0,flag_index:!0,liveList:[],videoclass:!0,isShow:!0,ispause:!0,scrollTop:0,old:{scrollTop:0},isdata:!1,page:1,rows:10,ispage:!0,flag:!0,shareData:{shareText:"随商商城系统，支持原生App、微信小程序，邀请你一起体验！",shareTitle:"随商商城系统，支持原生App、微信小程序，邀请你一起体验！",href:"https://www.suteshop.com",image:"",price:0}}},computed:(0,l.mapState)(["Config","StateCode","notice","plantformInfo","shopInfo","userInfo","hasLogin","__","$","sprintf"]),components:{liveCart:d.default,rollMsg:v.default,liveGift:c.default,shareBoxApp:r.default},beforeCreate:function(){},created:function(){var t=uni.getSystemInfoSync().statusBarHeight,i=t+50,e=uni.getSystemInfoSync().windowHeight,a=uni.getSystemInfoSync().windowWidth;this.statusBarH="".concat(t,"px"),this.headerBarH="".concat(i,"px"),this.winHeight="".concat(e,"px"),this.winWidth="".concat(a,"px"),this.headerBarH_1="".concat(i+20,"px")},onLoad:function(t){this.room_id=t.room_id?t.room_id:0,this.getLiveRome()},onReady:function(){},mounted:function(){var t=this;return(0,s.default)((0,o.default)().mark((function i(){return(0,o.default)().wrap((function(i){while(1)switch(i.prev=i.next){case 0:return i.next=2,t.getLiveList();case 2:setTimeout((function(){t.init()}),200);case 3:case"end":return i.stop()}}),i)})))()},onShareAppMessage:function(){return{imageUrl:this.vlist[this.videoIndex].user_avatar,title:this.sprintf(this.__("%s 的直播间"),this.vlist[this.videoIndex].user_nickname),path:"/pagesub/uLive/subnvue/live?room_id="+this.vlist[this.videoIndex].room_id+"&uid="+this.userInfo.user_id}},onUnload:function(){},onBackPress:function(){},onReachBottom:function(){},methods:(0,n.default)((0,n.default)({},(0,l.mapMutations)(["logout","getPlantformInfo","forceUserInfo","getStoreInfo"])),{},{init:function(){this.videoContextList=[];for(var t=0;t<this.vlist.length;t++)this.vlist[t].online?this.videoContextList.push(uni.createLivePlayerContext("myVideo"+t,this)):this.videoContextList.push(uni.createVideoContext("myVideo"+t,this))},confirm:function(t,i){console.log(t),this.winHeight="".concat(t,"px"),this.isShow=!!i},changeTab:function(t){this.tabIndex=t.detail.current,1==t.detail.current?this.videoContextList[this.videoIndex].pause():this.videoContextList[this.videoIndex].play()},goLive:function(t,i){i?uni.navigateTo({url:"/pagesub/uLive/subnvue/live?room_id="+t}):this.$.alert(this.__("主播离线中"),(function(){uni.navigateTo({url:"/pagesub/uLive/subnvue/live?room_id="+t})}))},tapTap:function(){console.log(2222),this.tabIndex=1},getLiveRome:function(){var t=this,i={page:this.page_index,rand:1,room_id:this.room_id};this.$.request({url:t.Config.URL.live.index,data:i,success:function(i,e,a,n){if(200==e)if(i.items.length>0){for(var o=0;o<i.items.length;o++);i.page>=i.total?(t.liveList=t.liveList.concat(i.items),t.flag_index=!1,t.ispage_index=!1):(t.liveList=t.liveList.concat(i.items),t.flag_index=!0,t.ispage_index=!0)}else t.flag_index=!1,t.ispage_index=!1}})},scroll:function(t){this.old.scrollTop=t.detail.scrollTop+100},scrolltolower:function(){if(this.flag_index){var t=this;t.flag_index=!1,clearTimeout(i);var i=setTimeout((function(){t.page_index=parseInt(t.page_index)+1,t.getLiveRome()}),500)}},getLiveList:function(){var t=this,i=new Promise((function(i,e){var a=t,n={page:t.page};n["room_id"]=a.room_id,t.$.request({url:t.Config.URL.live.index,data:n,success:function(t,e,n,o){if(t.items.length>0){for(var s=0;s<t.items.length;s++);t.page>=t.total?(a.vlist=a.vlist.concat(t.items),a.flag=!1,a.ispage=!1):(a.vlist=a.vlist.concat(t.items),a.flag=!0,a.ispage=!0),i()}else a.flag=!1,a.ispage=!1;a.ispage=!0,a.anchor={puid:a.vlist[a.videoIndex].puid,zid:a.vlist[a.videoIndex].zid,friend_id:a.vlist[a.videoIndex].user_id,user_id:a.vlist[a.videoIndex].user_id,name:a.vlist[a.videoIndex].user_nickname,avatar:a.vlist[a.videoIndex].user_avatar,type:"group"}}})}));return i},handleSlider:function(t){var i=t.detail.current;this.videoIndex>=0&&(this.videoContextList[this.videoIndex].pause(),"function"==typeof this.videoContextList[this.videoIndex].seek&&this.videoContextList[this.videoIndex].seek(0)),i===this.videoIndex+1?this.videoContextList[this.videoIndex+1].play():i===this.videoIndex-1&&this.videoContextList[this.videoIndex-1].play(),this.videoIndex=i;this.anchor={puid:this.vlist[this.videoIndex].puid,zid:this.vlist[this.videoIndex].zid,friend_id:this.vlist[this.videoIndex].user_id,user_id:this.vlist[this.videoIndex].user_id,name:this.vlist[this.videoIndex].user_nickname,avatar:this.vlist[this.videoIndex].user_avatar,type:"group"};var e=this.sprintf(this.__("%s 的直播间"),this.vlist[this.videoIndex].user_nickname),a=this.vlist[this.videoIndex].room_name,n=this.$.sprintf("%s/h5/pagesub/uLive/subnvue/live?room_id=%d&uid=%d",this.Config.SiteUrl,this.vlist[this.videoIndex].room_id,this.userInfo.user_id),o=this.vlist[this.videoIndex].user_avatar;this.$.wxShare(e,a,n,o)},play:function(t){this.videoContextList[t].play()},pause:function(t){this.videoContextList[t].pause()},handleFollow:function(t){var i=this.vlist;i[t].is_follow=!i[t].is_follow,this.vlist=i;var e={friend_id:i[t].user_id};i[t].is_follow?this.$.request({url:this.Config.URL.user.friend_agree,data:e,success:function(t,i,e,a){}}):this.$.request({url:this.Config.URL.user.friend_refuse,data:e,success:function(t,i,e,a){}})},handleRollMsg:function(t){console.log(9999),this.$refs.rollMsg.show(t)},handleLiveCart:function(t){this.$refs.liveCart.show(t)},handleLiveGift:function(){this.$refs.liveGift.show()},GoBack:function(){uni.navigateBack()},goToItem:function(t){uni.navigateTo({url:"/pagesub/product/detail?id="+t})},onShareBox:function(t){var i=this.$.sprintf("%s/h5/pagesub/uLive/subnvue/live?room_id=%d&uid=%d",this.Config.SiteUrl,this.vlist[this.videoIndex].room_id,this.userInfo.user_id);this.shareData={shareTitle:this.sprintf(this.__("%s 的直播间"),this.vlist[this.videoIndex].user_nickname),shareText:this.vlist[this.videoIndex].room_name,href:i,image:this.vlist[this.videoIndex].user_avatar,price:0}}})});i.default=u},"40fb":function(t,i,e){var a=e("f0eb");a.__esModule&&(a=a.default),"string"===typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);var n=e("967d").default;n("aed222b2",a,!0,{sourceMap:!1,shadowMode:!1})},"5cc9":function(t,i,e){"use strict";var a=e("40fb"),n=e.n(a);n.a},"8d26":function(t,i,e){"use strict";e.r(i);var a=e("0350"),n=e("958d");for(var o in n)["default"].indexOf(o)<0&&function(t){e.d(i,t,(function(){return n[t]}))}(o);e("5cc9");var s=e("828b"),r=Object(s["a"])(n["default"],a["b"],a["c"],!1,null,"254640a5",null,!1,a["a"],void 0);i["default"]=r.exports},"958d":function(t,i,e){"use strict";e.r(i);var a=e("1b27"),n=e.n(a);for(var o in a)["default"].indexOf(o)<0&&function(t){e.d(i,t,(function(){return a[t]}))}(o);i["default"]=n.a},f0eb:function(t,i,e){var a=e("c86c");i=a(!1),i.push([t.i,".iconfont[data-v-254640a5]{\r\n\r\n\r\n\r\nfont-family:zc!important;font-size:%?46?%;font-style:normal;line-height:1;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale\n}.nlv__container[data-v-254640a5]{flex:1;height:100vh}.nlv_topbar[data-v-254640a5]{flex:1;flex-direction:row;align-items:center;position:absolute;left:0;right:0;top:0;z-index:1001}.topbar-info[data-v-254640a5]{flex:1;flex-direction:row;align-items:center;padding-left:%?20?%}.avator-box[data-v-254640a5]{flex-direction:row;align-items:center;padding-right:3px;height:30px}.ta-avator[data-v-254640a5]{border-radius:50%;margin-right:%?10?%;height:30px;width:30px;background-color:rgba(0,0,0,.4);border-radius:20px}.tabox[data-v-254640a5]{text-overflow:ellipsis;\r\n\t/* width: 150rpx; */background-color:rgba(0,0,0,.4);border-radius:20px;padding:0 12px}.ta-name[data-v-254640a5]{color:#fff;font-size:%?24?%;height:%?30?%;line-height:%?30?%}.ta-num[data-v-254640a5]{color:#fff;font-size:%?20?%;height:%?24?%;line-height:%?24?%}.ta-gz[data-v-254640a5]{background:#ff0f33;border-radius:20px;color:#fff;font-size:%?24?%;text-align:center;padding:4px 0;width:45px}.ta-gz-on[data-v-254640a5]{background:hsla(0,0%,100%,.2);background-image:none}.avator-scroll[data-v-254640a5]{\r\nwhite-space:nowrap;\r\nflex-direction:row;padding:0 %?10?%;height:30px;width:%?230?%}.member-num[data-v-254640a5]{background-color:rgba(0,0,0,.4);border-radius:20px;color:#fff;font-size:%?24?%;text-align:center;padding:5px 7px}.topbar-right[data-v-254640a5]{flex-direction:row}.topbar_ico[data-v-254640a5]{color:#fff;font-size:%?40?%;padding:%?15?%;position:relative;z-index:1001}.nlv-rankbox[data-v-254640a5]{position:absolute;left:0;right:0;z-index:1001}.nlv-rkls[data-v-254640a5]{flex-direction:row;align-items:center}.rkitem[data-v-254640a5]{background:rgba(255,56,166,.5);border-radius:20px;color:#fff;font-size:%?24?%;margin-left:%?20?%;padding:2px 5px}.nlv-uid[data-v-254640a5]{color:hsla(0,0%,100%,.5);font-size:%?28?%;position:absolute;right:%?20?%;top:0}.nlv_main[data-v-254640a5]{flex:1;height:100%}.nlv-swiper[data-v-254640a5]{flex:1}.nlv-playerbox[data-v-254640a5]{flex:1}.player-video[data-v-254640a5]{height:100%;width:100%}.player-video-r[data-v-254640a5]{height:%?300?%}.nlv-footToolbar[data-v-254640a5]{position:absolute;left:0;right:0;bottom:0}.nlv-giftTipPanel[data-v-254640a5]{flex-direction:row;margin-left:%?20?%;margin-bottom:%?20?%}.giftip-item[data-v-254640a5]{background:rgba(0,0,0,.3);border-radius:50px;padding:4px;padding-right:50px;flex-direction:row;align-items:center}.gt-avator[data-v-254640a5]{border-radius:50%;height:36px;width:36px}.gt-info[data-v-254640a5]{padding:0 %?20?%;overflow:hidden;width:%?230?%}.gt-tit[data-v-254640a5]{color:#fff;font-size:%?28?%;overflow:hidden;height:%?40?%;line-height:%?40?%}.gt-subtit[data-v-254640a5]{color:hsla(0,0%,100%,.7);font-size:%?24?%}.gt-gift[data-v-254640a5]{height:70px;width:70px;position:absolute;top:%?-30?%;right:-5px}.nlv-rollMsgPanel[data-v-254640a5]{margin-left:%?20?%;margin-bottom:%?20?%;height:%?400?%;width:%?500?%}.nlv-msglist[data-v-254640a5]{margin-top:%?10?%;flex-direction:column;align-items:flex-start}.msg_bg[data-v-254640a5]{background:rgba(0,0,0,.3);border-radius:12px;padding:4px 7px;flex-direction:row;flex-wrap:wrap}.msg_name[data-v-254640a5]{color:#fff81f;font-size:%?28?%}.msg_text[data-v-254640a5]{color:#fff;font-size:%?28?%}.nlv-infobox[data-v-254640a5]{padding:%?20?%;flex:1;flex-direction:row;align-items:center}.nlv_reply[data-v-254640a5]{flex:1;flex-direction:row;background:rgba(0,0,0,.3);border-radius:%?50?%;padding:%?23?%}.nlv_reply_text[data-v-254640a5]{color:#fff;font-size:%?30?%}.nlv_btntool[data-v-254640a5]{flex-direction:row;justify-content:flex-end}.btn-toolitem[data-v-254640a5]{background:rgba(0,0,0,.3);border-radius:50%;align-items:center;justify-content:center;margin-left:%?10?%;height:%?75?%;width:%?75?%}.btn-toolitem-cart[data-v-254640a5]{background:#f93b37}.i-btntool[data-v-254640a5]{color:#fff;font-size:%?40?%}\r\n\r\n\r\n/* 横向两端对齐,位于各行之前、之间、之后都留有空白的容器内 */.row-space-around[data-v-254640a5]{\r\ndisplay:flex;position:relative;\r\nflex-direction:row!important;\r\n\t/* justify-content: space-around !important; */flex-wrap:wrap!important;background:#f5f5f5}.radius-xs[data-v-254640a5]{padding:%?10?%;border-radius:%?10?%;background:#fff;margin:%?4?%}.lv-status[data-v-254640a5]{height:%?50?%;width:%?120?%;background-color:rgba(0,0,0,.4);border-radius:%?50?%;color:#fff;font-size:%?18?%;padding:%?4?% %?12?%;position:absolute;top:%?20?%;left:%?20?%;display:flex;flex-direction:row!important;\n}.dot-gray[data-v-254640a5]{background-color:#ababab;border-radius:50%;margin-right:%?4?%;margin-top:%?14?%;height:%?12?%;width:%?12?%;width:%?12?%;\n}.dot[data-v-254640a5]{background-color:#ababab;border-radius:50%;margin-right:%?4?%;margin-top:%?14?%;height:%?12?%;width:%?12?%;\n}.info-avatar[data-v-254640a5]{height:%?50?%;width:%?50?%;border-radius:%?50?%;position:absolute;bottom:%?30?%;left:%?20?%}.live-room-name[data-v-254640a5]{width:%?344?%;padding:%?10?% %?6?%}.room-name[data-v-254640a5]{overflow:hidden;text-overflow:ellipsis;lines:2;font-size:%?24?%}",""]),t.exports=i}}]);