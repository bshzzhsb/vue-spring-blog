<template>
  <div>
    <tag-table ref="tagTable" @selectTag="selectTag"></tag-table>
    <blogs ref="tagBlog"></blogs>
    <pagination ref="pagination" @selectPage="selectPage"></pagination>
  </div>
</template>

<script>
  import TagTable from "./TagTable";
  import Blogs from "../index/Blogs";
  import Pagination from "../../common/Pagination";

  export default {
    name: "TagShow",
    components: {TagTable, Blogs, Pagination},
    data() {
      return {
        id: this.$route.query.id,
      }
    },
    mounted() {
      this.getTag();
    },
    methods: {
      getTag() {
        this.axios.get("/tag")
          .then(response => {
            if (response.data.status === 200) {
              this.$refs.tagTable.tagList = response.data.object;
              this.id = response.data.object[0].id;
              if(!this.$route.query.tagId) this.$router.push({query: {'tagId': this.id}});
              this.getTagBlogs(0);
            }
          })
          .catch(error => {
            console.log(error);
          })
      },
      getTagBlogs(page = 0) {
        this.$refs.tagBlog.loading = true;
        this.axios.get("/tag/" + this.id + "?page=" + page)
          .then(response => {
            if (response.data.status === 200) {
              var object = response.data.object;
              this.$refs.tagBlog.blogPage = object.content;
              this.$refs.pagination.totalPages = object.totalPages;
              this.$refs.pagination.currentPage = object.number + 1;
              this.$nextTick(() => {
                document.body.scrollTop;
                window.scrollTo({
                  top: 0,
                  left: 0,
                  behavior: "smooth",
                });
                this.$refs.tagBlog.loading = false;
              })
            }
          })
          .catch(error => {
            console.log(error)
          })
      },
      selectPage(page) {
        this.getTagBlogs(page);
      },
      selectTag(id) {
        this.$router.push({
          query: {'tagId': id},
        });
        this.id = id;
        this.getTagBlogs(0);
      }
    }
  }
</script>

<style scoped>

</style>
