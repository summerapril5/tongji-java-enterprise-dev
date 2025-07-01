<template>
  <div class="onemain">
    <router-link :to="reporterNamePath"><div>投诉人：{{ oneUserComplainInfo.reporterName }}</div></router-link>
    <router-link :to="reportedUserNamePath"><div>被投诉人：{{ oneUserComplainInfo.reportedUserName }}</div></router-link>
    <div>投诉原因：{{ oneUserComplainInfo.reportReason }}</div>
    <div>投诉时间：{{ oneUserComplainInfo.reportTime }}</div>
    <div @click="choosePass" class="button" style="background-color: green;">处理完毕</div>
  </div>
</template>

<script setup>
import { UserComplainInfo } from '@/utils/structures';
import { defineProps, defineEmits } from 'vue';
import { userComplainProcess } from '@/apis/admin';

const props = defineProps({
  oneUserComplainInfo: {
    type: UserComplainInfo,
    default: null
  },
  num: {
    type: Number,
    default: 0
  }
})

const choosePass=async()=>{
  await userComplainProcess(props.oneUserComplainInfo.reportId)
  deleteConfirm()
}

let reporterNamePath='/userhomepage/'+props.oneUserComplainInfo.reporterId
let reportedUserNamePath='/userhomepage/'+props.oneUserComplainInfo.reportedUserId

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