<template>
	<div class="type-tag">
		<type-and-tag-bar></type-and-tag-bar>
		<router-view />
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
			height: calc(100vh - 175px);
		}
	}
</style>