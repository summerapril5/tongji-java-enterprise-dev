<template>
  <div class="category-view">
    <!-- 分类选择区 -->
    <el-row :gutter="20" class="category-list">
      <el-col :span="3" v-for="category in categories" :key="category.id">
        <el-card 
          class="category-tag" 
          shadow="hover"
          :class="{ active: category.id === currentCategoryId }"
        >
          <button @click="switchCategory(category.id)">
            <img :src="category.imageUrl" :alt="category.description" class="category-image" />
          </button>
        </el-card>
      </el-col>
    </el-row>

    <!-- 帖子列表区 -->
    <div v-if="loading" class="loading">
      加载中...
    </div>
    
    
    <div v-else class="posts-list">
      <div v-for="post in posts" 
           :key="post.id" 
           class="post-card" 
           @click="goToPost(post.id)">
        <h3>{{ post.title }}</h3>
        <p> class="post-preview">{{ post.content.substring(0, 100) }}...</p>
        <div class="post-meta">
          <span>{{ post.author }}</span>
          <span>{{ post.releaseTime }}</span>
          <div class="post-stats">
            <span>👍 {{ post.likes }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCategories, getPostsByCategory } from '@/apis/forum'

export default {
  name: 'CategoryView',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const posts = ref([])
    const categories = ref([])
    const loading = ref(false)
    const currentCategoryId = ref(parseInt(route.params.id))

    // 获取分类列表
    const fetchCategories = async () => {
      try {
        const res = await getCategories()
        categories.value = res.data.data
      } catch (error) {
        console.error('获取分类失败:', error)
      }
    }

    // 获取当前分类的帖子
    const fetchPosts = async (categoryId) => {
      loading.value = true
      try {
        const res = await getPostsByCategory(categoryId)
        posts.value = res.data.data
      } catch (error) {
        console.error('获取帖子失败:', error)
      } finally {
        loading.value = false
      }
    }

    // 切换分类
    const switchCategory = (categoryId) => {
      router.push(`/category/${categoryId}`)
    }

    // 跳转到帖子详情
    const goToPost = (postId) => {
      router.push(`/post/${postId}`)
    }

    // 监听路由参数变化
    watch(() => route.params.id, (newId) => {
      currentCategoryId.value = parseInt(newId)
      fetchPosts(newId)
    })

    onMounted(() => {
      fetchCategories()
      fetchPosts(currentCategoryId.value)
    })

    return {
      posts,
      categories,
      loading,
      currentCategoryId,
      switchCategory,
      goToPost
    }
  }
}
</script>

<style scoped>
.category-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.category-list {
  margin-bottom: 30px;
}

.category-tag {
  cursor: pointer;
  transition: transform 0.2s;
}

.category-tag:hover {
  transform: translateY(-2px);
}

.category-tag.active {
  border: 2px solid #409EFF;
}

.category-image {
  width: 100%;
  height: auto;
}

.loading, .no-posts {
  text-align: center;
  padding: 40px;
  color: #666;
}

.posts-list {
  display: grid;
  gap: 20px;
}

.post-card {
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  cursor: pointer;
  transition: box-shadow 0.3s;
}

.post-card:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.post-meta {
  display: flex;
  justify-content: space-between;
  color: #666;
  font-size: 0.9em;
  margin-top: 10px;
}

.post-preview {
  color: #666;
  margin: 10px 0;
}
</style>