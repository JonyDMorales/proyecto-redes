import com.mongodb.client.FindIterable;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.json.JSONException;
import org.json.JSONObject;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import java.util.Date;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("snmp")
public class SNMP {
    
    @POST
    @Path("insert")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response busquedaMIB(String request) throws JSONException{
        JSONObject json = new JSONObject(request);
        
        String comunidad = (String) json.get("comunidad");
        String ip = (String) json.get("ip");
        String oid = (String) json.get("oid");
        int version = Integer.parseInt((String) json.get("version"));
        String key = (String) json.get("key");
        String res;
        /*if(version == 3){
            String user = (String) json.get("user");
            String pass = (String) json.get("pass");
            res = getMIB3(comunidad, ip, oid, version, key, user, pass);
        } else {*/
        res = getMIB(comunidad, ip, oid, version, key, "otra");
       
        return Response.ok(new JSONObject().append("res", res).toString())
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Credentials", "true")
            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            .build();
    }
    
    public void monitoreo(){
        String pc = "linux";
        String comunidad = "public";
        String ip = "10.100.64.130";
        String SysRAMUsed = ".1.3.6.1.4.1.2021.4.6.0";
        String SysRAMFree = ".1.3.6.1.4.1.2021.4.11.0";
        String SysCPU = ".1.3.6.1.4.1.2021.11.9.0";
        
        while(true){
            try{
                getMIB(comunidad, ip, SysRAMUsed, 2, "SysRAMUsed", pc);
                getMIB(comunidad, ip, SysRAMFree, 2, "SysRAMFree", pc);
                getMIB(comunidad, ip, SysCPU, 2, "SysCPU", pc);
                Thread.sleep(30000);
            } catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @POST
    @Path("monitoreo")
    public String monitorear(){
        monitoreo();
        return "200";
    }
    
    public String getMIB(String comunidad, String ip, String oid, int version, String key, String pc){
        SnmpGet s = new SnmpGet();
        String res = s.snmpGet(ip, comunidad, oid, version) ;
        if (!res.equals("No se encontro") && !res.equals(" noSuchObject")){
            insert(key, comunidad, ip, version, res, pc);
        }
        return res;
    }
    
    /*public String getMIB3(String comunidad, String ip, String oid, int version, String key, String user, String pass){
        SNMPGet3 s = new SNMPGet3();
        String res = s.SNMPGet3(ip, comunidad, oid, user, pass) ;
        if (!res.equals("No se encontro") && !res.equals(" noSuchObject")){
            insert(key, comunidad, ip, version, res);
        }
        return res;
    }*/
    
    public void insert(String key, String comunidad, String ip, int version, String res, String pc){
        System.out.println(pc);
        DBConnection conexion = new DBConnection();
        Document document = new Document();
        document.append("pc", pc);
        document.append("key", key);
        document.append("comunidad", comunidad);
        document.append("ip", ip);
        document.append("version", version);
        document.append("respuesta", res);
        document.append("created_at", new Date());
        MongoDatabase database = conexion.getDatabase();
        MongoCollection<Document> collection = database.getCollection("redes");
        collection.insertOne(document);
        conexion.closeDataBase();
    }
  
    @POST
    @Path("get")
    @Produces("application/json")
    public Response getAllCollection() throws JSONException{
        DBConnection conexion = new DBConnection();
        MongoCollection<Document> collection = conexion.getAllCollection();
        FindIterable<Document> docs = collection.find();
        JSONObject res = new JSONObject();
        
        for (Document doc : docs) {
            res.append("docs", doc);
        }
        
       return Response.ok(res.toString())
            //.status(200)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Credentials", "true")
            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            //.entity("")
            .build();
    }
}
