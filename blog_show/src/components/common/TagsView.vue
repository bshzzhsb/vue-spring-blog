<template>
  <div class="tags-view">
    <scroll-pane class="tags-view-wrapper" ref="scrollPane">
      <router-link ref="tag"
                   v-for="tag in visitedViews" :key="tag.path"
                   :class="isActive(tag)?'active':''"
                   :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }"
                   tag="span" class="tags-view-item"
                   @click.middle.native="!isAffix(tag)?closeSelectedTag(tag):''"
                   @contextmenu.prevent.native="openMenu(tag, $event)">
        {{ tag.title }}
        <span v-if="!isAffix(tag)" class="el-icon-close" @click.prevent.stop="closeSelectedTag(tag)"></span>
      </router-link>
    </scroll-pane>
    <ul v-show="visible" :style="{left:left+'px', top:top+'px'}" class="contextmenu">
      <li @click="refreshSelectedTag(selectedTag)">刷新</li>
      <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)">关闭当前</li>
      <li @click="closeOthersTags">关闭其他</li>
      <li @click="closeAllTags(selectedTag)">关闭所有</li>
    </ul>
  </div>
</template>

<script>
  import ScrollPane from "./ScrollPane";
  import path from 'path'

  export default {
    name: "TagsView",
    components: {ScrollPane},
    data() {
      return {
        visible: false,
        top: 0,
        left: 0,
        selectedTag: {},
        affixTags: [],
      }
    },
    computed: {
      visitedViews() {
        return this.$store.state.tagsView.visitedViews;
      },
      routes() {
        return this.$store.state.permission.routes;
      },
    },
    watch: {
      $route() {
        this.addTags();
        this.moveToCurrentTag();
      },
      visible(value) {
        if (value) {
          document.body.addEventListener('click', this.closeMenu);
        } else {
          document.body.removeEventListener('click', this.closeMenu);
        }
      }
    },
    mounted() {
      this.initTags();
      this.addTags();
    },
    methods: {
      isActive(route) {
        return route.path === this.$route.path;
      },
      isAffix(tag) {
        return tag.meta && tag.meta.affix;
      },
      filterAffixTags(routes, basePath = '/') {
        let tags = [];
        routes.forEach(route => {
          if (this.isAffix(route)) {
            const tagPath = path.resolve(basePath, route.path);
            tags.push({
              fullPath: tagPath,
              path: tagPath,
              name: route.name,
              meta: {...route.meta}
            })
          }
          if (route.children) {
            const tempTags = this.filterAffixTags(route.children, route.path);
            if (tempTags.length >= 1) {
              tags = [...tags, ...tempTags];
            }
          }
        });
        return tags;
      },
      initTags() {
        const affixTags = this.affixTags = this.filterAffixTags(this.routes);
        for (const tag of affixTags) {
          if (tag.name) {
            this.$store.dispatch('tagsView/addVisitedView', tag);
          }
        }
      },
      addTags() {
        const {name} = this.$route;
        if (name) {
          this.$store.dispatch('tagsView/addView', this.$route);
        }
        return false;
      },
      moveToCurrentTag() {
        const tags = this.$refs.tag;
        this.$nextTick(() => {
          for (const tag of tags) {
            if (tag.to.path === this.$route.path) {
              this.$refs.scrollPane.moveToTarget(tag);
              if (tag.to.fullPath !== this.$route.fullPath) {
                this.$store.dispatch('tagsView/updateVisitedView', this.$route);
              }
              break;
            }
          }
        })
      },
      refreshSelectedTag(view) {
        this.$store.dispatch('tagsView/delCachedView', view)
          .then(() => {
            const {fullPath} = view;
            this.$nextTick(() => {
              this.$router.replace({
                path: '/redirect' + fullPath,
              })
            })
          })
      },
      closeSelectedTag(view) {
        this.$store.dispatch('tagsView/delView', view)
          .then(({visitedViews}) => {
            if (this.isActive(view)) {
              this.toLastView(visitedViews, view);
            }
          })
      },
      closeOthersTags() {
        this.$router.push(this.selectedTag);
        this.$store.dispatch('tagsView/delOthersViews', this.selectedTag)
          .then(() => {
            this.moveToCurrentTag();
          })
      },
      closeAllTags(view) {
        this.$store.dispatch('tagsView/delAllViews')
          .then(({visitedViews}) => {
            this.toLastView(visitedViews, view);
          })
      },
      toLastView(visitedViews, view) {
        const lastestView = visitedViews.slice(-1)[0];
        if (lastestView) {
          this.$router.push(lastestView.fullPath);
        }
      },
      openMenu(tag, e) {
        /**
         * clientX: event 事件的水平坐标(不计算滚动)
         * getBoundingClientRect(): 计算滚动，除 width, height 都是相对视图窗口左上角计算(计算滚动)
         */
        const menuMinWidth = 105;
        const offsetLeft = this.$el.getBoundingClientRect().left;
        const offsetWidth = this.$el.offsetWidth;
        const maxLeft = offsetWidth - menuMinWidth;
        const left = e.clientX - offsetLeft + 10;
        if (left > maxLeft) {
          this.left = maxLeft;
        } else {
          this.left = left;
        }
        this.top = e.clientY;
        this.visible = true;
        this.selectedTag = tag;
      },
      closeMenu() {
        this.visible = false;
      }
    }
  }
</script>

<style scoped>
  .tags-view {
    height: 34px;
    width: 100%;
    background-color: #fff;
    border-bottom: 1px solid #d8dce5;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
    text-align: left;
  }

  .tags-view-wrapper .tags-view-item {
    display: inline-block;
    position: relative;
    cursor: pointer;
    height: 26px;
    line-height: 26px;
    border: 1px solid #d8dce5;
    color: #495060;
    background-color: #fff;
    padding: 0 8px;
    font-size: 12px;
    margin-left: 5px;
    margin-top: 4px;
  }

  .tags-view-item:first-of-type {
    margin-left: 15px;
  }

  .tags-view-item:last-of-type {
    margin-right: 15px;
  }

  .tags-view-item.active {
    background-color: #42b983;
    color: #fff;
    border-color: #42b983;
  }

  .tags-view-item.active::before {
    content: '';
    background-color: #fff;
    display: inline-block;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    position: relative;
    margin-right: 2px;
  }

  .tags-view-item .el-icon-close {
    width: 16px;
    height: 16px;
    vertical-align: 2px;
    border-radius: 50%;
    text-align: center;
    transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
    transform-origin: 100% 50%;
  }

  .tags-view-item .el-icon-close:before {
    transform: scale(0.6);
    display: inline-block;
    vertical-align: -3px;
  }

  .tags-view-item .el-icon-close:hover {
    background-color: #b4bccc;
    color: #fff;
  }

  .contextmenu {
    margin: 0;
    background-color: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-right: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
  }

  .contextmenu li {
    margin: 0;
    padding: 7px 16px;
    cursor: pointer;
  }

  .contextmenu li:hover {
    background-color: #eee;
  }
</style>
