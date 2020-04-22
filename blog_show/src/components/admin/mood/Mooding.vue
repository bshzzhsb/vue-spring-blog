<template>
	<div class="mood-container">
		<div class="mood-box">
			<div class="mood-header">
				<span class="header-name">博思何在</span>
				<div class="mood-profile-container">
					<img src="../../../assets/images/doraemon.jpg" alt="" class="mood-profile">
				</div>
				<div class="header-btn">
					<el-tooltip effect="dark" content="保存" placement="top">
						<a @click.prevent="onSave">
							<svg class="iconfont" style="font-size: 2em" aria-hidden="true">
								<use xlink:href="#iconyueqiu"></use>
							</svg>
						</a>
					</el-tooltip>
					<el-tooltip effect="dark" content="发布" placement="top">
						<a @click.prevent="onSubmit">
							<svg class="iconfont" style="font-size: 2em" aria-hidden="true">
								<use xlink:href="#icondiqiu"></use>
							</svg>
						</a>
					</el-tooltip>
				</div>
			</div>
			<div class="mood-input">
				<el-form ref="form" :model="mood" :rules="rules">
					<el-form-item prop="content">
						<el-input type="textarea" :rows="2"
						          placeholder="记录此刻心情"
						          v-model="mood.content">
						</el-input>
					</el-form-item>
					<el-form-item>
						<el-icon class="el-icon-upload" @click.native="showDrawer"></el-icon>
						<el-checkbox v-model="mood.commentable">评论</el-checkbox>
					</el-form-item>
				</el-form>
			</div>
		</div>
		<img-upload ref="imgUpload"></img-upload>
	</div>
</template>

<script>
	import ImgUpload from "../common/ImgUpload";

    export default {
        name: "Mooding",
        components: {ImgUpload},
        data() {
            return {
                mood: {
                    content: '',
	                commentable: true,
	                published: false,
                },
	            rules: {
                    content: [
	                    { required: true, message: '请输入动态内容', trigger: 'blur' },
                    ]
	            }
            }
	    },
	    methods: {
            showDrawer() {
                this.$refs.imgUpload.drawer=true;
            },
            onSubmit() {
                this.mood.published = true;
	            this.axios.post("/admin/mood", this.mood)
		            .then(response => {
		                if (response.data.status === 200) {
                            this.$message.success("发布成功");
                            this.$router.push({ path: '/admin/mood' });
		                }
		            })
		            .catch(error => {
		                console.log(error);
		            })
            },
		    onSave() {

		    }
	    }
    }
</script>

<style scoped>
	.mood-container {
		min-height: calc(100vh - 198px);
	}
	.mood-box {
		text-align: left;
		border: 1px solid rgba(0,0,0,0.1);
		border-radius: 6px;
	}
	.mood-header {
		padding: 5px 1em;
		height: 40px;
		line-height: 40px;
		position: relative;
		border-bottom: 1px solid rgba(0,0,0,0.05);
	}
	.mood-profile-container {
		width: 40px;
		height: 40px;
		display: block;
		position: relative;
		border-radius: 50%;
		float: left;
		border: 0.5px solid rgba(0,0,0,0.05);
	}
	.mood-profile {
		width: 40px;
		height: 40px;
		border-radius: 50%;
		box-shadow: 2px 2px 3px rgb(224,224,224) !important;
		max-width: 100%;
	}
	.mood-header .header-name {
		padding-left: 10px;
	}
	.mood-header .header-btn {
		float: right;
		height: 40px;
		line-height: 40px;
		margin-top: 4px;
	}
	.mood-header .header-btn a {
		cursor: pointer;
	}
	.mood-input {
		margin-top: 10px;
	}
	.mood-input .el-form {
		padding: 5px 1em;
	}
	.mood-input /deep/ textarea {
		min-height: 100px !important;
	}
	.mood-input .el-form-item:first-child {
		margin-bottom: 14px !important;
	}
	.mood-input .el-form-item:nth-child(2) {
		margin-bottom: 3px !important;
	}
	.mood-input .el-icon-upload {
		font-size: 30px;
		cursor: pointer;
	}
	.mood-input .el-checkbox {
		margin-top: -4px;
		float: right;
	}
</style>