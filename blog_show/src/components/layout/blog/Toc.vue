<template>
  <div class="toc-container">
    <div>
      <div v-for="(item,index) in titles" class="toc-item" :class="'toc'+item.type">
        <a slot="title" :href="item.href"
           :class="(index===activeToc?'active-toc':'')+' toc'" @click.prevent="goTo(item.href)">
          {{ item.name }}</a>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Toc",
    data() {
      return {
        titles: [],
        activeToc: 0,
      }
    },
    methods: {
      goTo(href) {
        document.querySelector(href).scrollIntoView({behavior: "smooth"});
      }
    }
  }
</script>

<style scoped>
  .toc-container {
    position: sticky;
    top: 1em;
    text-align: left;
  }

  .toc-item {
    position: relative;
    padding: 0 1em;
    margin-bottom: 5px;
  }

  .toc-item a {
    text-decoration: none;
    color: rgba(0, 0, 0, 0.6);
  }

  .toc-item a:hover {
    text-decoration: none;
    color: rgba(0, 0, 0, 1);
  }

  .toc2 ~ .toc3 a {
    margin-left: 16px;
  }

  .toc1 ~ .toc2 a {
    margin-left: 16px;
  }

  .toc1 ~ .toc3 a {
    margin-left: 32px;
  }

  .active-toc {
    font-weight: 600;
    color: rgba(0, 0, 0, 1) !important;
  }

  .active-toc::before {
    content: '';
    display: inline-block;
    width: 2px;
    height: 1em;
    margin-top: 4px;
    position: absolute;
    left: 0;
    background-color: var(--green);
  }
</style>
