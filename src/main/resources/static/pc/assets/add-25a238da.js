import{M as g,N as b,Q as I,dN as V,f as p,r as R,$ as f,i as B,t as q,U as h,p as _,v as C,y as o,z as r,F as E,C as S,I as U,x as w,A as v,D as A,W as D,X as L,L as j,K as M}from"./index-78223f15.js";import{a as N,E as z}from"./form-item-8b9adddb.js";/* empty css               */import{_ as O}from"./Img-372f434f.js";/* empty css              */import"./tag-c15f0a42.js";import{a as T,E as P}from"./select-0ae9fab1.js";import"./scrollbar-afb507b1.js";import"./popper-7ac694e5.js";import{_ as K}from"./_plugin-vue_export-helper-c27b6911.js";function de(e){return g({url:b.URL.user.feedback_lists,method:"get",params:e})}function Q(e){return g({url:b.URL.user.feedback_add,method:"POST",params:e})}function W(e){return g({url:b.URL.user.feedback_category,method:"get",params:e})}const X=I({components:{CaretRight:V},setup(){let e=p({categoryRows:[],active:0,dialogVisible:!1});const a=R();let s=p({feedback_question:"",feedback_category_id:"",feedback_img:""}),y=p({feedback_category_id:[{required:!0,message:f("请选择问题类型"),trigger:"blur"}],feedback_question:[{required:!0,message:f("请输入您的建议"),trigger:"blur"}]});const F=()=>{W({}).then(t=>{if(t.status==200)for(let l=0;l<t.data.length;l++)for(let n=0;n<t.data[l].rows.length;n++){let d={};d=t.data[l].rows[n],e.categoryRows.push(d)}})},k=t=>{e.active=t},c=t=>{s.feedback_category_id=t,s.feedback_category_id&&(e.dialogVisible=!0)},m=async t=>{console.log(t),t&&await t.validate((l,n)=>{l?Q(s).then(d=>{d.status==200?(e.dialogVisible=!1,s.feedback_question="",h.success(f("添加成功"))):h.error(d.msg)}):console.log("error submit!",n)})},u=t=>{s.feedback_img=t[0]};return B(()=>{F()}),{switchType:k,selectCategory:c,submitForm:m,changImg:u,ruleForm:s,ruleFormRef:a,rules:y,...q(e)}}});const G=e=>(D("data-v-8db274e9"),e=e(),L(),e),H=G(()=>w("div",{style:{"margin-bottom":"40px"}},null,-1)),J={style:{display:"inline-block","margin-top":"11px"},class:"tips"};function Y(e,a,s,y,F,k){const c=T,m=P,u=z,t=j,l=O,n=M,d=N;return _(),C(E,null,[H,o(d,{class:"feedback-from","label-position":"top","label-width":"80px",ref:"ruleFormRef",model:e.ruleForm,rules:e.rules},{default:r(()=>[o(u,{label:e.$t("问题类型"),prop:"feedback_type"},{default:r(()=>[o(m,{modelValue:e.ruleForm.feedback_category_id,"onUpdate:modelValue":a[0]||(a[0]=i=>e.ruleForm.feedback_category_id=i),class:"m-2",placeholder:e.$t("请选择问题类型"),size:"large",style:{width:"100%"}},{default:r(()=>[(_(!0),C(E,null,S(e.categoryRows,(i,$)=>(_(),U(c,{key:$,label:i.feedback_category_name,value:i.feedback_category_id},null,8,["label","value"]))),128))]),_:1},8,["modelValue","placeholder"])]),_:1},8,["label"]),o(u,{label:e.$t("问题描述"),prop:"feedback_question"},{default:r(()=>[o(t,{type:"textarea",maxlength:"200","show-word-limit":"",placeholder:e.$t("感谢提出意见"),size:"large",rows:"6",modelValue:e.ruleForm.feedback_question,"onUpdate:modelValue":a[1]||(a[1]=i=>e.ruleForm.feedback_question=i)},null,8,["placeholder","modelValue"])]),_:1},8,["label"]),o(u,null,{default:r(()=>[o(l,{fileIamge:e.ruleForm.feedback_img,limit:1,onChange:e.changImg},null,8,["fileIamge","onChange"])]),_:1}),o(u,null,{default:r(()=>[w("div",J,v(e.$t("格式jpg，jpeg，png，大小不超过5M")),1)]),_:1}),o(u,{style:{"margin-top":"35px"}},{default:r(()=>[o(n,{type:"primary",onClick:a[2]||(a[2]=i=>e.submitForm(e.ruleFormRef)),style:{width:"100px"}},{default:r(()=>[A(v(e.$t("提交")),1)]),_:1})]),_:1})]),_:1},8,["model","rules"])],64)}const Z=K(X,[["render",Y],["__scopeId","data-v-8db274e9"]]),ie=Object.freeze(Object.defineProperty({__proto__:null,default:Z},Symbol.toStringTag,{value:"Module"}));export{Z as A,ie as a,de as f};