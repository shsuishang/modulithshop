import{l as E,b as $,g as C,e as w,u as A,f as k,a9 as F,t as L,U as I,$ as v,O as D,n as b,p as f,v as h,x as i,y as o,z as r,A as s,D as _,F as S,C as R,J as z,K as M,W as N,X as T}from"./index-78223f15.js";/* empty css               */import{E as U}from"./image-viewer-32b8c113.js";/* empty css             */import{g as V}from"./activity-3b10319e.js";import{_ as j}from"./addr-5e1cd51c.js";import{_ as q}from"./_plugin-vue_export-helper-c27b6911.js";import"./debounce-429b1daa.js";const x={name:"detail",components:{Location:E,ArrowRight:$},setup(){const t=C(),u=w(),d=A(),e=k({activity_id:"",activityBase:{},items:[],isLoading:!1,subtotal:0,giftbag:{}}),m=()=>{V({activity_id:e.activity_id}).then(n=>{if(n.status==200){let a=n.data;a.activity_base&&(e.activityBase=a.activity_base,a.activity_base.activity_rule&&(e.activityBase.activity_rule=JSON.parse(a.activity_base.activity_rule),e.giftbag=e.activityBase.activity_rule.giftbag)),a.items&&(e.items=a.items,a.items.forEach(l=>{e.subtotal=e.subtotal+l.item_unit_price*l.activity_item_num}))}else I.error(v("获取数据失败"))})};let y=()=>{if(!t.hasLogin){D.confirm(v("请先登录"),{confirmButtonText:v("确定"),cancelButtonText:v("取消"),dangerouslyUseHTMLString:!0,center:!0}).then(()=>{d.push({path:"/login/login"})});return}e.isLoading=!0;let n=[];e.items.forEach(g=>{const c=g.item_id+"|"+g.activity_item_num+"|0";n.push(c)});const a=n.join(",");let l={activity_id:e.activity_id,cart_id:a};d.push({path:"/cart/checkout",query:l})};const p=()=>{d.push({path:"/activity/giftbag/list"})};return F(()=>{e.activity_id=u.query.activity_id,m()}),{buynow:y,getActivityDetail:m,goGiftBagList:p,...L(e)}}};const G=t=>(N("data-v-25f22f67"),t=t(),T(),t),J={class:"center_column"},O={class:"container"},H={class:"breadcrumb clearfix"},K=G(()=>i("img",{src:j,style:{width:"20px",height:"20px",position:"relative","margin-right":"10px"}},null,-1)),W={class:"space-icon"},X={class:"space-icon"},P=["title"],Q={class:"container"},Y={class:"activity-top"},Z={class:"activity-price"},tt={style:{"margin-left":"10px"}},it={class:"old-price"},st={class:"activity-item"},et={style:{padding:"20px"}},at={style:{"font-size":"22px","font-weight":"600"}},ot={class:"product-view-grid"},nt={class:"p-img"},ct={class:"p-name"},lt={class:"p-price-box"},ut={class:"p-price"},rt={style:{"margin-top":"20px","text-align":"center"}},_t={class:"activity-bot"},dt={class:"activity-bot-title"},pt={class:"activity-bot-remark"};function gt(t,u,d,e,m,y){const p=b("router-link"),n=b("ArrowRight"),a=z,l=U,g=M;return f(),h("div",J,[i("div",O,[i("div",H,[K,o(p,{to:"/index",class:"home",title:t.$t("首页")},{default:r(()=>[_(s(t.$t("首页")),1)]),_:1},8,["title"]),i("span",W,[o(a,null,{default:r(()=>[o(n)]),_:1})]),o(p,{to:"/activity/giftbag/list",class:"home",title:t.$t("礼包专区")},{default:r(()=>[_(s(t.$t("礼包专区")),1)]),_:1},8,["title"]),i("span",X,[o(a,null,{default:r(()=>[o(n)]),_:1})]),i("span",{class:"home cur",title:t.$t("礼包详情")},s(t.$t("礼包详情")),9,P)])]),i("div",Q,[i("div",Y,[o(l,{src:t.giftbag.giftbag_zu_image,fit:"cover",style:{width:"100%",height:"400px"},alt:t.giftbag.giftbag_zu_image},null,8,["src","alt"]),i("div",Z,[i("div",tt,s(t.$t("￥"))+s(t.giftbag.giftbag_amount),1),i("div",it,s(t.$t("￥"))+s(t.subtotal),1)])]),i("div",st,[i("div",et,[i("span",at,s(t.$t("礼包包含以下商品")),1),i("span",{style:{float:"right",color:"red",cursor:"pointer"},onClick:u[0]||(u[0]=c=>e.goGiftBagList())},[_(s(t.$t("更多礼包"))+" ",1),o(a,null,{default:r(()=>[o(n)]),_:1})])]),i("ul",ot,[(f(!0),h(S,null,R(t.items,(c,B)=>(f(),h("li",{key:B,class:"item"},[i("div",nt,[o(l,{src:c.product_image,fit:"contain",alt:c.product_image},null,8,["src","alt"])]),i("div",null,[i("div",ct,s(c.product_name),1),i("div",lt,[i("div",ut,[i("span",null,s(t.$t("￥")),1),_(" "+s(c.item_unit_price)+" ",1),i("span",null,s(t.$t("x"))+s(c.activity_item_num),1)])])])]))),128))])]),i("div",rt,[o(g,{type:"success",loading:t.isLoading,class:"content-right-bot bgj",onClick:u[1]||(u[1]=c=>e.buynow())},{default:r(()=>[_(s(t.$t("立即购买")),1)]),_:1},8,["loading"])]),i("div",_t,[i("div",dt,s(t.$t("活动介绍")),1),i("div",pt,s(t.activityBase.activity_remark),1)])])])}const $t=q(x,[["render",gt],["__scopeId","data-v-25f22f67"]]);export{$t as default};