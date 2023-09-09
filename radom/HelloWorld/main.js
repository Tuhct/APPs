import App from './App'

// #ifndef VUE3
import Vue from 'vue'

import uView from 'uview-ui'
Vue.use(uView);
// 日期转换工具
import moment from 'moment'

Vue.config.productionTip = false

App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()

// #endif
// 引入全局css
Vue.prototype.websiteUrl = 'https://ceshi2.dishait.cn/api/v1';  
Vue.prototype.nowTime = Date.nowTime || function () {  
    return new Date().getTime();  
};  


// 引入全局uView

Vue.filter('dateFormat', function(value, formatStr = 'YYYY-MM-DD HH:mm:ss') {
	return moment(value).format(formatStr)
})
Vue.config.productionTip = false
//获取日期与时间,
Vue.prototype.toLocaleString = (type = 'date') => {
	var myDate = new Date();
	var date
	if (type == 'time') {
		date = moment(myDate.toLocaleString()).format('YYYY-MM-DD HH:mm');
	} else {
		date = moment(myDate.toLocaleString()).format('YYYY-MM-DD'); //获取日期与时间
	}
	return date
}

// value代表内容（value不能为空），num是限制我们多少字符进行显示省略号（num=20意思是不传默认是等于20）
Vue.prototype.getEllipsis = (value, num = 20) => {
	if (!value) {
		return ''
	}
	if (value.length > num) {
		return value.slice(0, num) + '...'
	}
	return value
}

// 需求：判断date是否为今年，如果是就去除年份，不是就显示全部
Vue.prototype.contrastDate = (date) => {
	var myDate = new Date();
	// 此时此刻的年份
	var currentMonth = myDate.getFullYear()

	// 传递参数的年份进行处理
	var index = date.indexOf("\-");
	// 拿到第一个‘-’前面的内容（例如：2022）
	var transmitMonth = date.substring(0, index);

	// 对比传递过来的参数（时间）是不是今年，如果是今年就不显示年份
	if (transmitMonth == currentMonth) {
		// 拿到第一个‘-’后面的内容（例如：10-14）
		return date.substring(index + 1, date.length)
	} else {
		return date
	}
}



// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif