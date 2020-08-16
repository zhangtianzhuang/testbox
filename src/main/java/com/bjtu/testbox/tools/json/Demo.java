package com.bjtu.testbox.tools.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.io.Serializable;

class Article implements Serializable {
    private String id;
    private String title;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

// Demo
public class Demo {
    public static void main(String args[]) throws JsonProcessingException {
        CustomerJsonSerializer cjs= new CustomerJsonSerializer();
        // 设置转换 Article 类时，只包含 id, name
        cjs.filter(Article.class, "id,name", null);
        String include = cjs.toJson(new Article());

        cjs = new CustomerJsonSerializer();
        // 设置转换 Article 类时，过滤掉 id, name
        cjs.filter(Article.class, null, "id,name");

        String filter = cjs.toJson(new Article());

        System.out.println("include: " + include);
        System.out.println("filter: " + filter);

        HandlerMethodReturnValueHandler a;
    }
}
