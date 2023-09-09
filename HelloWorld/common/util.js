const baseUrl = 'https://ceshi2.dishait.cn/api/v1';  
const nowTime = Date.now || function () {  
    return new Date().getTime();  
};  
export default {  
    baseUrl,  
    nowTime,  
}  

