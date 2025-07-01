<template>
  <div class="componentmain">
    <div class="spinner" v-if="isLoading"></div>
    <div class="componentlist" v-else>
      <div class="empty" v-if="isEmpty">无</div>
      <div v-for="(item, index) in auditPostInfos" :key="item.postId" class="table" v-else>
        <OnePostAuditIntro :oneAuditPostInfo="item" :num="index" @delete="deletePostAudit" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { AuditPostInfo } from '@/utils/structures';
import { ref, onMounted } from 'vue'
import OnePostAuditIntro from './OnePostAuditIntro.vue';
import { getAuditPostInfo } from '@/apis/admin';

let isLoading=ref(true)
let isEmpty=ref(false)
let auditPostInfos=ref([])

const deletePostAudit = (index) => {
  auditPostInfos.value.splice(index, 1)
  if(auditPostInfos.value.length==0){
        isEmpty.value=true;
  }
}

onMounted(async () => {
  let getAuditPostInfos=await getAuditPostInfo()
  if(getAuditPostInfos!=null){
    for(let oneGetInfo of getAuditPostInfos){
      let oneInfo=new AuditPostInfo(oneGetInfo.postId,oneGetInfo.postTitle,oneGetInfo.categoryName,oneGetInfo.publishTime,oneGetInfo.postContent,oneGetInfo.authorId,oneGetInfo.authorName)
      auditPostInfos.value.push(oneInfo)
    }
  }
  isLoading.value=false
  if(auditPostInfos.value.length==0){
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