<template>
	<div class="type-table">
		<el-tag :key="item.id" v-for="(item,index) in typeList"
		        @click="selectType(item.id)" :class="item.id==activeId?'active':''">
			{{item.name}}
		</el-tag>
	</div>
</template>

<script>
    export default {
        name: "TypeTable",
        data() {
            return {
                typeList: [],
            }
        },
	    computed: {
            activeId() {
                return this.$route.query.typeId;
            }
	    },
        methods: {
            selectType(id) {
                this.$emit("selectType", id);
            },
        }
    }
</script>

<style scoped>
	.type-table {
		text-align: left;
		margin-bottom: 2em;
	}
	.el-tag {
		cursor: pointer;
	}
	.el-tag {
		margin: 5px 5px;
		display: inline-block;
	}
	.el-tag:nth-child(5n+1) {
		background-color: #f0f9eb;
		border: 1px solid #e1f3d8;
		color: #67c23a;
	}
	.el-tag:nth-child(5n+2) {
		background-color: #ecf5ff;
		border: 1px solid #d9ecff;
		color: #409eff;
	}
	.el-tag:nth-child(5n+3) {
		background-color: #f4f4f5;
		border: 1px solid #e9e9eb;
		color: #909399;
	}
	.el-tag:nth-child(5n+4) {
		background-color: #fdf6ec;
		border: 1px solid #faecd8;
		color: #e6a23c;
	}
	.el-tag:nth-child(5n+5) {
		background-color: #fef0f0;
		border: 1px solid #fde2e2;
		color: #f56c6c;
	}
	.active.el-tag {
		font-weight: 600;
		font-size: 16px;
		box-shadow: 0 2px 5px 2px rgba(110,110,110,.4)
	}
</style>