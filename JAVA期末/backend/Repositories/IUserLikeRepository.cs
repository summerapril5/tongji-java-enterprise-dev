public interface IUserLikeRepository
{
    IQueryable<Post> GetUserLikedPosts(string userId);
    // 其他方法...
} 