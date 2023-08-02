package de.peaqe.xbans.provider;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: BanDatabase
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 02.08.2023 / 11:39
 *
 */

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import de.peaqe.xbans.XBans;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.UUID;

@SuppressWarnings(value = "unused")
public class PlayerDatabase {

    MongoClient mongoClient;
    private final MongoCollection<Document> collection;
    private final Gson gson = new Gson();

    @SuppressWarnings(value = "deprecation")
    public PlayerDatabase() {

        var mongo_config = XBans.instance.getDatabaseConfig();

        MongoCredential mongoCredential = MongoCredential.createCredential("noviaplugins", "noviamc", "noviapluginpassword".toCharArray());

        mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), List.of(mongoCredential));
        MongoDatabase database = mongoClient.getDatabase("noviamc");
        String collectionName = "players";
        collection = database.getCollection(collectionName);

        MongoCollection<Document> mongoService = database.getCollection(collectionName);

    }

    public void registerPlayer(ProxiedPlayer player) {
        var uuid = player.getUniqueId();
        var name = player.getName();

        Document filter = new Document("player_uuid", uuid.toString());
        Document update = new Document("$set", new Document("player_name", name));
        UpdateOptions options = new UpdateOptions().upsert(true);

        collection.updateOne(filter, update, options);
    }

    public String getPlayerName(UUID uuid) {
        Document document = collection.find(Filters.eq("player_uuid", uuid.toString())).first();
        return document == null ? "none" : document.getString("player_name");
    }

    public UUID getPlayerUUID(String name) {
        Document document = collection.find(Filters.eq("player_name", name)).first();
        return document == null ? UUID.randomUUID() : UUID.fromString(document.getString("player_uuid"));
    }

    public boolean isRegistered(String name) {
        return collection.countDocuments(Filters.eq("player_name", name)) > 0;
    }

}
