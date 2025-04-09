package di.데이터저장소시스템;

public class MongoClient {

    public void save(String data) {
        System.out.println("Data saved in MongoDB: " + data);
    }

    public String findById(String id) {
        System.out.println("Data found in MongoDB with id: " + id);
        return "Data found in MongoDB with id: " + id;
    }
}
