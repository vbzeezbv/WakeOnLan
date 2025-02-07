package de.florianisme.wakeonlan.ui.backup.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.florianisme.wakeonlan.persistence.models.Device;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceBackupModel {

    /*
    Aliases for each field have to be kept for some time because backups exported prior to
    version 1.6.5 were created using obfuscated classes. This means that each field was written
    to JSON with its obfuscated name ("a", "b", and so on).
     */

    @JsonProperty("id")
    @JsonAlias("a")
    public int id;

    @JsonProperty("name")
    @JsonAlias("b")
    public String name;

    @JsonProperty("mac_address")
    @JsonAlias("c")
    public String macAddress;

    @JsonProperty("broadcast_address")
    @JsonAlias("d")
    public String broadcastAddress;

    @JsonProperty("port")
    @JsonAlias("e")
    public int port;

    @JsonProperty("status_ip")
    @JsonAlias("f")
    public String statusIp;

    @JsonProperty("secure_on_password")
    @JsonAlias("g")
    public String secureOnPassword;

    public DeviceBackupModel(Device device) {
        this.id = device.id;
        this.name = device.name;
        this.macAddress = device.macAddress;
        this.broadcastAddress = device.broadcastAddress;
        this.port = device.port;
        this.statusIp = device.statusIp;
        this.secureOnPassword = device.secureOnPassword;
    }

    public Device toModel() {
        return new Device(this.id, this.name, this.macAddress, this.broadcastAddress, this.port, this.statusIp, this.secureOnPassword);
    }

    @SuppressWarnings("unused")
    public DeviceBackupModel() {
    }

}
