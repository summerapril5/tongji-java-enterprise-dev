<template>
  <div class="componentmain">
    <div class="spinner" v-if="isLoading"></div>
    <div class="componentlist" v-else>
      <div class="empty" v-if="isEmpty">无</div>
      <div v-for="(item, index) in feedbackInfos" :key="item.feedbackId" class="table" v-else>
        <OneFeedbackIntro :oneFeedbackInfo="item" :num="index" @delete="deleteFeedback" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { FeedbackInfo } from '@/utils/structures';
import { ref, onMounted } from 'vue'
import OneFeedbackIntro from './OneFeedbackIntro.vue';
import { getFeedbackInfo } from '@/apis/admin';

let isLoading=ref(true)
let isEmpty=ref(false)
let feedbackInfos=ref([])

const deleteFeedback = (index) => {
  feedbackInfos.value.splice(index, 1)
  if(feedbackInfos.value.length==0){
        isEmpty.value=true;
  }
}

onMounted(async () => {
  let getFeedbackInfos=await getFeedbackInfo()
  if(getFeedbackInfos!=null){
    for(let oneGetInfo of getFeedbackInfos){
      let oneInfo=new FeedbackInfo(oneGetInfo.feedbackId, oneGetInfo.feedbackContent, oneGetInfo.feedbackUserId, oneGetInfo.feedbackUserName, oneGetInfo.feedbackTime)
      feedbackInfos.value.push(oneInfo)
    }
  }
  isLoading.value=false
  if(feedbackInfos.value.length==0){
    isEmpty.value=true;
  }
})
</script>

<style>
.componentmain {
  position: relative;
}

.componentlist {
  max-height: 650px;
  overflow-y: auto;
}

.empty{
  margin-top: 100px;
  margin-left: 50%;
  font-size: 40px;
  font-weight: bold; 
}

.table {
  background-color: rgb(255, 255, 255);
  width: 100%;
}

.spinner {
  border: 4px solid rgba(0, 0, 0, 0.1);
  border-radius: 50%;
  border-top: 4px solid #3498db;
  width: 80px;
  height: 80px;
  animation: spin 1s linear infinite;
  position: absolute;
  top: 100px;
  left: 50%;
  transform: translateX(-50%);
}
</style>