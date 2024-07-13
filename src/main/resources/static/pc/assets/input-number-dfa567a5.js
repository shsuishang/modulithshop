import{aC as le,cE as se,aJ as d,aK as j,aL as F,c3 as I,aG as h,Q as q,bp as oe,aO as ie,r as ce,f as de,aM as me,a7 as V,cB as E,aN as pe,aQ as be,aS as fe,i as ve,cA as Ne,p as b,v as D,P as J,aU as t,G as K,B as _,y as M,z as Q,I as S,a as Ve,cF as he,J as R,E as Y,cG as Ie,a5 as ye,aW as P,L as ge,aX as we,a$ as Ee,aI as _e,aT as H,b1 as Se}from"./index-78223f15.js";import{v as X}from"./index-799b1788.js";const Pe=le({id:{type:String,default:void 0},step:{type:Number,default:1},stepStrictly:Boolean,max:{type:Number,default:Number.POSITIVE_INFINITY},min:{type:Number,default:Number.NEGATIVE_INFINITY},modelValue:Number,readonly:Boolean,disabled:Boolean,size:se,controls:{type:Boolean,default:!0},controlsPosition:{type:String,default:"",values:["","right"]},valueOnClear:{type:[String,Number,null],validator:u=>u===null||d(u)||["min","max"].includes(u),default:null},name:String,label:String,placeholder:String,precision:{type:Number,validator:u=>u>=0&&u===Number.parseInt(`${u}`,10)},validateEvent:{type:Boolean,default:!0}}),Fe={[j]:(u,A)=>A!==u,blur:u=>u instanceof FocusEvent,focus:u=>u instanceof FocusEvent,[F]:u=>d(u)||I(u),[h]:u=>d(u)||I(u)},Ae=["aria-label","onKeydown"],ke=["aria-label","onKeydown"],Be=q({name:"ElInputNumber"}),Ce=q({...Be,props:Pe,emits:Fe,setup(u,{expose:A,emit:c}){const r=u,{t:O}=oe(),m=ie("input-number"),v=ce(),l=de({currentValue:r.modelValue,userInput:null}),{formItem:f}=me(),G=V(()=>d(r.modelValue)&&r.modelValue<=r.min),U=V(()=>d(r.modelValue)&&r.modelValue>=r.max),Z=V(()=>{const e=$(r.step);return E(r.precision)?Math.max($(r.modelValue),e):(e>r.precision,r.precision)}),k=V(()=>r.controls&&r.controlsPosition==="right"),L=pe(),N=be(),B=V(()=>{if(l.userInput!==null)return l.userInput;let e=l.currentValue;if(I(e))return"";if(d(e)){if(Number.isNaN(e))return"";E(r.precision)||(e=e.toFixed(r.precision))}return e}),C=(e,n)=>{if(E(n)&&(n=Z.value),n===0)return Math.round(e);let a=String(e);const s=a.indexOf(".");if(s===-1||!a.replace(".","").split("")[s+n])return e;const g=a.length;return a.charAt(g-1)==="5"&&(a=`${a.slice(0,Math.max(0,g-1))}6`),Number.parseFloat(Number(a).toFixed(n))},$=e=>{if(I(e))return 0;const n=e.toString(),a=n.indexOf(".");let s=0;return a!==-1&&(s=n.length-a-1),s},W=(e,n=1)=>d(e)?C(e+r.step*n):l.currentValue,x=()=>{if(r.readonly||N.value||U.value)return;const e=Number(B.value)||0,n=W(e);y(n),c(F,l.currentValue)},T=()=>{if(r.readonly||N.value||G.value)return;const e=Number(B.value)||0,n=W(e,-1);y(n),c(F,l.currentValue)},z=(e,n)=>{const{max:a,min:s,step:o,precision:p,stepStrictly:g,valueOnClear:w}=r;a<s&&Ee("InputNumber","min should not be greater than max.");let i=Number(e);if(I(e)||Number.isNaN(i))return null;if(e===""){if(w===null)return null;i=_e(w)?{min:s,max:a}[w]:w}return g&&(i=C(Math.round(i/o)*o,p)),E(p)||(i=C(i,p)),(i>a||i<s)&&(i=i>a?a:s,n&&c(h,i)),i},y=(e,n=!0)=>{var a;const s=l.currentValue,o=z(e);if(!n){c(h,o);return}s!==o&&(l.userInput=null,c(h,o),c(j,o,s),r.validateEvent&&((a=f==null?void 0:f.validate)==null||a.call(f,"change").catch(p=>H())),l.currentValue=o)},ee=e=>{l.userInput=e;const n=e===""?null:Number(e);c(F,n),y(n,!1)},ne=e=>{const n=e!==""?Number(e):"";(d(n)&&!Number.isNaN(n)||e==="")&&y(n),l.userInput=null},te=()=>{var e,n;(n=(e=v.value)==null?void 0:e.focus)==null||n.call(e)},ae=()=>{var e,n;(n=(e=v.value)==null?void 0:e.blur)==null||n.call(e)},re=e=>{c("focus",e)},ue=e=>{var n;c("blur",e),r.validateEvent&&((n=f==null?void 0:f.validate)==null||n.call(f,"blur").catch(a=>H()))};return fe(()=>r.modelValue,e=>{const n=z(l.userInput),a=z(e,!0);!d(n)&&(!n||n!==a)&&(l.currentValue=a,l.userInput=null)},{immediate:!0}),ve(()=>{var e;const{min:n,max:a,modelValue:s}=r,o=(e=v.value)==null?void 0:e.input;if(o.setAttribute("role","spinbutton"),Number.isFinite(a)?o.setAttribute("aria-valuemax",String(a)):o.removeAttribute("aria-valuemax"),Number.isFinite(n)?o.setAttribute("aria-valuemin",String(n)):o.removeAttribute("aria-valuemin"),o.setAttribute("aria-valuenow",l.currentValue||l.currentValue===0?String(l.currentValue):""),o.setAttribute("aria-disabled",String(N.value)),!d(s)&&s!=null){let p=Number(s);Number.isNaN(p)&&(p=null),c(h,p)}}),Ne(()=>{var e,n;const a=(e=v.value)==null?void 0:e.input;a==null||a.setAttribute("aria-valuenow",`${(n=l.currentValue)!=null?n:""}`)}),A({focus:te,blur:ae}),(e,n)=>(b(),D("div",{class:K([t(m).b(),t(m).m(t(L)),t(m).is("disabled",t(N)),t(m).is("without-controls",!e.controls),t(m).is("controls-right",t(k))]),onDragstart:n[1]||(n[1]=P(()=>{},["prevent"]))},[e.controls?J((b(),D("span",{key:0,role:"button","aria-label":t(O)("el.inputNumber.decrease"),class:K([t(m).e("decrease"),t(m).is("disabled",t(G))]),onKeydown:_(T,["enter"])},[M(t(R),null,{default:Q(()=>[t(k)?(b(),S(t(Ve),{key:0})):(b(),S(t(he),{key:1}))]),_:1})],42,Ae)),[[t(X),T]]):Y("v-if",!0),e.controls?J((b(),D("span",{key:1,role:"button","aria-label":t(O)("el.inputNumber.increase"),class:K([t(m).e("increase"),t(m).is("disabled",t(U))]),onKeydown:_(x,["enter"])},[M(t(R),null,{default:Q(()=>[t(k)?(b(),S(t(Ie),{key:0})):(b(),S(t(ye),{key:1}))]),_:1})],42,ke)),[[t(X),x]]):Y("v-if",!0),M(t(ge),{id:e.id,ref_key:"input",ref:v,type:"number",step:e.step,"model-value":t(B),placeholder:e.placeholder,readonly:e.readonly,disabled:t(N),size:t(L),max:e.max,min:e.min,name:e.name,label:e.label,"validate-event":!1,onWheel:n[0]||(n[0]=P(()=>{},["prevent"])),onKeydown:[_(P(x,["prevent"]),["up"]),_(P(T,["prevent"]),["down"])],onBlur:ue,onFocus:re,onInput:ee,onChange:ne},null,8,["id","step","model-value","placeholder","readonly","disabled","size","max","min","name","label","onKeydown"])],34))}});var xe=we(Ce,[["__file","/home/runner/work/element-plus/element-plus/packages/components/input-number/src/input-number.vue"]]);const De=Se(xe);export{De as E};