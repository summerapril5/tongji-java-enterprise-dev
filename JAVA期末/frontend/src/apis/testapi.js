import request from '../utils/request'

export function list(){
    return request({
        method: 'GET',
        url: '/user/list',
    })
}