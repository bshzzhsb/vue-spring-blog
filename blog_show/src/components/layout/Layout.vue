<template>
	<div :class="classObj">
		<div v-if="device==='mobile'&&sidebar.opened"
		     class="drawer-bg" @click="handleClickOutside">
		</div>
		<side-bar class="sidebar-container"></side-bar>
		<el-container class="main-container" ref="container">
			<el-header height="84px">
				<nav-bar></nav-bar>
				<tags-view></tags-view>
			</el-header>
			<el-main class="main">
				<transition name="main" mode="out-in">
					<keep-alive :include="cachedViews">
						<router-view :key="key" />
					</keep-alive>
				</transition>
			</el-main>
			<el-footer class="footer" height="72px">
				<Footer></Footer>
			</el-footer>
		</el-container>
	</div>
</template>

<script>
	import SideBar from "./common/SideBar"
	import TagsView from '../common/TagsView'
	import Footer from "../common/Footer";
	import NavBar from "./common/NavBar";
	import ResizeHandler from "../admin/common/mixin/ResizeHandler";
	import { mapState } from 'vuex'

    export default {
        name: "layout",
        components: {NavBar, Footer, TagsView, SideBar},
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
            },
            cachedViews() {
                return this.$store.state.tagsView.cachedViews;
            },
            key() {
                return this.$route.path;
            }
        },
        data() {
            return {
            }
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

<style scoped>
	.el-aside {
		position: fixed;
		height: 100vh;
		background-color: rgba(230,240,240,0.9);
		transition: width 0.2s ease;
	}
	.main-container {
		min-height: 100%;
		margin-left: 120px;
		transition: margin-left 0.2s ease;
	}
	.el-header,
	.el-footer {
		height: auto;
		padding: 0;
	}
	.main {
		overflow: visible !important;
		background-color: #f2f2f2;
	}
	.footer {
		background-color: #f2f2f2;
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
		position: fixed;
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
		width: 160px;
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
		width: 160px !important;
	}
	.openSidebar .main-container {
		margin-left: 160px !important;
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
		width: 160px !important;
	}
	.mobile.hideSidebar .sidebar-container {
		pointer-events: none;
		transition-duration: 0.3s;
		transform: translateX(-160px);
	}
</style>