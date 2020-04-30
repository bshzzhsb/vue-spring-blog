<template>
	<div>
		<el-row justify="center" type="flex">
			<el-col :xs="24" :md="20" :sm="20">
				<router-view />
			</el-col>
		</el-row>
	</div>
</template>

<script>
    export default {
        name: "MoodManager"
    }
</script>

<style scoped>

</style>