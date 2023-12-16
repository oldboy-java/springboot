import com.liuli.boot.es.java.utils.EsIndexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@FixMethodOrder(MethodSorters.JVM)  //从上向下执行用例
public class IndexTest {

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
     * create index
     */
    @Test
    public void testCreateIndex() throws Exception{
        EsIndexUtils.createIndex(client, "user");
    }


    /**
     * create index
     */
    @Test
    public void testCreateIndex2() throws Exception{
        Map<String, Object> properties =new HashMap<>();
        properties.put("id", "LONG");
        properties.put("name", "STRING");
        EsIndexUtils.createIndex(client, "student");
    }

    /**
     *  view index
     */
    @Test
    public void testViewIndex() throws Exception{
        GetIndexResponse getIndexResponse = EsIndexUtils.getIndex(client, "student");
        log.info("mappings={}", getIndexResponse.getMappings());
        log.info("aliases={}", getIndexResponse.getAliases());
        log.info("settings={}", getIndexResponse.getSettings());
    }

    /**
     *  exist index
     */
    @Test
    public void testExistsIndex() throws Exception{
        boolean existsIndex = EsIndexUtils.isExistsIndex(client, "user");
        log.info("the current index exists ?:{}", existsIndex);
    }



    /**
     *  delete index
     */
//    @Test
//    public void testDeleteIndex() throws Exception{
//        EsIndexUtils.deleteIndex(client, "user");
//    }
}
