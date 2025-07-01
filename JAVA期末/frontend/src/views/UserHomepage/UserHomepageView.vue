<template>

  <div class="container" v-if="currentUser">

    <div class="content-wrapper">
      <div class="left-panel">
        <div class="user-info-container">
          <div class="profile-background">
            <img src="../../assets/pictures/UserCenterView/puq.jpg" alt="用户背景图" class="background-image" />
          </div>
          <div class="avatar-container" @click="openAvatarSelection">
            <img :src="user. avatarUrl" alt="用户头像" class="avatar" />
            <div class="avatar-overlay">更换头像</div>
            <!-- 隐藏的文件输入框 -->
            <input type="file" ref="fileInput" @change="handleFileUpload" accept="image/*" style="display: none;" />
          </div>
          <div class="user-details">
            <!-- 用户信息表单部分 -->
            <div class="form-group">
              <label for="username">用户名：</label>
              <div class="editable-field-container">
                <span v-if="!editing">{{ user.nickname }}</span>
                <!--                <span v-if="!editing">{{ userID }}</span>-->
                <input v-if="editing" type="username" v-model="userTemp.nickname" @input="validateUsername"
                       class="fixed-input" />
                <!-- 错误信息显示在输入框下方 -->
                <p v-if="usernameError" class="error-message">{{ usernameError }}</p> <!-- 报错信息显示在输入框下方 -->
              </div>
            </div>

            <div class="form-group">
              <label for="email">邮&nbsp;箱：</label>
              <div class="editable-field-container">
                <span v-if="!editing">{{ user.email }}</span>
                <input v-if="editing" type="email" v-model="userTemp.email" @input="validateEmail"
                       class="fixed-input" />
                <!-- 错误信息显示在输入框下方 -->
                <p v-if="emailError" class="error-message">{{ emailError }}</p> <!-- 报错信息显示在输入框下方 -->
              </div>
            </div>

          </div>

          <button class="follow-button" @click="toggleFollow" :disabled="following" :class="{ 'followed': following }">
            {{ following ? '已关注' : '关注' }}
          </button>

          <button class="home-button" @click="goHome">返回主页</button>
        </div>

      </div>


      <div class="right-panel">
        <!-- 右侧框架的内容，例如其他信息 -->
        <!-- 使用 v-for 遍历从后端获取的帖子列表 -->

        <!-- 带三角形的文字选择栏 -->
        <div class="dropdown-container">
          <div class="dropdown-text" @click="toggleDropdown">
            {{ selectedText }}
            <span :class="{ 'triangle': true, 'triangle-rotate': showDropdown }">&#9662;</span>
          </div>

          <!-- 下拉菜单，点击三角形后显示 -->
          <ul v-if="showDropdown" class="dropdown-menu">
            <li @click="selectOption('帖子')">帖子</li>

            <li @click="selectOption('关注')">关注</li>

          </ul>
        </div>

        <!-- 分割线 -->
        <hr class="divider">


        <!-- 根据选择展示内容 -->
        <div v-if="selectedText === '帖子'" class="selectedoption">


          <!-- 帖子列表 -->
          <div v-for="post in userPosts" :key="post?.postId" class="post" @click="openPostDialog(post)">
            <h3 class="post-title">{{ post?.postTitle }}</h3>
            <p class="post-content">{{ post?.postContent }}</p>
            <p class="post-meta">
              发布日期: {{ post?.publishTime }}
              <!-- 如果需要显示文章类型，可以根据实际情况取消注释并调整逻辑 -->
              <!-- <span v-if="post.article_type === 'announcement'"> | 类型: 公告</span>
              <span v-else> | 类型: 文章</span> -->
            </p>
            <!-- 确保删除按钮使用 @click.stop 来防止冒泡 -->
<!--            <button class="delete-button" @click.stop="confirmDelete(post)">删除</button>-->
          </div>
        </div>


        <!-- 收藏列表 -->


        <!-- 评论列表 -->


        <!-- 关注列表 -->
        <div v-if="selectedText === '关注'" class="selectedoption">

          <!-- 关注列表 -->
          <div v-for="follow in  userFollows" :key="follow.articleId" class="post"
               @click="openFavoriteDialog(follow)">
            <h3 class="post-title">{{ follow.nickname }}</h3>

            <!--            <p class="post-meta">发布日期: {{ favorite.publishedDate }}</p>-->
            <!-- 改为取消关注按钮 -->
<!--            <button class="cancelFavorite-button" @click.stop="confirmCancelFavorite(follow)">取消关注</button>-->
          </div>
        </div>

        <!-- 板块列表 -->


      </div>


      <!-- 帖子详情弹框 -->


      <!-- 删除确认弹框 -->


      <!-- 取消收藏确认弹框 -->


      <!-- 删除评论弹框 -->


      <!-- 修改密码弹窗 -->

      <!-- 头像选择弹出框 -->


    </div>


  </div>
  <div v-else>
    加载中...
  </div>
</template>


<script>
// import { mapGetters, mapActions } from 'vuex';
import { getUserInfo } from '@/apis/userapi';
import { updateUserInfo } from '@/apis/userapi';
import { fetchPreferredCategoriesByUserId } from '@/apis/userapi';
import { fetchFavoritePostsByUser } from '@/apis/userapi';
import { fetchPostsByAuthor } from '@/apis/userapi';
import { fetchCommentsByUser } from '@/apis/userapi';
import { fetchFollowsByUser  } from '@/apis/userapi';
import request from '@/utils/request';

export default {
  created() {
    this.fetchUserInfo();
    this.fetchPostsByAuthor();
    this.fetchFavoritePosts();
    this.fetchCommentsByUser();
    this.fetchFollowsByUser();
    this.fetchPreferredCategories()
  },
  computed: {

    userId() {
      return this.$route.params.userid;
    },

    posts() {
      return this.userPosts;
    },
    commentInPost() {
      return this.postComment;
    },
  },
  data() {
    return {

      userPosts: [

      ],
      favoritePosts: [

      ],
      userComments:[],
      userFollows: [], // 存储用户关注列表
      userCategories:[],


      showChangePasswordDialog: false, // 控制弹出框显示
      passwordError: '',
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',

      showPostDialog: false, // 控制帖子详情弹框是否显示

      showConfirmDialog: false, // 删除确认弹框
      selectedPost: null, // 当前选中的帖子

      showDropdown: false, // 控制下拉菜单的显示与隐藏
      selectedText: '帖子', // 默认选择

      showConfirmFavoriteDialog: false, // 控制收藏删除确认弹框
      selectedFavorite: null, // 当前选中的收藏

      selectedComment: null, // 当前选择的评论
      showConfirmCommentDialog: false, // 控制评论删除确认弹框

      userTemp: {},
      editing: false,
      emailError: null,
      usernameError: null,
      userID: null, // 确保这里定义了 userID
      showAvatarSelection: false ,// 确保这里定义了 showAvatarSelection
      // 添加一个当前用户对象，用于模拟用户信息
      user: {
        userId: "",
        nickname: "",
        avatarUrl: "",
        account: "",
        password:"",
        email: "",
        status: ""
      },
      currentUser:{},
    };
  },

  methods: {


    async fetchUserInfo() {
      try {
        this.loading = true;
        const response = await getUserInfo(this.userId);
        this.user = response.data;
      } catch (error) {
        console.error('获取用户信息失败:', error);
        if (error.response && error.response.status === 404) {
          this.$message.error('用户不存在');
        } else {
          this.$message.error('获取用户信息失败，请重试');
        }
      } finally {
        this.loading = false;
      }
    },

    async fetchPostsByAuthor() {
      if (!this.userId) {
        this.$router.push('/login');
        return;
      }

      try {
        this.loading = true;
        const response = await fetchPostsByAuthor(this.userId);
        console.log(response);
        this.userPosts = response.data;
      } catch (error) {
        console.error('获取帖子信息失败:', error);
        this.$message.error('获取帖子信息失败，请重试');
      } finally {
        this.loading = false;
      }
    },
    async fetchFavoritePosts() {

      try {
        this.loading = true;
        const response = await fetchFavoritePostsByUser(this.userId); // 假设这是你用来获取收藏帖子的API调用
        console.log(response);
        this.favoritePosts = response.data; // 将响应数据赋值给组件的数据属性
      } catch (error) {
        console.error('获取收藏帖子信息失败:', error);
        this.$message.error('获取收藏帖子信息失败，请重试');
      } finally {
        this.loading = false;
      }
    },

    async fetchCommentsByUser() {

      try {
        this.loading = true;
        // 调用后端API获取用户评论，假设我们有一个名为 fetchCommentsByUser 的API方法
        const response = await fetchCommentsByUser(this.userId);
        console.log(response);
        this.userComments = response.data; // 假设返回的数据结构中，评论信息存储在data属性下
      } catch (error) {
        console.error('获取评论信息失败:', error);
        this.$message.error('获取评论信息失败，请重试'); // 使用框架的消息提示功能显示错误信息给用户
      } finally {
        this.loading = false;
      }
    },

    async fetchFollowsByUser() {

      try {
        this.loading = true;
        const response = await fetchFollowsByUser(this.userId); // 假设这是你的API调用
        console.log(response);
        this.userFollows = response.data; // 将获取到的关注列表保存到组件的状态中
      } catch (error) {
        console.error('获取关注信息失败:', error);
        this.$message.error('获取关注信息失败，请重试'); // 使用消息提示框显示错误信息
      } finally {
        this.loading = false;
      }
    },

    async fetchPreferredCategories() {
      try {
        this.loading = true;
        const response = await fetchPreferredCategoriesByUserId(this.userId);
        console.log(response);
        this.userCategories = response.data; // 假设响应格式与获取帖子类似，具有data属性
      } catch (error) {
        console.error('获取板块信息失败:', error);
        this.$message.error('获取板块信息失败，请重试');
      } finally {
        this.loading = false;
      }
    },

    async saveUserInfo() {
      try {
        await updateUserInfo(this.userId, this.userInfo);
        // 更新成功，刷新数据或提示用户
        this.$message.success('用户信息更新成功');
      } catch (error) {
        // 处理错误，提示用户
        this.$message.error('更新用户信息失败，请稍后重试');
      }
    },






    async fetchPostComments(articleId) {
      try {
        let response;
        if (process.env.NODE_ENV === 'development') {
          // 模拟数据（仅用于开发环境）
          console.log('模拟获取帖子评论:', articleId);
          response = await new Promise((resolve) =>
              setTimeout(() => resolve([
                { id: 1, content: '模拟评论1' },
                { id: 2, content: '模拟评论2' }
              ]), 1000));
        } else {
          // 生产环境中实际调用API
          response = await this.fetchApi('/api/comments/post/' + articleId); // 替换为实际的API路径
        }
        this.postComment = response; // 将评论数据存储在组件中
      } catch (error) {
        console.error('获取帖子评论失败:', error);
      }
    },

    async getUserFavorites() {
      try {
        let result;
        if (process.env.NODE_ENV === 'development') {
          // 模拟数据（仅用于开发环境）
          console.log('模拟加载收藏');
          result = [
            { id: 1, name: '模拟收藏1' },
            { id: 2, name: '模拟收藏2' }
          ];
        } else {
          // 生产环境中实际调用API
          result = await this.fetchApi('/api/favorites/user/' + this.currentUser.userId); // 替换为实际的API路径
        }
        console.log('收藏加载成功:', result);
        this.userFavors = result;
      } catch (error) {
        console.error('加载收藏失败:', error);
      }
    },
    editUserInfo() {
      this.userTemp = { ...this.user };
      this.editing = true;
    },
    // 验证用户名是否为邮箱格式
    validateUsername() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (emailPattern.test(this.userTemp.username)) {
        this.usernameError = '用户名不能是邮箱格式';
      } else {
        this.usernameError = null;
      }
    },
    // 验证邮箱格式
    validateEmail() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailPattern.test(this.userTemp.email)) {
        this.emailError = '请输入有效的邮箱地址';
      } else {
        this.emailError = null;
      }
    },

    async confirmEdit() {
      if (!this.emailError && !this.usernameError && this.editing) {
        try {
          // 调用 Vuex 中的 action，发送更新的用户信息到后端
          await this.updateUserInfo({
            userId: this.userTemp.userId,
            nickname: this.userTemp.nickname,
            avatarUrl: this.userTemp.avatarUrl,
            account: this.userTemp.account,
            password: this.userTemp.password,
            email: this.userTemp.email,
            status: this.userTemp.status,

          });
          this.editing = false;
        } catch (error) {
          console.error('更新失败:', error);
          alert('更新用户信息失败，请稍后重试。');
        }
      }
    },
    toggleUserMenu() {
      this.showUserMenu = !this.showUserMenu;
    },
    handleUserAvatarClick() {
      if (this.currentUser.role === 'guest') {
        this.$router.push('/login'); // 如果是guest用户，点击跳转到登录页面
      } else {
        this.goToUserProfile(); // 否则跳转到个人主页
      }
    },
    cancelEdit() {
      this.editing = false;  // 恢复到原来的状态，不保存修改
      this.emailError = null;  // 取消编辑时，清除错误信息
    },
    openAvatarSelection() {
      this.$refs.fileInput.click(); // 点击头像区域时，打开文件选择器
    },

    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.uploadAvatar(file).then(url => {
          this.currentUser.avatarUrl = url; // 假设后端返回文件的URL
          this.updateUserInfo(this.currentUser);
        }).catch(error => {
          console.error('上传头像失败:', error);
          alert('上传头像失败，请稍后再试。');
        });
      }
    },
    uploadAvatar(file) {
      const formData = new FormData();
      formData.append('file', file);
      return request.post('/userCenter/avatar', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
    },
    updateUserInfo(user) {
      if (!this.userId) {
        this.$router.push('/login');
        return;
      }

      this.loading = true;

      request.put(`/userCenter/update/${this.userId}`, user)
          .then(response => {
            this.$message.success('用户信息更新成功');
            console.log(response);
          })
          .catch(error => {
            console.error('更新用户信息失败:', error);
            this.$message.error('更新用户信息失败，请稍后再试。');
          })
          .finally(() => {
            this.loading = false;
          });
    },
//   async updateUserAvatar() {
//   try {
//   await this.updateUserInfo({
//   username: this.currentUser.username,
//   email: this.currentUser.email,
//   password: this.currentUser.password,
//   languagePreference: this.currentUser.languagePreference,
//   bio: this.currentUser.bio,
//   avatar: this.selectedAvatar,
//   gender: this.currentUser.gender
// });
// } catch (error) {
//   console.error('更新头像失败:', error);
//   alert('更新头像失败，请稍后再试。');
// }
// },

    goHome() {
      this.$router.push({ name: 'home' });
    },


    performLogout() {
      this.logout();
      this.$router.push('/login'); // 跳转到登录页面
    },
    goToUserProfile() {
      this.$router.push('/userprofile');
    },

    //刪除帖子部分
    // 点击删除按钮时，显示确认弹框
    confirmDelete(post) {
      this.selectedPost = post; // 保存要删除的帖子
      //console.log(this.selectedPost);
      this.showConfirmDialog = true; // 显示确认弹框
    },
    // 确认删除操作
    deletepost(post) {
      // 实际删除操作应通过API调用来删除帖子
      this.deletePost(post.articleId);
      this.userPosts = this.userPosts.filter(p => p.articleId !== post.articleId);
      this.showConfirmDialog = false; // 关闭弹框
      this.selectedPost = null; // 重置选择的帖子
    },
    // 取消删除操作
    cancelDelete() {
      this.showConfirmDialog = false; // 关闭弹框
      this.selectedPost = null; // 重置选择的帖子
    },
    // 打开帖子详情弹框
    openPostDialog(userPosts) {
      this.selectedPost = userPosts;
      this.showPostDialog = true;
      // 根据选中的 post 的 articleId 获取该帖子的评论
      this.fetchPostComments(userPosts.postId);
    },
    // 关闭帖子详情弹框
    closePostDialog() {
      this.showPostDialog = false;
      this.selectedPost = null;
    },
    async confirmChangePassword() {
      // 清除之前的错误提示
      this.passwordError = '';

      // 确保新密码和确认密码相同
      if (this.newPassword !== this.confirmPassword) {
        this.passwordError = '新密码与确认密码不匹配';
        return;
      }
      // 验证旧密码是否正确
      if (this.oldPassword !== this.currentUser.password) {
        this.passwordError = '旧密码不正确';
        return;
      }

      // 确保新密码与旧密码不同
      if (this.newPassword === this.oldPassword) {
        this.passwordError = '新密码不能与旧密码相同';
        return;
      }


      // 确保新密码长度在 8 到 20 位之间
      if (this.newPassword.length < 8 || this.newPassword.length > 20) {
        this.passwordError = '新密码长度应为 8 到 20 位';
        return;
      }
      // 向后端发送请求更新密码
      try {
        await this.updateUserInfo({
          username: this.currentUser.username,
          email: this.currentUser.email,
          password: this.newPassword,
          languagePreference: this.currentUser.languagePreference,
          bio: this.currentUser.bio,
          avatar: this.currentUser.avatar,
          gender: this.currentUser.gender
        });
        alert('密码修改成功');
        this.cancelChangePassword(); // 关闭弹框并清空数据
      } catch (error) {
        console.error('更新密码失败:', error);
        alert('更新密码失败，请稍后再试');
      }
    },
    cancelChangePassword() {
      this.showChangePasswordDialog = false; // 关闭弹窗
      this.oldPassword = '';
      this.newPassword = '';
      this.confirmPassword = '';
      this.passwordError = '';
    },
    // 打开收藏的帖子详情，使用与帖子相同的弹框
    openFavoriteDialog(favorite) {
      this.selectedPost = favorite; // 将选中的收藏帖子数据传递给 selectedPost
      //this.comments = [ /* 模拟或者获取实际的评论数据 */];
      this.showPostDialog = true; // 显示弹框
      this.fetchPostComments(favorite.articleId);
    },
    // 确认取消收藏操作
    confirmCancelFavorite(favorite) {
      this.selectedFavorite = favorite;
      this.showConfirmFavoriteDialog = true;
    },

    // 取消收藏操作
    cancelFavorite(favorite) {
      console.log('favor:', favorite.favorId); // 调试信息
      this.$store.dispatch('deleteFavorite', favorite.favorId);
      //console.log('comment:', comment); // 调试信息
      this.userFavors = this.userFavors.filter(p => p.favorId !== favorite.favorId);
      this.showConfirmFavoriteDialog = false;
      this.selectedFavorite = null;
    },

    // 关闭弹框
    cancelDialog() {
      this.showConfirmFavoriteDialog = false;
      this.selectedFavorite = null;
    },
    confirmComment(comment) {
      //确认删除函数，然后接入后端
      this.$store.dispatch('deleteComment', comment.commentId);
      console.log('comment:', comment); // 调试信息
      this.userComments = this.userComments.filter(p => p.commentId !== comment.commentId);
      this.showConfirmCommentDialog = false;
      this.selectedComment = null;
    },
    deleteComment(comment) {
      this.selectedComment = comment;
      this.showConfirmCommentDialog = true;
    },

    // 关闭弹框
    cancelCommentDialog() {
      this.showConfirmCommentDialog = false;
      this.selectedComment = null;
    },
    //选择框
    toggleDropdown() {
      this.showDropdown = !this.showDropdown; // 切换下拉菜单的显示状态
    },
    selectOption(option) {
      this.selectedText = option; // 设置选中的选项
      this.showDropdown = false; // 选择后隐藏下拉菜单
    },
  }
};
</script>

<style>
.avatar-container {
  position: relative;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  cursor: pointer;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  transition: all 0.3s ease;
}

.avatar-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: rgba(0, 0, 0, 0.6);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s ease;
  font-size: 14px;
  text-align: center;
}

.avatar-container:hover .avatar {
  filter: brightness(0.7);
}

.avatar-container:hover .avatar-overlay {
  opacity: 1;
}

.user-info-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}



.content-wrapper {

  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 80%;
  height: 100%;
  margin: 0 auto;
  z-index: 2;
  margin-top: 40px;
}

.content-wrapper::before {
  content: '';
  position: absolute;
  top: -30px;
  left: 0;
  right: 0;
  bottom: 0px;
  background-image: url('../../assets/pictures/UserCenterView/background.png');
  background-size: cover;
  background-position: center;
  z-index: -1; /* 确保背景在内容之下 */
}
.left-panel {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  background-color: rgba(255, 255, 255, 0.7);
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  height: 100%;
  z-index: 2;
  margin-bottom: 20px;
  /* 与底部之间的距离 */

}

.right-panel {
  flex: 1;
  display: flex;
  background-color: rgba(211, 211, 211, 0.7);
  flex-direction: column;
  /* 将帖子垂直排列 */
  align-items: flex-start;
  justify-content: flex-start;
  /* 让内容从顶部开始对齐 */
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  height: 100%;
  /* 固定高度 */
  margin-bottom: 20px;
  z-index: 2;
}

.user-details {
  width: 100%;
  max-width: 400px;
}

.form-group {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 20px;
  font-family: '宋体', 'ZhiMangXing-Regular', sans-serif;
  font-size: 16px;
  max-width: 290px;
}

.form-group label {
  min-width: 80px;
  /* 设置一个最小宽度确保标签在同一行 */
  margin-right: 10px;
  /* 标签和输入框之间的间距 */
  text-align: right;
}



.editable-field-container {
  display: flex;
  justify-content: center;
  flex-direction: column;
  /* 让输入框和反馈信息垂直排列 */
  width: 100%;
}

.editable-field input,
.editable-field span,
textarea {
  width: 100%;
  padding: 5px;
  text-align: center;
  border-radius: 5px;
  border: 1px solid #ccc;
  background-color: rgba(255, 255, 255, 0.7);
}

input[type="text"],
textarea {
  width: 100%;
  /* 设置宽度为父容器的100% */
  max-width: 300px;
  /* 设置最大宽度，确保不会变大 */
  height: 30px;
  /* 固定高度 */
  box-sizing: border-box;
  /* 包括内边距和边框在内计算宽度 */
  padding: 5px;
  font-size: 14px;
}

textarea {
  height: 100px;
  /* 设置多行文本框的固定高度 */
  resize: none;
  /* 禁用手动调整大小 */
}

.confirm-button,
.cancel-button {
  width: 120px;
  /* 调整宽度为长方形 */
  padding: 10px;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  transition: background-color 0.3s, transform 0.2s ease;
}

.confirm-button {
  background-color: #28a745;
  color: white;
}

.confirm-button:hover {
  background-color: #218838;
}

.cancel-button {
  background-color: #dc3545;
  color: white;
}

.cancel-button:hover {
  background-color: #c82333;
}

.edit-actions {
  display: flex;
  justify-content: center;
  /* 居中对齐容器内的按钮 */
  gap: 20px;
  /* 增加按钮之间的间距 */
  width: 260px;
  /* 容器的宽度设置为按钮的总宽度加上间距 */
}

.home-button {
  background-color: #28a745; /* 绿色按钮 */
  color: white;
  margin-top: 15px;
  /* 让按钮靠下 */
}

.home-button:hover {
  background-color: #218838; /* 悬停时颜色变深 */
}


.logout-button-container {
  display: flex;
  justify-content: center;
  margin-top: auto;
  /* 让按钮靠下 */
  margin-bottom: 0px;
}

.logout-button {
  background-color: #ff4d4d;
  width: 136px;
  /* 调整宽度为长方形 */
  height: 35px;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease, transform 0.2s ease;
  margin-top: 20px;
}

.logout-button:hover {
  background-color: #ff1a1a;
  transform: scale(1.05);
}

.logout-button:active {
  background-color: #cc0000;
  transform: scale(1);
}

/* 新增的样式 */
.edit-button-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.edit-button {
  background-color: #ffc107;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.edit-button:hover {
  background-color: #e0a800;
  transform: scale(1.05);
}

.edit-actions {
  display: flex;
  justify-content: center;
  /* 水平居中对齐 */
  width: 100%;
  margin-top: 10px;
}

/* 头像选择弹出框相关样式 */
.avatar-selection-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.avatar-selection-container {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 400px;
  text-align: center;
}

.avatar-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  margin-top: 20px;
}

.avatar-option {
  position: relative;
  cursor: pointer;
}

.avatar-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 2px solid transparent;
  transition: all 0.3s ease;
}

.avatar-option.selected .avatar-image {
  border-color: #007bff;
  filter: brightness(0.7);
}

.checkmark {
  position: absolute;
  top: 5px;
  right: 5px;
  font-size: 18px;
  color: white;
  background-color: #007bff;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.error-message {
  color: red;
  font-size: 12px;
  margin-top: 2px;
  margin-left: 5px;
  /* 保持与输入框对齐 */
}

.validation-feedback {
  font-size: 12px;
  color: green;
  /* 默认成功信息颜色 */
  display: block;
  /* 确保反馈信息占据整行 */
}

.validation-feedback.error {
  color: red;
  /* 错误信息颜色 */
}

.validation-feedback.success {
  color: green;
  /* 成功信息颜色 */
}


.user-info {
  display: table-column;
  align-items: center;
  gap: 10px;
  position: relative;
}

.user-info img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.user-info img:hover {
  transform: scale(1.1);
}

.user-info-list {
  z-index: 2;
  /* 提高用户信息列表的层级，确保它显示在 header 之上 */
  position: absolute;
  left: -200px !important;
  top: 50px;
  right: 0;
  background-color: white;
  /* 为弹出的内容添加背景色，避免透明度导致内容不清晰 */
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  /* 添加阴影效果，确保弹出层次感 */
}

.username {
  font-family: 'Caveat-VariableFont', 'ZhiMangXing-Regular', sans-serif;
  font-size: 28px;
  font-weight: bold;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  cursor: pointer;
}

.login-prompt {
  display: flex;
  justify-content: center;
  /* 水平居中对齐 */
  align-items: center;
  /* 垂直居中对齐 */
  height: 100%;
  /* 让内容充满父容器的高度 */
  color: blue;
  cursor: pointer;
  text-align: center;
}

.login-prompt:hover {
  text-decoration: underline;
}




/*帖子*/
/* 帖子悬停变大效果 */
.post {
  background-color: white;
  padding: 15px;
  margin-bottom: 5px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  max-width: 94%;
  max-height: 150px;
  box-sizing: border-box;
  position: relative;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
  z-index: 3;
  margin-left: 16px;
  margin-top: 10px;
}

.post:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
}

.post-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.post-content {
  font-size: 14px;
  color: #333;
  margin-bottom: 10px;
  max-height: 80px;
  /* 限制最大高度 */
  overflow: hidden;
  /* 隐藏超出的内容 */
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  /* 限制行数为3，超出部分显示省略号 */
  white-space: normal;
  /* 允许换行 */
}


.post-meta {
  font-size: 12px;
  color: #666;
  text-align: right;
}

.comment-title {
  font-size: 14px;
  color: #333;
  margin-bottom: 20px;
  max-height: 80px;
  /* 限制最大高度 */
  overflow: hidden;
  /* 隐藏超出的内容 */
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  /* 限制行数为3，超出部分显示省略号 */
  overflow-y: auto;
  white-space: normal;
  /* 允许换行 */
}

/* 删除按钮样式 */
.delete-comment,
.cancelFavorite-button,
.delete-button {
  position: absolute;
  right: 20px;
  top: 25%;
  transform: translateY(-50%);
  background-color: red;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
}

.delete-comment {
  top: 80%;
}

.cancelFavorite-button {
  background-color: #ffc107;
}

.delete-button:hover {
  background-color: darkred;
}

/* 删除确认弹框样式 */
.confirm-dialog {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
}

.confirm-dialog-content {
  text-align: center;
}

.confirm-dialog-content button {
  margin: 10px;
  padding: 5px 15px;
  cursor: pointer;
}

.confirm-dialog-content button {
  margin: 10px;
  padding: 10px 20px;
  cursor: pointer;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  transition: background-color 0.3s ease, color 0.3s ease, box-shadow 0.3s ease;
}

.confirm-dialog-content button {
  background-color: #4CAF50;
  /* 确认按钮的绿色 */
  color: white;
}

.confirm-dialog-content button:hover {
  background-color: #45a049;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.confirm-dialog-content button:last-child {
  background-color: #f44336;
  /* 取消按钮的红色 */
  color: white;
}

.confirm-dialog-content button:last-child:hover {
  background-color: #e53935;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 帖子详情弹框样式 */
.post-dialog {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  z-index: 100;
  width: 60%;
  max-width: 800px;
  max-height: 80vh;
  overflow: hidden;
  /* 不显示外部滚动条 */
}

.post-dialog-content {
  display: flex;
  flex-direction: column;
}

.post-dialog-header {
  display: flex;
  justify-content: space-between;
  /* 标题和按钮在同一行 */
  align-items: center;
  font-size: 24px;
  margin-bottom: 20px;
}

.post-dialog-header h3 {
  font-size: 20px;
  color: #00aaff;
}

.close-button {
  padding: 5px 10px;
  font-size: 40px;
  /* 设置字体大小，让叉叉明显 */
  background-color: transparent;
  /* 去掉背景色 */
  color: #007bff;
  /* 设置叉叉的颜色 */
  border: none;
  cursor: pointer;
  transition: color 0.3s ease;
}

.close-button:hover {
  color: #0056b3;
  /* 悬停时变成深蓝色 */
}

/* 中间内容部分：固定高度，支持滚动 */
.post-dialog-body {
  flex: 1;
  max-height: 250px;
  /* 固定内容区域高度 */
  overflow-y: auto;
  /* 内容超出时显示滚动条 */
  margin-bottom: 20px;
}

/* 评论区：固定高度，支持滚动 */
.post-dialog-footer {
  font-size: 14px;
  max-height: 150px;
  /* 固定评论区域高度 */
  overflow-y: auto;
  /* 评论超出时显示滚动条 */
  background-color: #f1f1f1;
  padding: 10px;
  border-radius: 5px;
}

/* 修改个人信息和修改密码按钮 */
.edit-button,
.change-password-button {
  padding: 10px 15px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin: 10px 0;
}

.edit-button {
  background-color: orange;
  color: white;
}

.change-password-button {
  background-color: #007bff;
  /* 蓝色按钮 */
  color: white;
}

.change-password-button:hover {
  background-color: #0056b3;
  /* 悬停时颜色变深 */
}

/* 修改密码弹窗背景 */
.password-dialog-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  /* 半透明背景 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 3;
  /* 确保弹窗显示在最上层 */
}

/* 修改密码弹窗 */
.password-dialog {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 300px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.password-dialog h3 {
  text-align: center;
  margin-bottom: 20px;
  color: #007bff;
}

.password-dialog .form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.password-dialog .form-group label {
  margin-bottom: 5px;
}

.password-dialog .form-group input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
}

.dialog-actions {
  display: flex;
  justify-content: space-between;
}

.confirm-button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.confirm-button:hover {
  background-color: #218838;
}

.cancel-button {
  background-color: #dc3545;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.cancel-button:hover {
  background-color: #c82333;
}

.dropdown-container {
  position: relative;
  display: inline-block;
  background-color: rgba(255, 255, 255, 0.5);
  /* 透明背景 */
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  text-align: right;
  z-index: 4;
  margin-left: 16px;
}

.dropdown-text {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.triangle {
  margin-left: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.triangle-rotate {
  transform: rotate(180deg);
  /* 点击后旋转三角形 */
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
  z-index: 1;
  list-style: none;
  padding: 10px 0;
  margin: 0;
  z-index: 5;
}

.dropdown-menu li {
  padding: 10px;
  text-align: left;
  cursor: pointer;
  transition: background-color 0.3s;
}

.dropdown-menu li:hover {
  background-color: #f0f0f0;
}

.selectedoption {
  width: 100%;
  margin-left: 1px;
  overflow-y: auto;
  /* 当内容溢出时显示滚动条 */
  overflow-x: hidden;
  /* 当内容溢出时显示滚动条 */
}

.divider {
  border: none;
  min-height: 3px;
  width: 100%;
  background-color: #45c789;
  /* 天蓝色 */
  z-index: 3;
}

.comment-meta {
  display: flex;
  justify-content: space-between;
  /* 左右分布 */
  font-size: 12px;
  color: #666;
}

.comment-left {
  display: flex;
}

.comment-right {
  margin-right: 40%;
  font-weight: bold;
  white-space: nowrap;
  /* 防止换行 */
  overflow: hidden;
  /* 隐藏超出部分 */
  text-overflow: ellipsis;
  /* 显示省略号 */
  width: 20ch;
  /* 限制显示的字符数为 20 */
}
</style>

<!--<template>-->
<!--  <div>-->
<!--    <h1>用户个人中心</h1>-->

<!--    &lt;!&ndash; 数据加载成功后的显示 &ndash;&gt;-->
<!--    <div v-if="users.length > 0">-->
<!--      <div v-for="user in users" :key="user.userId">-->
<!--        <p>昵称: {{ user.nickname }}</p>-->
<!--        <p>邮箱: {{ user.email }}</p>-->
<!--      </div>-->
<!--    </div>-->

<!--    &lt;!&ndash; 数据加载中 &ndash;&gt;-->
<!--    <p v-else>加载中...</p>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import { ref, onMounted } from 'vue';-->
<!--import { list } from '@/apis/testapi';  // 导入封装的 `list` 函数-->

<!--const users = ref([]);  // 用来存储用户列表数据-->

<!--onMounted(async () => {-->
<!--  try {-->
<!--    const response = await list();  // 调用封装的 `list` 函数获取数据-->
<!--    if (response && response.data && response.data.code === 200) {-->
<!--      users.value = response.data.data;  // 将数据存储到 `users`-->
<!--    } else {-->
<!--      console.error('数据加载失败', response);-->
<!--    }-->
<!--  } catch (error) {-->
<!--    console.error('获取用户列表失败:', error);-->
<!--  }-->
<!--});-->
<!--</script>-->

<!--<style scoped>-->
<!--/* 可以根据需要添加样式 */-->
<!--</style>-->
