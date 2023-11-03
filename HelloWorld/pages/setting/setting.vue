<template>

	<view class="item">
		<view class="tit">选择生辰：</view>
		<picker class="picker date" mode="date" :value="date"  @change="bindDateChange">
			<view>{{ date }}</view>
			<view v-model="year1">
				你已经在世界上存在了{{ year1 }}年 </view>
			<view v-model="month1">
				- {{ month1}}月 </view>
			<view :value="day1">- {{ day1 }}天
			</view>
		</picker>
	</view>
	

</template>

<script>
	export default {
		onLoad() {
		
		},	
		data() {
			var currentDate = this.getDate({
				format: true
			});
			 
			return {
				date: currentDate,
				year1: year1,
				month1: month1,
				day1:day1
			};
		},
		methods: {
			// 选择时间 日期
			bindDateChange: function(e) {
				this.date = e.target.value;
			},
			getDate(type) {
				console.log('999')
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				
				
				return `${year}-${month}-${day}`;
			}
		}
}
</script>

<style>

</style>
