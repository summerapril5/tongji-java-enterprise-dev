<template>
  <div class="login-container">
    <div class="slider">
      <div :class="active ===1? 'form':'form hidden'">
        <div class="title">
          管理员你好，欢迎 <b>回来</b>
        </div>
        <div class="subtitle">
          登录你的账户
        </div>
        <div class="inputf">
          <input v-model="adminlogin.account" type="text" placeholder="账号"/>
          <span class="label">账号</span>
          <div v-if="errors.loginAccount" class="error-message">{{ errors.loginAccount }}</div>
        </div>
        <div class="inputf">
          <input v-model="adminlogin.password" type="text" placeholder="密码"/>
          <span class="label">密码</span>
          <div v-if="errors.loginPassword" class="error-message">{{ errors.loginPassword }}</div>
        </div>
        <button @click="login" class="button1">登录</button>
      </div>
      <div :class="active ===2?'form':'form hidden'" >
        <div class="title">
          开始
        </div>
        <div class="subtitle">
          创建你的账户
        </div>
        <div class="inputf">
          <input v-model="adminregister.account" type="text" placeholder="账号"/>
          <span class="label">账号</span>
          <div v-if="errors.registerAccount" class="error-message">{{ errors.registerAccount }}</div>
        </div>
        <div class="inputf">
          <input v-model="adminregister.password" type="text" placeholder="密码"/>
          <span class="label">密码</span>
          <div v-if="errors.registerPassword" class="error-message">{{ errors.registerPassword }}</div>
        </div>
        <button @click="register" class="button1">注册</button>
      </div>
      <div :class="active ===1?'card':'card active'">
        <div class="head">
          <div class="name">
            javaee<span>论坛</span>
          </div>
        </div>
        <div class="desc">
          这目前是一个论坛-----------------------------------------------------------------------------------------------------------------------------------------------------------
        </div>
        <div class="btn">
          {{active===1?'新管理员 ？':'已有账户 ？'}}
          <button @click="active =(active===1)?2:1">
            {{active===1?'去注册 ':'去登录'}}
          </button>
        </div>
      </div>
    </div>

  </div>


</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';  // 导入 useRouter
import {loginadmin} from '@/apis/login';  // 导入封装的 login API
import {registeradmin} from "@/apis/register";


import {ref} from "vue"

const active = ref(1)

const router = useRouter();

const adminlogin = reactive({
  account:'',
  password:''
});

const adminregister = reactive({
  account:'',
  passwword:''
})

const errors = reactive({
  loginAccount: '',
  loginPassword: '',
  registerAccount: '',
  registerPassword: ''
});

// 验证登录表单
function validateLoginForm() {
  errors.loginAccount = adminlogin.account ? '' : '账号不能为空';
  errors.loginPassword = adminlogin.password ? '' : '密码不能为空';
  return !errors.loginAccount && !errors.loginPassword;
}

// 验证注册表单
function validateRegisterForm() {
  errors.registerAccount = adminregister.account ? '' : '账号不能为空';
  errors.registerPassword = adminregister.password ? '' : '密码不能为空';
  return !errors.registerAccount && !errors.registerPassword;
}


function login(){
  if (validateLoginForm()){
    console.log("管理员输入信息为",adminlogin);
    loginadmin(adminlogin).then((response)=>{
      console.log("获取信息为",response);
      if(response.data.code===1){
        console.log("登录成功");
        localStorage.setItem("token",response.data.data);
        router.push("/usercenter");
      }else{
        console.log("登录失败");
        alert("账号或密码错误");
      }

    }).catch(error =>{
      console.log("登录出错",error);
    })
  }

}

function register(){
  if (validateRegisterForm()){
    console.log("管理员输入信息为",adminregister);
    registeradmin(adminregister).then((response)=>{
      console.log("获取信息为",response);
      if(response.data.code===1){
        console.log("注册成功");
        localStorage.setItem("token",response.data.data);
        router.push("/usercenter");
      }else{
        console.log("注册失败");
        alert("注册失败")
      }
    }).catch(error =>{
      console.log("注册出错",error);
    })
  }

}


</script>

<style>
.login-container {
  width: 100%;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: url("../../assets/login_background.png")no-repeat center center;
  background-size: cover;


}
.slider{
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

.form{
  width: 400px;
  height: 500px;
  background: rgba(17,25,40,0.75);
  backdrop-filter: blur(16px) saturate(0);
  border-radius: 10px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  padding: 40px 60px;
  box-shadow: rgba(50,50,93,0.25) 50px 50px 100px -20px,
  rgba(0,0,0,0.5) 30px 30px 60px -30px,
  rgba(212,217,222,0.35) 2px -2px 6px 0 inset;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: flex-start;
  margin: 0 10px;
  z-index: 3;
  transition: 0.25s ease-in-out;

  &.hidden{
    height: 395px;
    box-shadow: none;
    z-index: 1;
  }


}

.title{
  font-size: 18px;
  color: rgba(246, 240, 255);
  letter-spacing: 1px;
  font-weight: 300;

}

.subtitle{
  font-size: 11px;
  color: rgba(246, 240, 255);
  letter-spacing: 1px;
  margin-bottom: 35px;
}

.inputf{
  width: 92%;
  position: relative;
  margin-bottom: 35px;
  box-sizing: border-box;
  input{
    width: 100%;
    height: 35px;
    border: none;
    outline: 1.5px solid rgb(200,200,220);
    background: transparent;
    border-radius: 8px;
    font-size: 12px;
    padding: 0 15px;
    color: rgba(246, 249, 255);
    &::placeholder{
      color: rgba(175, 180, 190);
    }
    &:focus{
      outline: 1.5px solid rgb(224,229,240);
      &::placeholder{
        opacity: 0;
      }
      & + .label{
        opacity: 1;
        top: -20px;
      }

    }
    &:not(:placeholder-shown) + .label{
      opacity: 1;
      top: -20px;

    }
  }
}

.label{
  position: absolute;
  top: 0;
  left: 0;
  color: rgba(246, 249, 255);
  font-size: 11px;
  font-weight: bold;
  transition: 0.25s ease-out;
  opacity: 0;
}

.button1{
  width: 100%;
  height: 35px;
  background: rgb(36,217,127);
  color: #ffffff;
  border: none;
  outline: none;
  border-radius: 5px;
  font-weight: bold;
  font-size: 12px;
  cursor: pointer;
  box-sizing: border-box;

}

.card{
  position: absolute;
  right: 0;
  top: 50%;
  transform: translate(0, -50%);
  width: 471px;
  height: 400px;
  background: url("../../assets/card_background.png") white;
  background-size: contain;
  padding: 40px;
  border-radius: 0 10px 10px 0;
  transition: 0.5s ease-in-out;
  z-index: 2;
  &.active{
    right:calc(100% - 551px);
    border-radius: 10px 0 0 10px;
  }
  .head{
    font-size: 34px;
    margin-bottom: 35px;
    .name{
      font-weight: 300;
      span{
        color: rgba(36, 217, 127);
        font-weight: bold;

      }
    }

  }
  .desc{
    font-size: 14px;
    text-align: justify;
    margin-bottom: 35px;
  }
  .btn{
    button{
      background: rgba(36, 217, 127);
      font-size: 14px;
      padding: 5px 15px;
      border: none;
      outline: none;
      border-radius: 5px;
      cursor: pointer;
      margin-left: 10px;
    }
  }

}

.error-message {
  color: red;
  font-size: 12px;
  margin-top: 5px;
}

</style>