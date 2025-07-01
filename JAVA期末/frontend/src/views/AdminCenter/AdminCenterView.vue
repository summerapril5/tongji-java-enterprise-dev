<template>
  <div class="all">
    <div class="main">
      <div class="selectlist">
        <p @click="choosePostAudit">帖子审核</p>
        <p @click="chooseUserComplain">用户投诉</p>
        <p @click="choosePostComplain">帖子投诉</p>
        <p @click="chooseFeedbackProcess">反馈处理</p>
        <p @click="chooseCategoryAdd">新增分类</p>
      </div>
      <div class="component">
        <PostAuditComponent v-if="contentclass=='postaudit'"/>
        <UserComplainComponent v-else-if="contentclass=='usercomplain'"/>
        <PostComplainComponent v-else-if="contentclass=='postcomplain'"/>
        <FeedbackProcess v-else-if="contentclass=='feedbackprocess'"/>
        <CategoryAddComponent v-else/>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import PostAuditComponent from './components/PostAudit.vue';
import UserComplainComponent from './components/UserComplain.vue';
import PostComplainComponent from './components/PostComplain.vue';
import CategoryAddComponent from './components/CategoryAdd.vue';
import FeedbackProcess from './components/FeedbackProcess.vue';

let contentclass = ref('postaudit')

function choosePostAudit() {
  contentclass.value = 'postaudit'
}
function chooseUserComplain() {
  contentclass.value = 'usercomplain'
}
function choosePostComplain() {
  contentclass.value = 'postcomplain'
}
function chooseCategoryAdd() {
  contentclass.value = 'categoryadd'
}
function chooseFeedbackProcess() {
  contentclass.value = 'feedbackprocess'
}
</script>

<style>
.all{
  background-color: lightcyan;
  background-size: cover; 
}

.main {
  width: 70%;
  margin: auto;
  margin-top: 200px;
}

.selectlist {
  width: 15%;
  float: left;
}

.selectlist p {
  display: block;
  width: 100%;
  height: 80px;
  background-color: #3064bb;
  color: white;
  text-decoration: none;
  text-align: center;
  line-height: 80px;
  font-size: 18px;
  margin-block-start: 0em;
  margin-block-end: 0em;
}

.selectlist p:hover {
  background-color: #608dd9;
}

.component {
  float: right;
  width: 80%;
}

.component > * {
  width: 100%;
}
</style>