<template>
	<el-drawer :visible.sync="drawer"
	           size="50%" @closed="clearUpload"
	           :show-close="false">
		<p>选择图片</p>
		<el-upload ref="upload"
		           class="upload"
		           list-type="picture"
		           action="http://127.0.0.1:8088/hsblog/admin/upload"
		           :on-success="handleSuccess"
		           :auto-upload="false"
		           :show-file-list="true"
		           :mulitple="false" :limit="1">
			<el-button class="plus-btn" icon="el-icon-plus" circle></el-button>
		</el-upload>
		<el-button class="upload-btn" size="small" type="success" @click="submitUpload">
			上传到服务器
		</el-button>
		<el-input class="image-url" id="url" v-model="imageUrl"
		          readonly placeholder="URL">
			<template slot="prepend">图片URL</template>
			<el-button data-clipboard-target="#url"
			           data-clipboard-action="copy"
			           icon="el-icon-document-copy"
			           @click="copyUrl" id="copy-btn" slot="append">
				复制
			</el-button>
		</el-input>

	</el-drawer>
</template>

<script>
	import Clipboard from "clipboard"

    export default {
        name: "ImgUpload",
	    data() {
            return {
	            imageUrl: '',
	            drawer: false,
            }
	    },
	    methods: {
            handleSuccess(response) {
	            if (response.status === 200) {
	                this.imageUrl = response.object;
                    this.$message({
	                    message: '上传成功',
	                    type: 'success',
	                    duration: 2000,
                    });
	            }
            },
            submitUpload() {
                this.$refs.upload.submit();
            },
            copyUrl() {
	            var clipboard = new Clipboard("#copy-btn");
	            clipboard.on('success', e => {
                    this.$message({
                        message: '复制成功',
                        type: 'success',
                        duration: 1000,
                    });
                    clipboard.destroy();
	            });
                clipboard.on('error', e => {
                    this.$message({
                        message: '复制失败',
                        type: 'error',
                        duration: 1000,
                    });
                    clipboard.destroy();
                })
            },
            clearUpload() {
                this.imageUrl = '';
                this.$refs.upload.clearFiles();
            }
	    }
    }
</script>

<style scoped>
	.el-input /deep/ .el-input-group__prepend,
	.el-input /deep/ .el-input-group__append {
		padding: 0 10px !important;
	}
	.upload /deep/ .el-upload-list {
		width: 80%;
		margin: auto;
	}
	.plus-btn {
		margin-top: 10px;
		margin-bottom: 10px;
	}
	.image-url {
		width: 80%;
	}
	.upload-btn {
		margin: 10px auto;
		display: block !important;
	}
</style>