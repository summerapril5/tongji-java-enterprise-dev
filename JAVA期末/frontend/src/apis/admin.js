import { httpInstance } from '@/utils/baseapi';

export const getAuditPostInfo=async()=>{
  try{
    const response = await httpInstance({
      url: '/post/auditPostInfo',
      method: 'GET'
    })
    if(response.status===200){
      return response.data
    }
    else{
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const postPass=async(postId)=>{
  try{
    const response = await httpInstance({
      url: '/post/postPass',
      method: 'POST',
      data:{
        postId:postId
      }
    })
    if(response.status!==200){
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const postRefund=async(postId)=>{
  try{
    const response = await httpInstance({
      url: '/post/postRefund',
      method: 'POST',
      data:{
        postId:postId
      }
    })
    if(response.status!==200){
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const getUserComplainInfo=async()=>{
  try{
    const response = await httpInstance({
      url: '/report/userComplainInfo',
      method: 'GET'
    })
    if(response.status===200){
      return response.data
    }
    else{
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const userComplainProcess=async(reportId)=>{
  try{
    const response = await httpInstance({
      url: '/report/userComplainProcess',
      method: 'POST',
      data:{
        reportId:reportId
      }
    })
    if(response.status!==200){
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const getPostComplainInfo=async()=>{
  try{
    const response = await httpInstance({
      url: '/report/postComplainInfo',
      method: 'GET'
    })
    if(response.status===200){
      return response.data
    }
    else{
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const postComplainProcess=async(reportId)=>{
  try{
    const response = await httpInstance({
      url: '/report/postComplainProcess',
      method: 'POST',
      data:{
        reportId:reportId
      }
    })
    if(response.status!==200){
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const getFeedbackInfo=async()=>{
  try{
    const response = await httpInstance({
      url: '/feedback/feedbackInfo',
      method: 'GET'
    })
    if(response.status===200){
      return response.data
    }
    else{
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const feedbackProcess=async(feedbackId)=>{
  try{
    const response = await httpInstance({
      url: '/feedback/feedbackProcess',
      method: 'POST',
      data:{
        feedbackId:feedbackId
      }
    })
    if(response.status!==200){
      console.log('未知错误')
    }
  }catch(error){
    console.log(error)
  }
}

export const categoryAdd=async(categoryAddInfo)=>{
  try{
    const formData = new FormData();
    formData.append('categoryName', categoryAddInfo.categoryName);
    formData.append('description', categoryAddInfo.description);
    formData.append('image', categoryAddInfo.image, categoryAddInfo.image.name);

    const response = await httpInstance({
      url: '/category/categoryAdd',
      method: 'POST',
      data:formData
    })
    if(response.status!==200){
      console.log('未知错误')
    }else{
      return true
    }
  }catch(error){
    console.log(error)
  }
}