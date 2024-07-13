import{aC as J,aE as K,aF as N,Q as Z,cm as Q,r as X,a7 as g,cn as L,i as R,b8 as Y,p as m,I as E,z as I,P,x as C,G as v,aU as a,H as x,J as F,an as ee,E as _,A as $,ba as te,v as A,F as oe,Z as D,aW as ne,y as j,ao as se,aX as ie,bJ as ae,bO as le,bz as w,bj as re,co as z,bE as O,aI as ce,cp as V,cq as ue}from"./index-78223f15.js";const q=["success","info","warning","error"],fe=J({customClass:{type:String,default:""},dangerouslyUseHTMLString:{type:Boolean,default:!1},duration:{type:Number,default:4500},icon:{type:K},id:{type:String,default:""},message:{type:N([String,Object]),default:""},offset:{type:Number,default:0},onClick:{type:N(Function),default:()=>{}},onClose:{type:N(Function),required:!0},position:{type:String,values:["top-right","top-left","bottom-right","bottom-left"],default:"top-right"},showClose:{type:Boolean,default:!0},title:{type:String,default:""},type:{type:String,values:[...q,""],default:""},zIndex:Number}),de={destroy:()=>!0},pe=["id"],me=["textContent"],ve={key:0},ye=["innerHTML"],ge=Z({name:"ElNotification"}),Ce=Z({...ge,props:fe,emits:de,setup(t,{expose:s}){const o=t,{ns:n,zIndex:r}=Q("notification"),{nextZIndex:f,currentZIndex:d}=r,{Close:c}=ae,l=X(!1);let i;const u=g(()=>{const e=o.type;return e&&L[o.type]?n.m(e):""}),p=g(()=>o.type&&L[o.type]||o.icon),k=g(()=>o.position.endsWith("right")?"right":"left"),G=g(()=>o.position.startsWith("top")?"top":"bottom"),U=g(()=>{var e;return{[G.value]:`${o.offset}px`,zIndex:(e=o.zIndex)!=null?e:d.value}});function S(){o.duration>0&&({stop:i}=le(()=>{l.value&&h()},o.duration))}function M(){i==null||i()}function h(){l.value=!1}function W({code:e}){e===w.delete||e===w.backspace?M():e===w.esc?l.value&&h():S()}return R(()=>{S(),f(),l.value=!0}),Y(document,"keydown",W),s({visible:l,close:h}),(e,b)=>(m(),E(se,{name:a(n).b("fade"),onBeforeLeave:e.onClose,onAfterLeave:b[1]||(b[1]=H=>e.$emit("destroy")),persisted:""},{default:I(()=>[P(C("div",{id:e.id,class:v([a(n).b(),e.customClass,a(k)]),style:x(a(U)),role:"alert",onMouseenter:M,onMouseleave:S,onClick:b[0]||(b[0]=(...H)=>e.onClick&&e.onClick(...H))},[a(p)?(m(),E(a(F),{key:0,class:v([a(n).e("icon"),a(u)])},{default:I(()=>[(m(),E(ee(a(p))))]),_:1},8,["class"])):_("v-if",!0),C("div",{class:v(a(n).e("group"))},[C("h2",{class:v(a(n).e("title")),textContent:$(e.title)},null,10,me),P(C("div",{class:v(a(n).e("content")),style:x(e.title?void 0:{margin:0})},[te(e.$slots,"default",{},()=>[e.dangerouslyUseHTMLString?(m(),A(oe,{key:1},[_(" Caution here, message could've been compromised, never use user's input as message "),C("p",{innerHTML:e.message},null,8,ye)],2112)):(m(),A("p",ve,$(e.message),1))])],6),[[D,e.message]]),e.showClose?(m(),E(a(F),{key:0,class:v(a(n).e("closeBtn")),onClick:ne(h,["stop"])},{default:I(()=>[j(a(c))]),_:1},8,["class","onClick"])):_("v-if",!0)],2)],46,pe),[[D,l.value]])]),_:3},8,["name","onBeforeLeave"]))}});var he=ie(Ce,[["__file","/home/runner/work/element-plus/element-plus/packages/components/notification/src/notification.vue"]]);const T={"top-left":[],"top-right":[],"bottom-left":[],"bottom-right":[]},B=16;let be=1;const y=function(t={},s=null){if(!re)return{close:()=>{}};(typeof t=="string"||z(t))&&(t={message:t});const o=t.position||"top-right";let n=t.offset||0;T[o].forEach(({vm:u})=>{var p;n+=(((p=u.el)==null?void 0:p.offsetHeight)||0)+B}),n+=B;const r=`notification_${be++}`,f=t.onClose,d={...t,offset:n,id:r,onClose:()=>{Ee(r,o,f)}};let c=document.body;O(t.appendTo)?c=t.appendTo:ce(t.appendTo)&&(c=document.querySelector(t.appendTo)),O(c)||(c=document.body);const l=document.createElement("div"),i=j(he,d,z(d.message)?{default:()=>d.message}:null);return i.appContext=s??y._context,i.props.onDestroy=()=>{V(null,l)},V(i,l),T[o].push({vm:i}),c.appendChild(l.firstElementChild),{close:()=>{i.component.exposed.visible.value=!1}}};q.forEach(t=>{y[t]=(s={})=>((typeof s=="string"||z(s))&&(s={message:s}),y({...s,type:t}))});function Ee(t,s,o){const n=T[s],r=n.findIndex(({vm:i})=>{var u;return((u=i.component)==null?void 0:u.props.id)===t});if(r===-1)return;const{vm:f}=n[r];if(!f)return;o==null||o(f);const d=f.el.offsetHeight,c=s.split("-")[0];n.splice(r,1);const l=n.length;if(!(l<1))for(let i=r;i<l;i++){const{el:u,component:p}=n[i].vm,k=Number.parseInt(u.style[c],10)-d-B;p.props.offset=k}}function Te(){for(const t of Object.values(T))t.forEach(({vm:s})=>{s.component.exposed.visible.value=!1})}y.closeAll=Te;y._context=null;const Se=ue(y,"$notify");export{Se as E};