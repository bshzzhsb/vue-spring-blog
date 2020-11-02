import hljs from "highlight.js"
import "highlight.js/styles/monokai.css"

let Highlight = {};
Highlight.install = function (Vue) {
  Vue.directive('highlight', {
    componentUpdated: function (el) {
      let blocks = el.querySelector('pre code');
      console.log(blocks)
      for (let i = 0; i < blocks.length; i++) {
        hljs.highlightBlock(blocks[i]);
      }
    },
  })
}

export default Highlight
