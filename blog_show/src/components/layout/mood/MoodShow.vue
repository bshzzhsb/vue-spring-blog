<template>
	<el-row type="flex" justify="center">
		<el-col :xs="24" :sm="20" :md="16" :lg="14" :xl="12">
			<mood></mood>
		</el-col>
		<scroll-top></scroll-top>
	</el-row>
</template>

<script>
	import Mood from "./Mood";
	import ScrollTop from "../../common/ScrollTop";

    export default {
        name: "MoodShow",
	    components: {ScrollTop, Mood},
    }
</script>

<style scoped>

</style>