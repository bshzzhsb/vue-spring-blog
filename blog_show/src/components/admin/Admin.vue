<template>
	<div :class="classObj">
		<div v-if="device==='mobile'&&sidebar.opened"
		     class="drawer-bg" @click="handleClickOutside"></div>
		<admin-side-bar class="sidebar-container" />
		<el-container class="main-container" ref="container">
			<el-header>
				<admin-nav-bar @openSidebar="openSidebar"></admin-nav-bar>
			</el-header>
			<el-main class="main">
				<el-row type="flex" justify="center">
					<el-col :xs="24" :sm="24" :md="20" :lg="18" :xl="16">
						<transition name="main" mode="out-in">
							<router-view />
						</transition>
					</el-col>
				</el-row>
			</el-main>
			<el-footer>
				<admin-footer></admin-footer>
			</el-footer>
		</el-container>
	</div>
</template>

<script>
	import ResizeHandler from "./common/mixin/ResizeHandler";
	import { mapState } from 'vuex';
	import AdminSideBar from "./common/AdminSideBar";
	import AdminFooter from "./common/AdminFooter";
	import AdminNavBar from "./common/AdminNavBar";

    export default {
        name: "Admin",
        components: {AdminNavBar, AdminFooter, AdminSideBar},
	    mixins: [ResizeHandler],
	    computed: {
            ...mapState({
	            sidebar: state => state.app.sidebar,
	            device: state => state.app.device,
            }),
		    classObj() {
                return {
                    hideSidebar: !this.sidebar.opened,
                    openSidebar: this.sidebar.opened,
	                mobile: this.device === 'mobile',
                }
		    }
	    },
	    data() {
            return {
            }
	    },
	    beforeMount() {

	    },
	    methods: {
            openSidebar() {
                this.$store.dispatch('app/toggleSideBar');
		    },
            handleClickOutside() {
                this.$store.dispatch('app/closeSideBar');
            }
	    },
    }
</script>

<style module>
	.main-container {
		min-height: 100%;
		transition: margin-left 0.3s ease;
	}
</style>

<style scoped>
	.el-aside {
		position: fixed;
		height: 100vh;
		background-color: rgba(230,240,240,0.9);
		transition: width 0.2s ease;
	}
	.main-container {
		margin-left: 120px;
		transition: margin-left 0.2s ease;
	}
	.el-header,
	.el-footer {
		height: auto;
		padding: 0;
	}
	.main {
		overflow: hidden;
	}
	.main-enter {
		opacity: 0;
		transform: translateX(-50px);
	}
	.main-leave-to {
		opacity: 0;
		transform: translateX(50px);
	}
	.main-enter-active,
	.main-leave-active {
		transition: all 0.3s ease-in-out;
	}
	.drawer-bg {
		background-color: rgba(0,0,0,0.3);
		width: 100%;
		top: 0;
		height: 100%;
		position: absolute;
		z-index: 99;
	}
	.main-container {
		min-height: 100%;
		transition: margin-left 0.2s ease;
		margin-left: 120px;
		position: relative;
	}
	.sidebar-container {
		transition: width 0.2s ease;
		width: 120px;
		background-color: rgba(230,240,240,0.9);
		height: 100%;
		position: fixed;
		top: 0;
		bottom: 0;
		left: 0;
		z-index: 100;
		overflow: hidden;
	}
	.openSidebar .sidebar-container {
		width: 120px !important;
	}
	.openSidebar .main-container {
		margin-left: 120px !important;
	}
	.hideSidebar .sidebar-container {
		width: 60px !important;
	}
	.hideSidebar .main-container {
		margin-left: 60px !important;
	}
	.mobile .main-container {
		margin-left: 0 !important;
	}
	.mobile .sidebar-container {
		transition: transform 0.3s;
		width: 120px !important;
	}
	.mobile.hideSidebar .sidebar-container {
		pointer-events: none;
		transition-duration: 0.3s;
		transform: translateX(-120px);
	}
</style>