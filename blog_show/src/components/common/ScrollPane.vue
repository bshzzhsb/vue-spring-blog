<template>
  <el-scrollbar ref="scrollbar" :vertical="false" class="scroll-pane"
                @wheel.native.prevent="handleScroll">
    <slot/>
  </el-scrollbar>
</template>

<script>
  const tagAndTagSpacing = 4;

  export default {
    name: "ScrollPane",
    data() {
      return {
        left: 0
      }
    },
    computed: {
      scrollWrapper() {
        return this.$refs.scrollbar.$refs.wrap;
      }
    },
    methods: {
      handleScroll(e) {
        const eventDelta = e.wheelDelta || -e.deltaY * 40;
        const $scrollWrapper = this.scrollWrapper;
        $scrollWrapper.scrollLeft = $scrollWrapper.scrollLeft + eventDelta / 4;
      },
      moveToTarget(currentTag) {
        const $scrollbar = this.$refs.scrollbar.$el;
        const $scrollbarWidth = $scrollbar.offsetWidth;
        const $scrollWrapper = this.scrollWrapper;
        const tagList = this.$parent.$refs.tag;

        let firstTag = null;
        let lastTag = null;

        if (tagList.length > 0) {
          firstTag = tagList[0];
          lastTag = tagList[tagList.length - 1];
        }
        if (firstTag === currentTag) {
          $scrollWrapper.scrollLeft = 0;
        } else if (lastTag === currentTag) {
          $scrollWrapper.scrollLeft = $scrollWrapper.scrollWidth - $scrollbarWidth;
        } else {
          const currentIndex = tagList.findIndex(item => item === currentTag);
          const prevTag = tagList[currentIndex - 1];
          const nextTag = tagList[currentIndex + 1];
          const afterNextTagOffsetLeft = nextTag.$el.offsetLeft + nextTag.$el.offsetWidth + tagAndTagSpacing;
          const beforePrevTagOffsetLeft = prevTag.$el.offsetLeft - tagAndTagSpacing;
          // 若 nextTag 未显示，向右滚动到刚好 nextTag 完全显示
          // 若 prevTag 未显示， 向左滚动到刚好 prevTag 完全显示
          if (afterNextTagOffsetLeft > $scrollWrapper.scrollLeft + $scrollbarWidth) {
            $scrollWrapper.scrollLeft = afterNextTagOffsetLeft - $scrollbarWidth;
          } else if (beforePrevTagOffsetLeft < $scrollWrapper.scrollLeft) {
            $scrollWrapper.scrollLeft = beforePrevTagOffsetLeft;
          }
        }
      }
    }
  }
</script>

<style scoped>
  .scroll-pane {
    white-space: nowrap;
    position: relative;
    overflow: hidden;
    width: 100%;
  }

  .scroll-pane /deep/ .el-scrollbar__bar {
    bottom: 0;
  }

  .scroll-pane /deep/ .el-scrollbar__wrap {
    height: 50px;
  }
</style>
