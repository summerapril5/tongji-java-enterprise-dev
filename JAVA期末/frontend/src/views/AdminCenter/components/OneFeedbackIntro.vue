<template>
  <div class="onemain">
    <router-link :to="feedbackUserPath"><div>反馈人：{{ oneFeedbackInfo.feedbackUserName }}</div></router-link>
    <div>反馈内容：{{ oneFeedbackInfo.feedbackContent }}</div>
    <div>反馈时间：{{ oneFeedbackInfo.feedbackTime }}</div>
    <div @click="choosePass" class="button" style="background-color: green;">处理完毕</div>
  </div>
</template>

<script setup>
import { FeedbackInfo } from '@/utils/structures';
import { defineProps, defineEmits, ref } from 'vue';
import { feedbackProcess } from '@/apis/admin';

const props = defineProps({
  oneFeedbackInfo: {
    type: FeedbackInfo,
    default: null
  },
  num: {
    type: Number,
    default: 0
  }
})

let feedbackUserPath=ref('/userhomepage/'+props.oneFeedbackInfo.feedbackUserId)

const choosePass=async()=>{
  await feedbackProcess(props.oneFeedbackInfo.feedbackId)
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