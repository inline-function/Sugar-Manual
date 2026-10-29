// app/build-test.js —— 拼装 out.test.js = out.js IIFE（顶部插桩 + 去掉自带 main() 调用）+ driver-tail
// 用法：node app/build-test.js
const fs = require("fs");
const path = require("path");
const out = fs.readFileSync(path.join(__dirname, "out.js"), "utf8");
const tail = fs.readFileSync(path.join(__dirname, "driver-tail.js"), "utf8");
const head = "\"use strict\";\n(() => {\n";
if (!out.startsWith(head)) throw new Error("out.js 头部结构不匹配");
const body = out.slice(head.length).replace(/\n  main\(\);\n\}\)\(\);\n?$/, "");
if (body.length === out.length - head.length) throw new Error("out.js 尾部结构不匹配");
const shims = [
  "  // ===== 测试桩（driver-tail 依赖；必须先于 const __dshAndroid 快照）=====",
  "  const __smStore = {};",
  "  const __smLayouts = [];",
  "  let __smLastToast = \"\";",
  "  globalThis.__dshAndroid = (kind, args) => {",
  "    switch (kind) {",
  "      case \"layout\": __smLayouts.push(args[0]); return null;",
  "      case \"storageGet\": return __smStore[args[0]] || \"\";",
  "      case \"storagePut\": __smStore[args[0]] = args[1]; return null;",
  "      case \"toast\": __smLastToast = args[0]; return null;",
  "      case \"log\": return null;",
  "      case \"screenWidth\": return 1080;",
  "      case \"screenHeight\": return 2400;",
  "      case \"screenDensity\": return 420;",
  "      default: return null;",
  "    }",
  "  };",
].join("\n");
fs.writeFileSync(path.join(__dirname, "out.test.js"), head + shims + "\n" + body + tail);
console.log("built out.test.js");