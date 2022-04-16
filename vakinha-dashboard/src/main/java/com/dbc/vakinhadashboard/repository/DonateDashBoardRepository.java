package com.dbc.vakinhadashboard.repository;

import com.dbc.vakinhadashboard.config.MongoDBConnection;
import com.dbc.vakinhadashboard.dto.DonateDashBoardDTO;
import com.dbc.vakinhadashboard.entity.DonateDashBoardEntity;
import com.dbc.vakinhadashboard.exception.BusinessRuleException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;

@Repository
public class DonateDashBoardRepository {

    private final MongoDBConnection conn = new MongoDBConnection();

    private MongoCollection<Document> collection;

    private void connect() throws BusinessRuleException {
        collection = conn.connection().getCollection("donates");
    }

    private void close() {
        conn.close();
    }


    public List<DonateDashBoardEntity> findAll() throws Exception {
        List<DonateDashBoardEntity> list = new ArrayList<>();
        try {
            connect();

            FindIterable<Document> findIterable = collection.find();

            for (Document document : findIterable) {
                list.add(documentToDonate(document));
            }

            close();
            return list;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void insert(DonateDashBoardEntity donateEntity) throws BusinessRuleException {
        try {
            connect();

            Document document = new Document("id_donate", donateEntity.getIdDonate())
                    .append("id_request", donateEntity.getIdRequest())
                    .append("donate_value", donateEntity.getDonateValue())
                    .append("donator_email", donateEntity.getDonatorEmail())
                    .append("category", donateEntity.getCategory())
                    .append("id_request", donateEntity.getIdRequest());

            collection.insertOne(document);
            close();

        } catch (Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    public void update(DonateDashBoardEntity donateEntity) throws Exception {
        try {
            connect();

            Bson update = Updates.combine(
                    Updates.set("id_request", donateEntity.getIdRequest()),
                    Updates.set("donate_value", donateEntity.getDonateValue()),
                    Updates.set("donator_email", donateEntity.getDonatorEmail()),
                    Updates.set("category", donateEntity.getCategory()),
                    Updates.set("id_request", donateEntity.getIdRequest())
            );

            Document query = new Document("id_donate", donateEntity.getIdDonate());
            UpdateOptions options = new UpdateOptions().upsert(true);

            collection.updateOne(query, update, options);

            close();

            donateEntity.setIdDonate(donateEntity.getIdDonate());
        } catch (Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    public void deleteById(Integer id) throws Exception {
        try {
            connect();

            Document query = new Document("id_donate", id);

            collection.deleteOne(query);

            close();
        } catch (Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    private DonateDashBoardEntity documentToDonate(Document document) {
        DonateDashBoardEntity donateEntity = new DonateDashBoardEntity();
        donateEntity.setIdDonate(Integer.parseInt(document.get("id_donate").toString()));
        donateEntity.setIdRequest(Integer.parseInt(document.get("id_request").toString()));
        donateEntity.setDonateValue(Double.parseDouble(document.get("donate_value").toString()));
        donateEntity.setDonatorEmail((document.get("donator_email").toString()));
        donateEntity.setCategory((document.get("category").toString()));

        return donateEntity;
    }

    public List<DonateDashBoardDTO> donatesDashBoard() throws Exception {
        try {
            connect();

            AggregateIterable<Document> agg = collection.aggregate(List.of(
                    group("$category", Accumulators.sum("total_donates", 1), Accumulators.sum("total_value", "$donate_value"))));

            List<DonateDashBoardDTO> donateDashBoardDTOList = new ArrayList<>();
            for (Document document : agg) {
                DonateDashBoardDTO donateDashBoardDTO = new DonateDashBoardDTO();
                donateDashBoardDTO.setCategory(document.get("_id").toString());
                donateDashBoardDTO.setDonates(Integer.parseInt(document.get("total_donates").toString()));
                donateDashBoardDTO.setDonatesValue(Double.parseDouble(document.get("total_value").toString()));
                donateDashBoardDTOList.add(donateDashBoardDTO);
            }

            close();
            return donateDashBoardDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}

