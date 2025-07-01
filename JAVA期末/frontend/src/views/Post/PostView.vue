<template>
  <div class="forum-detail">
    <!-- 帖子内容 -->
    <div class="post-content">
      <div class="author-info" @click="goToUserProfile(post.authorId)">
        <img :src="post.authorAvatar" class="avatar" />
        <div class="author-meta">
          <span class="author-name">{{ post.authorName }}</span>
          <span class="post-time">{{ post.createTime }}</span>
        </div>
      </div>
      
      <div class="post-body">
        <h1 class="post-title">{{ post.title }}</h1>
        <div class="post-text">{{ post.content }}</div>
      </div>

      <div class="post-actions">
        <el-button 
          :class="['action-btn', { active: post.isLiked }]" 
          @click="handlePostLike"
        >
          <i class="el-icon-thumb"></i>
          <span>{{ post.likes || 0 }}</span>
        </el-button>
        
        <el-button 
          :class="['action-btn', { active: post.isReported }]" 
          @click="handleReportPost"
          type="warning"
        >
          <i class="el-icon-warning-outline"></i>
          <span>举报</span>
        </el-button>

        <el-button 
          v-if="isAdmin"
          type="danger"
          @click="handleDeletePost"
        >
          删除帖子
        </el-button>
      </div>
    </div>

    <!-- 评论区 -->
    <div class="comments-section">
      <div v-if="!isVisitor" class="comment-form">
        <el-input
          v-model="newComment.content"
          type="textarea"
          :rows="3"
          placeholder="发表评论..."
          resize="none"
          class="transparent-input"
        ></el-input>
        <el-button type="primary" @click="submitComment">发表评论</el-button>
      </div>

      <div class="comments-list">
        <div v-for="comment in comments" :key="comment.id" class="comment-item">
          <div class="comment-header">
            <div class="commenter-info" @click="goToUserProfile(comment.authorId)">
              <img :src="comment.authorAvatar" class="avatar" />
              <span>{{ comment.authorName }}</span>
            </div>
            <span class="comment-date">{{ comment.releasetime }}</span>
          </div>
          
          <p class="comment-content">{{ comment.content }}</p>
          
          <div class="comment-actions">
            <el-button 
              :class="['action-btn', { active: comment.isLiked }]" 
              @click="handleCommentLike(comment)"
            >
              <i class="el-icon-thumb"></i>
              <span>{{ comment.likes || 0 }}</span>
            </el-button>
            
            <el-button 
              :class="['action-btn', { active: comment.isDisliked }]" 
              @click="handleCommentDislike(comment)"
            >
              <i class="el-icon-thumb" style="transform: rotate(180deg)"></i>
              <span>{{ comment.dislikes || 0 }}</span>
            </el-button>

            <el-popover
              placement="top"
              trigger="hover"
              :width="200"
            >
              <template #reference>
                <span class="reaction-stats">
                  {{ comment.likes || 0 }}赞 · {{ comment.dislikes || 0 }}踩
                </span>
              </template>
            </el-popover>

            <el-button 
              v-if="isAdmin"
              type="danger"
              size="small"
              @click="handleDeleteComment(comment.id)"
            >
              删除
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getPostDetails, likePost, dislikePost, reportPost, createComment, getComments, likeComment, dislikeComment, deletePost, deleteComment } from '@/apis/forum'
import { identityGet } from '@/apis/identity'  // 假设这是获取身份的API
import { basePicturesPath } from '@/utils/alldata';

export default {
  name: 'ForumDetail',
  data() {
    return {
      isAdmin: false,
      isVisitor: false,
      post: {
        id: '',
        title: '',
        content: '',
        authorId: '',
        authorName: '',
        authorAvatar: '',
        likes: '',
        dislikes: '',
        isLiked: false,
        isDisliked: false,
        likedUsers: [],
        dislikedUsers: []
      },
      comments: [],
      newComment: {
        content: '',
        commentTime: '',
        postId: ''
      },
      postId: null
    }
  },
  methods: {
    async fetchPostDetail() {
      try {
        
        const response = await getPostDetails(this.postId)
        this.post = response.data.data
        console.log(response.data.data)
        this.post.authorAvatar=basePicturesPath+this.post.authorAvatar
      } catch (error) {
        this.$message.error('获取帖子详情失败')
      }
    },

    async fetchComments() {
      const response = await getComments(this.postId)
      this.comments = response.data.data
    },
    
    async handlePostLike() {
      try {
        await likePost(this.post.id)
        this.post.isLiked = !this.post.isLiked
        if (this.post.isLiked) {
          this.post.likes = (this.post.likes || 0) + 1
          if (this.post.isDisliked) {
            this.post.isDisliked = false
            this.post.dislikes = Math.max(0, (this.post.dislikes || 0) - 1)
          }
        } else {
          this.post.likes = Math.max(0, (this.post.likes || 0) - 1)
        }
      } catch (error) {
        this.$message.error('操作失败')
      }
    },

    async handlePostDislike() {
      try {
        await dislikePost(this.post.id)
        this.post.isDisliked = !this.post.isDisliked
        if (this.post.isDisliked) {
          this.post.dislikes = (this.post.dislikes || 0) + 1
          if (this.post.isLiked) {
            this.post.isLiked = false
            this.post.likes = Math.max(0, (this.post.likes || 0) - 1)
          }
        } else {
          this.post.dislikes = Math.max(0, (this.post.dislikes || 0) - 1)
        }
      } catch (error) {
        this.$message.error('操作失败')
      }
    },

    async reportPost() {
      try {
        await reportPost(this.post.id)
        this.$message.success('举报成功')
      } catch (error) {
        this.$message.error('举报失败')
      }
    },

    goToUserProfile(userId) {
      this.$router.push(`/userhomepage/${userId}`)
    },

    async submitComment() {
      if (!this.newComment.content.trim()) return
      
      try {
        const now = new Date();
        const ptime = now.getFullYear() + '-' + 
          String(now.getMonth() + 1).padStart(2, '0') + '-' +
          String(now.getDate()).padStart(2, '0') + ' ' +
          String(now.getHours()).padStart(2, '0') + ':' +
          String(now.getMinutes()).padStart(2, '0') + ':' +
          String(now.getSeconds()).padStart(2, '0');


        this.newComment.postId = this.post.id
        this.newComment.commentTime = ptime
        await createComment(this.newComment)
        
        this.newComment = {
          content: '',
          commentTime: '',
          postId: ''
        }
        
        await this.fetchComments()
        this.$message.success('评论成功')
      } catch (error) {
        console.error('评论失败:', error)
        this.$message.error('评论失败')
      }
    },

    async handleCommentLike(comment) {
      try {
        await likeComment(comment.id)
        comment.isLiked = !comment.isLiked
        if (comment.isLiked) {
          comment.likes = (comment.likes || 0) + 1
          if (comment.isDisliked) {
            comment.isDisliked = false
            comment.dislikes = Math.max(0, (comment.dislikes || 0) - 1)
          }
        } else {
          comment.likes = Math.max(0, (comment.likes || 0) - 1)
        }
      } catch (error) {
        this.$message.error('操作失败')
      }
    },

    async handleCommentDislike(comment) {
      try {
        await dislikeComment(comment.id)
        comment.isDisliked = !comment.isDisliked
        if (comment.isDisliked) {
          comment.dislikes = (comment.dislikes || 0) + 1
          if (comment.isLiked) {
            comment.isLiked = false
            comment.likes = Math.max(0, (comment.likes || 0) - 1)
          }
        } else {
          comment.dislikes = Math.max(0, (comment.dislikes || 0) - 1)
        }
      } catch (error) {
        this.$message.error('操作失败')
      }
    },

    async handleDeletePost() {
      try {
        await this.$confirm('确定要删除这个帖子吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deletePost(this.postId)
        this.$message.success('删除成功')
        this.$router.push('/')
      } catch (error) {
        if (error !== 'cancel') {
          this.$message.error('删除失败')
        }
      }
    },

    async handleDeleteComment(commentId) {
      try {
        await this.$confirm('确定要删除这条评论吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteComment(commentId)
        this.$message.success('删除成功')
        await this.fetchComments()
      } catch (error) {
        if (error !== 'cancel') {
          this.$message.error('删除失败')
        }
      }
    },

    async fetchUserIdentity() {
      try {
        const response = await identityGet()
        const identity = response.data.identity
        this.isAdmin = identity === 'admin'
        this.isVisitor = identity === 'visitor'
      } catch (error) {
        console.error('获取用户身份失败:', error)
      }
    }

  },
  created() {
    // 从路由路径中获取 postId
    const pathParts = this.$route.path.split('/')
    this.postId = pathParts[pathParts.length - 1]  // 获取路径最后一部分作为 postId
    
    if (!this.postId) {
      this.$message.error('无效的帖子ID')
      this.$router.push('/')
      return
    }
    
    console.log('获取到的帖子ID:', this.postId) // 调试用
    this.fetchPostDetail()
    this.fetchComments()
    this.fetchUserIdentity()  // 获取用户身份
  }
}
</script>

<style scoped>
.forum-detail {
  max-width: 900px;
  margin: 20px auto;
  padding: 30px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 16px rgb(255, 255, 255);
}

.post-content {
  display: flex;
  flex-direction: column;
  padding: 24px;
  margin-bottom: 30px;
  border-bottom: 1px solid #ffffff;
  min-height: 300px;  /* 设置最小高度 */
}

.author-info {
  display: flex;
  align-items: center;
  padding: 12px;
  border-radius: 8px;
  transition: background-color 0.3s;
  margin-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.author-info:hover {
  background-color: #f5f7fa;
}

.avatar {
  width: 56px;  /* 更大的头像 */
  height: 56px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #fff;
  box-shadow: 0 2px 8px rgba(255, 255, 255, 0.1);
}

.author-meta {
  margin-left: 16px;
}

.author-name {
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
}

.post-time {
  font-size: 14px;
  color: #909399;
  margin-top: 6px;
}

.post-title {
  font-size: 28px;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 20px;
  padding: 0 12px;
}

.post-text {
  flex: 1;  /* 让文本内容占据剩余空间 */
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  padding: 0 12px;
  overflow-y: auto;  /* 内容过多时显示滚动条 */
  white-space: pre-wrap;
}

.post-actions {
  display: flex;
  gap: 24px;
  padding: 16px 12px;
  border-top: 1px solid #000000;
  margin-top: auto;  /* 将操作栏推到底部 */
  justify-content: flex-end;  /* 按钮靠右对齐 */
}

.action-btn {
  height: 20px;
  padding: 0 20px;
  font-size: 15px;
  border-radius: 20px;
  display: flex;
  align-items: center;
  gap: 8px;
  background: transparent;
  border: 1px solid #dcdfe6;
}

.action-btn:hover {
  background-color: #f5f7fa;
}

.action-btn.active {
  color: #409EFF;
  border-color: #409EFF;
}

/* 评论区样式 */
.comments-section {
  padding: 24px 12px;
}

.comments-title {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 24px;
  padding-bottom: 12px;
  border-bottom: 2px solid #ebeef5;
  color: #2c3e50;
}

.comment-form {
  margin-bottom: 36px;
  padding: 20px;
  border-radius: 8px;
}

.comment-form .el-input {
  margin-bottom: 16px;
}

.comment-form .el-button {
  height: 40px;
  padding: 0 24px;
  font-size: 15px;
}

.comment-item {
  padding: 20px;
  margin-bottom: 16px;
  border-radius: 8px;
  border: 1px solid #ebeef5;
  transition: all 0.3s ease;
}

.comment-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.commenter-info {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}

.commenter-meta {
  margin-left: 16px;
}

.commenter-name {
  font-size: 16px;
  font-weight: 500;
  color: #2c3e50;
}

.comment-date {
  font-size: 13px;
  color: #909399;
  margin-left: 12px;
}

.comment-content {
  margin: 16px 0;
  font-size: 15px;
  line-height: 1.7;
  color: #333;
  padding: 0 8px;
}

.comment-actions {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-top: 12px;
  padding-top: 12px;
  border-top: 1px solid #ebeef5;
}

.comment-actions .action-btn {
  height: 32px;  /* 评论区按钮稍小 */
  padding: 0 16px;
  font-size: 14px;
}

.reaction-stats {
  font-size: 13px;
  color: #606266;
  margin-left: auto;  /* 将统计信息推到右边 */
}

/* 响应式调整 */
@media (max-width: 768px) {
  .post-content {
    padding: 16px;
  }

  .post-body {
    padding: 12px;
  }

  .post-title {
    font-size: 24px;
  }
}

.post-body {
  flex: 1;  /* 让内容区域占据剩余空间 */
  display: flex;
  flex-direction: column;
  padding: 20px;
  margin: 20px 0;
  border: 1px solid #010a1f;
  border-radius: 8px;
}

/* 透明输入框样式 */
.transparent-input :deep(.el-textarea__inner) {
  background-color: transparent;
  border: 1px solid #dcdfe6;
}

.transparent-input :deep(.el-textarea__inner:focus) {
  background-color: rgba(255, 255, 255, 0.9);
}
</style> 