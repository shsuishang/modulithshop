import{P as h,f as m,i as b,t as y,T as $,$ as D,n as E,p as r,v as l,I as F,E as S,x as o,y as i,z as c,A as s,F as k,C as B,D as _,V as C,W as I,X as V}from"./index-21964c78.js";import{E as N}from"./image-viewer-dd6920ac.js";import{_ as w}from"./Index-e5de64cf.js";import{l as G}from"./store-b45313f8.js";import{_ as x}from"./_plugin-vue_export-helper-c27b6911.js";import"./debounce-6ea0a711.js";import"./overlay-b290a267.js";import"./refs-1278d799.js";import"./isUndefined-aa0326a0.js";import"./Quick-23459fd5.js";import"./loading-9a2abf10.js";/* empty css               */import"./input-number-2592ba06.js";import"./index-a2686495.js";/* empty css              */import"./cascader-panel-e311c1a6.js";import"./scrollbar-c1d26490.js";import"./checkbox-85319853.js";import"./isEqual-3f4e7b62.js";import"./_Uint8Array-4a48d71f.js";import"./hasIn-54cf4aec.js";import"./flatten-3a6a3668.js";import"./radio-30ecb740.js";import"./rand-14326ce1.js";import"./strings-b80b2625.js";import"./arrays-e667dc24.js";import"./cloneDeep-9c8ed640.js";import"./_baseClone-42b68cca.js";import"./_initCloneObject-dc96d043.js";import"./popper-251c4401.js";import"./tag-b8b29d1f.js";import"./index-a2bc563c.js";import"./main-34db54b3.js";import"./row-d248ec80.js";import"./constants-2604e35b.js";import"./col-d6038888.js";import"./Index-c0d55bc5.js";import"./product-4d2f9c7a.js";import"./index-f12e260f.js";import"./card-695ab518.js";import"./tab-pane-58065554.js";import"./index-a7a3c269.js";import"./carousel-item-05a08577.js";import"./link-8dfac441.js";const A=h({setup(){let t=m({lists:[],floor:{},loading:!0,status:!1}),d=m({page:1,rows:10,total:1});const u=()=>{G({}).then(p=>{var a=p.data;p.status==200?(t.lists=a.items,t.floor=a.floor,t.status=!0,d.total=a.records):$.error(D("获取数据失败")),t.loading=!1})};return b(()=>{u()}),{...y(t)}}});const T=t=>(C("data-v-42b18e30"),t=t(),I(),t),q={id:"page-container"},L={class:"container columns"},M={class:"breadcrumb clearfix"},P=T(()=>o("span",{class:"navigation-pipe"}," ",-1)),z={class:"navigation_page"},R={class:"content"},W={class:"container"},X={class:"groups"},j={class:"gr_goods_list clearfix"},H={class:"gr_goods_ev clearfix"},J={class:"bbc_color"},K={class:"bbc_color",style:{color:"red"}},O={class:"gr_sold_hav clearfix"},Q={class:"num-color ml4"},U=V('<em class="leftTime hide" data-end="2022-05-31 10:35:00" data-v-42b18e30><span class="d" data-v-42b18e30>24</span><strong data-v-42b18e30>天</strong> <span class="h" data-v-42b18e30>23</span><strong data-v-42b18e30>小时</strong> <span class="m" data-v-42b18e30>54</span><strong data-v-42b18e30>分</strong><span class="s" data-v-42b18e30>40</span><strong data-v-42b18e30>秒</strong></em>',1);function Y(t,d,u,p,a,Z){const g=w,n=E("router-link"),v=N;return r(),l("div",q,[t.status?(r(),F(g,{key:0,pageData:t.floor},null,8,["pageData"])):S("",!0),o("div",null,[o("div",L,[o("div",M,[i(n,{class:"home",to:"/",title:t.$t("返回首页")},{default:c(()=>[_(s(t.$t("首页")),1)]),_:1},8,["title"]),P,o("span",z,s(t.$t("全部团购")),1)])])]),o("div",R,[o("div",W,[o("div",X,[o("ul",j,[(r(!0),l(k,null,B(t.lists,(e,f)=>(r(),l("li",{key:f},[i(n,{to:{path:"/product/detail",query:{item_id:e.item_id,gbs_id:1}},class:"gr_goods_a"},{default:c(()=>[i(v,{src:e.product_image},null,8,["src"])]),_:2},1032,["to"]),o("p",H,[o("span",null,[o("strong",J,s(t.$t("￥"))+s(e.product_sale_price),1)]),o("em",K,s(t.$t("立省"))+s(e.activity_row.activity_rule.group_sale_price),1)]),o("h5",null,[i(n,{to:{path:"/product/detail",query:{item_id:e.item_id,gbs_id:1}}},{default:c(()=>[_(s(e.product_name),1)]),_:2},1032,["to"])]),o("p",O,[o("span",null,[_(s(t.$t("已售")),1),o("i",Q,s(e.group_success_num),1)]),U])]))),128))])])])])])}const Xt=x(A,[["render",Y],["__scopeId","data-v-42b18e30"]]);export{Xt as default};