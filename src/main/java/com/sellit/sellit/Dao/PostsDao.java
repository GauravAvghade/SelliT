package com.sellit.sellit.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellit.sellit.entities.Posts;

public interface PostsDao extends JpaRepository<Posts, Integer>{

}


