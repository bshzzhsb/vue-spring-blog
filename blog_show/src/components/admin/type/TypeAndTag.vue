<template>
	<div class="type-tag">
		<el-row type="flex" justify="center">
			<el-col :xs="24" :sm="24" :md="20" :lg="18" :xl="16">
				<type-and-tag-bar></type-and-tag-bar>
				<transition name="type-tag" mode="out-in">
					<router-view />
				</transition>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import TypeAndTagBar from "./TypeAndTagBar";

    export default {
        name: "TypeAndTag",
        components: {TypeAndTagBar}
    }
</script>

<style scoped>
	@media screen and (min-width: 768px) {
		.type-tag {
			height: calc(100vh - 198px);
		}
	}
	.type-tag-enter {
		opacity: 0;
		transform: translateX(-40px);
	}
	.type-tag-leave-to {
		opacity: 0;
		transform: translateX(40px);
	}
	.type-tag-enter-active,
	.type-tag-leave-active {
		transition: all 0.2s ease-in-out;
	}
</style>