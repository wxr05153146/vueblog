package com.prj.controller;
import cn.hutool.core.bean.BeanUtil;
import com.prj.common.lang.Result;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prj.entity.Blog;
import com.prj.service.BlogService;
import com.prj.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2020-08-14
 */
@RestController
//@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private static Blog blog=null;
    @Autowired
    BlogService blogService;

    /**
     * 分页*/
    //@GetMapping("/blogs")
    @RequestMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData);
    }

    /**
     * 删除博客记录*/
    //@GetMapping("/blog/{id}")
    @RequestMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.succ(blog);
    }
    /**
     * 添加编辑博客*/
    //@RequiresAuthentication//权限
    @PostMapping("/blog/edit")
    @ResponseBody
    public  Result edit(@Validated @RequestBody Blog blog) {

        Blog temp = null;
        if(blog.getId() != null) {
          temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            //System.out.println(ShiroUtil.getProfile().getId());
            //Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(1);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.succ(null);


    }


}
