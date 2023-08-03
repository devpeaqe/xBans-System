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
import de.peaqe.xbans.XBans;
import de.peaqe.xbans.utils.BanID;
import de.peaqe.xbans.utils.IDUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = "unused")
public class BanDatabase {

    MongoClient mongoClient;
    private final MongoCollection<Document> collection;
    private final Gson gson = new Gson();

    @SuppressWarnings(value = "deprecation")
    public BanDatabase() {

        var mongo_config = XBans.instance.getDatabaseConfig();

        MongoCredential mongoCredential = MongoCredential.createCredential("noviaplugins", "noviamc", "noviapluginpassword".toCharArray());

        mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), List.of(mongoCredential));
        MongoDatabase database = mongoClient.getDatabase("noviamc");
        String collectionName = "xbans";
        collection = database.getCollection(collectionName);

        MongoCollection<Document> mongoService = database.getCollection(collectionName);
    }

    public void banPlayer(String autor, String name, BanID banID) {
        if (!XBans.instance.getPlayerDatabase().isRegistered(name)) {
            return;
        }

        UUID uuid = XBans.instance.getPlayerDatabase().getPlayerUUID(name);
        IDUtils idUtils = new IDUtils(banID);

        Document document = new Document("player_uuid", uuid.toString())
                .append("player_name", name)
                .append("ban_autor", autor)
                .append("ban_id", idUtils.getIdentifier())
                .append("ban_reason", idUtils.getReason())
                .append("ban_duration", idUtils.getBanDuration())
                .append("ban_expiry", idUtils.getBanExpiry())
                .append("ban_date", System.currentTimeMillis())
                .append("ban_level", idUtils.getBanLevel())
                .append("ban_type", idUtils.getBanType());

        collection.insertOne(document);
    }

    public void unbanPlayer(String name) {
        if (!XBans.instance.getPlayerDatabase().isRegistered(name) || !isBanned(name)) {
            return;
        }

        UUID uuid = XBans.instance.getPlayerDatabase().getPlayerUUID(name);
        Bson filter = Filters.eq("player_uuid", uuid.toString());
        collection.deleteOne(filter);
    }

    public boolean isBanned(String name) {
        if (!XBans.instance.getPlayerDatabase().isRegistered(name)) {
            return false;
        }

        UUID uuid = XBans.instance.getPlayerDatabase().getPlayerUUID(name);
        Bson filter = Filters.eq("player_uuid", uuid.toString());
        return collection.countDocuments(filter) > 0;
    }

    public BanID getBanID(String name) {
        if (!isBanned(name)) {
            return null;
        }

        UUID uuid = XBans.instance.getPlayerDatabase().getPlayerUUID(name);
        Bson filter = Filters.eq("player_uuid", uuid.toString());
        Document document = collection.find(filter).first();

        if (document == null) {
            return null;
        }

        return XBans.instance.getIdManager().getBanID(document.getInteger("ban_id"));
    }

    public String getAutor(String name) {
        if (!isBanned(name)) {
            return null;
        }

        UUID uuid = XBans.instance.getPlayerDatabase().getPlayerUUID(name);
        Bson filter = Filters.eq("player_uuid", uuid.toString());
        Document document = collection.find(filter).first();

        if (document == null) {
            return null;
        }

        return document.getString("ban_autor");
    }

    public Long getBanDate(String name) {

        if (!isBanned(name)) {
            return null;
        }

        UUID uuid = XBans.instance.getPlayerDatabase().getPlayerUUID(name);
        Bson filter = Filters.eq("player_uuid", uuid.toString());
        Document document = collection.find(filter).first();

        if (document == null) {
            return null;
        }

        return document.getLong("ban_date");

    }

    private void checkExpiredBans() {
        long currentTimestamp = System.currentTimeMillis();

        for (Document document : collection.find()) {
            long banExpiry = document.getLong("ban_expiry");

            if (banExpiry != -1 && currentTimestamp >= banExpiry) {
                String name = document.getString("player_name");
                ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), "unban " + name);
            }
        }
    }

    public void check() {
        TaskScheduler scheduler = ProxyServer.getInstance().getScheduler();
        ProxyServer.getInstance().getScheduler().schedule(XBans.instance, this::checkExpiredBans, 0, 30, TimeUnit.SECONDS);
    }

}
