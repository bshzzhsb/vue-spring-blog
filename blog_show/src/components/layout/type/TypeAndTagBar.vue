<template>
	<div class="type-tag-bar">
		<span v-for="(item,index) in TypeAndTag" :key="item.path" class="type-tag-nav">
			<router-link :to="item.path">{{ item.name }}</router-link>
		</span>
	</div>
</template>

<script>
    export default {
        name: "TypeAndTagBar",
        data() {
            return {
                TypeAndTag: [
                    { path: '/type', name: '分类' },
                    { path: '/tag', name: '标签' }
                ],
            }
        },
        methods: {

        }
    }
</script>

<style scoped>
	.type-tag-bar {
		text-align: left;
		margin-bottom: 1em;
	}
	.type-tag-nav {
		text-align: center;
		display: inline-block;
		background-color: #fff;
		border: 1px solid rgba(0,0,0,0.3);
		border-radius: 2px;
	}
	.type-tag-nav + .type-tag-nav {
		margin-left: 10px;
	}
	.type-tag-nav a {
		display: inline-block;
		width: 48px;
		height: 25px;
		line-height: 25px;
		color: var(--dark);
	}
	.type-tag-bar .router-link-active {
		background-color: #42b983;
		color: var(--white);
	}
	a:hover {
		text-decoration: none;
	}
</style>