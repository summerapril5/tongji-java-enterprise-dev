<template>
  <div>
    <form @submit.prevent="handleSubmit" class="form-container">
      <div class="form-group">
        <label for="name">名称:</label>
        <input type="text" id="name" v-model="formData.name" required />
      </div>
      <div class="form-group">
        <label for="description">介绍:</label>
        <textarea id="description" v-model="formData.description" required></textarea>
      </div>
      <div class="form-group">
        <label for="image">上传图片:</label>
        <input type="file" id="image" @change="handleImageChange" accept="image/*" required />
        <p v-if="imagePreviewUrl" class="image-preview">预览: <img :src="imagePreviewUrl" alt="预览图片" width="100" /></p>
      </div>
      <button type="submit">提交</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { CategoryAddInfo } from '@/utils/structures';
import { categoryAdd } from '@/apis/admin';

const formData = ref({
  name: '',
  description: '',
  image: null,
});
 
const imagePreviewUrl = ref(null);
 
const handleImageChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    formData.value.image = file;
    const reader = new FileReader();
    reader.onload = (e) => {
      imagePreviewUrl.value = e.target.result;
    };
    reader.readAsDataURL(file);
  }
};

const clearFormData = () => {
  formData.value = {
    name: '',
    description: '',
    image: null,
  };
  imagePreviewUrl.value = null;
};
 
const handleSubmit = async () => {
  if (!formData.value.name || !formData.value.description || !formData.value.image) {
    alert('请填写所有字段并上传图片');
    return;
  }

  const formDataToSend = new CategoryAddInfo()
  formDataToSend.categoryName=formData.value.name
  formDataToSend.description=formData.value.description
  formDataToSend.image=formData.value.image

  if(await categoryAdd(formDataToSend)){
    clearFormData()
  }
};
</script>

<style>
body {
  font-family: Arial, sans-serif;
  background-color: #f4f4f4;
  margin: 0;
  padding: 20px;
}

form {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin: auto;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"], textarea, input[type="file"] {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

textarea {
  resize: vertical;
  min-height: 100px;
}

button {
  background-color: #007bff;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #0056b3;
}

p img {
  display: block;
  margin-top: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-group {
  margin-bottom: 15px;
}
</style>