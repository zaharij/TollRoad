/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tollroad.daopack;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mycompany.tollroad.constants.TollRoadConstants.*;

/**
 * data access object
 * @author Zakhar
 */
public class TollRoadDao {
    MongoClient client = new MongoClient(new MongoClientURI(MONGO_CLIENT_URI));
    MongoDatabase mongoDatabase = client.getDatabase(TOLLROAD_DB);
    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(DRIVERS_COLLECTION);
    MongoCollection<Document> mongoCollectionMatrix = mongoDatabase.getCollection(GRAPHCHECKPOINT_COLLECTION);

    
    /**
     * returns the matrix of checkpoints
     * , which represents the graph of 10 vertices and 10 line graphs
     * @return ArrayList of ArrayLists of integers
     */
    public ArrayList getCheckpointsMatrix(){
        FindIterable<Document> find = mongoCollectionMatrix.find();
        ArrayList checkpointsMatrixArray = (ArrayList) find.iterator().next().get(GRAPHCHECKPOINTS_COLLECTION);
        return checkpointsMatrixArray;
    }
    
    /**
     * returns an array of numbers of passed checkpoints
     * @param idUser - user's id
     * @return ArrayList of integers
     */
    public ArrayList getPassedCheckpoints(int idUser){
        Bson filter = new Document(USER_ID, idUser);
        FindIterable<Document> findCollection = mongoCollection.find(filter);
        ArrayList checkpointsPassedArray = (ArrayList) findCollection.iterator().next().get(CHECKPOINTS_PASSED_COLLECTION);
        return checkpointsPassedArray;
    }
    
    /**
     * returns start time
     * @param idUser - user's id
     * @return String
     */
    public String getStartTime(int idUser){
        Bson filter = new Document(USER_ID, idUser);
        FindIterable<Document> findCollection = mongoCollection.find(filter);
        String timeIn = (String) findCollection.iterator().next().get(DATETIME_IN); 
        return timeIn;
    }
    
    /**
     * returns price per kilometer
     * @return 
     */
    public double getPricePerKm(){
        FindIterable<Document> find = mongoCollectionMatrix.find();
        double pricePerKm = (double) find.iterator().next().get(PRICE_PER_KM);
        return pricePerKm;
    }
    
    /**
     * creates collection of driver's data
     * @param idUser new id
     */
    public void createDriverData(int idUser){
        Document doc = new Document();
        doc.putIfAbsent(USER_ID, idUser);                 
        mongoCollection.insertOne(doc);
    }
    
    /**
     * deletes driver's data
     * @param idUser - user's identification
     */
    public void deleteDriverData(int idUser){
	Bson filter = new Document(USER_ID, idUser);
	mongoCollection.deleteOne(filter);
    }
    
    /**
     * updates driver's data, using parameters
     * @param idUser - user key
     * @param updateName - name of value to update
     * @param updateValue - value to update
     */
    public void updateDriverData(int idUser, String updateName, Object updateValue){
        ArrayList passedCheckpointsArr = getPassedCheckpoints(idUser);
        if (updateName.equals(CHECKPOINTS_PASSED_COLLECTION) && passedCheckpointsArr != null){
            passedCheckpointsArr.add(updateValue);
            updateValue = passedCheckpointsArr;
        } else if (updateName.equals(CHECKPOINTS_PASSED_COLLECTION) && passedCheckpointsArr == null){
            passedCheckpointsArr = new ArrayList();
            passedCheckpointsArr.add(updateValue);
            updateValue = passedCheckpointsArr;
        }
 	Bson filter = new Document(USER_ID, idUser);
	Bson newValue = new Document(updateName, updateValue);
	Bson updateOperationDocument = new Document(SET_COMMAND, newValue);
	mongoCollection.updateOne(filter, updateOperationDocument);
    }
}
