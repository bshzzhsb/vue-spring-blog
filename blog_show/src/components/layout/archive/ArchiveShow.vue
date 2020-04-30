<template>
	<el-row class="archive-show" type="flex" justify="center">
		<el-col :xs="24" :sm="20" :md="18" :lg="16" :xl="16">
			<archive></archive>
		</el-col>
	</el-row>
</template>

<script>
	import Archive from "./Archive";

    export default {
        name: "ArchiveShow",
	    components: {Archive},
    }
</script>

<style scoped>
	.archive-show {
		min-height: calc(100vh - 198px);
	}
</style>