package com.liuli.Controller;

import com.liuli.pojo.Novel;
import com.liuli.service.NovelService;
import com.liuli.utils.elasticsearch.Construct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-9-1.
 */
@RestController
@RequestMapping("")
public class NovelController {

    @Autowired
    private NovelService novelService;


    /***
     * 建立索引
     * @return
     */
    @GetMapping("create/index")
    public ResponseEntity createBookIndex(){
        try{
            List<Construct> constructList = new ArrayList<Construct>();
            Construct title = new Construct();
            title.setField("title");
            title.setType("text");
            title.setAnalyzer("ik_max_word");
            title.setSearchAnalyzer("ik_smart");

            Construct author = new Construct();
            author.setField("author");
            author.setType("keyword");

            Construct pages = new Construct();
            pages.setField("pages");
            pages.setType("integer");


            Construct publishDate = new Construct();
            publishDate.setField("publish_date");
            publishDate.setType("date");
            publishDate.setFormat("yyyy-MM-dd");

            constructList.add(title);
            constructList.add(author);
            constructList.add(pages);
            constructList.add(publishDate);
           String result =  novelService.createIndex(constructList,"book","novel");
            return new ResponseEntity(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * 建立索引
     * @return
     */
    @GetMapping("del/index")
    public ResponseEntity delBookIndex(){
        try{
            String result =  novelService.delIndex("book");
            return new ResponseEntity(result, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /***
     * 新增小说
     * @param novel
     * @return
     * @throws Exception
     */
    @PostMapping("/add/book/novel")
    public ResponseEntity addNovel(Novel novel){
        try {
           String docId =  novelService.saveNovel(novel);
            return new ResponseEntity(docId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除小说
     * @param id 文档ID
     * @return
     */
    @DeleteMapping("/del/book/novel/{id}")
    public ResponseEntity delNovel(@PathVariable("id") String id) {
        try {
            String docId =  novelService.delNovel(id);
            return new ResponseEntity(docId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     *  更新文档
     * @param novel
     * @return
     */
    @PutMapping("/update/book/novel")
    public ResponseEntity updateNovel(Novel novel){
        try {
            String docId =  novelService.updateNovel(novel);
            return new ResponseEntity(docId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /***
     *  查询文档
     * @param novel
     * @return
     */
    @PostMapping("/query/book/novel")
    public ResponseEntity queryNovel(Novel novel,@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "pageSize",defaultValue = "2")
                                     Integer pageSize){
        try{
            List<Novel> novelList = novelService.schNovel(novel,(page-1)*pageSize,pageSize);
            return new ResponseEntity(novelList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
