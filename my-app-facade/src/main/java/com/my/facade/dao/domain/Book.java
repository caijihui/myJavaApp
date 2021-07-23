package com.my.facade.dao.domain;

public class Book {

    // 名称
    private String name;
    // 描述
    private String desc;
    // 作者
    private String author;
    // 标签
    private String tag;

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", author='" + author + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
