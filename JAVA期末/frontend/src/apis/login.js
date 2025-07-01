import request from '../utils/request'

export function loginuser(userlogin={}){
    return request({
        method: 'POST',
        url: '/user/login',
        data:userlogin
    })
}

export function loginadmin(adminlogin={}){
    return request({
        method: 'POST',
        url: '/admin/login',
        data:adminlogin
    })
}
