package com.example.my.dao.impl;

import com.example.my.dao.ArticleDao;
import com.example.my.dao.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleDaoJdbcTemplateImpl  implements  ArticleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long insertArticle(Article article){
        String sql = "INSERT INTO `uc` (`name`) VALUES (?)";
        return (long) jdbcTemplate.update(sql, article.getName());
    }

    public List<Article> getArticleList(){
        String sql = "select * from article";

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql);
        if(CollectionUtils.isEmpty(mapList)){
            return null;
        }
        ArrayList<Article> articles = new ArrayList<>();
        for (Map<String,Object> map : mapList){
            Article article = new Article();
            String name = (String) map.get("name");
            article.setName(name);
            articles.add(article);
        }

        return articles;
    }

    public Map<String, Object> getArticle(){
        String sql = "select * from article limit 1";

        Map<String, Object> map = jdbcTemplate.queryForMap(sql);

        return map;
    }
}
