<template>
	<div>
		<el-form ref="form" label-width="80px" :model="registerForm">
			<el-form-item label="账号">
				<el-input v-model="registerForm.username"></el-input>
			</el-form-item>
			<el-form-item label="密码">
				<el-input v-model="registerForm.password"></el-input>
			</el-form-item>
			<el-form-item label="昵称">
				<el-input v-model="registerForm.nickname"></el-input>
			</el-form-item>
			<el-form-item label="邮箱">
				<el-input v-model="registerForm.email"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="onSubmit">立即创建</el-button>
				<el-button>取消</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
    export default {
        name: "Register",
	    data() {
            return {
                registerForm: {
                    username: '',
	                password: '',
	                nickname: '',
	                email: '',
                }
            }
	    },
	    methods: {
            onSubmit() {
                this.axios.post("/register", {
                    username: this.registerForm.username,
	                password: this.registerForm.password,
	                nickname: this.registerForm.nickname,
	                email: this.registerForm.nickname,
                })
	                .then(response => {
	                    console.log(response);
	                })
	                .catch(error => {
	                    console.log(error);
	                })
            }
	    }
    }
</script>

<style scoped>

</style>