<template>
	<div class="login">
		<img class="logo" src="../../assets/images/logo-fz.png" alt="">
		<el-row type="flex" justify="center">
			<el-col :xs="24" :sm="12" :md="10" :lg="8">
				<el-card class="box-card">
					<el-form ref="form" :model="loginForm" label-width="0">
						<el-form-item>
							<el-input v-model="loginForm.username" @focus="nameph=true" @blur="nameph=false"></el-input>
							<div class="input-placeholder">
								<div class="placeholder-first"></div>
								<div class="placeholder-second">
									<div :class="nameph || loginForm.username ? 'placeholder-second-focus' : ''">账号</div>
								</div>
								<div class="placeholder-third"></div>
							</div>
						</el-form-item>
						<el-form-item>
							<el-input v-model="loginForm.password" @focus="passph=true" @blur="passph=false" show-password></el-input>
							<div class="input-placeholder">
								<div class="placeholder-first"></div>
								<div class="placeholder-second">
									<div :class="passph || loginForm.password ? 'placeholder-second-focus' : ''">密码</div>
								</div>
								<div class="placeholder-third"></div>
							</div>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="onSubmit">登录</el-button>
						</el-form-item>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script>
    export default {
        name: "Login",
	    data() {
            return {
                loginForm: {
                    username: '',
	                password: '',
                },
	            nameph: false,
	            passph: false,
            }
	    },
	    methods: {
            onSubmit() {
	            this.axios.post("/admin/login", {
	                username: this.loginForm.username,
		            password: this.loginForm.password,
	            })
		            .then(response => {
		                console.log(response);
		                if (response.data.status === 200) {
			                this.$store.commit('login', response.data.object);
			                var path = this.$route.query.redirect;
			                this.$router.replace({ path: path === undefined ? '/admin/index' : path})
		                }
		            })
		            .catch(error => {
		                console.log(error);
		            })
            },
	    }
    }
</script>

<style scoped>
	.logo {
		width: 10em;
	}
	.login {
		position: absolute;
		width: 100%;
		top: 50%;
		left: 50%;
		transform: translate(-50%,-50%);
		margin-top: -4em;
	}
	.el-form-item:last-child{
		margin-bottom: 0;
	}
	.el-form-item:last-child /deep/ .el-form-item__content {
		display: block;
	}
	.el-form-item /deep/ .el-form-item__content {
		display: flex;
	}
	.input-placeholder {
		display: flex;
		position: absolute;
		left: 0;
		right: 0;
		width: 100%;
		height: 100%;
		pointer-events: none;
	}
	.el-input {
		display: flex;
		height: 48px;
	}
	.el-input /deep/ input {
		padding: 10px 15px;
		height: 100%;
	}
	.placeholder-first {
		width: 12px;
	}
	.placeholder-second {
		font-size: 16px;
		height: 48px;
		line-height: 48px;
		position: relative;
	}
	.placeholder-second div {
		top: 14px;
		height: 20px;
		line-height: 20px;
		padding: 0 2px;
		position: relative;
		background-color: #fff;
		color: rgba(44, 62, 80, 0.5);
		transition: transform 0.3s ease-in-out;
	}
	.placeholder-second-focus {
		transform: translateY(-24px) scale(0.8);
		-webkit-transform: translateY(-24px) scale(0.8);
	}
</style>