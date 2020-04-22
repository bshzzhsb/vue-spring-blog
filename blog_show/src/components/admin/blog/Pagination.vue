<template>
	<div>
		<ul class="pagination" v-if="totalPages>1">
			<li class="page-prev page-number" @click="selectPage(currentPage-1)">
				<svg class="iconfont" style="font-size: 1.2em" aria-hidden="true">
				    <use xlink:href="#iconarrowsleftline"></use>
				</svg>
			</li>
			<li v-for="(item,index) in showPageNums" :key="index"
			    :class="[item===currentPage?'active-page':'',item===-1||item===-2?'ignore-page':'page-number']"
			    @click="selectPage(item)">
				{{ item }}
			</li>
			<li class="page-next page-number" @click="selectPage(currentPage+1)">
				<svg class="iconfont" style="font-size: 1.2em" aria-hidden="true">
				    <use xlink:href="#iconarrowsrightline1"></use>
				</svg>
			</li>
		</ul>
	</div>
</template>

<script>
    export default {
        name: "Pagination",
        computed: {
            showPageNums() {
                var res = [];
                if (this.totalPages < 7) {
                    res = Array.from({length:this.totalPages}, (v,k) => k+1);
                } else{
                    let start = this.currentPage - 2;
                    let end = this.currentPage + 2;
                    res.push(1);
                    if (start <= 2) {
                        start = 2;
                    } else {
                        res.push(-1);
                    }
                    for (let i=start; i<this.currentPage; i++) {
                        res.push(i);
                    }
                    var flag = false;
                    if (end >= this.totalPages - 1) {
                        end = this.totalPages - 1;
                    } else {
                        flag = true;
                    }
                    if (this.currentPage !== 1 && this.currentPage !== this.totalPages) {
                        res.push(this.currentPage);
                    }
                    for (let i=this.currentPage+1; i<=end; i++) {
                        res.push(i);
                    }
                    if (flag) {
                        res.push(-2);
                    }
                    res.push(this.totalPages);
                }
                return res;
            },
        },
	    data() {
            return {
                totalPages: '',
                currentPage: '',
            }
	    },
	    methods: {
            selectPage(pageNum) {
                if (pageNum <= 0 || pageNum > this.totalPages) {
                    this.$message.info("我到底了");
                } else {
                    this.$emit("selectPage", pageNum - 1);
                }
            }
	    }
    }
</script>

<style scoped>
	.pagination {
		font-size: 0;
		display: inline-flex;
		padding: 5px 10px;
		background-color: var(--white);
		border-radius: 30px;
		box-shadow: 0 0.3px 0.6px rgba(0, 0, 0, 0.056), 
			0 0.7px 1.3px rgba(0, 0, 0, 0.081),
			0 1.3px 2.5px rgba(0, 0, 0, 0.1),
			0 2.2px 4.5px rgba(0, 0, 0, 0.119),
			0 4.2px 8.4px rgba(0, 0, 0, 0.144),
			0 10px 20px rgba(0, 0, 0, 0.2);
		list-style-type: none;
	}
	.pagination .page-number {
		margin: 0 5px;
		width: 30px;
		height: 30px;
		line-height: 30px;
		border-radius: 50%;
		text-align: center;
		display: inline-block;
		font-size: 14px;
		transition: 0.3s;
		cursor: pointer;
	}
	.pagination .page-number:hover {
		color: var(--white);
		background-color: #777;
	}
	.pagination .page-number.active-page {
		color: var(--white);
		background-color: #333;
	}
	.pagination .ignore-page {
		display: inline-block;
		position: relative;
		margin: 0 5px;
		width: 30px;
		height: 30px;
		line-height: 30px;
	}
	.pagination .ignore-page::before {
		content: '...';
		position: absolute;
		z-index: 1;
		width: 30px;
		height: 30px;
		line-height: 30px;
		font-size: 20px;
		left: 0;
		top: -6px;
	}
</style>