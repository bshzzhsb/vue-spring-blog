<template>
  <div>
    <div class="burger-menu" @click="openSidebar">
      <div class="line"></div>
      <div class="line"></div>
      <div class="line"></div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Hamburger",
    methods: {
      openSidebar() {
        this.$emit("openSidebar");
      }
    }
  }
</script>

<style scoped>
  .openSidebar .burger-menu .line:nth-child(1) {
    transform: translateY(calc(var(--burger-menu-radius) / 5)) rotate(45deg);
  }

  .openSidebar .burger-menu .line:nth-child(2) {
    transform: scale(0);
  }

  .openSidebar .burger-menu .line:nth-child(3) {
    transform: translateY(calc(var(--burger-menu-radius) / -5)) rotate(-45deg);
  }

  .burger-menu {
    --burger-menu-radius: 2.5em;
    position: relative;
    z-index: 100;
    display: block;
    width: var(--burger-menu-radius);
    height: var(--burger-menu-radius);
    border: none;
    outline: none;
    cursor: pointer;
    transition: 0.5s ease-in-out;
  }

  .line {
    position: absolute;
    left: 25%;
    width: 50%;
    height: 2px;
    background: hsla(210, 29%, 24%, 0.3);
    border-radius: 10px;
    overflow: hidden;
    transition: all 0.5s ease;
  }

  .line:nth-child(1) {
    top: 30%;
  }

  .line:nth-child(2) {
    top: 50%;
  }

  .line:nth-child(2)::after {
    transition-delay: 0.1s;
  }

  .line:nth-child(3) {
    top: 70%;
  }

  .line:nth-child(3)::after {
    transition-delay: 0.2s;
  }

  .line::after {
    position: absolute;
    content: "";
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #2980b9;
    transform: translateX(-100%);
    transition: all 0.25s ease;
  }

  .burger-menu:hover .line::after {
    transform: translateX(0);
  }
</style>
