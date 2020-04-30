<template>
	<div class="type-tag">
		<el-row type="flex" justify="center">
			<el-col :xs="24" :sm="24" :md="20" :lg="16" :xl="14">
				<type-and-tag-bar></type-and-tag-bar>
				<transition name="type-tag" mode="out-in">
					<router-view />
				</transition>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import TypeAndTagBar from './TypeAndTagBar'

    export default {
        name: "TypeAndTagShow",
	    components: {TypeAndTagBar}
    }
</script>

<style scoped>
	.type-tag {
		min-height: calc(100vh - 196px);
	}
</style>