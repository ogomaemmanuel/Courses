package ke.co.safaricom.weblog.blog.controller;

import ke.co.safaricom.weblog.blog.dto.BlogCreateRequest;
import ke.co.safaricom.weblog.blog.entity.Blog;
import ke.co.safaricom.weblog.blog.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    private final BlogService blogService;
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs(){
        var blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id){
        var blog = blogService.getBlogById(id);
        return ResponseEntity.of(blog);
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody BlogCreateRequest newBlog){
        var blog = blogService.createBlog(newBlog);
        return ResponseEntity.ok(blog);
    }

}
