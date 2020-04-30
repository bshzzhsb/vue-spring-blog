<template>
	<div class="index">
		<el-row type="flex" justify="center">
			<el-col :xs="24" :sm="24" :md="20" :lg="16" :xl="14">
				<blogs ref="indexBlogs"></blogs>
				<pagination @selectPage="getBlog" ref="pagination"></pagination>
			</el-col>
		</el-row>
		<scroll-top></scroll-top>
	</div>
</template>

<script>
	import Blogs from "./Blogs";
	import Pagination from "../../common/Pagination";
	import ScrollTop from "../../common/ScrollTop";

    export default {
        name: "Index",
        components: {ScrollTop, Blogs, Pagination},
        data() {
            return {
            }
	    },
	    mounted() {
            this.getBlog();
	    },
	    updated() {

	    },
	    methods: {
            getBlog(page=0) {
                this.$refs.indexBlogs.loading = true;
                this.axios.get("/blog?page="+page)
	                .then(response => {
	                    if (response.data.status === 200) {
	                        var object = response.data.object;
                            this.$refs.indexBlogs.blogPage = object.content;
                            this.$refs.indexBlogs.loading = false;
                            this.$nextTick(() => {
	                            document.body.scrollTop;
                                window.scrollTo({
                                    top: 0,
                                    left: 0,
                                    behavior: "smooth",
                                });
                            })
                            this.$refs.pagination.totalPages = object.totalPages;
                            this.$refs.pagination.currentPage = object.number+1;
                        }
	                })
	                .catch(error => {
                        console.log(error)
	                });
            },
	    }
    }
</script>

<style scoped>
	.index {
		min-height: calc(100vh - 196px);
	}
</style>