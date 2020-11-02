<template>
  <div ref="commentContainer" class="comment-container" v-show="visible">
    <div class="comment-box" v-for="(item,index) in comments" :key="item.id">
      <div class="comment-avatar">
        <img :src="item.avatar" alt="">
      </div>
      <div class="comment-header">
        <span class="comment-name">{{ item.nickname }}</span>
        <span v-if="item.adminMoodComment" class="comment-admin">博主</span>
      </div>
      <div class="comment-content">
        {{ item.content }}
      </div>
      <div class="comment-action">
        <span>{{ item.createTime | formatTime('{y}-{m}-{d}-{h}-{i}') }}</span>
        <span @click="replyComment(item.id, item.nickname, $event)" class="comment-button">回复</span>
      </div>
      <div v-if="item.replyMoodComments"
           v-for="(reply,i) in item.replyMoodComments" :key="reply.id"
           class="reply-box">
        <div class="reply-avatar">
          <img :src="reply.avatar" alt="">
        </div>
        <div class="reply-header">
          <span class="reply-name">{{ reply.nickname }}</span>
          <span v-if="reply.adminMoodComment" class="reply-admin">博主</span>
          @ <span>{{ reply.parentMoodComment.nickname }}</span>
        </div>
        <div class="reply-content">
          {{ reply.content }}
        </div>
        <div class="reply-action">
          <span>{{ reply.createTime | formatTime('{y}-{m}-{d}-{h}-{i}') }}</span>
          <span @click="replyComment(reply.id, reply.nickname, $event)" class="reply-button">回复</span>
        </div>
      </div>
    </div>
    <div ref="formContainer" class="reply-form">
      <el-form ref="form" :model="comment" :rules="rules">
        <el-form-item prop="content" class="reply-content">
          <el-input ref="reply" type="textarea" :rows="2" resize="none"
                    :placeholder="placeholder"
                    v-model="comment.content">
          </el-input>
          <div class="form-btn">
            <button v-if="cancelVisible" @click.prevent="cancelReply" class="cancel-btn">取消回复</button>
            <button class="reply-btn" @click.prevent="onSubmit">回复</button>
          </div>
        </el-form-item>
        <el-form-item prop="nickname" class="reply-item">
          <el-input placeholder="昵称" v-model="comment.nickname"></el-input>
        </el-form-item>
        <el-form-item prop="email" class="reply-item">
          <el-input placeholder="邮箱" v-model="comment.email"></el-input>
        </el-form-item>
        <el-form-item class="reply-item">
          <el-input placeholder="Web" v-model="comment.web"></el-input>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {getToken} from "../../utils/auth";
  import {getInfo, setInfo} from "../../utils/getInfo";

  export default {
    name: "Comment",
    data() {
      return {
        comments: [],
        comment: {
          content: '',
          nickname: '',
          email: '',
          web: '',
          mood: {id: ''},
          adminMoodComment: false,
          parentMoodComment: {id: -1},
        },
        rules: {
          content: [
            {required: true, message: '*', trigger: 'blur'},
          ],
          nickname: [
            {required: true, message: '请输入昵称', trigger: 'blur'}
          ],
          email: [
            {required: true, message: '请输入邮箱', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
          ],
        },
        visible: false,
        cancelVisible: false,
        placeholder: '回复',
      }
    },
    mounted() {
      let info = getInfo();
      if (info) {
        info = JSON.parse(info)
        this.comment.nickname = info.nickname;
        this.comment.email = info.email;
        this.comment.web = info.web;
      }
    },
    methods: {
      replyComment(id, nickname, event) {
        event.currentTarget.after(this.$refs.formContainer);
        this.cancelVisible = true;
        this.placeholder = `@${nickname}`;
        this.comment.parentMoodComment.id = id;
        this.$refs.reply.focus();
      },
      onSubmit() {
        if (getToken()) {
          this.comment.adminMoodComment = true;
        }
        this.$refs.form.validate((flag, object) => {
          if (flag) {
            if (!getInfo()) {
              setInfo(this.comment.nickname, this.comment.email, this.comment.web);
            }
            this.axios.post("/mood/comments", this.comment)
              .then(response => {
                if (response.data.status === 200) {
                  this.comment.content = '';
                  this.comment.parentMoodComment.id = -1;
                  this.$message.success("回复成功");
                  this.placeholder = '回复';
                  this.getComment();
                }
              })
              .catch(error => {
                console.log(error)
              })
          }
        })
      },
      getComment() {
        this.axios.get("/mood/" + this.comment.mood.id + "/comments")
          .then(response => {
            if (response.data.status === 200) {
              this.comments = response.data.object;
            }
          })
          .catch(error => {
            console.log(error)
          })
      },
      cancelReply() {
        this.cancelVisible = false;
        this.$refs.commentContainer.appendChild(this.$refs.formContainer);
        this.placeholder = '回复';
        this.comment.parentMoodComment.id = -1;
      }
    },
  }
</script>

<style scoped>
  .comment-container {
    margin: 10px 0;
  }

  .comment-box {
    margin-left: 60px;
    margin-right: 20px;
    padding: 1em 0 3px 0;
    position: relative;
    border-top: 1px solid rgba(0, 0, 0, 0.2);
  }

  .comment-avatar {
    display: inline-block;
    position: absolute;
    margin-left: -44px;
  }

  .comment-avatar img {
    width: 36px;
    height: 36px;
    border-radius: 50%;
  }

  .comment-header,
  .reply-header {
    font-size: 12px;
    margin-bottom: 4px;
  }

  .comment-header .comment-name,
  .reply-header .reply-name {
    font-weight: 600;
  }

  .comment-header .comment-admin,
  .reply-header .reply-admin {
    background-color: rgba(0, 0, 0, 0.2);
    padding: 1px 3px;
    margin-left: 4px;
    border-radius: 4px;
  }

  .comment-content,
  .reply-content {
    padding: 2px 0;
  }

  .comment-action,
  .reply-action {
    font-size: 12px;
    color: #99a2aa;
    border-radius: 4px;
    display: inline-block;
  }

  .comment-action .comment-button,
  .reply-action .reply-button {
    margin-left: 6px;
    cursor: pointer;
  }

  .comment-action .comment-button:hover,
  .reply-action .reply-button:hover {
    color: #00a1d6;
  }

  .comment-action .reply-form,
  .reply-action .reply-form {
    margin-left: 0 !important;
  }

  .reply-box {
    margin-left: 40px;
    margin-right: 20px;
    position: relative;
    padding: 6px 0;
  }

  .reply-avatar {
    display: inline-block;
    position: absolute;
    margin-left: -40px;
  }

  .reply-avatar img {
    width: 30px;
    height: 30px;
    border-radius: 50%;
  }

  .reply-form {
    margin-left: 20px;
    margin-right: 20px;
    font-size: 0;
  }

  .comment-container > .reply-form {
    margin-left: 60px;
  }

  .reply-form .el-form-item {
    margin-bottom: 6px;
  }

  .reply-form .reply-item {
    display: inline-block;
    width: 31.33%;
  }

  .reply-form .reply-item + .reply-item {
    margin-left: 3%;
  }

  .reply-form .reply-content /deep/ .el-form-item__content {
    font-size: 0;
    line-height: 0;
  }

  .reply-form .el-textarea {
    width: 80%;
  }

  .reply-form /deep/ textarea {
    font-size: 12px;
    display: inline-block;
    box-sizing: border-box;
    background-color: #f4f5f7;
    border: 1px solid #e5e9ef;
    overflow: auto;
    border-radius: 4px;
    color: #555;
    width: 100% !important;
    height: 65px;
    transition: 0s;
    padding: 5px 10px;
    line-height: normal;
  }

  .reply-form .form-btn {
    width: 18%;
    height: 65px;
    margin-left: 2%;
    display: inline-block;
  }

  .reply-form .reply-btn {
    width: 100%;
    height: 40px;
    font-weight: 600;
    font-size: 14px;
    cursor: pointer;
    background: -moz-linear-gradient(top, #fff, #ededed) !important;
    background: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#ededed)) !important;
    box-shadow: 0 1px 2px rgba(0, 0, 0, .2) !important;
    border-radius: 0.5rem !important;
    border: 1px solid #b7b7b7 !important;
  }

  .reply-form .reply-btn:nth-child(1) {
    height: 65px;
  }

  .reply-form .reply-btn:hover {
    background: -moz-linear-gradient(top, #fff, #dcdcdc) !important;
    background: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#dcdcdc)) !important;
  }

  .reply-form .cancel-btn {
    border: none;
    border-radius: 4px;
    width: 100%;
    height: 20px;
    line-height: 20px;
    margin-bottom: 5px;
    cursor: pointer;
    font-size: 12px;
    color: rgba(0, 0, 0, 0.7);
    background-color: rgba(0, 0, 0, 0.2);
  }

  .reply-form .cancel-btn:hover {
    background-color: rgba(0, 0, 0, 0.3);
  }

  .reply-form /deep/ input {
    font-size: 12px;
    padding: 5px 10px;
    background-color: #f4f5f7;
    border: 1px solid #e5e9ef;
    overflow-x: auto;
  }

  .reply-form /deep/ textarea:focus,
  .reply-form /deep/ input:focus {
    background-color: #fff;
  }

  @media screen and (max-width: 768px) {
    .reply-form .reply-item:nth-child(2),
    .reply-form .reply-item:nth-child(3) {
      width: 48.5%;
    }

    .reply-form .reply-item:nth-child(4) {
      margin-left: 0;
      width: 100%;
    }
  }
</style>
