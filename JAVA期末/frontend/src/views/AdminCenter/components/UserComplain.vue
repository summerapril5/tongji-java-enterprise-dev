<template>
  <div class="componentmain">
    <div class="spinner" v-if="isLoading"></div>
    <div class="componentlist" v-else>
      <div class="empty" v-if="isEmpty">无</div>
      <div v-for="(item, index) in userComplainInfos" :key="item.reportId" class="table" v-else>
        <OneUserComplainIntro :oneUserComplainInfo="item" :num="index" @delete="deleteUserComplain" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { UserComplainInfo } from '@/utils/structures';
import { ref, onMounted } from 'vue'
import OneUserComplainIntro from './OneUserComplainIntro.vue';
import { getUserComplainInfo } from '@/apis/admin';

let isLoading=ref(true)
let isEmpty=ref(false)
let userComplainInfos=ref([])

const deleteUserComplain = (index) => {
  userComplainInfos.value.splice(index, 1)
  if(userComplainInfos.value.length==0){
    isEmpty.value=true;
  }
}

onMounted(async () => {
  let getUserComplainInfos=await getUserComplainInfo()
  if(getUserComplainInfos!=null){
    for(let oneGetInfo of getUserComplainInfos){
      let oneInfo=new UserComplainInfo(oneGetInfo.reportId,oneGetInfo.reportReason,oneGetInfo.reporterId,oneGetInfo.reporterName,oneGetInfo.reportedUserId,oneGetInfo.reportedUserName,oneGetInfo.reportTime)
      userComplainInfos.value.push(oneInfo)
    }
  }
  isLoading.value=false
  if(userComplainInfos.value.length==0){
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