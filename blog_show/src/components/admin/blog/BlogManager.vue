<template>
	<div class="blog-manager">
		<el-row type="flex" justify="center">
			<el-col :xs="24" :sm="24" :md="20" :lg="18" :xl="16">
				<div class="searchbar">
					<search-bar ref="searchbar" @blogQuery="blogQuery"></search-bar>
				</div>
				<blog-table ref="blogTable" @getBlog="blogQuery"></blog-table>
				<pagination ref="pagination" @selectPage="blogQuery"></pagination>
				<new-blog @newBlog="newBlog"></new-blog>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import BlogTable from "./BlogTable";
	import SearchBar from "./SearchBar";
	import NewBlog from "./NewBlog";
	import Pagination from "./Pagination";

    export default {
        name: "BlogManager",
        components: {Pagination, NewBlog, SearchBar, BlogTable},
        data() {
            return {
                blogPage: [],
            }
	    },
	    created() {
	    },
	    methods: {
		    blogQuery(page=0) {
		        var blogQuery = this.$refs.searchbar.blogQuery;
		        var query = {
                    title: blogQuery.title,
                    typeId: blogQuery.typeId,
                    recommend: blogQuery.recommend,
                };
		        this.axios.post('/admin/blog/search?page='+page, query)
			        .then(response => {
			            if (response.data.status === 200) {
			                let object = response.data.object;
			                this.$refs.blogTable.blogPage = object.content;
			                this.$refs.pagination.totalPages = object.totalPages;
			                this.$refs.pagination.currentPage = object.number+1;
			            }
			        })
			        .catch(error => {
			            console.log(error);
			        })
		    },
            newBlog() {
		        this.$router.push({ path: '/admin/blogging'})
            }
	    }
    }
</script>

<style scoped>
	@media screen and (min-width: 768px) {
		.blog-manager {
			height: calc(100vh - 198px);
		}
	}
</style>