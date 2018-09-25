import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBConnection {
    
    private MongoClient client;
    private MongoDatabase database;

    public DBConnection() {
        MongoClientURI uri = new MongoClientURI("mongodb://JonyD:JDmorales#0131@trabajo-terminal-shard-00-00-jjpal.mongodb.net:27017,trabajo-terminal-shard-00-01-jjpal.mongodb.net:27017,trabajo-terminal-shard-00-02-jjpal.mongodb.net:27017/redes?ssl=true&replicaSet=trabajo-terminal-shard-0&authSource=admin");
        client = new MongoClient(uri);
        database = client.getDatabase("registros");
    }
    
    public MongoDatabase getDatabase(){
        return database;
    }
    
    public MongoCollection getAllCollection(){
        MongoCollection<Document> collection = database.getCollection("redes");
        return collection;
    }
    
    public void closeDataBase(){
        client.close();
    }
    
}
