package cn.test.demo.es;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Jimmy on 2019/12/3.
 */
public class ElasticsearchUtils {

    private static class ElasticsearchHolder{
        private static RestHighLevelClient client = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200, "http")));
    }

    public static RestHighLevelClient getClient(){
        return ElasticsearchHolder.client;
    }

    public static <T> void bulkSaveDocument(String indexName, String type, Collection<T> collection){
        //TODO indexName exists?
        // if not CreateIndexRequest else

        // 1. set temporary index settings
        UpdateSettingsRequest settingsRequest = new UpdateSettingsRequest(indexName);
        settingsRequest.settings("", XContentType.JSON);
        try {
            UpdateSettingsResponse settingsResponse = getClient().indices().putSettings(settingsRequest);
            if(!settingsResponse.isAcknowledged()){
                System.out.println("set temporary index settings is not acknowledged");
            }
        } catch (IOException e) {
            // do nothing
        }

        // 2. bulk save documents
        BulkRequest bulkRequest = new BulkRequest();
        collection.stream().filter(Objects::nonNull).forEach(t->bulkRequest.add(new IndexRequest(indexName,type).source(JSON.toJSON(t), XContentType.JSON)));
        try {
            // TODO async?
            BulkResponse bulkResponse = getClient().bulk(bulkRequest);
        } catch (IOException e) {
            // do nothing
        }

        // 3. regress index settings
        settingsRequest.settings("", XContentType.JSON);
        try {
            UpdateSettingsResponse settingsResponse = getClient().indices().putSettings(settingsRequest);
            if(!settingsResponse.isAcknowledged()){
                System.out.println("return index settings is not acknowledged");
            }
        } catch (IOException e) {
            // do nothing
        }
    }

    // search method

}
