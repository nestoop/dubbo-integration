package cn.nest.sample;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by botter
 *
 * @Date 23/11/16.
 * @description
 */
public class MonogoDBUtil {

    private static final String DB_HOST = "192.168.1.31";
    private static final int DB_PORT = 27017;
    private static final String DB_NAME = "dm";

    /**
     *
     */
    public static final ThreadLocal<MongoClient> mongoClient = new ThreadLocal<>();

    /**
     *
     */
    public static final ThreadLocal<MongoDatabase> mongoDatabase = new ThreadLocal<>();

    /**
     *
     */
    public static final ThreadLocal<MongoCollection<Document>> collection = new ThreadLocal<>();

    static {

        mongoClient.set(new MongoClient(DB_HOST, DB_PORT));
        mongoDatabase.set(mongoClient.get().getDatabase(DB_NAME));

        mongoDatabase.get().createCollection("dmConnect", new CreateCollectionOptions().capped(true).sizeInBytes(0x10000000));
        if (collection.get() == null) {
            collection.set(mongoDatabase.get().getCollection("dmConnect"));

        }
    }


    /**
     * @param key
     * @param value
     */
    public static void insertOneDocument(String key, String value) {
        Map<String, Object> columes = new HashMap<>();
        Document document = new Document(columes);
        collection.get().insertOne(document);
    }

    public static void selectOneDocumentByKey(String key) {
        FindIterable<Document> findIterable = collection.get().find();

        MongoCursor<Document> iterator = findIterable.iterator();

        while (iterator.hasNext()) {
            Document document = iterator.next();
            String value = (String) document.get(key);

            System.out.println("[key] " + key + " [value] " + value);
        }
    }

    public static void main(String[] args) {
//        MonogoDBUtil.insertOneDocument("admin", "maxwit");
        MonogoDBUtil.selectOneDocumentByKey("admin");
    }
}
