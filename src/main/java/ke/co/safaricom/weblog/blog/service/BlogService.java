package ke.co.safaricom.weblog.blog.service;

import ke.co.safaricom.weblog.blog.dto.BlogCreateRequest;
import ke.co.safaricom.weblog.blog.entity.Blog;
import ke.co.safaricom.weblog.blog.repository.BlogRepository;
import ke.co.safaricom.weblog.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public List<Blog> getAllBlogs(){
        var blogs = blogRepository.findAll();
        return blogs;
    }

    public Optional<Blog> getBlogById(Long id){
        var blog = blogRepository.findById(id);
        return blog;
    }

    public Blog createBlog(BlogCreateRequest newBlog){
        var blog = new Blog();
        var user = userRepository.findById(newBlog.getUserId());
        user.ifPresent(u->{
            blog.setUser(u);
            blog.setTitle(newBlog.getTitle());
            blogRepository.save(blog);
        });
        return blog;
    }

}


