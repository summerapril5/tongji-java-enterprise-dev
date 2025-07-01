import request from '../utils/request'

export function getUserInfo(userId) {
    return request.get(`/userCenter/${userId}`);
}

export function updateUserInfo(userId, userInfo) {
    return request.put(`/userCenter/update/${userId}`, userInfo);
}

export function fetchPostsByAuthor(userId) {
    return request.get(`/userCenter/author/${userId}`);
}

export const fetchFavoritePostsByUser = async (userId) => {
    try {
        const response = await request.get(`/userCenter/favorites/${userId}`);
        return response;
    } catch (error) {
        throw new Error('无法获取收藏帖子: ' + error.message);
    }
};

export const fetchCommentsByUser = async (userId) => {
    const response = await request.get(`/userCenter/comments/${userId}`);
    return response;
};
// 新增：获取用户的关注列表
export const fetchFollowsByUser = async (userId) => {
    try {
        const response = await request.get(`/follow/user/${userId}`);
        return response;
    } catch (error) {
        throw new Error('无法获取关注信息: ' + error.message);
    }
};

export const fetchPreferredCategoriesByUserId = async (userId) => {
    if (!userId) {
        throw new Error('用户ID不能为空');
    }

    try {
        // 使用模板字符串来构建URL
        // 返回响应数据
        return await request.get(`/userCenter/preferredCategories/${userId}`, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    } catch (error) {
        // 抛出带有自定义消息的错误
        throw new Error('无法获取偏好板块信息: ' + error.message);
    }
};

export const deletePostById = async (postId) => {
    try {
        const response = await request.delete(`/userCenter/delete/${postId}`);
        return response;

    } catch (error) {
        throw new Error('删除帖子失败: ' + error.message);
    }
};
