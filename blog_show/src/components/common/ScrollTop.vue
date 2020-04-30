<template>
	<transition name="scroll-top"
	            enter-active-class="animated bounceInUp fast"
	            leave-active-class="animated bounceOutUp fast">
		<div class="scroll-top" @click="scrollTop" v-show="visible">
			<svg class="iconfont" style="font-size: 3em" aria-hidden="true">
				<use xlink:href="#iconhuojian"></use>
			</svg>
		</div>
	</transition>
</template>

<script>
	import 'animate.css'

    export default {
        name: "ScrollTop",
	    data() {
            return {
                visible: false,
            }
	    },
	    mounted() {
            window.addEventListener("scroll", this.scrollF);
	    },
	    beforeDestroy() {
            window.removeEventListener("scroll", this.scrollF);
	    },
	    methods: {
            scrollTop() {
	            window.scrollTo({
		            top: 0,
		            behavior: "smooth",
	            })
            },
            scrollF() {
                this.visible = document.documentElement.scrollTop > 40;
            }
	    }
    }
</script>

<style scoped>
	.scroll-top {
		position: fixed;
		bottom: 3em;
		right: 2em;
		cursor: pointer;
	}
</style>