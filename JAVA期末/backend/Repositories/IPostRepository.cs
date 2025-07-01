public interface IPostRepository
{
    IQueryable<Post> GetPosts();
    // 其他方法...
} 