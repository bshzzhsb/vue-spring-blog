<template>
  <el-row type="flex" justify="center">
    <el-col :xs="24" :sm="20" :md="16" :lg="14" :xl="12">
      <mood></mood>
    </el-col>
    <scroll-top></scroll-top>
  </el-row>
</template>

<script>
  import Mood from "./Mood";
  import ScrollTop from "../../common/ScrollTop";

  export default {
    name: "MoodShow",
    components: { ScrollTop, Mood },
    mounted() {
      this.$el.addEventListener('click', this.clickImgHandler)
    },
    beforeDestroy() {
      this.$el.removeEventListener('click', this.clickImgHandler)
    },
    methods: {
      clickImgHandler(e) {
        console.log(e)
        if (e.target.tagName === 'IMG') {
          let img = e.target;
          console.log(img.classList);
          if (img.classList.contains('full')) {
            img.classList.remove('full')
          } else {
            img.classList.add('full');
          }
        }
      }
    }
  }
</script>

<style scoped>

</style>

<style lang="css">
  .mood-box .mood-content img.full {
    width: 100%;
  }
</style>
