import com.alibaba.fastjson.JSON;
import com.liuli.boot.es.java.model.Sort;
import com.liuli.boot.es.java.model.es.UserEsModel;
import com.liuli.boot.es.java.model.page.PageResult;
import com.liuli.boot.es.java.utils.EsDocumentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.List;

@Slf4j
@FixMethodOrder(MethodSorters.JVM)
public class DocTest {

    private static  RestHighLevelClient client;

    @Before
    public void createEsClient(){
         client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost",9200, "http")
        ));
    }

    @After
    public void close(){
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
              log.error("close client error:{}", e);
            }
        }
    }


    /**
     * create doc
     */
    @Test
    public void testCreateDoc() throws Exception{
        UserEsModel model = new UserEsModel();
        model.setId("11");
        model.setName("三毛");
        model.setAge(38);
        EsDocumentUtils.indexDoc(client, "user", model);
    }


    /**
     * update doc
     */
//    @Test
//    public void testUpdateDoc() throws Exception{
//        UserEsModel model = new UserEsModel();
//        model.setId("1");
//        model.setAge(68);
//        EsDocumentUtils.updateDoc(client, "user", model);
//    }


//    /**
//     * get doc
//     */
//    @Test
//    public void testGetDoc() throws Exception{
//        UserEsModel user = EsDocumentUtils.getDoc(client, "user", "1", UserEsModel.class);
//        log.info("user={}", JSON.toJSONString(user));
//    }


    /**
     * delete doc
     */
//    @Test
//    public void testDeleteDoc() throws Exception{
//        EsDocumentUtils.deleteDoc(client, "user", "1");
//    }


//    @Test
//    public void testBatchInsertDocs() throws Exception{
//        List<BaseEsModel> models = new ArrayList<>();
//        for (int i = 1; i < 10; i++) {
//            UserEsModel model = new UserEsModel();
//            model.setId("" + i);
//            model.setName("张三" + i);
//            model.setAge(38 + i);
//            models.add(model);
//        }
//        EsDocumentUtils.batchInsertDocs(client, "user", models);
//    }


//    @Test
//    public void testBatchDeleteDocs() throws Exception{
//        List<String> docIds = new ArrayList<>();
//        for (int i = 1; i < 10; i++) {
//            docIds.add(i+"");
//        }
//        EsDocumentUtils.batchDeleteDocs(client, "user", docIds);
//    }


//        @Test
//    public void testListAllDocs() throws Exception{
//            List<UserEsModel> users = EsDocumentUtils.listAll(client, "user", UserEsModel.class);
//            log.info("users={}", JSON.toJSONString(users));
//        }

    /**
     * 分页查询所有
     * @throws Exception
     */
    @Test
    public void testListAllByPageDocs() throws Exception{
        PageResult<UserEsModel> users = EsDocumentUtils.listAllByPage(client, "user", UserEsModel.class, 2, 1);
        log.info("users={}", JSON.toJSONString(users));
    }


    /**
     * 分页查询 + 按年龄升序
     * @throws Exception
     */
    @Test
    public void testListAllByPageSortDocs() throws Exception{
        Sort age = Sort.builder().sortField("age").asc(true).build();
        PageResult<UserEsModel> users = EsDocumentUtils.listAllByPage(client, "user", UserEsModel.class, 1, 1, age);
        log.info("users={}", JSON.toJSONString(users));
    }


    /**
     * 分页查询 + 按年龄升序
     * @throws Exception
     */
    @Test
    public void testListByPageSortDocs() throws Exception{
        Sort age = Sort.builder().sortField("age").asc(true).build();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // age = 40
//        TermQueryBuilder termQuery = QueryBuilders.termQuery("age", 40);
//        boolQueryBuilder.filter(termQuery);
        // id = 3
//        TermQueryBuilder termQuery2 = QueryBuilders.termQuery("id", 3);
//        boolQueryBuilder.filter(termQuery2);

        // 使用match查询会对检索词进行分词后查询，分词后是按OR ，会查询出姓名中含张或者三的记录
        //MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "张三");

        //使用match查询会对检索词进行分词后查询，分词后是按AND，会查询出姓名中必须同时含张三的记录
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "张三").operator(Operator.AND);
//        boolQueryBuilder.filter(matchQueryBuilder);

        // 查询以张三开头 （形如LIKE：张三%）的记录：  prefixQuery不会对检索词进行分词查询
        // 由于索引数据时使用默认标准分词器，分词后只词项：张、三，不包含张三的词项，故以张三开头查询匹配不到数据
//        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("name", "张三");
//        boolQueryBuilder.filter(prefixQueryBuilder);

        // 查询含有张三的数据（形如LIKE：%张三%）的记录：  wildcardQuery不会对检索词进行分词查询
        // 由于索引数据时使用默认标准分词器，分词后只词项：张、三，不包含张三的词项，故以*张三*查询匹配不到数据
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("name", "*张三*");
        boolQueryBuilder.filter(wildcardQueryBuilder);

        PageResult<UserEsModel> users = EsDocumentUtils.listAllByPage(client, "user", UserEsModel.class, 1, 10, age, boolQueryBuilder);
        log.info("users={}", JSON.toJSONString(users));
    }


}
