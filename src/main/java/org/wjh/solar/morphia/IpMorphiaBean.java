package org.wjh.solar.morphia;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

public class IpMorphiaBean extends Morphia {

    private Mongo mongo = null;
    private String dbName = null;
    private Datastore ds = null;
    private String username = null;
    private String password = null;

    public IpMorphiaBean() {
        super();
    }

    public void setMapPackage(String packageName) {
        super.mapPackage(packageName, true);
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Datastore getDs() {
        return ds;
    }

    public void setDs(Datastore ds) {
        this.ds = ds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DB getDB() {
        if (dbName == null || dbName.trim().equalsIgnoreCase("")) {
            return null;
        }
        return mongo.getDB(dbName);
    }

    public synchronized Datastore getDataStore() {
        if (this.mongo == null || this.dbName == null) {
            return null;
        }
        if (this.ds == null) {
            if (username == null || password == null) {
                this.ds = createDatastore(mongo, dbName);
            } else {
                this.ds = createDatastore(mongo, dbName, username, password.toCharArray());
            }
            ds.setDefaultWriteConcern(WriteConcern.SAFE);
            return ds;
        } else {
            return this.ds;
        }
    }
}
