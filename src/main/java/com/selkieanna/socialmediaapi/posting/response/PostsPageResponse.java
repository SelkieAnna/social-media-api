package com.selkieanna.socialmediaapi.posting.response;

import java.util.List;

public class PostsPageResponse {
    public List<PostResponse> posts;
    public Integer currentPage;
    public Integer totalItems;
    public Integer totalPages;

    public PostsPageResponse(List<PostResponse> posts, Integer currentPage, Integer totalItems, Integer totalPages) {
        this.posts = posts;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<PostResponse> getPosts() {
        return posts;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setPosts(List<PostResponse> posts) {
        this.posts = posts;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
