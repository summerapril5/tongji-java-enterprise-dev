import request from '../utils/request'

export function getCategories(){
  return request({
    method: 'GET',
    url: '/category',
  })
}

export function getRecentPosts(){
  return request({
    method: 'GET',
    url: '/HomeView/getPosts',
  })
}

export function getPostsByCategory(categoryId){
  return request({
    method: 'GET',
    url: `/category/${categoryId}`,
  })
}

export function getPostByTitle(title){
  return request({
    method: 'GET',
    url: `/search/${title}`,
  })
}

export function createPost(post){
  return request({
    method: 'POST',
    url: '/post',
    data: post,
  })
}

export const searchPosts = (keyword) => {
  return request({
    method: 'GET',
    url: `/post/search/${keyword}`,
    
  })
}

export const getPostDetails = (postId)=>{
  return request({
    method: 'GET',
    url: `/post/${postId}`,
  })
}


export const likePost = (postId)=>{
  return request({
    method: 'POST',
    url: `/post/${postId}/like`,
  })
}

export const reportPost = (postId)=>{
  return request({
    method: 'POST',
    url: `/post/${postId}/report`,
  })
}

export const createComment = (newComment)=>{
  return request({
    method: 'POST',
    url: `/comment/${newComment.postId}`,
    data: newComment
  })
}

export const getComments = (postId)=>{
  return request({
    method: 'GET',
    url: `/comment/${postId}`,
  })
}

export const likeComment = (commentId) => {
  return request({
    method: 'POST',
    url: `/comment/${commentId}/like`
  })
}

export const dislikeComment = (commentId) => {
  return request({
    method: 'POST',
    url: `/comment/${commentId}/dislike`
  })
}

// 删除帖子
export const deletePost = (postId) => {
  return request({
    method: 'DELETE',
    url: `/post/${postId}`,
  })
}

// 删除评论
export const deleteComment = (commentId) => {
  return request({
    method: 'DELETE',
    url: `/comment/${commentId}`,
  })
}
