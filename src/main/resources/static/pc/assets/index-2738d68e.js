import{Q as F,T as x,b as G,aa as H,ab as P,ac as U,ad as O,ae as V,af as z,ag as j,ah as J,ai as M,aj as Q,ak as W,al as X,g as q,e as K,u as Y,r as w,o as Z,f as ee,i as ne,t as ue,am as ie,n as v,p as s,v as d,x as u,y as n,z as a,D,A as f,P as ae,I as g,F as y,C as L,G as C,an as A,E as B,ao as se,J as _e,W as te,X as le}from"./index-78223f15.js";import{v as me}from"./loading-9148d0db.js";import{b as oe,d as de,E as re}from"./main-0e4d3339.js";/* empty css             */import{_ as ce}from"./_plugin-vue_export-helper-c27b6911.js";const fe=F({components:{LocationFilled:x,ArrowRight:G,House:H,Tickets:P,Goods:U,OfficeBuilding:O,Service:V,Document:z,Star:j,Coin:J,Edit:M,ChatLineRound:Q,CreditCard:W,Clock:X},setup(){const e=q(),l=K(),k=Y();let r=w(""),p=w("");l.name=="resetPassword"||l.name=="bindPhone"||l.name=="bindEmail"||l.name=="deliveryAddress"||l.name=="invoiceTitle"?r.value="userInformation":r.value=l.name,p.value=l.meta.title,Z((i,m)=>{i.name=="resetPassword"||i.name=="bindPhone"||i.name=="bindEmail"||i.name=="deliveryAddress"||i.name=="invoiceTitle"?r.value="userInformation":r.value=i.name,p.value=i.meta.title});let c=ee({widgets:[{menu_id:1,menu_parent_id:0,menu_name:"我的首页",menu_icon:"House",menu_type_id:1,menu_order:1,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/mine/index",menu_label:"userCenter",id:1,name:"我的首页",value:1,sub:[]},{menu_id:2,menu_parent_id:0,menu_name:"我的订单",menu_icon:"Tickets",menu_type_id:1,menu_order:2,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/order/index",menu_label:"orderList",id:2,name:"我的订单",value:2,sub:[]},{menu_id:3,menu_parent_id:0,menu_name:"售后订单",menu_icon:"Service",menu_type_id:1,menu_order:3,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/return/index",menu_label:"returnOrderList",id:3,name:"售后订单",value:3,sub:[]},{menu_id:4,menu_parent_id:0,menu_name:"我的拼团",menu_icon:"Goods",menu_type_id:1,menu_order:4,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/activity/listsUserGroupbooking",menu_label:"listsUserGroupbooking",id:4,name:"我的拼团",value:4,sub:[]},{menu_id:6,menu_parent_id:0,menu_name:"我的发票",menu_icon:"Document",menu_type_id:1,menu_order:6,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/invoice/lists",menu_label:"invoiceLists",id:6,name:"我的发票",value:6,sub:[]},{menu_id:7,menu_parent_id:0,menu_name:"我的收藏",menu_icon:"Star",menu_type_id:1,menu_order:7,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/favorites/item",menu_label:"favoritesItem",id:7,name:"我的收藏",value:7,sub:[]},{menu_id:9,menu_parent_id:0,menu_name:"浏览记录",menu_icon:"Clock",menu_type_id:1,menu_order:9,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/favorites/browser",menu_label:"userBrowser",id:9,name:"浏览记录",value:9,sub:[]},{menu_id:10,menu_parent_id:0,menu_name:"账户管理",menu_icon:"CreditCard",menu_type_id:1,menu_order:10,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/information/index",menu_label:"userInformation",id:10,name:"账户管理",value:10,sub:[]},{menu_id:11,menu_parent_id:0,menu_name:"企业信息",menu_icon:"OfficeBuilding",menu_type_id:1,menu_order:11,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/company/detail",menu_label:"companyDetail",id:11,name:"企业信息",value:11,sub:[]},{menu_id:12,menu_parent_id:0,menu_name:"消息中心",menu_icon:"ChatLineRound",menu_type_id:1,menu_order:12,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/message/lists",menu_label:"messageLists",id:12,name:"消息中心",value:12,sub:[]},{menu_id:13,menu_parent_id:0,menu_name:"我的积分",menu_icon:"Coin",menu_type_id:1,menu_order:13,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/resource/pointsHistory",menu_label:"pointsHistory",id:13,name:"我的积分",value:13,sub:[]},{menu_id:14,menu_parent_id:0,menu_name:"意见反馈",menu_icon:"Edit",menu_type_id:1,menu_order:14,menu_level:0,menu_is_leaf:0,menu_is_enable:1,menu_is_buildin:0,menu_url:"/user/feedback/lists",menu_label:"feedbackLists",id:14,name:"意见反馈",value:14,sub:[]}],loading:!1});const b=i=>{k.push({path:i})},_=()=>{ie({}).then(i=>{(!i.data||!e.info.b2b_enable)&&c.widgets.forEach(m=>{if(m.menu_id==11){let h=c.widgets.indexOf(m);c.widgets.splice(h,1)}})})};return ne(()=>{_()}),{...ue(c),routeName:r,getCompanyData:_,title:p,goTo:b}}});const I=e=>(te("data-v-3b2f0680"),e=e(),le(),e),pe={id:"page-container"},ve={class:"container"},be={class:"breadcrumb clearfix"},he={class:"space-icon"},ge={class:"space-icon"},ye={class:"cur"},Ce={key:0,class:"user-menu"},ke=["onClick"],Ee=I(()=>u("div",{class:"menu-bar"},null,-1)),we={class:"menu-icon"},De={class:"menu-name"},Le={class:"name"},Ae={class:"right-icon"},Be={key:0},Ie=["onClick"],$e=I(()=>u("div",{class:"menu-bar"},null,-1)),Te={class:"menu-sub-icon"},Ne=["src"],Re={class:"menu-name"},Se={class:"sub-name"},Fe={class:"right-icon"},xe={class:"right-container"};function Ge(e,l,k,r,p,c){const b=v("LocationFilled"),_=_e,i=v("router-link"),m=v("ArrowRight"),h=de,$=re,T=v("router-view"),E=oe,N=me;return s(),d("div",pe,[u("div",ve,[n(E,null,{default:a(()=>[n(h,{height:"70px"},{default:a(()=>[u("div",be,[n(_,{class:"location"},{default:a(()=>[n(b)]),_:1}),n(i,{to:"/index",class:"home",title:e.$t("返回首页")},{default:a(()=>[D(f(e.$t("首页")),1)]),_:1},8,["title"]),u("span",he,[n(_,null,{default:a(()=>[n(m)]),_:1})]),n(i,{to:"/user/mine/index",class:"home",title:e.$t("个人中心")},{default:a(()=>[D(f(e.$t("个人中心")),1)]),_:1},8,["title"]),u("span",ge,[n(_,null,{default:a(()=>[n(m)]),_:1})]),u("span",ye,f(e.$t(e.title)),1)])]),_:1}),ae((s(),g(E,null,{default:a(()=>[n($,{width:"266px"},{default:a(()=>[e.widgets.length?(s(),d("ul",Ce,[(s(!0),d(y,null,L(e.widgets,(t,R)=>(s(),d(y,{key:R},[u("li",{class:C(["li-left",{active:t.menu_label==e.routeName}]),onClick:o=>e.goTo(t.menu_url)},[Ee,u("div",we,[n(_,{class:C(t.menu_label==e.routeName?"active":"")},{default:a(()=>[(s(),g(A(t.menu_icon)))]),_:2},1032,["class"])]),u("div",De,[u("span",Le,f(t.menu_name),1)]),u("div",Ae,[n(_,null,{default:a(()=>[n(m)]),_:1})])],10,ke),t.sub.length?(s(),d("ul",Be,[(s(!0),d(y,null,L(t.sub,(o,S)=>(s(),d("li",{key:S,class:C(["li-left",{active:o.menu_label==e.routeName}]),onClick:He=>e.goTo(o.menu_url)},[$e,u("div",Te,[u("img",{src:o.menu_label==e.routeName?o.menu_active_image:o.menu_image},null,8,Ne)]),u("div",Re,[u("span",Se,f(o.menu_name),1)]),u("div",Fe,[n(_,null,{default:a(()=>[n(m)]),_:1})])],10,Ie))),128))])):B("",!0)],64))),128))])):B("",!0)]),_:1}),u("div",xe,[n(T,null,{default:a(({Component:t})=>[n(se,{name:"slide-fade"},{default:a(()=>[(s(),g(A(t)))]),_:2},1024)]),_:1})])]),_:1})),[[N,e.loading]])]),_:1})])])}const je=ce(fe,[["render",Ge],["__scopeId","data-v-3b2f0680"]]);export{je as default};