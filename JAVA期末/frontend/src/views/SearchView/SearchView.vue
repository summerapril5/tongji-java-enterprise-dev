<template>
  <div class="search-results">
    <h2>搜索结果: "{{ keyword }}"</h2>
    
    <div v-if="loading" class="loading">
      正在搜索...
    </div>
    
    <div v-else-if="searchResults.length === 0" class="no-results">
      未找到相关帖子
    </div>
    
    <div v-else class="posts-list">
      <div v-for="post in searchResults" 
           :key="post.id" 
           class="post-card" 
           @click="goToPost(post.id)">
        <h3>{{ post.title }}</h3>
        <p class="post-preview">{{ post.content.substring(0, 100) }}...</p>
        <div class="post-meta">
          <span>{{ post.authorName }}</span>
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
import { searchPosts } from '@/apis/forum'

export default {
  name: 'SearchView',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const keyword = ref(route.params.keyword)
    const searchResults = ref([])
    const loading = ref(false)

    const fetchSearchResults = async () => {
      loading.value = true
      try {
        const response = await searchPosts(keyword.value)
        searchResults.value = response.data.data
      } catch (error) {
        console.error('搜索失败:', error)
      } finally {
        loading.value = false
      }
    }

    const goToPost = (postId) => {
      router.push(`/post/${postId}`)
    }

    // 监听路由参数变化，重新搜索
    watch(() => route.params.title, (newKeyword) => {
      keyword.value = newKeyword
      fetchSearchResults()
    })

    onMounted(() => {
      fetchSearchResults()
    })

    return {
      keyword,
      searchResults,
      loading,
      goToPost
    }
  }
}
</script>

<style scoped>
.search-results {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.loading, .no-results {
  text-align: center;
  padding: 40px;
  color: #666;
}

.posts-list {
  display: grid;
  gap: 20px;
  margin-top: 20px;
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
