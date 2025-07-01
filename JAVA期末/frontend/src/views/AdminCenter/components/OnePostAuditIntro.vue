<template>
  <div class="onemain">
    <div>帖子标题：{{ oneAuditPostInfo.postTitle }}</div>
    <router-link :to="authorNamePath"><div>发帖人：{{ oneAuditPostInfo.authorName }}</div></router-link>
    <div>帖子种类：{{ oneAuditPostInfo.categoryName }}</div>
    <div>发布时间：{{ oneAuditPostInfo.publishTime }}</div>
    <div @click="isPostDetail=true" class="button" style="background-color: blue;">详情</div>
    <div @click="choosePass" class="button" style="background-color: green;">审核通过</div>
    <div @click="chooseRefund" class="button" style="background-color: red;">审核不通过</div>
    <el-dialog title="帖子详情" v-model="isPostDetail">
      <div style="font-size: 30px;color: black;font-weight: 300;display: flex;justify-content: center;">{{ oneAuditPostInfo.postTitle }}</div>
      <div style="font-size: 15px;color: gray;font-weight: 100;display: flex;justify-content: center;">{{ oneAuditPostInfo.authorName }}</div>
      <div style="font-size: 15px;color: gray;font-weight: 100;display: flex;justify-content: center;">{{ oneAuditPostInfo.publishTime }}</div>
      <div style="font-size: 20px;color: black;font-weight: 200;">{{ oneAuditPostInfo.postContent }}</div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { AuditPostInfo } from '@/utils/structures';
import { defineProps, defineEmits } from 'vue';
import { postPass, postRefund } from '@/apis/admin';

const props = defineProps({
  oneAuditPostInfo: {
    type: AuditPostInfo,
    default: null
  },
  num: {
    type: Number,
    default: 0
  }
})

let isPostDetail=ref(false)

let authorNamePath='/userhomepage/'+props.oneAuditPostInfo.authorId

const choosePass=async()=>{
  await postPass(props.oneAuditPostInfo.postId)
  deleteConfirm()
}

const chooseRefund=async()=>{
  await postRefund(props.oneAuditPostInfo.postId)
  deleteConfirm()
}

let emit = defineEmits(['delete'])
let deleteConfirm = () => {
  emit('delete', props.num)
}
</script>

<style>
.onemain {
  height: auto;
  background-color: rgb(255, 255, 255);
  margin-bottom: 10px;
  border: 2px solid #a4a4a4b7;
  border-radius: 15px;
}

.button {
  width: 100px;
  height: 30px;
  border: 2px solid rgb(255, 255, 255);
  color: aliceblue;
  transition: transform 0.3s ease;
  text-align: center;
  margin-top: 1.5%;
  margin-left: 5%;
  display: inline-block;
  border-radius: 5px;
}

.button:hover {
  transform: scale(1.1);
}
</style>