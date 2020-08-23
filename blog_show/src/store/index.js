import Vue from 'vue'
import VueX from 'vuex'
import getters from "./getters";

if (process.env.NODE_ENV === 'development') {
  Vue.use(VueX);
}

// require.context("目录", "是否遍历子目录", "正则")
const modulesFiles = require.context('./modules', true, /\.js$/);

// modulesFiles.keys(): ["./app.js", "./permission.js", "./tagsView.js", "./user.js"]
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
    // $1--$9 是 RegExp 自带的，只要放生了匹配就会有。代表的是分组，即小括号里面的小正则捕获到的内容。
    const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1');
    const value = modulesFiles(modulePath);
    modules[moduleName] = value.default;
    return modules;
}, {});

const store = new VueX.Store({
    modules,
    getters,
});

export default store;
