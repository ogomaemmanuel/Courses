package ke.co.safaricom.weblog.blog.dto;

public class BlogCreateRequest {

    private String title;
    private Long userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
