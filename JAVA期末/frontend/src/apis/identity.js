import request from "@/utils/request"

export const identityGet=async () => {
  try{
      const response = await request({
        url: '/identity',
        method: 'GET',
      })
      if(response.status===200){
        return response.data
      }else{
        console.log('未知错误')
      }
    }catch(error){
      console.log(error)
    }
}