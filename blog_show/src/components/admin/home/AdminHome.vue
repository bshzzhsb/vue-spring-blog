<template>
	<div>
		<el-row :gutter="10">
			<el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
				<git-hub-trending></git-hub-trending>
			</el-col>
			<el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
				<todo></todo>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import GitHubTrending from "./GitHubTrending";
	import Todo from "./Todo";

    export default {
        name: "AdminHome",
        components: {Todo, GitHubTrending}
    }
</script>

<style scoped>

</style>