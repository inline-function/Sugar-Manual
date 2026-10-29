
let n = 0;
const br = (x) => { n++; return x; };
const content = (a) => ({ tag: "root", arg: a });
const onStart = ((_) => (function(f){ return br(JSON.stringify(f(null))); })(content));
try { console.log("ret:", onStart(null)); } catch (e) { console.log("THREW", e.message); }
console.log("calls:", n);
